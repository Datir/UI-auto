package pages.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.base.BasePage;

import static constants.Constants.TimeoutVariable.PAUSE_WAIT;
import static constants.Constants.Urls.LOGIN_PAGE;
import static constants.Constants.UserInfo.LOGIN;
import static constants.Constants.UserInfo.PASSWORD;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    //пути до элементов
    By login = By.xpath("//input[@name=\"sign\"]");
    By password = By.xpath("//input[@name=\"password\"]");
    By submit = By.xpath("//button[@type=\"submit\"]");

    public LoginPage login ()  { //авторизация пользователя
        open(LOGIN_PAGE);        findElem(login).sendKeys(LOGIN);
        findElem(password).sendKeys(PASSWORD);
        findElem(submit).click();
        return this;
    }

}
