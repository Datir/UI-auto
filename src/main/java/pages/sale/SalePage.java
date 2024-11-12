package pages.sale;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.base.BasePage;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constants.Constants.TimeoutVariable.PAUSE_WAIT;

public class SalePage extends BasePage {

    public SalePage(WebDriver driver) {
        super(driver);
    }

    private class BrandData { //вложенный класс
        public int amount;
        public String brandName;

        BrandData(int amount, String brandName)
        {
            this.amount = amount;
            this.brandName = brandName;
        }

    }

    Actions actions = new Actions(driver);
    public List<Integer> carNamesYearValue = new ArrayList<>(); //массив годов выпуска автомобиля
    public List<String> carBackgoundCSS = new ArrayList<>(); //массив атрибутов back-ground имени автомобиля
    public List<WebElement> carMileage = new ArrayList<>(); //массив элементов полей с пробегом
    List <BrandData> brandData = new ArrayList<>();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));



    //пути к элементам
    By brandAuto = By.xpath("//input[@placeholder=\"Марка\"]");
    By modelAuto = By.xpath("//input[@placeholder=\"Модель\"]");
    By fuel = By.xpath("//button[text()=\"Топливо\"]");
    By unsoldChecker = By.xpath("//label[@for=\"sales__filter_unsold\"]");
    By yearFrom = By.xpath("//button[text()=\"Год от\"]");
    By extendedSearch = By.xpath("//span[text()='Расширенный поиск']");
    By mileage = By.xpath("//input[@placeholder=\"от, км\"]");
    By submit = By.xpath("//button[@data-ftid=\"sales__filter_submit-button\"]");
    By fieldsWithMileage = By.xpath("//div[@data-ftid=\"component_inline-bull-description\"]/span[5]");
    By carNamesFields = By.xpath("//h3");
    By secondPage = By.xpath("//a[text()=\"2\"]");
    By filterByCity = By.xpath("//a[text()='Другой город']");
    By searchCityTextBox = By.xpath("//input[@placeholder=\"поиск города, региона\"]");
    By showEveryBrandBtn = By.xpath("//div[text()='Показать все']");
    By brandDropBoxElems = By.xpath("//div[@class=\"css-1r0zrug e1uu17r80\" and contains(@style, 'height: 35px;')] ");

    public SalePage setFiltres () //фильтрация машин
    {
        actions.click(findElem(brandAuto)).pause(PAUSE_WAIT).sendKeys("Toyota").pause(PAUSE_WAIT).sendKeys(Keys.ENTER).perform(); // заполнение выпадающего списка "Марка"
        actions.pause(PAUSE_WAIT).click(findElem(modelAuto)).pause(PAUSE_WAIT).sendKeys("Harrier").pause(PAUSE_WAIT).sendKeys(Keys.ENTER).perform(); //заполнение выпадающего списка "Модель"
        actions.pause(PAUSE_WAIT).click(findElem(fuel)).pause(PAUSE_WAIT).sendKeys("гибрид").pause(PAUSE_WAIT).sendKeys(Keys.ENTER).perform(); //выбор значения "Гибрид" в выпадающем "Топлива"
        findElem(unsoldChecker).click(); //активация признака "Непроданные"
        findElem(extendedSearch).click(); // Открытие расширенного поиска
        actions.pause(PAUSE_WAIT).click(findElem(yearFrom)).pause(PAUSE_WAIT).sendKeys("2007").pause(PAUSE_WAIT).sendKeys(Keys.ENTER).perform(); // заполнение поля "Год от"
        findElem(mileage).sendKeys("1"); // заполнение пробега
        findElem(submit).click();
        return this;
    }

    public SalePage saveInfoFromPage() { //получение информации о машинах с карточки
        this.carMileage.addAll(driver.findElements(fieldsWithMileage)); // сохранение полей с пробегом
        List<WebElement> carNames = driver.findElements(carNamesFields);
        for (WebElement carName : carNames) {
            String YearText = carName.getText();
            String carNameBackgroundValue = carName.getCssValue("background-size");
            YearText = YearText.substring(YearText.length() - 4);
            int year = Integer.parseInt(YearText);
            this.carNamesYearValue.add(year);
            this.carBackgoundCSS.add(carNameBackgroundValue);
        }
        return this;
    }

    public SalePage goToSecondPage() { //переход на вторую страницу
        findElem(secondPage).click();
        return this;
    }

    public SalePage filterAutoByCity(String cityName) { //фильтрация машин по городу
        findElem(filterByCity).click();
        actions.sendKeys(findElem(searchCityTextBox), cityName)
                .sendKeys(Keys.ENTER)
                .perform();
        return this;
    }

    public SalePage scrollBrandDropBoxAndSaveData() throws InterruptedException { //скролл марок и сохранение информации о марках и кол-ве объявлений

        actions.moveToElement(findElem(showEveryBrandBtn));
        findElem(brandAuto).click();
        Thread.sleep(PAUSE_WAIT);

        //описание переменных
        var elems = findElems(brandDropBoxElems); //получение элементов списка
        int count = elems.size();
        boolean firstNewMark = false; //переменная, для того чтобы понимать, когда будут встречаться новые элементы списка
        boolean duplicate = false; //проверка на встречу дубликата, т.к. по пути могут встретиться избранные марки, которые находятся в начале списка (Toyota, Nissan etc)
        HashSet<String> brandUniqueValues = new HashSet<>(); // объявление HashSet для того, чтобы не сохранять дубликаты

        //скролл элементов и сохранение текста
        while (true)
        {
            //Thread.sleep(100);
            elems = findElems(brandDropBoxElems);

            for(int i = 0; i < elems.size();i++)
            {
                brandUniqueValues.add(elems.get(i).getText());
            }

            actions.sendKeys(Keys.ARROW_DOWN).perform(); //скролл с помощью нажатия стрелочки вниз
            wait.until(ExpectedConditions.invisibilityOf(elems.getLast()));
            if (count < brandUniqueValues.size())
            {
                count = brandUniqueValues.size();
                firstNewMark = true;
                duplicate = false;
            } else if (!duplicate) {
                duplicate = true;
            } else if (firstNewMark) {
                break;
            }
        }


        Pattern patternForBrandsInStock = Pattern.compile("([a-zA-Z]|[а-яА-Я])+ \\(\\d+\\)");
        List<String> brandsInStock = new ArrayList<>();
        List<String> brandList = new ArrayList<>(brandUniqueValues); // заполнение массива марок


        for (String brand : brandList) { //отбрасываются марки без объявлений
            Matcher matcher = patternForBrandsInStock.matcher(brand);
            if (matcher.find()) {
                brandsInStock.add(brand.substring(matcher.start(), matcher.end()));
            }
        }

        for (String stock : brandsInStock) { //сохранение информации о марках в класс BrandAData
            int amount = Integer.parseInt(stock.replaceAll("\\D", "")); //получение кол-ва объявлений
            String brandName = stock.replaceAll("\\d|\\(|\\)|\\s", ""); //получение имя марки
            this.brandData.add(new BrandData(amount, brandName));
        }

        Collections.sort(this.brandData, new Comparator<BrandData>() { //сортировка массива по убыванию
            @Override
            public int compare(BrandData o1, BrandData o2) {
                return o2.amount - o1.amount;
            }
        });
        return this;
    }

    public SalePage printBrandInfo(int amountToPrint) { //печать марки и кол-ва объявлений для этой марки
        if (amountToPrint > brandData.size()){
            System.out.println("The amount of brands is less than " + amountToPrint);
            return this;
        }

        System.out.println("| Фирма | Количество объявлений |");
        for (int i = 0; i < amountToPrint; i++) {
            BrandData brand = brandData.get(i);
            System.out.printf("| %s | %d |\n", brand.brandName, brand.amount);
        }
        return this;
    }







}
