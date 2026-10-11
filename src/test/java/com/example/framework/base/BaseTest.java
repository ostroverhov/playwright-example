package com.example.framework.base;

import com.example.framework.config.ConfigLoader;
import com.example.framework.config.FrameworkConfig;
import com.example.framework.core.BrowserManager;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public abstract class BaseTest {

    private static BrowserManager browserManager;
    private static FrameworkConfig config;

    private BrowserContext context;
    private Page page;

    @BeforeSuite
    public void startBrowser() {
        config = ConfigLoader.load();
        browserManager = new BrowserManager();
        browserManager.start(config);
    }

    @AfterSuite(alwaysRun = true)
    public void stopBrowser() {
        if (browserManager != null) {
            browserManager.close();
        }
    }

    @BeforeMethod
    public void openContext() {
        context = browserManager.browser().newContext(new Browser.NewContextOptions()
                .setLocale(config.getLocale())
                .setViewportSize(1280, 800));
        context.setDefaultTimeout(config.getTimeoutMs());
        page = context.newPage();
    }

    @AfterMethod(alwaysRun = true)
    public void closeContext() {
        if (context != null) {
            context.close();
        }
    }

    public Page page() {
        return page;
    }

    protected FrameworkConfig config() {
        return config;
    }
}
