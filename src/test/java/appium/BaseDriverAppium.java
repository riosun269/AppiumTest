package appium;

import cucumber.api.java.Before;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static appium.utils.AppiumConstant.*;
import static appium.utils.AppiumConstant.URL_APPIUM;
import static appium.utils.Utils.getPropertiesValue;


public abstract class BaseDriverAppium extends AbstractTestNGCucumberTests {
    protected static AndroidDriver<MobileElement> androidDriver;
    private AppiumDriverLocalService service;

    @BeforeMethod
    protected void startServer() throws MalformedURLException {
        /*service = AppiumDriverLocalService.buildDefaultService();
        service.start();*/

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANDROID);
        //cap.setCapability(CapabilityType.APPLICATION_NAME, getPropertiesValue(APP_NAME));
        //cap.setCapability(MobileCapabilityType.NO_RESET, false);
        //cap.setCapability(MobileCapabilityType.UDID, getPropertiesValue(DEVICE_ID));
        //cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, getPropertiesValue(PLATFORM_VERSION));
        //cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, getPropertiesValue(AUTOMATION_NAME));
        cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, getPropertiesValue(APP_PACKAGE_NAME) + getPropertiesValue(APP_ACTIVITY));
        cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, getPropertiesValue(APP_PACKAGE_NAME));

        androidDriver = new AndroidDriver<MobileElement>(new URL(URL_APPIUM), cap);
        androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Before
    public abstract void setUpPage();

    @AfterMethod
    protected void stopServer() {
        androidDriver.quit();
        /*service.stop();*/
    }

}
