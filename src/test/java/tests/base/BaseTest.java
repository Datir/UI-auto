package tests.base;

import common.CommonActions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import pages.base.BasePage;
import pages.login.LoginPage;
import pages.sale.SalePage;

import static common.Config.HOLD_BROWSER_OPEN;

public class BaseTest {
    protected WebDriver driver = CommonActions.createDriver();
    protected BasePage basePage = new BasePage(driver);
    protected SalePage salePage = new SalePage(driver);
    protected LoginPage loginPage = new LoginPage(driver);

    @AfterSuite (alwaysRun = true)
    public void close(){
        if (!HOLD_BROWSER_OPEN){
            driver.quit();
        }

    }
}
