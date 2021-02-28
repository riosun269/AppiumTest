package selenium.page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.utils.TestUltils;

public class HomePage {
    WebDriver driver;
    TestUltils testUltils;
    @FindBy(name = "q")
    private WebElement searchBox;
    @FindBy(id = "logo")
    private WebElement logo;

    public HomePage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean logoDisplayed () {
        return logo.isDisplayed();
    }

    public void inputSearchAndNext (String inputText) {
        testUltils = new TestUltils(driver);
        searchBox.sendKeys(inputText);
        searchBox.sendKeys(Keys.ENTER);
    }
}
