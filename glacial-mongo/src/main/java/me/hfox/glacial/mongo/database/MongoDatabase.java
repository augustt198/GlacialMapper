package me.hfox.glacial.mongo.database;

import com.mongodb.DB;
import me.hfox.glacial.database.GlacialDatabase;
import me.hfox.glacial.mongo.connection.MongoConnection;
import me.hfox.glacial.mongo.mapping.MongoMapper;
import me.hfox.glacial.mongo.query.MongoQuery;
import me.hfox.glacial.query.GlacialQuery;

import java.util.HashMap;
import java.util.Map;

public class MongoDatabase implements GlacialDatabase {

    private MongoConnection connection;

    private String name;
    private DB database;
    private Map<String, MongoDatastore> datastores;

    public MongoDatabase(MongoConnection connection, String name) {
        this.connection = connection;
        this.name = name;
        this.database = connection.getClient().getDB(name);
        this.datastores = new HashMap<>();
    }

    public MongoConnection getConnection() {
        return connection;
    }

    public MongoMapper getMapper() {
        return connection.getMapper();
    }

    @Override
    public String getName() {
        return name;
    }

    public DB getDatabase() {
        return database;
    }

    @Override
    public MongoDatastore getDatastore(String name) {
        MongoDatastore datastore = datastores.get(name);
        if (datastore == null) {
            datastore = new MongoDatastore(this, name);
            datastores.put(name, datastore);
        }

        return datastore;
    }

    @Override
    public MongoDatastore getDatastore(Class<?> cls) {
        return getDatastore(connection.getOptions().getNameCreator().create(cls));
    }

    @Override
    public MongoQuery<Object> query(String datastore) {
        return getDatastore(datastore).query();
    }

    @Override
    public <E> MongoQuery<E> query(Class<E> cls) {
        return getDatastore(cls).query(cls);
    }

    @Override
    public <E> MongoQuery<E> query(String datastore, Class<E> cls) {
        return getDatastore(datastore).query(cls);
    }

}
