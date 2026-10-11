package com.example.framework.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class GoogleHomePage  {

    private final Page page;
    private final Locator logo;
    private final Locator searchInput;
    private final Locator searchButton;
    private final Locator feelingLuckyButton;
    private final Locator acceptCookiesButton;

    public GoogleHomePage(Page page) {
        this.page = page;
        this.logo = page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("Google"));
        this.searchInput = page.locator("textarea[name='q'], input[name='q']");
        this.searchButton = page.locator("[name='btnK']:visible").first();
        this.feelingLuckyButton = page.locator("[name='btnI']").first();
        this.acceptCookiesButton = page.locator("#L2AGLb");
    }

    public void open(String baseUrl) {
        page.navigate(baseUrl);
        acceptCookiesIfShown();
    }

    public void focusSearch() {
        searchInput.click();
    }

    public Locator logo() {
        return logo;
    }

    public Locator searchInput() {
        return searchInput;
    }

    public Locator searchButton() {
        return searchButton;
    }

    public Locator feelingLuckyButton() {
        return feelingLuckyButton;
    }

    private void acceptCookiesIfShown() {
        if (acceptCookiesButton.count() > 0 && acceptCookiesButton.isVisible()) {
            acceptCookiesButton.click();
        }
    }
}
