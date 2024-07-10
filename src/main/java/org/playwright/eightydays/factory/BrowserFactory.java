package org.playwright.eightydays.factory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BrowserFactory {

  private Playwright playwright;
  private Browser browser;
  private Properties properties;

  public Page initializeBrowser(String browserName, String headless)
      throws IllegalArgumentException {
    boolean isHeadless = Boolean.parseBoolean(headless);

    playwright = Playwright.create();

    switch (browserName.toLowerCase()) {
      case "chromium":
        browser = playwright.chromium().launch(new LaunchOptions()
            .setHeadless(isHeadless));
        break;

      case "chrome":
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
            .setChannel("chrome").setHeadless(isHeadless));
        break;

      case "firefox":
        browser = playwright.firefox().launch(new BrowserType.LaunchOptions()
            .setHeadless(isHeadless));
        break;

      case "webkit":
        browser = playwright.webkit().launch(new BrowserType.LaunchOptions()
            .setHeadless(isHeadless));
        break;

      default:
        throw new IllegalArgumentException("Provide a valid browser name:"
            + " (chrome, firefox, webkit or chromium).");
    }

    BrowserContext browserContext = browser.newContext();
    return browserContext.newPage();
  }

  public Properties initializeConfigProperties() {
    String configPath = "config/config.properties";
    Properties properties = new Properties();

    try (InputStream inputStream = getClass().getClassLoader()
        .getResourceAsStream(configPath)) {
      if (inputStream != null) {
        properties.load(inputStream);
      } else {
        throw new FileNotFoundException("Resource not found: " + configPath);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to load properties file: " + configPath, e);
    }

    return properties;
  }
}
