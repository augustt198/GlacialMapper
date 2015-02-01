package me.hfox.glacial.mongo.database;

import com.mongodb.DBCollection;
import me.hfox.glacial.database.Datastore;
import me.hfox.glacial.mongo.mapping.MongoMapper;
import me.hfox.glacial.mongo.query.MongoQuery;

public class MongoDatastore implements Datastore {

    private MongoDatabase database;
    private String name;
    private DBCollection collection;

    public MongoDatastore(MongoDatabase database, String name) {
        this.database = database;
        this.name = name;
        this.collection = database.getDatabase().getCollection(name);
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoMapper getMapper() {
        return database.getMapper();
    }

    public String getName() {
        return name;
    }

    public DBCollection getCollection() {
        return collection;
    }

    @Override
    public MongoQuery<Object> query() {
        return query(Object.class);
    }

    @Override
    public <E> MongoQuery<E> query(Class<E> cls) {
        return new MongoQuery<>(this, cls);
    }

}
