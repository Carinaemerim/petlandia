package br.edu.ifrs.canoas.webapp.web.config.browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxOptions;

class Firefox implements BrowserInterface {

    private boolean isHeadless;

    @Override
    public Capabilities getCapabilities() {
        FirefoxOptions options = new FirefoxOptions();

        if (isHeadless) {
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-gpu");
            options.addArguments("--headless");
            options.addArguments("--no-proxy-server");
            // Window size is bugged in firefox driver
        }

        return options;
    }

    @Override
    public String getDriverExecutableName() {
        return "geckodriver";
    }

    @Override
    public String getDriverSystemPropertyName() {
        return "webdriver.gecko.driver";
    }

    @Override
    public String toString() {
        return "Firefox";
    }

    @Override
    public void manage() {
        WebDriverManager.firefoxdriver().setup();
    }

    public void setHeadless(boolean remote) {
        this.isHeadless = remote;
    }
}

