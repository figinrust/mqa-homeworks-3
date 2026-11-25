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

        URL remoteUrl = new URL("http://127.0.0.1:4723/");
        driver = new AndroidDriver<>(remoteUrl, desiredCapabilities);
    }

    @Test
    public void testChangeTextEmptyString() {

        MobileElement textToBeChanged = driver.findElementById("ru.netology.testing.uiautomator:id/textToBeChanged");
        String initialText = textToBeChanged.getText();


        MobileElement userInput = driver.findElementById("ru.netology.testing.uiautomator:id/userInput");
        userInput.sendKeys("   ");


        MobileElement buttonChange = driver.findElementById("ru.netology.testing.uiautomator:id/buttonChange");
        buttonChange.click();


        MobileElement resultText = driver.findElementById("ru.netology.testing.uiautomator:id/textToBeChanged");
        Assertions.assertEquals(initialText, resultText.getText());
    }

    @Test
    public void testOpenTextInNewActivity() {
        String textToSet = "Netology Appium Test";


        MobileElement userInput = driver.findElementById("ru.netology.testing.uiautomator:id/userInput");
        userInput.sendKeys(textToSet);


        MobileElement buttonActivity = driver.findElementById("ru.netology.testing.uiautomator:id/buttonActivity");
        buttonActivity.click();


        WebDriverWait wait = new WebDriverWait(driver, 10);
        MobileElement activityText = (MobileElement) wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("ru.netology.testing.uiautomator:id/text"))
        );

        Assertions.assertEquals(textToSet, activityText.getText());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
