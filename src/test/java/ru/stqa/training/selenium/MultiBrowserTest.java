package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class MultiBrowserTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
//        driver = new ChromeDriver();
        DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability("unexpectedAlertBehaviour","dismiss");
        driver = new InternetExplorerDriver(caps);
        System.out.println(((HasCapabilities) driver).getCapabilities());
//        driver = new FirefoxDriver();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);

    }

    @Test
    public void MultiBrowserTest() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");

        driver.findElement(By.name("login")).click();


//        sendKeys(Keys.chord(Keys.CONTROL, Keys.C));


        wait.until(titleIs("My Store"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
