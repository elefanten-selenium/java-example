package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
import static ru.stqa.training.selenium.BaseExtendTest.tlDriver;

public class MultiLayerArc extends TestBase {

    @Test
    public void MultiLayerArc() {
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
            openPage("http://localhost/litecart/en/","Online Store | My Store");

// запоминаем состояние счётчика корзины
            quantityBasket = basketCount();

//2) открыть первый товар из списка
//2) добавить его в корзину (при этом может случайно добавиться товар, который там уже есть, ничего страшного)
            addGift2Basket();

//3) подождать, пока счётчик товаров в корзине обновится
            waitUpdateBasket(quantityBasket);

        } while ((quantityBasket+1) < 3);

//4) вернуться на главную страницу, повторить предыдущие шаги ещё два раза, чтобы в общей сложности в корзине было 3 единицы товара
//5) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
        openBackPage();

//6) удалить все товары из корзины один за другим, после каждого удаления подождать, пока внизу обновится таблица
        clearBasket();
    }
}
