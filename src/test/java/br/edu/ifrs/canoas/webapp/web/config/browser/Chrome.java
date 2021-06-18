package br.edu.ifrs.canoas.webapp.web.config.browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;

class Chrome implements BrowserInterface {

    private boolean isHeadless;
    @Override
    public Capabilities getCapabilities() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");

        if (this.isHeadless) {
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-gpu");
            options.addArguments("--headless");
            options.addArguments("--no-proxy-server");
        }

        return options;
    }

    @Override
    public String getDriverExecutableName() {
        return "chromedriver";
    }

    @Override
    public String getDriverSystemPropertyName() {
        return "webdriver.chrome.driver";
    }

    @Override
    public String toString() {
        return "Chrome";
    }

    @Override
    public void manage() {
        WebDriverManager.chromedriver().setup();
    }

    public void setHeadless(boolean remote) {
        this.isHeadless = remote;
    }
}

