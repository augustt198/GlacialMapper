package me.hfox.glacial;

import me.hfox.glacial.connection.GlacialConnection;
import me.hfox.glacial.options.GlacialOptions;

public class GlacialMapper {

    private GlacialConnection connection;
    private GlacialOptions options;

    public GlacialMapper(GlacialConnection connection) {
        this.connection = connection;
        this.options = new GlacialOptions();
    }

    public GlacialConnection getConnection() {
        return connection;
    }

    public GlacialOptions getOptions() {
        return options;
    }

}
