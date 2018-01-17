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

    // переменная для хранения привязки драйверов к разным потокам
    // стандартная для java
    // + инициализируем переменную (хранилище)
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    // пример расширенного управления
    public void start() {


// если с текущим потоком ассоциирован драйвер, то используем его
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 10);
            return;
        }
// инициализируем новый драйвер (если это не было сделано ранее)
//        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setCapability(FirefoxDriver.MARIONETTE, false);
//        driver = new FirefoxDriver(caps);
        driver = new ChromeDriver();
        tlDriver.set(driver);

//        System.out.println(((HasCapabilities) driver).getCapabilities());
        wait =  new WebDriverWait(driver, 10);


//    проверяем наличие драйвера, если он уже проинициализирован, то ничего не делаем
//    public void start() {
//        if (driver != null) {
//            return;
//        }

//        driver = new FirefoxDriver();
//        driver = new ChromeDriver();
//        wait =  new WebDriverWait(driver, 15);

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
