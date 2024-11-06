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

public class FundTransferTest {
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
    public void widthdrawWithEmptyPayersAccTest() {
        try {
            driver.get("https://demo.guru99.com/V4/");

            WebElement payersAccField = driver.findElement(By.name("payersAcc"));
            payersAccField.click();
            payersAccField.sendKeys(Keys.TAB);

            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'Payers Account field must not be blank')]")));
            Assert.assertTrue(errorMsg.isDisplayed(), "Error message for blank Payers Account is not displayed!");

            System.out.println("Error message for blank Payers Account verified successfully!");

        } catch (NoAlertPresentException e) {
            System.out.println("No alert was present.");
        } catch (Exception e) {
            Assert.fail("An unexpected error occurred: " + e.getMessage());
        }
    }

    @Test
    public void widthdrawWithEmptyPayeesAccTest() {
        try {
            driver.get("https://demo.guru99.com/V4/");

            WebElement payeesAccField = driver.findElement(By.name("payeesAcc"));
            payeesAccField.click();
            payeesAccField.sendKeys(Keys.TAB);

            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'Payees Account field must not be blank')]")));
            Assert.assertTrue(errorMsg.isDisplayed(), "Error message for blank Payees Account is not displayed!");

            System.out.println("Error message for blank Payees Account verified successfully!");

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
    public void fundTransferTest() {
        WebElement payersAcc = driver.findElement(By.name("payersAcc"));
        WebElement payeesAcc = driver.findElement(By.name("payeesAcc"));
        WebElement amount = driver.findElement(By.name("amount"));
        WebElement description = driver.findElement(By.name("description"));
        WebElement submitButton = driver.findElement(By.name("btnSubmit"));

        payersAcc.sendKeys("267434");
        payeesAcc.sendKeys("232434");
        amount.sendKeys("20000");
        description.sendKeys("Today 10/30/2014");
        submitButton.click();

        String expectedTitle = "Fund transfer successfully!";
        Assert.assertEquals(driver.getTitle(), expectedTitle, "Fund transfer failed!");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
