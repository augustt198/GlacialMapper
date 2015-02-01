package me.hfox.glacial.mongo.connection;

import com.mongodb.MongoClient;
import me.hfox.glacial.connection.GlacialConnection;
import me.hfox.glacial.database.GlacialDatabase;
import me.hfox.glacial.mongo.mapping.MongoMapper;
import me.hfox.glacial.mongo.database.MongoDatabase;
import me.hfox.glacial.options.GlacialOptions;

public class MongoConnection implements GlacialConnection {

    private GlacialOptions options;
    private MongoClient client;
    private MongoMapper mapper;

    public MongoConnection(MongoClient client) {
        this.options = new GlacialOptions();
        this.client = client;
        this.mapper = new MongoMapper(this);
    }

    @Override
    public GlacialOptions getOptions() {
        return options;
    }

    public MongoClient getClient() {
        return client;
    }

    public MongoMapper getMapper() {
        return mapper;
    }

    @Override
    public boolean isOpen() {
        return client.getConnector().isOpen();
    }

    @Override
    public GlacialDatabase getDatabase(String name) {
        return new MongoDatabase(this, name);
    }

}
