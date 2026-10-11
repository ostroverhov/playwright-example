package com.example.framework.listeners;

import com.example.framework.base.BaseTest;
import com.microsoft.playwright.Page;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        if (!(result.getInstance() instanceof BaseTest baseTest)) {
            return;
        }
        Page page = baseTest.page();
        if (page == null) {
            return;
        }
        try {
            Path directory = Path.of("target", "screenshots");
            Files.createDirectories(directory);
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(directory.resolve(result.getName() + ".png"))
                    .setFullPage(true));
        } catch (Exception ignored) {
            // A failed screenshot must not hide the original test error.
        }
    }
}
