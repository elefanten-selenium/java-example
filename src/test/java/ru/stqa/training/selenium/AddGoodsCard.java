package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.security.Key;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;


public class AddGoodsCard {

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
    public void AddGoodsCard() {
//     Сделайте сценарий для добавления нового товара (продукта)
//  в учебном приложении litecart (в админке).
//     Для добавления товара нужно открыть меню Catalog,
//  в правом верхнем углу нажать кнопку "Add New Product",
//  заполнить поля с информацией о товаре и сохранить.
//     Достаточно заполнить только информацию на вкладках General,
//  Information и Prices.
//  Скидки (Campains) на вкладке Prices можно не добавлять.
//     Переключение между вкладками происходит не мгновенно,
//  поэтому после переключения можно сделать небольшую паузу
//  (о том, как делать более правильные ожидания,
//  будет рассказано в следующих занятиях).
//     Картинку с изображением товара нужно уложить в репозиторий
//  вместе с кодом.
//  При этом указывать в коде полный абсолютный путь к файлу плохо,
//  на другой машине работать не будет.
//  Надо средствами языка программирования преобразовать относительный
//  путь в абсолютный.
//     После сохранения товара нужно убедиться,
//  что он появился в каталоге (в админке).
//  Клиентскую часть магазина можно не проверять.

// src/test/resources/tortoise1.jpg

        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
//        sendKeys(Keys.chord(Keys.CONTROL, Keys.C));
        wait.until(titleIs("Catalog | My Store"));


//     Для добавления товара нужно открыть меню Catalog,
//  в правом верхнем углу нажать кнопку "Add New Product",
        driver.findElement(By.xpath("//a[.=' Add New Product']")).click();
//        wait.until(titleIs("\n" +
//                "  \n" +
//                "  \n" +
//                " Add New Product | My Store"));

//  заполнить поля с информацией о товаре и сохранить.
//     Достаточно заполнить только информацию на вкладках General,
//  Information и Prices.

        String nameGood = "Tortoise";

        String baseTabs = "//*[@class='tabs']/";
        String baseXpath = baseTabs.concat("..//strong[.='");

        // General
        driver.findElement(By.xpath(baseTabs+"ul/*[1]")).click();
        wait.until(visibilityOfElementLocated(By.xpath(baseXpath.concat("Status']"))));

        driver.findElement(By.xpath(baseXpath.concat("Status']/..//label[.=' Enabled']"))).click();
        driver.findElement(By.xpath(baseXpath.concat("Name']/..//input"))).sendKeys(nameGood);
        driver.findElement(By.xpath(baseXpath.concat("Code']/..//input"))).sendKeys("trt"+"0001");
        // Categories (заполнено)
        // Dafault Category (заполнено)
        driver.findElement(By.xpath(baseXpath.concat("Product Groups']/..//*[.='Unisex']/..//input"))).click();

        driver.findElement(By.xpath(baseXpath.concat("Quantity']/..//input"))).sendKeys(Keys.CONTROL+"A");
            driver.findElement(By.xpath(baseXpath.concat("Quantity']/..//input"))).sendKeys("5");
        // Quantity Unit (заполнено)
        // Delivery Status (заполнено)
        // Sold Out Status (заполнено)

//     Картинку с изображением товара нужно уложить в репозиторий
//  вместе с кодом.
//  При этом указывать в коде полный абсолютный путь к файлу плохо,
//  на другой машине работать не будет.
//  Надо средствами языка программирования преобразовать относительный
//  путь в абсолютный.
        String dirPath = new File("").getAbsolutePath();
        String fSep=(String)System.getProperty("file.separator");
        String imagePath=dirPath.concat(fSep).concat("src").concat(fSep).concat("test").concat(fSep).concat("resources").concat(fSep).concat("tortoise1.jpg");
        driver.findElement(By.xpath(baseXpath.concat("Upload Images']/..//input"))).sendKeys(imagePath);

//  \src\test\resources
//        String filename = "...";
//        Image image = ImageIO.read(new File(filename));

        driver.findElement(By.xpath(baseXpath.concat("Date Valid From']/..//input"))).sendKeys(Keys.HOME + "01.02.2018");
        driver.findElement(By.xpath(baseXpath.concat("Date Valid To']/..//input"))).sendKeys(Keys.HOME + "01.04.2018");


        // Information
        driver.findElement(By.xpath(baseTabs+"ul/*[2]")).click();
        wait.until(visibilityOfElementLocated(By.xpath(baseXpath.concat("Manufacturer']"))));

        Select sel = new Select(driver.findElement(By.xpath(baseXpath.concat("Manufacturer']/..//select"))));
        sel.selectByVisibleText("ACME Corp.");
        //   //*[@class='tabs']/..//strong[.='Manufacturer']/..//option
//        Select(driver.findElement(By.xpath(baseXpath.concat("Supplier']/..//select")))).selectByVisibleText("-- Select --");
        driver.findElement(By.xpath(baseXpath.concat("Keywords']/..//input"))).sendKeys("tortoise, Tortilla");
        driver.findElement(By.xpath(baseXpath.concat("Short Description']/..//input"))).sendKeys("small Tortilla");
        driver.findElement(By.xpath(baseXpath.concat("Description']/..//*[@class='trumbowyg-editor']"))).sendKeys(Keys.HOME+"a small figurine of a turtle");
        driver.findElement(By.xpath(baseXpath.concat("Head Title']/..//input"))).sendKeys("Tortoise");
        driver.findElement(By.xpath(baseXpath.concat("Meta Description']/..//input"))).sendKeys("gift");



//  Скидки (Campains) на вкладке Prices можно не добавлять.
//     Переключение между вкладками происходит не мгновенно,
//  поэтому после переключения можно сделать небольшую паузу
        // Prices
        driver.findElement(By.xpath(baseTabs+"ul/*[4]")).click();
        wait.until(visibilityOfElementLocated(By.xpath(baseXpath.concat("Purchase Price']"))));

        // Purchase Price
        driver.findElement(By.xpath(baseXpath.concat("Purchase Price']/..//input"))).clear();
                driver.findElement(By.xpath(baseXpath.concat("Purchase Price']/..//input"))).sendKeys("35,99");
        sel = new Select(driver.findElement(By.xpath(baseXpath.concat("Purchase Price']/..//select"))));
        sel.selectByValue("EUR");

        // Tax Class
        // Price & Price Incl. Tax (?)
//        String baseXpath = baseTabs.concat("..//strong[.='");
        baseXpath = baseTabs.concat("..//th[.='");
        driver.findElement(By.xpath(baseXpath.concat("Price']/../..//input[@name='prices[USD]']"))).sendKeys("50,25");
//        driver.findElement(By.xpath(baseXpath.concat("Price']/../..//input[@name='gross_prices[USD]']"))).sendKeys("58,00");
        driver.findElement(By.xpath(baseXpath.concat("Price']/../..//input[@name='prices[EUR]']"))).sendKeys("48,05");
//        driver.findElement(By.xpath(baseXpath.concat("Price']/../..//input[@name='gross_prices[EUR]']"))).sendKeys("51,00");




        // сохраняем изменения
        driver.findElement(By.name("save")).click();

//     После сохранения товара нужно убедиться,
//  что он появился в каталоге (в админке).

        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog");
        wait.until(visibilityOfElementLocated(By.xpath("//*[@id='content']//th[.='Name']")));

        Assert.assertTrue("Товар добавлен", driver.findElements(By.xpath("//*[@id='content']//a[.='".concat(nameGood).concat("']"))).size()>0);



    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
