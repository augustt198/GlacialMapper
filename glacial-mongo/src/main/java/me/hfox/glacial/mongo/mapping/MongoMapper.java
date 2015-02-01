package me.hfox.glacial.mongo.mapping;

import com.mongodb.DBObject;
import me.hfox.glacial.exception.GlacialException;
import me.hfox.glacial.mongo.connection.MongoConnection;
import org.bson.types.ObjectId;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class MongoMapper {

    private MongoConnection connection;
    private Map<ObjectId, Object> cache;

    public MongoMapper(MongoConnection connection) {
        this.connection = connection;
        this.cache = new HashMap<>();
    }

    public Map<ObjectId, Object> getCache() {
        return cache;
    }

    public Object getObject(ObjectId id) {
        return cache.get(id);
    }

    @SuppressWarnings("unchecked")
    public <E> E getObject(Class<?> cls, ObjectId id) {
        Object object = getObject(id);
        if (object == null) {
            return null;
        }

        return (E) object;
    }

    public Object fromDBObject(DBObject object) {
        Object classNameObj = object.get("class_name");
        if (classNameObj == null) {
            throw new GlacialException("No class was supplied and no class_name was supplied");
        }

        if (!(classNameObj instanceof String)) {
            throw new GlacialException("class_name supplied was not a string");
        }

        String className = (String) classNameObj;
        return fromDBObject(connection.getOptions().getClassFinder().fromString(className), object);
    }

    public <E> E fromDBObject(Class<E> cls, DBObject object) {
        if (cls == null) {
            throw new IllegalArgumentException("Supplied class cannot be null");
        }

        E result = create(cls);
        return result;
    }

    public <E> E create(Class<E> cls) {
        try {
            return cls.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            GlacialException ge = new GlacialException("Failed to create empty " + cls.getSimpleName() + " object");
            ge.initCause(ex);
            ge.setStackTrace(ex.getStackTrace());
            throw ge;
        }
    }

}
