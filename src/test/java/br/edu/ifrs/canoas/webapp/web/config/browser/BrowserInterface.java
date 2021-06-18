package br.edu.ifrs.canoas.webapp.web.config.browser;

import br.edu.ifrs.canoas.webapp.web.config.ConfigException;
import org.openqa.selenium.Capabilities;

import java.util.Map;

public interface BrowserInterface {
    Chrome chrome = new Chrome();
    Firefox firefox = new Firefox();

    Map<String, BrowserInterface> browsers = Map.ofEntries(
            Map.entry("chrome", chrome),
            Map.entry("firefox", firefox)
    );

    static BrowserInterface getBrowser(String browserName) {
        return browsers.getOrDefault(browserName, chrome);
    }

    Capabilities getCapabilities();

    default String getDriverExecutableName() {
        throw new ConfigException("Not supported");
    }

    default String getDriverSystemPropertyName() {
        throw new ConfigException("Not supported");
    }

    default void manage() {
        throw new ConfigException("Not supported");
    }

    void setHeadless(boolean headless);
}
