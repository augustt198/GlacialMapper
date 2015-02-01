package me.hfox.glacial.query;

public interface GlacialFieldQuery<E> {

    public GlacialQuery<E> equal(Object object);

    public GlacialQuery<E> notEqual(Object object);

    public GlacialQuery<E> greaterThan(Object object);

    public GlacialQuery<E> greaterThanOrEqual(Object object);

    public GlacialQuery<E> lessThan(Object object);

    public GlacialQuery<E> lessThanOrEqual(Object object);

    public GlacialQuery<E> exists();

    public GlacialQuery<E> doesNotExist();

}
