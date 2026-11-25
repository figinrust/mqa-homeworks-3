package ru.netology;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumTest {

    private AndroidDriver<MobileElement> driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:deviceName", "Pixel 6a");
        desiredCapabilities.setCapability("appium:automationName", "UiAutomator2");
        desiredCapabilities.setCapability("appium:appPackage", "ru.netology.testing.uiautomator");
        desiredCapabilities.setCapability("appium:appActivity", "ru.netology.testing.uiautomator.MainActivity");

        // Если нужно запускать через путь к скомпилированному APK:
        // desiredCapabilities.setCapability("appium:app", "C:\\Users\\NightOwl\\StudioProjects\\mqa-homeworks\\2.2 UI Automator\\sample\\app\\build\\outputs\\apk\\debug\\app-debug.apk");

        // Подключение к серверу Appium (по умолчанию порт 4723)
        URL remoteUrl = new URL("http://127.0.0.1:4723/");
        driver = new AndroidDriver<>(remoteUrl, desiredCapabilities);
    }

    @Test
    public void testChangeTextEmptyString() {
        // 1. Запоминаем исходный текст в TextView
        MobileElement textToBeChanged = driver.findElementById("ru.netology.testing.uiautomator:id/textToBeChanged");
        String initialText = textToBeChanged.getText();

        // 2. Вводим строку, состоящую только из пробелов
        MobileElement userInput = driver.findElementById("ru.netology.testing.uiautomator:id/userInput");
        userInput.sendKeys("   ");

        // 3. Нажимаем кнопку изменения текста
        MobileElement buttonChange = driver.findElementById("ru.netology.testing.uiautomator:id/buttonChange");
        buttonChange.click();

        // 4. Проверяем, что текст в TextView остался прежним
        MobileElement resultText = driver.findElementById("ru.netology.testing.uiautomator:id/textToBeChanged");
        Assertions.assertEquals(initialText, resultText.getText());
    }

    @Test
    public void testOpenTextInNewActivity() {
        String textToSet = "Netology Appium Test";

        // 1. Вводим непустой текст в поле
        MobileElement userInput = driver.findElementById("ru.netology.testing.uiautomator:id/userInput");
        userInput.sendKeys(textToSet);

        // 2. Нажимаем кнопку запуска новой Activity
        MobileElement buttonActivity = driver.findElementById("ru.netology.testing.uiautomator:id/buttonActivity");
        buttonActivity.click();

        // 3. Ожидаем появления элемента TextView на экране второй Activity (ID: text)
        WebDriverWait wait = new WebDriverWait(driver, 10);
        MobileElement activityText = (MobileElement) wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("ru.netology.testing.uiautomator:id/text"))
        );

        // 4. Проверяем, что текст во второй Activity совпадает с тем, что был введен
        Assertions.assertEquals(textToSet, activityText.getText());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
