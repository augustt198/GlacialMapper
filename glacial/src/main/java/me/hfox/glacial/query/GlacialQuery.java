package me.hfox.glacial.query;

import java.util.List;

public interface GlacialQuery<E> {

    public GlacialFieldQuery<E> field(String name);

    public GlacialQuery<E> order(String... fields);

    public E get();

    public List<E> asList();

}
