package me.hfox.glacial.options.name;

import me.hfox.glacial.annotation.Entity;

public interface NameCreator {

    public String create(Class<?> cls);

    public String create(Entity entity, Class<?> cls);

}
