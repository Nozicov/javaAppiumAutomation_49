import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class First {

  private AppiumDriver driver;

  @Before
  public void setUp() throws Exception{
    DesiredCapabilities capabilities = new DesiredCapabilities();

    capabilities.setCapability("platformName","Android");
    capabilities.setCapability("deviceName","emulator-5554");
    capabilities.setCapability("platformVersion","8.1");
    capabilities.setCapability("automationName","Appium");
    capabilities.setCapability("appPackage","org.wikipedia");
    capabilities.setCapability("appActivity",".main.MainActivity");
    capabilities.setCapability("app","C:\\Users\\Nozicov\\Desktop\\JavaAutomationTest\\GitHub\\javaAppiumAutomation_49\\apks\\org.wikipedia_50393_apps.evozi.com.apk");

    String hostAppium = "http://localhost:4723/wd/hub";
    driver = new AndroidDriver(new URL(hostAppium), capabilities);
  }

  @Test
  public void firstTest(){
    System.out.println("test");
  }

  @After
  public void tearDown(){
    driver.quit();
  }
}
