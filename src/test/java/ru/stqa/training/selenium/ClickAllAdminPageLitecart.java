package ru.stqa.training.selenium;

import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class ClickAllAdminPageLitecart {

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

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

//        sendKeys(Keys.chord(Keys.CONTROL, Keys.C));

        wait.until(titleIs("My Store"));

//        List<WebElement> menuElements = driver.findElements(By.id("app-"));
        int countElement = driver.findElements(By.xpath("//*[@id='app-']")).size();
    //     #app-
    //     #app- > a
    //     //*[@id="app-"]/a
    //      By.id("app-")


        for (int i = 0; i < countElement; i++) {
//        for (WebElement menuItem : menuElements) {
// так и не понял, почему однажды найденный список элементов пропадает из DOM,
// пришлось переписать на постоянный перепоиск элемента :(

            WebElement menuItem = driver.findElements(By.xpath("//*[@id='app-']")).get(i);
//            menuItem.findElement(By.xpath("./a")).click();
            menuItem.click();
//            https://stackoverflow.com/questions/34537344/getting-cannot-focus-element-in-chrome-and-edge-using-java-selenium
//                Actions actions = new Actions(driver);
//                actions.moveToElement(menuItem);
//                actions.click();
//                actions.build().perform();

//            Assert.assertEquals(true, driver.findElement(By.tagName("h1")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
//            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

//            List<WebElement> ancorList = menuItem.findElements(By.xpath("./a"));
            // аналогичным образом ищем подменю в открытом меню, ищем каждый раз после клика
            // (и помним, что корневой элемент уже потерян)
            int countSubMenuElement = driver.findElements(By.xpath("//*[@id='app-']//*[contains(@id,'doc')]")).size();
            //  #app- > ul
            //  #doc-languages > a > span.name
            //  #doc-storage_encoding > a > span.name
            //  //*[@id="doc-jobs"]
            //  //*[@id="doc-logotype"]/a/span //*[@class="name"][3]
            //  //li[@id='app-']and[@class='selected']/li[@id='doc-*']
            //  //*[@id="doc-languages"]
            //  ./li#[contains(@id,'doc-')]


            for (int j = 0; j < countSubMenuElement; j++) {
//            for (WebElement ancorItem : ancorList) {
//                ancorItem.click();
//                Assert.assertEquals(true, driver.findElement(By.tagName("h1")));
//                }
                WebElement subMenuItem = driver.findElements(By.xpath("//*[@id='app-']//*[contains(@id,'doc')]")).get(j);
                subMenuItem.click();
                wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
            }
        }


    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
