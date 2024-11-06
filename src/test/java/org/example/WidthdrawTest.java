package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class WidthdrawTest {
    WebDriver driver;
    WebDriverWait wait;
    String baseUrl = "http://www.demo.guru99.com/V4/";

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(baseUrl);
    }

    @Test
    public void widthdrawWithEmptyAccountNoTest() {
        try {
            driver.get("https://demo.guru99.com/V4/");

            WebElement accountNoField = driver.findElement(By.name("accountNo"));
            accountNoField.click();
            accountNoField.sendKeys(Keys.TAB);

            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'Account Number field must not be blank')]")));
            Assert.assertTrue(errorMsg.isDisplayed(), "Error message for blank Account Number is not displayed!");

            System.out.println("Error message for blank Account Number verified successfully!");

        } catch (NoAlertPresentException e) {
            System.out.println("No alert was present.");
        } catch (Exception e) {
            Assert.fail("An unexpected error occurred: " + e.getMessage());
        }
    }

    @Test
    public void widthdrawWithEmptyAmountTest() {
        try {
            driver.get("https://demo.guru99.com/V4/");

            WebElement amountField = driver.findElement(By.name("amount"));
            amountField.click();
            amountField.sendKeys(Keys.TAB);

            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'Amount field must not be blank')]")));
            Assert.assertTrue(errorMsg.isDisplayed(), "Error message for blank Amount is not displayed!");

            System.out.println("Error message for blank Amount verified successfully!");

        } catch (NoAlertPresentException e) {
            System.out.println("No alert was present.");
        } catch (Exception e) {
            Assert.fail("An unexpected error occurred: " + e.getMessage());
        }
    }

    @Test
    public void widthdrawWithEmptyDescriptionTest() {
        try {
            driver.get("https://demo.guru99.com/V4/");

            WebElement descriptionField = driver.findElement(By.name("description"));
            descriptionField.click();
            descriptionField.sendKeys(Keys.TAB);

            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'Description field must not be blank')]")));
            Assert.assertTrue(errorMsg.isDisplayed(), "Error message for blank Description is not displayed!");

            System.out.println("Error message for blank Description verified successfully!");

        } catch (NoAlertPresentException e) {
            System.out.println("No alert was present.");
        } catch (Exception e) {
            Assert.fail("An unexpected error occurred: " + e.getMessage());
        }
    }

    @Test
    public void widthdrawTest() {
        WebElement accountNo = driver.findElement(By.name("accountNo"));
        WebElement amount = driver.findElement(By.name("amount"));
        WebElement description = driver.findElement(By.name("description"));
        WebElement submitButton = driver.findElement(By.name("btnSubmit"));

        accountNo.sendKeys("267434");
        amount.sendKeys("20000");
        description.sendKeys("Today 10/30/2014");
        submitButton.click();

        String expectedTitle = "Make widthdraw successfully!";
        Assert.assertEquals(driver.getTitle(), expectedTitle, "Make widthdraw failed!");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
