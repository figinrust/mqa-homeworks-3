package ru.netology;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.netology.screens.MainScreen;
import ru.netology.screens.ShowTextScreen;

import java.net.MalformedURLException;
import java.net.URL;

public class AppiumTest {

    private AppiumDriver<MobileElement> driver;
    private MainScreen mainScreen;
    private ShowTextScreen showTextScreen;

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

        mainScreen = new MainScreen(driver);
        showTextScreen = new ShowTextScreen(driver);
    }

    @Test
    public void testChangeTextEmptyString() {
        String initialText = mainScreen.textToBeChanged.getText();

        mainScreen.userInput.sendKeys("   ");
        mainScreen.buttonChange.click();

        Assertions.assertEquals(initialText, mainScreen.textToBeChanged.getText());
    }

    @Test
    public void testOpenTextInNewActivity() {
        String textToSet = "Netology Appium Test";

        mainScreen.userInput.sendKeys(textToSet);
        mainScreen.buttonActivity.click();

        Assertions.assertEquals(textToSet, showTextScreen.text.getText());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
