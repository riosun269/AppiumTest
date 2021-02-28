package appium.pages;

import appium.utils.AppiumConstant;
import appium.utils.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class WelcomePage {
    private AndroidDriver androidDriver;

    public WelcomePage () {
    }

    public WelcomePage (AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
        PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
    }

    /** Elements */
    @AndroidFindBy(id = "helloBtn")
    private MobileElement helloButton;

    @AndroidFindBy (id = "welcomeTitle")
    private MobileElement welcomeMessage;

    /** Actions */
    public void clickHelloButton() {
        helloButton.click();
        androidDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /** Questions */
    public String actualHelloButtonText() {
        return helloButton.getText();
    }

    public String actualWelcomeMessage() {
        return welcomeMessage.getText();
    }

    public boolean isDisplayHelloButton() { return helloButton.isDisplayed();}
}
