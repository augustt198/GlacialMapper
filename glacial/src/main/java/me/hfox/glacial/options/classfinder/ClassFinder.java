package me.hfox.glacial.options.classfinder;

public interface ClassFinder {

    public Class<?> fromString(String string);

    public String toString(Class<?> cls);

}
