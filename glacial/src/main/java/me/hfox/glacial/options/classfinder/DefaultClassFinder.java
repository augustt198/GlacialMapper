package me.hfox.glacial.options.classfinder;

import me.hfox.glacial.exception.GlacialException;

public class DefaultClassFinder implements ClassFinder {

    @Override
    public Class<?> fromString(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException ex) {
            GlacialException ge = new GlacialException("Could not find a class for the name '" + string + "'");
            ge.initCause(ex);
            ge.setStackTrace(ex.getStackTrace());
            throw ge;
        }
    }

    @Override
    public String toString(Class<?> cls) {
        return cls.getName();
    }

}
