package org.playwright.eightydays.base;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import org.playwright.eightydays.factory.BrowserFactory;
import org.playwright.eightydays.pageobjects.HomePage;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

  private final BrowserFactory bf = new BrowserFactory();
  protected Properties properties = bf.initializeConfigProperties();
  protected Page page;
  protected HomePage homePage;
  private String browserName;

  @BeforeMethod
  @Parameters({"browserName", "headless"})
  public void setUp(@Optional("chrome") String browserName
      , @Optional("false") String headless) throws IllegalArgumentException {

    this.browserName = browserName;
    page = bf.initializeBrowser(browserName, headless);
    page.navigate(properties.getProperty("BASE_URL").trim());
    homePage = new HomePage(page);
  }

  @AfterMethod
  public void tearDown(ITestResult result) throws IOException {
    if (result.getStatus() == ITestResult.FAILURE) {
      LocalDateTime currentDateTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
      String formattedDateTime = formatter.format(currentDateTime);

      String resultName = result.getName() + "_" + browserName + "_" + formattedDateTime;
      Path screenshotPath = Paths.get("./target/screenshot/" + resultName + ".png");

      page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
      attachScreenshotToAllureReport(resultName, screenshotPath);
    }

    page.context().browser().close();
  }

  @Attachment(type = "image/png")
  private void attachScreenshotToAllureReport(String resultName
      , Path screenshotPath) throws IOException {

    Allure.addAttachment(resultName, new ByteArrayInputStream(Files.readAllBytes(screenshotPath)));
  }
}
