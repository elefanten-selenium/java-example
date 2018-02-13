package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class TestCountryList {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
//        driver = new FirefoxDriver();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);

    }

    @Test
    public void TestCountryListSort() {

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        wait.until(titleIs("My Store"));

        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        wait.until(titleIs("Countries | My Store"));


        // получаем список строк со странами
        List<WebElement> countriesList = driver.findElements(By.xpath("//tr[@class='row']"));
        int countriesNumber = countriesList.size();

        // получаем первое название страны
        String currentCountry = countriesList.get(0).findElement(By.xpath("./td[5]")).getText();

        // проходим по каждой строчке списка стран
        for (WebElement countryItem : countriesList) {

            String nextCountry = countryItem.findElement(By.xpath("./td[5]")).getText();

            // выдаём ошибку только в том случае, если названия стран не по алфавиту
            Assert.assertEquals(true, (currentCountry.compareTo(nextCountry)<=0));

            currentCountry = nextCountry;

        }
    }

    @Test // просто для примера, точно заваленный тест, так как коды вообще не сортируются
    public void TestCountryCodeSort() {

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        wait.until(titleIs("My Store"));

        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        wait.until(titleIs("Countries | My Store"));


        // получаем список строк с кодами странам
        List<WebElement> countriesList = driver.findElements(By.xpath("//tr[@class='row']"));
        int countriesNumber = countriesList.size();

        // получаем первый код страны
        String currentCodeCountry = countriesList.get(0).findElement(By.xpath("./td[4]")).getText();

        // проходим по каждой строчке списка кодов стран
        for (WebElement countryItem : countriesList) {

            String nextCodeCountry = countryItem.findElement(By.xpath("./td[4]")).getText();

            // выдаём ошибку только в том случае, если коды стран не по алфавиту
            Assert.assertEquals(true, (currentCodeCountry.compareTo(nextCodeCountry)<=0));

            currentCodeCountry = nextCodeCountry;

        }
    }

    @Test
    public void TestCountryZonesSort() {

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        wait.until(titleIs("My Store"));

        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        wait.until(titleIs("Countries | My Store"));


        // получаем список строк таблицы с заполненными зонами
        // этот список надо формировать каждый раз заново при возвращении на страницу
//        List<WebElement> countriesList = driver.findElements(By.xpath("//tr[@class='row']//td[6][not(contains(text(),'0'))]"));
//        int countriesNumber = countriesList.size();
        int countriesNumber = driver.findElements(By.xpath("//tr[@class='row']//td[6][not(contains(text(),'0'))]")).size();

        // проходим по каждой строчке стран с геозонами
//        for (WebElement countryItem : countriesList) {
        for (int i = 0; i < countriesNumber ; i++) {

            driver.findElements(By.xpath("//tr[@class='row']//td[6][not(contains(text(),'0'))]")).get(i).findElement(By.xpath("../td[5]/a")).click();

            // получаем список строк с зонами
            List<WebElement> zonesList = driver.findElements(By.xpath("//*[@id='remove-zone']/../.."));
            //   //*[@id='table-zones']/tbody/tr/td/a[@id='remove-zone']/../..
            int zonesNumber = zonesList.size();

            // получаем первое название зоны
            String currentZone = zonesList.get(0).findElement(By.xpath("./td[3]")).getText();

            // проходим по каждой строчке списка зон
            for (WebElement zoneItem : zonesList) {

                String nextZone = zoneItem.findElement(By.xpath("./td[3]")).getText();

                // выдаём ошибку только в том случае, если названия стран не по алфавиту
                Assert.assertEquals(true, (currentZone.compareTo(nextZone)<=0));

                currentZone = nextZone;

            }

            driver.navigate().back();
        }

    }



    @Test
    public void TestGeoZonesSort() {

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        wait.until(titleIs("My Store"));

        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        wait.until(titleIs("Geo Zones | My Store"));


        // получаем список строк таблицы с заполненными зонами
        // этот список надо формировать каждый раз заново при возвращении на страницу
        int countriesNumber = driver.findElements(By.xpath("//*[@class='fa fa-pencil']/../../..")).size();
        //  //tr[@class='row']//td[4][not(contains(text(),'0'))]

        // проходим по каждой строчке стран с геозонами
//        for (WebElement countryItem : countriesList) {
        for (int i = 0; i < countriesNumber ; i++) {

            driver.findElements(By.xpath("//*[@class='fa fa-pencil']/../../../td[3]/a")).get(i).click();

            // получаем список выпадающих списков с зонами
            List<WebElement> zonesList = driver.findElements(By.xpath("//*[contains(@name,'zone_code')]"));
            //   //*[@id='table-zones']/tbody/tr/td/a[@id='remove-zone']/../..
            int zonesNumber = zonesList.size();

            // получаем первое название зоны
            Select Selector = new Select(zonesList.get(0));
            String currentZone = Selector.getFirstSelectedOption().getText();

            // проходим по каждой строчке списка зон
            for (WebElement zoneItem : zonesList) {

                Selector = new Select(zoneItem);
                String nextZone = Selector.getFirstSelectedOption().getText();

                // выдаём ошибку только в том случае, если названия стран не по алфавиту
                // ОДНАКО! В задаче не сказано, что при этом ID зон может быть не отсортировано
                //         сейчас там сортировки по id Уву таковой нет
                Assert.assertEquals(true, (currentZone.compareTo(nextZone)<=0));

                currentZone = nextZone;

            }

            driver.navigate().back();
        }

    }




    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
