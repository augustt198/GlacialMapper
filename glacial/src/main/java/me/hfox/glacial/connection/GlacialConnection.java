package me.hfox.glacial.connection;

import me.hfox.glacial.database.GlacialDatabase;
import me.hfox.glacial.options.GlacialOptions;

public interface GlacialConnection {

    public GlacialOptions getOptions();

    public GlacialDatabase getDatabase(String name);

    public boolean isOpen();

}
