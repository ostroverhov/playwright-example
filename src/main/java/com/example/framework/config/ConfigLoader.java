package com.example.framework.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigLoader {

    public static FrameworkConfig load() {
        Properties properties = readProperties();
        FrameworkConfig config = new FrameworkConfig();
        config.setBaseUrl(properties.getProperty("baseUrl"));
        config.setBrowser(properties.getProperty("browser"));
        config.setChannel(properties.getProperty("channel", ""));
        config.setHeadless(Boolean.parseBoolean(properties.getProperty("headless", "true")));
        config.setTimeoutMs(Double.parseDouble(properties.getProperty("timeoutMs", "30000")));
        config.setLocale(properties.getProperty("locale"));
        config.setArgs(splitArgs(properties.getProperty("args", "")));
        return config;
    }

    private static Properties readProperties() {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties is not on the classpath");
            }
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new UncheckedIOException("Failed to read config.properties", exception);
        }
    }

    private static List<String> splitArgs(String rawArgs) {
        return Arrays.stream(rawArgs.split(","))
                .map(String::trim)
                .filter(arg -> !arg.isEmpty())
                .toList();
    }
}
