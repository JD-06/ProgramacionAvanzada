package com.tienda.util;

import java.io.*;
import java.util.*;

public class Config {
    private static Properties props = new Properties();

    static {
        try (InputStream input = new FileInputStream("config/config.txt")) {
            props.load(input);
        } catch (IOException e) {
            System.err.println("Error loading config: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}