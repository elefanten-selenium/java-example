package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.java2d.loops.XORComposite;

import java.util.List;

import static jdk.nashorn.internal.objects.NativeString.concat;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class TestStickers {

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
    public void TestAllPageAdminLitecart() {

        driver.get("http://localhost/litecart/en/");
//        driver.findElement(By.name("username")).sendKeys("admin");
//        driver.findElement(By.name("password")).sendKeys("admin");
//        driver.findElement(By.name("login")).click();

//        sendKeys(Keys.chord(Keys.CONTROL, Keys.C));

        wait.until(titleIs("Online Store | My Store"));

        // получаем список карточек товара на главной странице
        List<WebElement> goodsList = driver.findElements(By.xpath("//li[contains(@class,'product')]"));
//        int countGoods = driver.findElements(By.xpath("//*[@id='app-']")).size();
        int countGoods = goodsList.size();
        int countSticker = 0;
    //     #app-
    //     #app- > a
    //     //*[@id="app-"]/a
    //      By.id("app-")

        // проходим по каждой карточе товара и выясняем, какие стикеры есть
//        for (int i = 0; i < countElement; i++) {
        for (WebElement goodsItem : goodsList) {

            int numberStickers = goodsItem.findElements(By.xpath("//div[@class='sticker']")).size();

            // выдаём ошибку в том случае, если стикер не 1
            // "Сценарий должен проверять, что у каждого товара имеется ровно один стикер."
//            Assert.assertTrue( "Стикер должен быть 1", (numberStickers == 1) );

            // с этим условием делали отладку, на странице стикер или один или ни одного
            Assert.assertTrue( "Стикеров больше одного:" +numberStickers, numberStickers < 1 );


        }


    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
