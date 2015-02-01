package me.hfox.glacial.database;

import me.hfox.glacial.query.GlacialQuery;

public interface GlacialDatabase {

    public String getName();

    public Datastore getDatastore(String name);

    public Datastore getDatastore(Class<?> cls);

    public GlacialQuery<Object> query(String datastore);

    public <E> GlacialQuery<E> query(Class<E> cls);

    public <E> GlacialQuery<E> query(String datastore, Class<E> cls);

}
