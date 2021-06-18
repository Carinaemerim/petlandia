package br.edu.ifrs.canoas.webapp.web.config;


import java.util.Optional;

public class SeleniumBrowserConfigProperties {
    public static Boolean isHeadless() {
        return getBooleanProperty("isHeadless", false);
    }

    public static String getBrowserName() {
        return getStringProperty("browserName", "chrome");
    }

    public static String getPageUrl(int port) {
        return getStringProperty("pageUrl", "http://localhost") + ":" + port;
    }

    private static String getStringProperty(String propertyName, String propertyValue) {
        return Optional.ofNullable(System.getProperty(propertyName))
                .orElse(propertyValue);
    }

    private static Boolean getBooleanProperty(String propertyName, Boolean configuredValue) {
        if (System.getProperty(propertyName) == null) {
            return configuredValue;
        }
        return Boolean.valueOf(System.getProperty(propertyName));
    }
}

