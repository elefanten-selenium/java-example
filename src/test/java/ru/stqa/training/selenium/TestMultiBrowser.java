package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class TestMultiBrowser {

    private WebDriver driver1;
    private WebDriver driver2;
    private WebDriverWait wait1;
    private WebDriverWait wait2;

    @Before
    public void start() {
        driver1 = new ChromeDriver();
        driver2 = new FirefoxDriver();
//        driver = new InternetExplorerDriver();
//        driver = new FirefoxDriver();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait1 = new WebDriverWait(driver1, 20);
        wait2 = new WebDriverWait(driver2, 20);

    }

    @Test
    public void TestMultiBrowser() {
        driver1.get("http://localhost/litecart/admin/");
        driver1.findElement(By.name("username")).sendKeys("admin");
        driver1.findElement(By.name("password")).sendKeys("admin");

        driver1.findElement(By.name("login")).click();


        driver2.get("http://localhost/litecart/admin/");
        driver2.findElement(By.name("username")).sendKeys("admin");
        driver2.findElement(By.name("password")).sendKeys("admin");

        driver2.findElement(By.name("login")).click();


//        sendKeys(Keys.chord(Keys.CONTROL, Keys.C));


        driver1.switchTo();

        wait1.until(titleIs("My Store"));
        wait2.until(titleIs("My Store"));
    }

    @After
    public void stop() {
        driver1.quit();
        driver1 = null;
        driver2.quit();
        driver2 = null;
    }
}
