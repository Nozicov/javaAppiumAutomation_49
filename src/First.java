import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

import static java.lang.Thread.sleep;

public class First{

  private AppiumDriver driver;
  private int timeout = 5;

  @Before
  public void setUp() throws Exception{
    DesiredCapabilities capabilities = new DesiredCapabilities();

    capabilities.setCapability("platformName","Android");
    capabilities.setCapability("deviceName","emulator-5554");
    capabilities.setCapability("platformVersion","8.1");
    capabilities.setCapability("automationName","Appium");
    capabilities.setCapability("appPackage","org.wikipedia");
    capabilities.setCapability("appActivity",".main.MainActivity");
    capabilities.setCapability("app","C:\\Users\\Nozicov\\Desktop\\JavaAutomationTest\\GitHub\\javaAppiumAutomation_49\\apks\\org.wikipedia.apk");

    String hostAppium = "http://localhost:4723/wd/hub";
    driver = new AndroidDriver(new URL(hostAppium), capabilities);
  }

  @Test
  public void testSearchJavaResult(){

    waitAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Not Found Search",
            5
    );

    waitAndSendKeys(
            By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
            "Java",
            "Not Found Search Input",
            5
    );

    waitPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Not result search: Object-oriented programming language",
            15
    );
  }

  @Test
  public void testCancelSearch(){

    waitAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Not Found Search",
            5
    );

    waitAndSendKeys(
            By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
            "Java",
            "Not Found Search Input",
            5
    );

    waitAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            "Not clear serarch input",
            5
    );

    waitAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Not Found element X in Search",
            5
    );

    waitNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "Element X in Page",
            5
    );
  }

  @Test
  public void testCompareArticleTitle(){

    waitAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Not Found Search",
            5
    );

    waitAndSendKeys(
            By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
            "Java",
            "Not Found Search Input",
            5
    );

    waitAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Not Found Search",
            5
    );

    WebElement title_element  = waitPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "",
            15
    );
    String arcicle_title = title_element.getAttribute("text");
    Assert.assertEquals(
            "Title not correct",
            "Java (programming language)",
            arcicle_title
    );

  }

  @After
  public void tearDown(){
    driver.quit();
  }

  private WebElement waitPresent(By by, String error_message, int timeout){
    WebDriverWait wait = new WebDriverWait(driver, timeout);
    wait.withMessage(error_message + "\n");
    return wait.until(ExpectedConditions.presenceOfElementLocated(by));
  }

  private WebElement waitPresent(By by, String error_message){
    return waitPresent(by, error_message, timeout);
  }


  private WebElement waitAndClick(By by, String error_message, int timeout){
    WebElement element = waitPresent(by, error_message, timeout);
    element.click();
    return element;
  }

  private WebElement waitAndSendKeys(By by, String text, String error_message, int timeout){
    WebElement element = waitPresent(by, error_message, timeout);
    element.sendKeys(text);
    return element;
  }

  private boolean waitNotPresent(By by, String error_message, int timeout){
    WebDriverWait wait = new WebDriverWait(driver, timeout);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(by)
    );
  }

  private WebElement waitAndClear(By by, String error_message, int timeout){
    WebElement element = waitPresent(by, error_message, timeout);
    element.clear();
    return element;
  }

}
