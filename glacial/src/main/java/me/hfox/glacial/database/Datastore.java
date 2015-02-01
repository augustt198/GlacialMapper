package me.hfox.glacial.database;

import me.hfox.glacial.query.GlacialQuery;

public interface Datastore {

    public String getName();

    public GlacialQuery<Object> query();

    public <E> GlacialQuery<E> query(Class<E> cls);

}
