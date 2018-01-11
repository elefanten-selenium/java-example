package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.util.locale.provider.DecimalFormatSymbolsProviderImpl;

public class BaseExtendTest {

//    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public static WebDriver driver;
    public static WebDriverWait wait;

    @Before
    // пример расширенного управления (в будущих уроках)
//    public void start() {
//        if (tlDriver.get() != null) {
//            driver = tlDriver.get();
//            wait = new WebDriverWait(driver, 10);
//            return;
//        }
//
//        driver = new ChromeDriver();
//        tlDriver.set(driver);
//        wait = new WebDriverWait(driver, 10);
//
//        Runtime.getRuntime().addShutdownHook(
//                new Thread(() -> { driver.quit(); driver = null; }));
    public void start() {
        if (driver != null) {
            return;
        }
//        driver = new FirefoxDriver();
        driver = new ChromeDriver();
        wait =  new WebDriverWait(driver, 15);

//        shutdown hook
//        описания для java для случая полной остановки выполнения
        Runtime.getRuntime().addShutdownHook(
            new Thread(() -> { driver.quit(); driver = null; }));

    }

    @After
    public void stop() {
//        driver.quit();
//        driver = null;
    }

}
