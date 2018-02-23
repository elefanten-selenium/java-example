package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class TestUserRegistry {

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
    public void TestUserRegistry() {
//        Сценарий должен состоять из следующих частей:
//
//        1) регистрация новой учётной записи с достаточно уникальным адресом
//                электронной почты (чтобы не конфликтовало с ранее созданными пользователями,
//                в том числе при предыдущих запусках того же самого сценария),
//        2) выход (logout), потому что после успешной регистрации автоматически происходит вход,
//        3) повторный вход в только что созданную учётную запись,
//        4) и ещё раз выход.
//
//        В качестве страны выбирайте United States, штат произвольный. При этом формат индекса -- пять цифр.
//
//                Можно оформить сценарий либо как тест, либо как отдельный исполняемый файл.
//
//        Проверки можно никакие не делать, только действия -- заполнение полей, нажатия на кнопки и ссылки. Если сценарий дошёл до конца, то есть созданный пользователь смог выполнить вход и выход -- значит создание прошло успешно.
//
//                В форме регистрации есть капча, её нужно отключить в админке учебного приложения на вкладке Settings -> Security.

        // проверяем (и отключаем) включённую капчу
        driver.get("http://localhost/litecart/admin/?app=settings&doc=security");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");

        driver.findElement(By.name("login")).click();
//        sendKeys(Keys.chord(Keys.CONTROL, Keys.C));

        wait.until(titleIs("Settings | My Store"));
        String captchaBase = "//*[contains(text(),'CAPTCHA')]";
        boolean isCaptchaON = driver.findElement(By.xpath(captchaBase.concat("/../td[2]"))).getText().equals("True");
        if (isCaptchaON == true) {
            driver.findElement(By.xpath(captchaBase.concat("/..//*[@class='fa fa-pencil']"))).click();
            driver.findElement(By.xpath(captchaBase.concat("/../td[2]/*[contains(text(),'False')]"))).click();
            driver.findElement(By.xpath(captchaBase.concat("/../td[3]/*[contains(text(),'Save')]"))).click();
        }
        driver.findElement(By.className("fa-sign-out")).click();


//        1) регистрация новой учётной записи с достаточно уникальным адресом электронной почты (чтобы не конфликтовало с ранее созданными пользователями, в том числе при предыдущих запусках того же самого сценария),
        // регистрируем случайный, но рабочий емейл
//        driver.get("http://10minutemail.net/");
//        wait.until(titleIs("10 минутная почта"));
//        String userEmail = driver.findElement(By.id("fe_text")).getAttribute("value");
        driver.get("https://tempail.com/");
        String userEmail = driver.findElement(By.id("eposta_adres")).getAttribute("value");
//        driver.findElement(By.id("fe_text")).sendKeys (Keys.chord (Keys.CONTROL, "a"));
//        driver.findElement(By.id("fe_text")).sendKeys (Keys.chord (Keys.CONTROL, "c"));

// keyDown, keyUp, keyPress
// searchField.sendKeys("selenium" + Keys.ENTER);


        //начинаем с главной страницы
        driver.get("http://localhost/litecart/en/");
        // переходим на страницу регистации
        driver.findElement(By.xpath("//*[contains(text(),'New customers click here')]")).click();
        wait.until(titleIs("Create Account | My Store"));

        driver.findElement(By.name("firstname")).sendKeys(Keys.HOME+"UseName");
        driver.findElement(By.name("lastname")).sendKeys(Keys.HOME+"UserFamily");
        driver.findElement(By.name("address1")).sendKeys(Keys.HOME+"UserAddress");
        driver.findElement(By.name("postcode")).sendKeys(Keys.HOME+"12345");
        driver.findElement(By.name("city")).sendKeys(Keys.HOME+"Санкт-Петербург");
//  В качестве страны выбирайте United States, штат произвольный.
//  При этом формат индекса -- пять цифр.
        driver.findElement(By.className("selection")).click();
        driver.findElement(By.className("select2-search__field")).sendKeys("United States"+Keys.ENTER);

        driver.findElement(By.name("email")).sendKeys(userEmail);
        driver.findElement(By.name("phone")).sendKeys(Keys.HOME+"+12345678901");
        boolean newsCheckbox = driver.findElement(By.name("newsletter")).isSelected();
        if (newsCheckbox == true ) {
            driver.findElement(By.name("newsletter")).click();
        }

        driver.findElement(By.name("password")).sendKeys("user");
        driver.findElement(By.name("confirmed_password")).sendKeys("user");

        driver.findElement(By.name("create_account")).click();
        wait.until(titleIs("Online Store | My Store"));

//        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
//        wait.until(titleIs("Countries | My Store"));


//        2) выход (logout), потому что после успешной регистрации автоматически происходит вход,
        driver.findElement(By.xpath("//a[.='Logout']")).click();
//        3) повторный вход в только что созданную учётную запись,
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys(userEmail);
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("user");
        driver.findElement(By.name("login")).click();
//        4) и ещё раз выход.
        driver.findElement(By.xpath("//a[.='Logout']")).click();




// возвращаем капчу на место
        if (isCaptchaON == true) {
            driver.get("http://localhost/litecart/admin/?app=settings&doc=security");
            driver.findElement(By.name("username")).sendKeys("admin");
            driver.findElement(By.name("password")).sendKeys("admin");

            driver.findElement(By.name("login")).click();
            wait.until(titleIs("Settings | My Store"));
            driver.findElement(By.xpath(captchaBase.concat("/..//*[@class='fa fa-pencil']"))).click();
            driver.findElement(By.xpath(captchaBase.concat("/../td[2]/*[contains(text(),'True')]"))).click();
            driver.findElement(By.xpath(captchaBase.concat("/../td[3]/*[contains(text(),'Save')]"))).click();
            driver.findElement(By.className("fa-sign-out")).click();
        }

    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
