package pages.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static constants.Constants.TimeoutVariable.PAUSE_WAIT;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver = driver;
    }

    public void open(String url){
        driver.get(url);
    }

    public WebElement findElem (By locator){
        return driver.findElement(locator);
    }

    public List<WebElement> findElems (By locator){
        return driver.findElements(locator);
    }



}
