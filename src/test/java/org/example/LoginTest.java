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

public class LoginTest {
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
    public void loginWithEmptyUserIdTest() {
        try {
            driver.get("https://demo.guru99.com/V4/");

            WebElement userIdField = driver.findElement(By.name("uid"));
            userIdField.click();
            userIdField.sendKeys(Keys.TAB);

            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'User-ID must not be blank')]")));
            Assert.assertTrue(errorMsg.isDisplayed(), "Error message for blank User ID is not displayed!");

            System.out.println("Error message for blank User ID verified successfully!");

        } catch (NoAlertPresentException e) {
            System.out.println("No alert was present.");
        } catch (Exception e) {
            Assert.fail("An unexpected error occurred: " + e.getMessage());
        }
    }

    @Test
    public void loginWithEmptyPasswordTest() {
        try {
            driver.get("https://demo.guru99.com/V4/");

            WebElement userIdField = driver.findElement(By.name("uid"));
            userIdField.sendKeys("mngr596374");

            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.click();
            passwordField.sendKeys(Keys.TAB);

            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'Password must not be blank')]")));
            Assert.assertTrue(errorMsg.isDisplayed(), "Error message for blank Password is not displayed!");

            System.out.println("Error message for blank Password verified successfully!");

        } catch (NoAlertPresentException e) {
            System.out.println("No alert was present.");
        } catch (Exception e) {
            Assert.fail("An unexpected error occurred: " + e.getMessage());
        }
    }



    @Test
    public void loginTest() {
        WebElement userId = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("uid")));
        WebElement password = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.name("btnLogin"));

        userId.sendKeys("mngr596374"); // User ID
        password.sendKeys("upugeju"); // Password
        loginButton.click();

        String expectedTitle = "Guru99 Bank Manager HomePage";
        Assert.assertEquals(driver.getTitle(), expectedTitle, "Login failed!");
    }

//    @Test(dependsOnMethods = {"loginTest"})
//    public void newCustomerTest() {
//        try {
//            driver.findElement(By.linkText("New Customer")).click();
//
//            driver.findElement(By.name("name")).sendKeys("John Doe");
//            driver.findElement(By.xpath("//input[@value='m']")).click(); // Chọn giới tính nam
//            driver.findElement(By.name("dob")).sendKeys("01011990");
//            driver.findElement(By.name("addr")).sendKeys("123 Street City");
//            driver.findElement(By.name("city")).sendKeys("CityName");
//            driver.findElement(By.name("state")).sendKeys("StateName");
//            driver.findElement(By.name("pinno")).sendKeys("123456");
//            driver.findElement(By.name("telephoneno")).sendKeys("1234567890");
//            driver.findElement(By.name("emailid")).sendKeys("lanhuong2@gmail.com");
//            driver.findElement(By.name("password")).sendKeys("password123");
//            driver.findElement(By.name("sub")).click(); // Nút Submit để tạo khách hàng
//
//            WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//*[contains(text(), 'Customer Registered Successfully!!!')]")));
//            Assert.assertTrue(successMsg.isDisplayed(), "New Customer registration failed!");
//
//            System.out.println("New Customer registration successful!");
//
//        } catch (NoAlertPresentException e) {
//            System.out.println("No alert was present.");
//        } catch (UnhandledAlertException e) {
//            driver.switchTo().alert().accept();
//            Assert.fail("An unexpected alert appeared: " + e.getAlertText());
//        }
//    }



    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
