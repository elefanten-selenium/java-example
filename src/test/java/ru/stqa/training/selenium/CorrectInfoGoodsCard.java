package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class CorrectInfoGoodsCard {

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
    public void TestInfoGoodsLitecart() {

        driver.get("http://localhost/litecart/en/");
//        driver.findElement(By.name("username")).sendKeys("admin");
//        driver.findElement(By.name("password")).sendKeys("admin");
//        driver.findElement(By.name("login")).click();

//        sendKeys(Keys.chord(Keys.CONTROL, Keys.C));

        wait.until(titleIs("Online Store | My Store"));

        // получаем список карточек товара на главной странице в разделе Campaigns
        String xpathPrgh = "//h3[@class='title'][contains(text(),'Campaigns')]/..//li[contains(@class,'product')]";
        List<WebElement> goodsList = driver.findElements(By.xpath(xpathPrgh));
//        int countGoods = goodsList.size();

        //  проходим по каждой карточе товара и собираем данные о товаре
        // так как мы будем открывать каждый раз новую страницу,
        // то надо будет пересоздавать список каждый раз заново
//        for (int i = 0; i < countGoods; i++) {
//        for (WebElement goodsItem : goodsList) {

        // В задаче требуется проверить только 1 элемент, если нужно будет проверять несколько, включаем цикл
//        WebElement goodsItem = driver.findElements(By.xpath("//h3[@class='title'][contains(text(),'Campaigns')]/..//li[contains(@class,'product')]")).get(i);
           WebElement goodsItem = driver.findElement(By.xpath(xpathPrgh));


//            а) на главной странице и на странице товара совпадает текст названия товара
//            б) на главной странице и на странице товара совпадают цены (обычная и акционная)
//            в) обычная цена зачёркнутая и серая (можно считать, что "серый" цвет это такой, у которого в RGBa представлении одинаковые значения для каналов R, G и B)
//            г) акционная жирная и красная (можно считать, что "красный" цвет это такой, у которого в RGBa представлении каналы G и B имеют нулевые значения)
//            (цвета надо проверить на каждой странице независимо, при этом цвета на разных страницах могут не совпадать)
//            г) акционная цена крупнее, чем обычная (это тоже надо проверить на каждой странице независимо)
//
//            Необходимо убедиться, что тесты работают в разных браузерах, желательно проверить во всех трёх ключевых браузерах (Chrome, Firefox, IE).

            // тупо собираем необходимые параметры
//            а) на главной странице и на странице товара совпадает текст названия товара
            String nameTop = goodsItem.findElement(By.xpath(xpathPrgh.concat("/a/div[contains(@class,'name')]"))).getText();
//            б) на главной странице и на странице товара совпадают цены (обычная и акционная)
            String regularPriceTop =  goodsItem.findElement(By.xpath(xpathPrgh.concat("//s[@class='regular-price']"))).getText();
            String campaignPriceTop = goodsItem.findElement(By.xpath(xpathPrgh.concat("//*[contains(@class,'campaign-price')]"))).getText();
            // если просто одна цена, то class="price"
//            в) обычная цена зачёркнутая и серая (можно считать, что "серый" цвет это такой, у которого в RGBa представлении одинаковые значения для каналов R, G и B)
            String regularPriceStyleTop = goodsItem.findElement(By.xpath(xpathPrgh.concat("//s[contains(@class,'regular-price')]"))).getCssValue("text-decoration-line");
            String regularPriceColorTop = goodsItem.findElement(By.xpath(xpathPrgh.concat("//s[contains(@class,'regular-price')]"))).getCssValue("color");
            boolean isRegularGray = ((Color.fromString(regularPriceColorTop).getColor().getRed() == Color.fromString(regularPriceColorTop).getColor().getGreen())
                        && (Color.fromString(regularPriceColorTop).getColor().getRed() == Color.fromString(regularPriceColorTop).getColor().getBlue()));
//        int regularColorRed = Color.fromString(regularPriceColorTop).getColor().getRed();

//            г) акционная жирная и красная (можно считать, что "красный" цвет это такой, у которого в RGBa представлении каналы G и B имеют нулевые значения)
//            (цвета надо проверить на каждой странице независимо, при этом цвета на разных страницах могут не совпадать)
            String campaignPriceStyleTop = goodsItem.findElement(By.xpath(xpathPrgh.concat("//*[contains(@class,'campaign-price')]"))).getCssValue("font-weight"); // > 400 (700)
            String campaignPriceColorTop = goodsItem.findElement(By.xpath(xpathPrgh.concat("//*[contains(@class,'campaign-price')]"))).getCssValue("color");
            boolean isCampaignColorRed = ((Color.fromString(campaignPriceColorTop).getColor().getRed() > 0) && (Color.fromString(campaignPriceColorTop).getColor().getGreen() == 0) && (Color.fromString(campaignPriceColorTop).getColor().getBlue() == 0));
//            г) акционная цена крупнее, чем обычная (это тоже надо проверить на каждой странице независимо)
            String regularPriceFontSizeTop = goodsItem.findElement(By.xpath(xpathPrgh.concat("//s[contains(@class,'regular-price')]"))).getCssValue("font-size");
            String campaignPriceFontSizeTop = goodsItem.findElement(By.xpath(xpathPrgh.concat("//*[contains(@class,'campaign-price')]"))).getCssValue("font-size");


            // переходим по ссылке на страницу карточки товара
            goodsItem.click();


            WebElement goodsPage = driver.findElement(By.xpath("//*[contains(@id,'box-product')]"));

        // тупо собираем необходимые параметры
//            а) на главной странице и на странице товара совпадает текст названия товара
        String namePage = goodsPage.findElement(By.xpath("//*[@itemprop='name']")).getText();
//            б) на главной странице и на странице товара совпадают цены (обычная и акционная)
        String regularPricePage =  goodsPage.findElement(By.xpath("//s[@class='regular-price']")).getText();
        String campaignPricePage = goodsPage.findElement(By.xpath("//*[contains(@class,'campaign-price')]")).getText();
        // если просто одна цена, то class="price"
//            в) обычная цена зачёркнутая и серая (можно считать, что "серый" цвет это такой, у которого в RGBa представлении одинаковые значения для каналов R, G и B)
        String regularPriceStylePage = goodsPage.findElement(By.xpath("//s[contains(@class,'regular-price')]")).getCssValue("text-decoration-line");
        String regularPriceColorPage = goodsPage.findElement(By.xpath("//s[contains(@class,'regular-price')]")).getCssValue("color");
        boolean isPageRegularGray = ((Color.fromString(regularPriceColorPage).getColor().getRed() == Color.fromString(regularPriceColorPage).getColor().getGreen())
                    && (Color.fromString(regularPriceColorPage).getColor().getRed() == Color.fromString(regularPriceColorPage).getColor().getBlue()));
//        int regularPageColorRed = Color.fromString(regularPriceColorPage).getColor().getRed();

//            г) акционная жирная и красная (можно считать, что "красный" цвет это такой, у которого в RGBa представлении каналы G и B имеют нулевые значения)
//            (цвета надо проверить на каждой странице независимо, при этом цвета на разных страницах могут не совпадать)
        String campaignPriceStylePage = goodsPage.findElement(By.xpath("//*[contains(@class,'campaign-price')]")).getCssValue("font-weight"); // > 400 (700)
        String campaignPriceColorPage = goodsPage.findElement(By.xpath("//*[contains(@class,'campaign-price')]")).getCssValue("color");
        boolean isPageCampaignColorRed = ((Color.fromString(campaignPriceColorPage).getColor().getRed() > 0)
                    && (Color.fromString(campaignPriceColorPage).getColor().getGreen() == 0)
                    && (Color.fromString(campaignPriceColorPage).getColor().getBlue() == 0));
//            г) акционная цена крупнее, чем обычная (это тоже надо проверить на каждой странице независимо)
        String regularPriceFontSizePage = goodsPage.findElement(By.xpath("//s[contains(@class,'regular-price')]")).getCssValue("font-size");
        String campaignPriceFontSizePage = goodsPage.findElement(By.xpath("//*[contains(@class,'campaign-price')]")).getCssValue("font-size");


//            int numberStickers = goodsPage.findElements(By.xpath("//div[@class='sticker']")).size();


//            а) на главной странице и на странице товара совпадает текст названия товара
            Assert.assertTrue( "совпадает текст названия товара T:".concat(nameTop).concat(", P:").concat(namePage), nameTop.equals(namePage));
//            б) на главной странице и на странице товара совпадают цены (обычная и акционная)
            Assert.assertTrue( "совпадает обычная цена T:".concat(regularPriceTop).concat(", P:").concat(regularPricePage), regularPriceTop.equals(regularPricePage));
            Assert.assertTrue( "совпадает акционная цена", campaignPriceTop.equals(campaignPricePage));
//            в) обычная цена зачёркнутая и серая (можно считать, что "серый" цвет это такой,
//                      у которого в RGBa представлении одинаковые значения для каналов R, G и B)
            Assert.assertTrue( "обычная цена зачёркнута", regularPriceStyleTop.equals("line-through"));
            Assert.assertTrue( "обычная цена зачёркнута", regularPriceStylePage.equals("line-through"));
        Assert.assertTrue( "обычная цена серая ".concat(regularPriceColorTop), isRegularGray);
        Assert.assertTrue( "обычная цена серая ".concat(regularPriceColorPage), isPageRegularGray);
//            г) акционная жирная и красная (можно считать, что "красный" цвет это такой,
//                      у которого в RGBa представлении каналы G и B имеют нулевые значения)
//            (цвета надо проверить на каждой странице независимо, при этом цвета на разных страницах могут не совпадать)
            Assert.assertTrue( "акционная цена жирная", ( Integer.parseInt(campaignPriceStyleTop.replaceAll("[^\\d]","")) > 400 ) );
            Assert.assertTrue( "акционная цена жирная", ( Integer.parseInt(campaignPriceStylePage.replaceAll("[^\\d]","")) > 400 ) );
        Assert.assertTrue( "акционная цена красная ".concat(campaignPriceColorTop), isCampaignColorRed);
        Assert.assertTrue( "акционная цена красная ".concat(campaignPriceColorPage), isPageCampaignColorRed);
//            г) акционная цена крупнее, чем обычная (это тоже надо проверить на каждой странице независимо)
//        float rPFST = Float.parseFloat(regularPriceFontSizeTop.replaceAll("[^\\d,.]",""));
//        float cPFST = Float.parseFloat(campaignPriceFontSizeTop.replaceAll("[^\\d,.]",""));
        Assert.assertTrue( "акционная цена крупнее Tr:".concat(regularPriceFontSizeTop).concat(", Tc:").concat(campaignPriceFontSizeTop),
                    (Float.parseFloat(regularPriceFontSizeTop.replaceAll("[^\\d,.]","")) < Float.parseFloat(campaignPriceFontSizeTop.replaceAll("[^\\d]",""))));
//        int rPFSP = Integer.parseInt(regularPriceFontSizePage.replaceAll("[^\\d,.]","").replaceAll("\\.", ","));
//        int cPFSP = Integer.parseInt(campaignPriceFontSizePage.replaceAll("[^\\d,.]","").replaceAll("\\.", ","));
        Assert.assertTrue( "акционная цена крупнее Pr:".concat(regularPriceFontSizePage).concat(", Pc:").concat(campaignPriceFontSizePage),
                    (Float.parseFloat(regularPriceFontSizePage.replaceAll("[^\\d]","")) < Float.parseFloat(campaignPriceFontSizePage.replaceAll("[^\\d]",""))));



//            System.out.println("Equality: " + map1.equals(map2));
//            System.out.println(users.get(2));//получение по ключу
//            System.out.println(users.containsKey(1));//проверка есть значение с таким ключем
//            users.remove(1);//удаление по ключу
//            System.out.println(users.containsKey(1));
//            System.out.println(users.size());//размер мапы
//            System.out.println(users.isEmpty());//проверка пустая ли мапа
//            users.forEach((k, v) -> System.out.println(k + ": " + v));//элегантный вывод
        // цикл
//        }


    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
