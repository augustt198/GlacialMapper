package me.hfox.glacial.options;

import me.hfox.glacial.options.classfinder.ClassFinder;
import me.hfox.glacial.options.classfinder.DefaultClassFinder;
import me.hfox.glacial.options.name.DefaultNameCreator;
import me.hfox.glacial.options.name.NameCreator;

public class GlacialOptions {

    private NameCreator nameCreator = new DefaultNameCreator();
    private ClassFinder classFinder = new DefaultClassFinder();

    public NameCreator getNameCreator() {
        return nameCreator;
    }

    public void setNameCreator(NameCreator nameCreator) {
        this.nameCreator = nameCreator;
    }

    public ClassFinder getClassFinder() {
        return classFinder;
    }

    public void setClassFinder(ClassFinder classFinder) {
        this.classFinder = classFinder;
    }

}
