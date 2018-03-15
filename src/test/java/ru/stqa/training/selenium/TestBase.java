package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class TestBase {

    public static ThreadLocal<EventFiringWebDriver> tlDriver = new ThreadLocal<>();
    public EventFiringWebDriver driver;
    public WebDriverWait wait;

    public static class MyListener extends AbstractWebDriverEventListener {
        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by + " found");
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println(throwable);
        }
    }

    public void openPage(String url, String title){
        driver.get(url);
        wait.until(titleIs(title));
    }

    public Integer basketCount(){
        WebElement varQuantityBasket = driver.findElement(By.xpath("//*[@class='quantity']"));
        int quantityBasket = Integer.parseUnsignedInt(varQuantityBasket.getText());
        return quantityBasket;
    }

    public void addGift2Basket() {
        String xpathPrgh = "//h3[@class='title'][contains(text(),'Campaigns')]/..//li[contains(@class,'product')]";
        // В задаче требуется проверить только 1 элемент
        WebElement goodsItem = driver.findElement(By.xpath(xpathPrgh));
        // переходим по ссылке на страницу карточки товара
        goodsItem.click();
        WebElement goodsPage = driver.findElement(By.xpath("//*[contains(@id,'box-product')]"));

        boolean isSetSize = goodsPage.findElement(By.name("options[Size]")).isDisplayed();
        if (isSetSize == true) {
            Select sel = new Select(goodsPage.findElement(By.name("options[Size]")));
            sel.selectByValue("Small");
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        }
        goodsPage.findElement(By.name("quantity")).clear();
        goodsPage.findElement(By.name("quantity")).sendKeys("1");
        goodsPage.findElement(By.name("add_cart_product")).click();
    }

    public void waitUpdateBasket(int oldQuantityBasket){
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@class='quantity']"))
                ,String.valueOf(oldQuantityBasket + 1)));
    }

    public void openBackPage(){
        driver.findElement(By.xpath("//a[.='Checkout »']")).click();
    }

    public void clearBasket(){
        int numberOfGift = driver.findElements(By.xpath("//button[@value='Remove']")).size();
        for (int i = 0; i < numberOfGift; i++) {
            driver.findElement(By.xpath("//button[@value='Remove']")).click();
            if (driver.findElement(By.xpath("//*[contains(@class,'dataTable')]")).isDisplayed() == true) {
                wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//td[@class='item']"),numberOfGift-i));
            }
        }
    }


    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 10);
        }

        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.register(new MyListener());
        tlDriver.set(driver);
        wait = new WebDriverWait(driver, 10);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    driver.quit();
                    driver = null;
                })
        );
    }

    @After
    public void stop() {
//        driver.quit();
//        driver = null;
    }

}
