package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;


public class CheckBasket {

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
    public void CheckBasket() {
//Задание 13. Сделайте сценарий работы с корзиной
//Сделайте сценарий для добавления товаров в корзину и удаления товаров из корзины.
//
//1) открыть главную страницу
//2) открыть первый товар из списка
//2) добавить его в корзину (при этом может случайно добавиться товар, который там уже есть, ничего страшного)
//3) подождать, пока счётчик товаров в корзине обновится
//4) вернуться на главную страницу, повторить предыдущие шаги ещё два раза, чтобы в общей сложности в корзине было 3 единицы товара
//5) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
//6) удалить все товары из корзины один за другим, после каждого удаления подождать, пока внизу обновится таблица

        int quantityBasket=0;
        do {
//1) открыть главную страницу
            driver.get("http://localhost/litecart/en/");
//        driver.findElement(By.name("username")).sendKeys("admin");
//        driver.findElement(By.name("password")).sendKeys("admin");
//        driver.findElement(By.name("login")).click();

//        sendKeys(Keys.chord(Keys.CONTROL, Keys.C));

            wait.until(titleIs("Online Store | My Store"));
// Ожидания
// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
// WebDriverWait wait = new WebDriverWait(driver, 10/*seconds*/);  // <-- ждём элемент 10 секунд
// WebElement element2 = wait.until((WebDriver d) -> d.findElement(By.name("q")));
// WebElement element = wait.until(presenceOfElementLocated(By.name("q")));
// WebElement element=wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//

            // запоминаем состояние счётчика корзины
            WebElement varQuantityBasket = driver.findElement(By.xpath("//*[@class='quantity']"));
            quantityBasket = Integer.parseUnsignedInt(varQuantityBasket.getText());

//2) открыть первый товар из списка
            // получаем список карточек товара на главной странице в разделе Campaigns
            String xpathPrgh = "//h3[@class='title'][contains(text(),'Campaigns')]/..//li[contains(@class,'product')]";
//        List<WebElement> goodsList = driver.findElements(By.xpath(xpathPrgh));
//        int countGoods = goodsList.size();

            //  проходим по каждой карточе товара и собираем данные о товаре
            // так как мы будем открывать каждый раз новую страницу,
            // то надо будет пересоздавать список каждый раз заново
//        for (int i = 0; i < countGoods; i++) {
//        for (WebElement goodsItem : goodsList) {

            // В задаче требуется проверить только 1 элемент, если нужно будет проверять несколько, включаем цикл
//        WebElement goodsItem = driver.findElements(By.xpath("//h3[@class='title'][contains(text(),'Campaigns')]/..//li[contains(@class,'product')]")).get(i);
            WebElement goodsItem = driver.findElement(By.xpath(xpathPrgh));
            // переходим по ссылке на страницу карточки товара
            goodsItem.click();


//2) добавить его в корзину (при этом может случайно добавиться товар, который там уже есть, ничего страшного)
            WebElement goodsPage = driver.findElement(By.xpath("//*[contains(@id,'box-product')]"));

//        options[Size]
            boolean isSetSize = goodsPage.findElement(By.name("options[Size]")).isDisplayed();
            if (isSetSize == true) {
                Select sel = new Select(goodsPage.findElement(By.name("options[Size]")));
                sel.selectByValue("Small");
            }
            goodsPage.findElement(By.name("quantity")).clear();
            goodsPage.findElement(By.name("quantity")).sendKeys("1");
            goodsPage.findElement(By.name("add_cart_product")).click();


//3) подождать, пока счётчик товаров в корзине обновится
            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@class='quantity']")), String.valueOf(quantityBasket + 1)));

        } while ((quantityBasket+1) < 3);

//4) вернуться на главную страницу, повторить предыдущие шаги ещё два раза, чтобы в общей сложности в корзине было 3 единицы товара

//5) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
        driver.findElement(By.xpath("//a[.='Checkout »']")).click();

//6) удалить все товары из корзины один за другим, после каждого удаления подождать, пока внизу обновится таблица
        //    //a[contains(@class,'inact')]
        int numberOfGift = driver.findElements(By.xpath("//button[@value='Remove']")).size();
        for (int i = 0; i < numberOfGift; i++) {
//            int pcs = driver.findElement(By.xpath("//input[@name='quantity']")).getText();
//            // штук каждого товара может быть несколько, надо удалить все
//            for (int j = 0; j < pcs; j++) {
//            }
            driver.findElement(By.xpath("//button[@value='Remove']")).click();
            if (driver.findElement(By.xpath("//*[contains(@class,'dataTable')]")).isDisplayed() == true) {
                wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//td[@class='item']"),numberOfGift-i));
            }
        }

//        }


    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}


// http://automated-testing.info/t/by-vs-webelement/5149/17
//Кстати, в Java 8 при помощи функциональных интерфейсов можно очень легко соорудить универсальный waiter:
//
//public WebElement waitForElement(final By locator, final Function<By, ExpectedCondition<WebElement>> condition, final Integer timeout) {
//
//final WebElement element = wait.withTimeout(
//        Optional.ofNullable(timeout)
//        .filter(value -> value >= 0)
//        .orElse(DEFAULT_TIMEOUT), TimeUnit.SECONDS)
//        .until(condition.apply(locator));
//
//        wait.withTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//
//        return element;
//        }
//
//Дергать такой метод можно следующим образом:
//
//        waitForElement(By.id(""), ExpectedConditions::presenceOfElementLocated, 0);
//        waitForElement(By.xpath(""), ExpectedConditions::visibilityOfElementLocated, null);
//        waitForElement(By.cssSelector(""), ExpectedConditions::elementToBeClickable, 15);


//
//wait.until(
//        ExpectedConditions.and(
//        ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name("Services")),
//        ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name("Products"))
//        )
//        );


//
//public static boolean isElementVisible(WebElement webElement, int timeOut) {
//    boolean isElementVisible = false;
//    WebDriverWait wait = new WebDriverWait(driver, timeOut);
//    if (timeOut < DEFAULT_TIMEOUT || timeOut < 2){
//        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
//    }
//    try {
//        wait.until(ExpectedConditions.visibilityOf(webElement));
//        isElementVisible =  true;
//    } finally {
//        driver.manage().timeouts()
//                .implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//    }
//    return isElementVisible;
//}
