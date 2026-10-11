package com.example.framework.tests;

import com.example.framework.base.BaseTest;
import com.example.framework.pages.GoogleHomePage;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class GoogleHomeTest extends BaseTest {

    @Test
    public void homePageShowsLogoSearchFieldAndButtons() {
        GoogleHomePage google = new GoogleHomePage(page());

        google.open(config().getBaseUrl());

        assertThat(google.logo()).isVisible();
        assertThat(google.searchInput()).isVisible();
        assertThat(google.searchInput()).isEditable();

        google.focusSearch();

        assertThat(google.searchButton()).isVisible();
        assertThat(google.feelingLuckyButton()).isAttached();
    }
}
