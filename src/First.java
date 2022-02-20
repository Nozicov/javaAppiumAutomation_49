import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class First{

  private AppiumDriver driver;
  private int timeout = 5;
  private String searchWord = "Java";
  private String searchArticleTitle = "Java (programming language)";
  private String folderFavorites = "My favorites article";

  @Before
  public void setUp() throws Exception{
    DesiredCapabilities capabilities = new DesiredCapabilities();

    capabilities.setCapability("platformName","Android");
    capabilities.setCapability("deviceName","emulator-5554");
    capabilities.setCapability("platformVersion","8.1");
    capabilities.setCapability("automationName","Appium");
    capabilities.setCapability("orientation", "PORTRAIT");
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
            searchWord,
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
            searchWord,
            "Not Found Search Input",
            5
    );

    waitAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + searchArticleTitle + "']"),
            "Cannot find '" + searchWord + "' article in search",
            5
    );

    WebElement title_element  =  waitPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );
    String arcicle_title = title_element.getAttribute("text");
    Assert.assertEquals(
            "Title not correct",
            "Java (programming language)",
            arcicle_title
    );

  }

  @Test
  public void testValidTextSearch(){

    assertElementHasText(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Search Wikipedia",
            "Main: input search text not correct",
            5
    );

    waitAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Not Found Search: " + "Search Wikipedia" ,
            5
    );

    assertElementHasText(
            By.id("org.wikipedia:id/search_src_text"),
            "Search…",
            "Inside: input search text not correct",
            5
    );

  }

  @Test
  public void testSearchResultsList(){

    waitAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Not Found Search",
            5
    );

    waitAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            searchWord,
            "Not Found Search Input",
            5
    );

    List resultSearch = findElements(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
            "Not results Search",
            5
    );
    Assert.assertFalse("Not results (not correct)",resultSearch.size() == 0);

    boolean resultContainsSearch = containsTextForElementList(
            resultSearch,
            searchWord
    );
    Assert.assertTrue("Not all search results contain " + searchWord, resultContainsSearch);

    waitAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Not Found element X in Search",
            5
    );

    waitNotPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
            "Yes results (not correct)",
            5
    );

    assertElementHasText(
            By.id("org.wikipedia:id/search_empty_message"),
            "Search and read the free encyclopedia in your language",
            "Not description main search page",
            5
    );


  }

  @Test
  public void testSwipeArticle(){

    waitAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input for Search",
            5
    );

    waitAndSendKeys(
            By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
            searchWord,
            "Cannot find input for Search",
            5
    );

    waitAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + searchArticleTitle + "']"),
            "Cannot find '" + searchWord + "' article in search",
            5
    );

    waitPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

    swipeUpToFindElement(
            By.id("org.wikipedia:id/page_external_link"),
            "Cannot find the end of the article",
            60
    );

  }

  @Test
  public void testSaveFirstArticleToMyList() throws InterruptedException {
    waitAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input for Search",
            5
    );

    waitAndSendKeys(
            By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
            searchWord,
            "Cannot find input for Search",
            5
    );

    waitAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + searchArticleTitle + "']"),
            "Cannot find '" + searchWord + "' article in search",
            5
    );

    waitPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

    waitAndClick(
            By.xpath("//*[@content-desc='More options']"),
            "Cannot find button to open article options",
            5
    );

    Thread.sleep(1000);

    waitAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            10
    );

    waitAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'GOT IT'",
            10
    );

    waitAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of articles folder",
            5
    );

    waitAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            folderFavorites,
            "Cannot put text into articles input",
            5
    );

    waitAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot press OK button",
            5
    );

    waitAndClick(
            By.xpath("//*[@content-desc='Navigate up']"),
            "Cannot close article, cannot Navigate up (X)",
            5
    );

    waitAndClick(
            By.xpath("//*[@content-desc='My lists']"),
            "Cannot find navigation button to My lists",
            5
    );

    waitAndClick(
            By.xpath("//*[@text='" + folderFavorites + "']"),
            "Cannot find created folder My lists",
            5
    );

    waitPresent(
            By.xpath("//*[@text='" + searchArticleTitle + "']"),
            "Cannot find article in created My lists",
            5
    );

    swipeElementToLeft(
            By.xpath("//*[@text='" + searchArticleTitle + "']"),
            "Cannot find article '" + searchArticleTitle + "'"
    );

    waitNotPresent(
            By.xpath("//*[@text='" + searchArticleTitle + "']"),
            "Cannot delete saved article '" + searchArticleTitle + "'",
            5
    );

  }

  @Test
  public void testAmountOfNotEmptySearch(){
    String searchWord = "Almaty";

    waitAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input for Search",
            5
    );

    waitAndSendKeys(
            By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
            searchWord,
            "Cannot find input for Search",
            5
    );

    String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    waitPresent(
            By.xpath(searchResultLocator),
            "Cannot find anything by the request " + searchWord,
            15
    );

    int amountOfSearchResult = getAmountOfElements(
            By.xpath(searchResultLocator)
    );
    Assert.assertTrue(
            "We found too few result",
            amountOfSearchResult > 0
    );

  }

  @Test
  public void testAmountOfEmptySearch(){
    String searchWord = "qwqwqwqwqwqwq";

    waitAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input for Search",
            5
    );

    waitAndSendKeys(
            By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
            searchWord,
            "Cannot find input for Search",
            5
    );

    String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    String emptyResultLabel = "//*[@text='No results found']";

    waitPresent(
            By.xpath(emptyResultLabel),
            "Cannot find empty result label by the request " + searchWord,
            15
    );

    assertElementNotPresent(
            By.xpath(searchResultLocator),
            "We`ve found some results by request " + searchWord
    );

  }

  @Test
  public void testChangeScreenOrientationOnSearchResults(){

    waitAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Not Found Search",
            5
    );

    waitAndSendKeys(
            By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
            searchWord,
            "Not Found Search Input",
            5
    );

    waitAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + searchArticleTitle + "']"),
            "Cannot find '" + searchWord + "' article in search",
            15
    );

    String title_before_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Canot find title of articale",
            15
    );

    driver.rotate(ScreenOrientation.LANDSCAPE);

    String title_after_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Canot find title of articale",
            15
    );

    Assert.assertEquals(
            "Artical title have been changed after screen rotation",
            title_before_rotation,
            title_after_rotation
    );

    driver.rotate(ScreenOrientation.PORTRAIT);

    String title_after_second_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Canot find title of articale",
            15
    );

    Assert.assertEquals(
            "Artical title have been changed after screen rotation",
            title_before_rotation,
            title_after_second_rotation
    );

  }

  @Test
  public void testCheckSearchArticleInBackground(){

    waitAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Not Found Search",
            5
    );

    waitAndSendKeys(
            By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
            searchWord,
            "Not Found Search Input",
            5
    );

    waitPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + searchArticleTitle + "']"),
            "Cannot find '" + searchWord + "' article after returning from background",
            15
    );

    driver.runAppInBackground(5);

    waitPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + searchArticleTitle + "']"),
            "Cannot find '" + searchWord + "' article in search",
            15
    );

  }

  @Test
  public void testSaveAndRemoveArticlesToMyList() throws InterruptedException {
    String articleTitleOne = searchArticleTitle;
    String articleTitleTwo = "JavaScript";

    searchAndOpenArticle(searchWord, articleTitleOne);

    waitAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'GOT IT'",
            10
    );

    waitAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of articles folder",
            5
    );

    waitAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            folderFavorites,
            "Cannot put text into articles input",
            5
    );

    waitAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot press OK button",
            5
    );

    waitAndClick(
            By.xpath("//*[@content-desc='Navigate up']"),
            "Cannot close article, cannot Navigate up (X)",
            5
    );

    searchAndOpenArticle(searchWord, articleTitleTwo);

    waitAndClick(
            By.xpath("//*[@text='" + folderFavorites + "']"),
            "Cannot find folder favorites '" + folderFavorites + "'",
            5
    );

    waitAndClick(
            By.xpath("//*[@content-desc='Navigate up']"),
            "Cannot close article, cannot Navigate up (X)",
            5
    );

    waitAndClick(
            By.xpath("//*[@content-desc='My lists']"),
            "Cannot find navigation button to My lists",
            5
    );

    waitAndClick(
            By.xpath("//*[@text='" + folderFavorites + "']"),
            "Cannot find created folder My lists",
            5
    );

    waitPresent(
            By.xpath("//*[@text='" + articleTitleOne + "']"),
            "Cannot find one article in created My lists '" + articleTitleOne + "'",
            5
    );

    waitPresent(
            By.xpath("//*[@text='" + articleTitleTwo + "']"),
            "Cannot find two article in created My lists '" + articleTitleTwo + "'",
            5
    );

    swipeElementToLeft(
            By.xpath("//*[@text='" + articleTitleTwo + "']"),
            "Cannot find article '" + articleTitleTwo + "'"
    );

    waitNotPresent(
            By.xpath("//*[@text='" + articleTitleTwo + "']"),
            "Cannot delete saved article '" + articleTitleTwo + "'",
            5
    );

    waitPresent(
            By.xpath("//*[@text='" + articleTitleOne + "']"),
            "Cannot find one article in created My lists '" + articleTitleOne + "'",
            5
    );

    waitAndClick(
            By.xpath("//*[@text='" + articleTitleOne + "']"),
            "Cannot find folder favorites '" + articleTitleOne + "'",
            5
    );

    String title_full_article = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Canot find title of articale",
            15
    );

    Assert.assertEquals(
            "Tittle not equals",
            articleTitleOne,
            title_full_article
    );

  }

  @Test
  public void testPresentTitleArticle(){

    waitAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Not Found Search",
            5
    );

    waitAndSendKeys(
            By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
            searchWord,
            "Not Found Search Input",
            5
    );

    waitAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + searchArticleTitle + "']"),
            "Cannot find '" + searchWord + "' article in search",
            5
    );

    String locator_title = "org.wikipedia:id/view_page_title_text";

    assertElementPresent(
            By.id(locator_title),
            "We`ve not found article title  " + searchArticleTitle
    );

  }

  @After
  public void tearDown(){
    ScreenOrientation orientation = driver.getOrientation();
    if (!orientation.toString().equals("PORTRAIT")){
      driver.rotate(ScreenOrientation.PORTRAIT);
    }
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

  private WebElement assertElementHasText(By by, String text, String error_message, int timeout){
    WebElement element = waitPresent(by, error_message, timeout);
    String textFact = element.getAttribute("text");
    Assert.assertEquals(error_message, text, textFact);
    return element;
  }

  private List<WebElement> findElements(By by, String error_message, int timeout){
    waitPresent(by, error_message, timeout);
    List<WebElement> listOfElements = driver.findElements(by);
    return listOfElements;
  }

  private boolean containsTextForElementList(List list, String text){
    int n = 0;
    for (int i = 0; i < list.size(); i ++ ){
      WebElement element = (WebElement) list.get(i);
      String title = element.getText();
      if(title.contains(text)){
        n++;
      }
    }
    return n == list.size();
  }

  private void swipeUp(int timeOfSwipe){
    TouchAction action = new TouchAction(driver);

    Dimension size = driver.manage().window().getSize();
    int x = size.width / 2;
    int start_y = (int) (size.height * 0.8);
    int end_y = (int) (size.height * 0.2);

    action
            .press(x, start_y)
            .waitAction(timeOfSwipe)
            .moveTo(x, end_y)
            .release()
            .perform();
  }

  private void swipeUpQuick(){
    swipeUp(300);
  }

  private void swipeUpToFindElement(By by, String error_message, int maxSwipes){
    int n = 0;
    while (driver.findElements(by).size() == 0){
      if (n > maxSwipes) {
        waitPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
        return;
      }
      swipeUpQuick();
      n++;
    }
  }

  private void swipeElementToLeft(By by, String error_message){
    WebElement element = waitPresent(by,error_message, 10);

    int left_x = element.getLocation().getX();
    int right_x = left_x + element.getSize().getWidth();

    int top_y = element.getLocation().getY();
    int bottom_y = top_y + element.getSize().getHeight();

    int middle_y = (top_y + bottom_y) / 2;

    TouchAction action = new TouchAction(driver);
    action
            .press(right_x, middle_y)
            .waitAction(300)
            .moveTo(left_x, middle_y)
            .release()
            .perform();

  }

  private int getAmountOfElements(By by){
    List elements = driver.findElements(by);
    return  elements.size();
  }

  private void assertElementNotPresent(By by, String error_message){
    int amountOfElements = getAmountOfElements(by);
    if (amountOfElements > 0){
      String defaul_message = "An element '" + by.toString() + "' supposed to be not present";
      throw new AssertionError(defaul_message + " " + error_message);
    }
  }

  private void assertElementPresent(By by, String error_message){
    int amountOfElements = getAmountOfElements(by);
    if (amountOfElements == 0){
      String defaul_message = "An element '" + by.toString() + "' supposed to be present";
      throw new AssertionError(defaul_message + " " + error_message);
    }
  }

  private String waitForElementAndGetAttribute(By by, String attributes, String error_message, int timeoutInSeconds){
    WebElement element = waitPresent(by, error_message, timeoutInSeconds);
    return element.getAttribute(attributes);
  }

  private void searchAndOpenArticle(String searchWord, String ArticleTitle) throws InterruptedException {
    waitAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input for Search",
            5
    );

    waitAndSendKeys(
            By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"),
            searchWord,
            "Cannot find input for Search",
            5
    );

    waitAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + ArticleTitle + "']"),
            "Cannot find '" + searchWord + "' article in search",
            5
    );

    waitPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );

    waitAndClick(
            By.xpath("//*[@content-desc='More options']"),
            "Cannot find button to open article options",
            5
    );

    Thread.sleep(1000);

    waitAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            10
    );
  }

}
