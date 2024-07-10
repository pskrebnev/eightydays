package org.playwright.eightydays.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {

  private final Page page;

  public HomePage(Page page) {
    this.page = page;
  }

  public Locator getTitle() {
    return page.locator("title");
  }
}
