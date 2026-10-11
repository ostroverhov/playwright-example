package com.example.framework.core;

import com.example.framework.config.FrameworkConfig;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import java.util.HashMap;
import java.util.Map;

public final class BrowserManager implements AutoCloseable {

    private Playwright playwright;
    private Browser browser;

    public void start(FrameworkConfig config) {
        playwright = Playwright.create(new Playwright.CreateOptions().setEnv(driverEnvironment()));
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(config.isHeadless());
        if (config.getChannel() != null && !config.getChannel().isBlank()) {
            options.setChannel(config.getChannel());
        }
        if (config.getArgs() != null && !config.getArgs().isEmpty()) {
            options.setArgs(config.getArgs());
        }
        browser = browserType(config.getBrowser()).launch(options);
    }

    public Browser browser() {
        if (browser == null) {
            throw new IllegalStateException("Browser is not started");
        }
        return browser;
    }

    @Override
    public void close() {
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }

    private static Map<String, String> driverEnvironment() {
        Map<String, String> env = new HashMap<>(System.getenv());
        env.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1");
        return env;
    }

    private BrowserType browserType(String browserName) {
        return switch (browserName.toLowerCase()) {
            case "firefox" -> playwright.firefox();
            case "webkit" -> playwright.webkit();
            case "chromium" -> playwright.chromium();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
        };
    }
}
