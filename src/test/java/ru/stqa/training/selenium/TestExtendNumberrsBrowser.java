package ru.stqa.training.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class TestExtendNumberrsBrowser extends BaseExtendTest {


    private WebDriverWait webDriverWait;

    @Test
    public void myFirstTest() {

        driver.get("http://www.google.com/");
        driver.findElement(By.name("q")).sendKeys("webdriver");


//        wait.until(ExpectedConditions.visibilityOf(By.id("gs_ok0")));
        driver.findElement(By.id("gs_ok0")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("K32")));
        driver.findElement(By.id("K32")).click();
        driver.findElement(By.id("gs_ok0")).click();

        driver.findElement(By.name("q")).sendKeys(Keys.RETURN);
        wait.until(titleIs("webdriver - Поиск в Google"));
    }


    @Test
    public void mySecondTest() {

        driver.get("http://www.google.com/");
        driver.findElement(By.name("q")).sendKeys("webdriver");
        driver.findElement(By.name("q")).sendKeys(Keys.RETURN);

//        этот элемент оказывается невидим под "выпадушкой" с вариантами поиска
//        driver.findElement(By.name("btnK")).click();
//        <input type="button" value="Поиск в Google" class="lsb">
//        <div class="sbsb_g" style="width: 581px;"><span class="ds"><span class="lsbb"><input type="button" value="Поиск в Google" class="lsb"></span></span><span class="ds"><span class="lsbb"><input type="button" value="Мне повезёт!" class="lsb"></span></span></div>
//        sendKeys(Keys.chord(Keys.CONTROL, Keys.C));

//        кнопка-клавиатура
// <span class="gsok_a gsst_e" id="gs_ok0" style="outline: rgba(0, 0, 255, 0.3) solid 1px;"><img src="//www.gstatic.com/inputtools/images/tia.png" tia_field_name="q" tia_disable_swap="true" tia_property="web"></span>



        wait.until(titleIs("webdriver - Поиск в Google"));
    }


}
