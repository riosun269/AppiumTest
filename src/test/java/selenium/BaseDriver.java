package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import selenium.utils.TestConstant;

import java.util.concurrent.TimeUnit;

public abstract class BaseDriver {
    protected WebDriver driver;


    protected void setup(String url){
        System.setProperty(TestConstant.CHROME_DRIVER, TestConstant.CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(url);
    }



}
