package me.hfox.glacial.annotation;

import me.hfox.glacial.GlacialDefaults;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {

    public String value() default GlacialDefaults.DEFAULT_COLLECTION_NAME;

}
