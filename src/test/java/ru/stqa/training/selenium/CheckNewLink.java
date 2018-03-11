package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class CheckNewLink {

    private WebDriver driver;
    private WebDriverWait wait;
    private String baseLink;
    private int countLink;
    private String originalWindow;
    private String childHandle;
    private String linH;
    private String linT;


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
    public void CheckBasket() {
// Задание 14. Проверьте, что ссылки открываются в новом окне
// Сделайте сценарий, который проверяет,
// что ссылки на странице редактирования страны открываются в новом окне.
//                Сценарий должен состоять из следующих частей:
//  1) зайти в админку
//  2) открыть пункт меню Countries
//     (или страницу http://localhost/litecart/admin/?app=countries&doc=countries)
//  3) открыть на редактирование какую-нибудь страну или
//     начать создание новой
//  4) возле некоторых полей есть ссылки с иконкой в виде квадратика
//     со стрелкой -- они ведут на внешние страницы и открываются в новом окне,
//     именно это и нужно проверить.
// Конечно, можно просто убедиться в том, что у ссылки есть атрибут
// target="_blank".
// Но в этом упражнении требуется именно кликнуть по ссылке,
// чтобы она открылась в новом окне, потом переключиться в новое окно,
// закрыть его, вернуться обратно, и повторить эти действия для всех
// таких ссылок.
// Не забудьте, что новое окно открывается не мгновенно,
// поэтому требуется ожидание открытия окна.




        // переходм на страницу добавления страны
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
//        sendKeys(Keys.chord(Keys.CONTROL, Keys.C));
        wait.until(titleIs("Countries | My Store"));

//  3) открыть на редактирование какую-нибудь страну или
//     начать создание новой

        driver.findElement(By.xpath("//*[.=' Add New Country']")).click();
//        wait.until(ExpectedConditions.urlContains("edit_country"))

//  4) возле некоторых полей есть ссылки с иконкой в виде квадратика
//     со стрелкой -- они ведут на внешние страницы и открываются в новом окне,
//     именно это и нужно проверить.

        baseLink = "//i[contains(@class,'external-link')]//..//..//a[contains(@href,'http')]";
        List<WebElement> linkList = driver.findElements(By.xpath(baseLink));
        countLink = linkList.size();
//i[contains(@class,'external-link')]//..//..//a
//i[contains(@class,'external-link')]

// Ожидания
// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
// WebDriverWait wait = new WebDriverWait(driver, 10/*seconds*/);  // <-- ждём элемент 10 секунд
// WebElement element2 = wait.until((WebDriver d) -> d.findElement(By.name("q")));
// WebElement element = wait.until(presenceOfElementLocated(By.name("q")));
// WebElement element=wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//

        originalWindow = driver.getWindowHandle();
        final Set<String> oldWindowsSet = driver.getWindowHandles();

        for (WebElement itemLink : linkList) {
// Конечно, можно просто убедиться в том, что у ссылки есть атрибут
// target="_blank".
// Но в этом упражнении требуется именно кликнуть по ссылке,
// чтобы она открылась в новом окне, потом переключиться в новое окно,
// закрыть его, вернуться обратно, и повторить эти действия для всех
// таких ссылок.
// Не забудьте, что новое окно открывается не мгновенно,
// поэтому требуется ожидание открытия окна.
            linH = itemLink.getAttribute("href");
            linT = itemLink.getAttribute("target");
            Assert.assertTrue("Target=_blank",linT.equals("_blank"));
            itemLink.click();

//            wait.until(ExpectedConditions.urlToBe(linH));
            String newWindow = (new WebDriverWait(driver, 10))
                    .until(new ExpectedCondition<String>() {
                               public String apply(WebDriver driver) {
                                   Set<String> newWindowsSet = driver.getWindowHandles();
                                   newWindowsSet.removeAll(oldWindowsSet);
                                   return newWindowsSet.size() > 0 ?
                                           newWindowsSet.iterator().next() : null;
                               }
                           }
                    );

//            newWindow = driver.getWindowHandle();
            Assert.assertFalse("Открылось новое окно", originalWindow.equals(newWindow));

            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(originalWindow);

        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
