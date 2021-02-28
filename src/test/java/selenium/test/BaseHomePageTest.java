package selenium.test;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import selenium.BaseDriver;
import selenium.utils.TestConstant;

public abstract class BaseHomePageTest extends BaseDriver {

    /*@BeforeTest
    public void homePageSetup () {
        super.setup(TestConstant.URL);
    }

    @AfterTest
    public void closeWeb () {
        driver.close();
    }*/
}
