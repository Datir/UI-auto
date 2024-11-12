package tests.sale_page;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.base.BaseTest;

import java.time.Duration;

import static constants.Constants.TimeoutVariable.PAUSE_WAIT;
import static constants.Constants.Urls.LOGIN_PAGE;
import static constants.Constants.Urls.SALE_AUTO_PAGE;

public class SalePageTests extends BaseTest {

    @Test (priority = 1)
    public void checkCarFiltres () throws InterruptedException {
        basePage.open(SALE_AUTO_PAGE);
        salePage
                .setFiltres()
                .saveInfoFromPage()
                .goToSecondPage()
                .saveInfoFromPage();

        Assert.assertEquals(salePage.carMileage.size(), salePage.carNamesYearValue.size(), "Exists cars without mileage");  //Сравнение кол-во полей, которые содержат пробег с кол-вом карточек (т.к. пробег необязательное поле);

        for (int i = 0; i < salePage.carNamesYearValue.size(); i++) {
            Assert.assertTrue(salePage.carNamesYearValue.get(i) >= 2007, "Year is lower than 2007"); // Год больше или равен 2007
            Assert.assertEquals(salePage.carBackgoundCSS.get(i), "0px 2px", "Car was sold"); //Имя машины не перечеркнуто
        }


    }

    @Test (priority = 3)
    public void addAutoToFavourite() throws InterruptedException { //добавление карточки автомобиля в избранное
        basePage.open(LOGIN_PAGE);
        loginPage.login();
        basePage.open(SALE_AUTO_PAGE);


        //Добавление первой карточки в избранное
        var addToFavouriteButton = driver.findElement(By.xpath("//span[@class=\"css-1a8kwyo e157qrb61\"]"));
        addToFavouriteButton.click();
        Thread.sleep(PAUSE_WAIT);

        //Проверка всплывающего окна
        var popUp = driver.findElement(By.xpath("//div[text()='Объявление добавлено в']"));
        Assert.assertEquals(popUp.getText(),"Объявление добавлено в избранное.\n" +
                "Вы получите уведомление при изменении цены.", "Auto hasn't added as favourite or pop-up window is broken");
    }

    @Test (priority = 2)
    public void parseBrandOfPrimorskyRegion() throws InterruptedException { //
        basePage.open(SALE_AUTO_PAGE);
        salePage
                .filterAutoByCity("Приморский край")//Фильтрация объявлений по Приморскому краю
                .scrollBrandDropBoxAndSaveData() //сохранение информации о моделях и кол-ве объявлений
                .printBrandInfo(20); //вывод в консоль информации


    }
    @Test (priority = 4)
    public void FufelTest() {
        Assert.assertTrue(false);
    }


}
