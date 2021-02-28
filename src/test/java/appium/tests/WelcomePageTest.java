package appium.tests;

import appium.BaseDriverAppium;
import appium.pages.WelcomePage;
import appium.utils.AppiumConstant;


import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.testng.Assert;
import org.testng.annotations.Test;

@CucumberOptions(
        strict = true,
        monochrome = true,
        features = "classpath:features",
        plugin = {"pretty"}
)
public class WelcomePageTest extends appium.BaseDriverAppium {
    private WelcomePage welcomePage;

    @Given("^I navigate to login page$")
    public void setUpPage() {
        welcomePage = new WelcomePage(androidDriver);
    }

    @Given("^I verify Hello button$")
    public void test01_verifyHelloButton() {
        welcomePage = new WelcomePage(androidDriver);
        Assert.assertTrue(welcomePage.isDisplayHelloButton(), "Check Hello button is displayed: ");
    }

    @Then("^I see welcome message correctly$")
    public void test02_verifyWelcomeMessage() {
        welcomePage = new WelcomePage(androidDriver);
        welcomePage.clickHelloButton();
        Assert.assertEquals(welcomePage.actualWelcomeMessage(), AppiumConstant.WELCOME_MESSAGE);
    }
}
