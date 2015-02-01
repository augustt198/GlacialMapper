package me.hfox.glacial.mongo.query;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import me.hfox.glacial.exception.IllegalQueryException;
import me.hfox.glacial.mongo.database.MongoDatastore;
import me.hfox.glacial.query.GlacialQuery;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MongoQuery<E> implements GlacialQuery<E>, Iterator<E> {

    private MongoDatastore datastore;

    private Class<E> cls;
    private Map<String, List<MongoFieldQuery<E>>> fields;
    private String[] order;

    private DBCursor cursor;

    public MongoQuery(MongoDatastore datastore, Class<E> cls) {
        this.datastore = datastore;
        this.fields = new HashMap<>();
        this.cls = cls;
    }

    @Override
    public MongoFieldQuery<E> field(String name) {
        MongoFieldQuery<E> query = new MongoFieldQuery<>(this);

        List<MongoFieldQuery<E>> queries = fields.get(name);
        if (queries == null) {
            queries = new ArrayList<>();
            fields.put(name, queries);
        }

        queries.add(query);
        if (cursor != null) {
            cursor = null;
        }

        return query;
    }

    @Override
    public MongoQuery<E> order(String... fields) {
        this.order = fields;
        if (cursor != null) {
            cursor = null;
        }

        return this;
    }

    @Override
    public E get() {
        DBCursor cursor = cursor();

        DBObject result = null;
        if (cursor.hasNext()) {
            result = cursor.next();
        }

        return create(result);
    }

    @Override
    public List<E> asList() {
        DBCursor cursor = cursor();

        List<DBObject> objects = new ArrayList<>();
        while (cursor.hasNext()) {
            objects.add(cursor.next());
        }

        List<E> results = new ArrayList<>();
        for (DBObject object : objects) {
            results.add(create(object));
        }

        return results;
    }

    public DBCursor cursor() {
        BasicDBObject query = new BasicDBObject();
        for (String name : fields.keySet()) {
            List<MongoFieldQuery<E>> queries = fields.get(name);
            for (MongoFieldQuery<E> field : queries) {
                Object object = query.get(name);
                if (field.getObject() == null) {
                    if (object != null && object instanceof DBObject) {
                        throw new IllegalQueryException("Equals operators are standalone");
                    }

                    query.put(name, field.getObject());
                } else {
                    if (object != null && !(object instanceof DBObject)) {
                        throw new IllegalQueryException("Equals operators are standalone");
                    }

                    DBObject operators = object != null ? (DBObject) object : new BasicDBObject();
                    operators.put(field.getOperator(), field.getObject());
                }
            }
        }

        DBCursor cursor = datastore.getCollection().find(query);
        if (order != null) {
            BasicDBObject sort = new BasicDBObject();
            for (String string : order) {
                boolean desc = false;
                if (string.startsWith("-")) {
                    desc = true;
                    string = string.substring(1);
                }

                sort.put(string, desc ? -1 : 1);
            }

            cursor.sort(sort);
        }

        return (this.cursor = cursor);
    }

    @Override
    public boolean hasNext() {
        if (cursor == null) {
            cursor();
        }

        return cursor.hasNext();
    }

    @Override
    public E next() {
        if (cursor == null) {
            cursor();
        }

        return create(cursor.next());
    }

    public E create(DBObject object) {
        E result = null;
        try {
            result = datastore.getMapper().getObject(cls, (ObjectId) object.get("_id"));
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
