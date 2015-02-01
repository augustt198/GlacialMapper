package me.hfox.glacial.mongo.mapping;

import com.mongodb.DBObject;
import me.hfox.glacial.GlacialDefaults;
import me.hfox.glacial.annotation.Entity;
import me.hfox.glacial.annotation.field.Id;
import me.hfox.glacial.exception.GlacialException;
import me.hfox.glacial.mongo.connection.MongoConnection;
import me.hfox.glacial.mongo.database.MongoDatabase;
import me.hfox.glacial.util.ReflectionUtils;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
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
    public <E> E getObject(Class<E> cls, ObjectId id) {
        Object object = getObject(id);
        if (object == null) {
            return null;
        }

        return (E) object;
    }

    public Object fromDBObject(MongoDatabase database, DBObject object) {
        Object classNameObj = object.get("class_name");
        if (classNameObj == null) {
            throw new GlacialException("No class was supplied and no class_name was supplied");
        }

        if (!(classNameObj instanceof String)) {
            throw new GlacialException("class_name supplied was not a string");
        }

        String className = (String) classNameObj;
        return fromDBObject(database, connection.getOptions().getClassFinder().fromString(className), object);
    }

    public <E> E fromDBObject(MongoDatabase database, Class<E> cls, DBObject object) {
        if (cls == null) {
            throw new IllegalArgumentException("Supplied class cannot be null");
        }

        E result = create(cls);
        Entity entity = GlacialDefaults.getEntity(cls, true);

        if (entity.cache() && object.get("_id") != null) {
            cache.put((ObjectId) object.get("_id"), result);
        }

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

    public ObjectId getId(Object object) {
        try {
            List<Field> fields = ReflectionUtils.getFields(object.getClass());
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getAnnotation(Id.class) != null) {
                    return (ObjectId) field.get(object);
                }
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public <E> E update(MongoDatabase database, E object) {
        ObjectId id = getId(object);
        if (id == null) {
            throw new GlacialException("Could not find id field for " + object.getClass().getSimpleName());
        }

        return update(object, database.getDatastore(object.getClass()).query().field("_id").equal(id).getDB());
    }

    public <E> E update(E object, DBObject document) {
        if (document == null) {
            return object;
        }

        // TODO: Update fields
        return null;
    }

}
