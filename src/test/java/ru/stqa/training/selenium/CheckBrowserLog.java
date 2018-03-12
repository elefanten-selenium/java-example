package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class CheckBrowserLog {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
//        driver = new FirefoxDriver();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
//        WebDriverWait wait = new WebDriverWait(driver, 10/*seconds*/);
//        WebElement element = wait.until(presenceOfElementLocated(By.name("q")));
//        WebElement element2 = wait.until((WebDriver d) -> d.findElement(By.name("q")));


    }

    @Test
    public void CheckBrowserLog() {
//Задание 17. Проверьте отсутствие сообщений в логе браузера
//Сделайте сценарий, который проверяет,
// не появляются ли в логе браузера сообщения при открытии
// страниц в учебном приложении,
// а именно -- страниц товаров в каталоге в административной панели.
//
//Сценарий должен состоять из следующих частей:
//
//1) зайти в админку
//2) открыть каталог, категорию, которая содержит товары
// (страница http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1)
//3) последовательно открывать страницы товаров и проверять,
// не появляются ли в логе браузера сообщения (любого уровня)

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

//        sendKeys(Keys.chord(Keys.CONTROL, Keys.C));

        wait.until(titleIs("My Store"));

        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");

        // получаем список карточек товара
        String xpathPrgh = "//img/../a[contains(@href,'edit_product')]";
        List<WebElement> goodsList = driver.findElements(By.xpath(xpathPrgh));
        int countGoods = goodsList.size();

//3) последовательно открывать страницы товаров и проверять,
// не появляются ли в логе браузера сообщения (любого уровня)
        for (int i = 0; i < countGoods; i++) {
//        for (WebElement goodsItem : goodsList) {

            goodsList = driver.findElements(By.xpath(xpathPrgh));
//            WebElement goodsItem = goodsList.get(i+1);
//            goodsItem.click();
            goodsList.get(i).click();

            for (LogEntry logEntry : driver.manage().logs().get("browser").getAll()) {
                System.out.println(logEntry);
                Assert.assertNotNull("Есть сообщение лога", logEntry);
            }

//            Assert.assertTrue("Есть сообщение лога",(logEntry > 0));

            driver.navigate().back();
        }


    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
