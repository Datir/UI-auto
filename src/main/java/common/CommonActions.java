package common;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.time.Duration;

import static common.Config.PLATFORM_AND_BROWSER;
import static constants.Constants.TimeoutVariable.IMPLICIT_WAIT;

public class CommonActions {
    public static WebDriver createDriver(){
        WebDriver driver = null;

        switch (PLATFORM_AND_BROWSER){
            case "win_chrome":
                driver = new ChromeDriver();
                break;
            default:
                Assert.fail("Incorrect platform or browser name");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
        return driver;
    }

}

