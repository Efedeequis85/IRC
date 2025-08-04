package com.da.irc.Servicios;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró application.properties en el classpath");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar application.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
