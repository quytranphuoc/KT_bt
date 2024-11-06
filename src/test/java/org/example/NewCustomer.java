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

public class NewCustomer {
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

    @Test(dependsOnMethods = {"loginTest"})
    public void verifyBlankNameFieldMessage() {
        verifyBlankFieldMessage("name", "Customer name must not be blank");
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void verifyBlankDOBFieldMessage() {
        verifyBlankFieldMessage("dob", "Date Field must not be blank");
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void verifyBlankAddressFieldMessage() {
        verifyBlankFieldMessage("addr", "Address Field must not be blank");
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void verifyBlankCityFieldMessage() {
        verifyBlankFieldMessage("city", "City Field must not be blank");
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void verifyBlankStateFieldMessage() {
        verifyBlankFieldMessage("state", "State must not be blank");
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void verifyBlankPINFieldMessage() {
        verifyBlankFieldMessage("pinno", "PIN Code must not be blank");
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void verifyBlankMobileFieldMessage() {
        verifyBlankFieldMessage("telephoneno", "Mobile no must not be blank");
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void verifyBlankEmailFieldMessage() {
        verifyBlankFieldMessage("emailid", "Email-ID must not be blank");
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void verifyBlankPasswordFieldMessage() {
        verifyBlankFieldMessage("password", "Password must not be blank");
    }

    public void verifyBlankFieldMessage(String fieldName, String expectedMessage) {
        driver.get("https://demo.guru99.com/V4/manager/addcustomerpage.php");

        WebElement field = driver.findElement(By.name(fieldName));
        field.click();
        field.sendKeys(Keys.TAB);

        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), '" + expectedMessage + "')]")));
        Assert.assertTrue(errorMsg.isDisplayed(), "Error message for blank " + fieldName + " is not displayed!");

        System.out.println("Error message for blank " + fieldName + " verified successfully!");
    }



    @Test(dependsOnMethods = {"loginTest"})
    public void newCustomerTest() {
        try {
            driver.findElement(By.linkText("New Customer")).click();

            driver.findElement(By.name("name")).sendKeys("John Doe");
            driver.findElement(By.xpath("//input[@value='m']")).click();
            driver.findElement(By.name("dob")).sendKeys("01011990");
            driver.findElement(By.name("addr")).sendKeys("123 Street City");
            driver.findElement(By.name("city")).sendKeys("CityName");
            driver.findElement(By.name("state")).sendKeys("StateName");
            driver.findElement(By.name("pinno")).sendKeys("123456");
            driver.findElement(By.name("telephoneno")).sendKeys("1234567890");
            driver.findElement(By.name("emailid")).sendKeys("lanhuong8@gmail.com");
            driver.findElement(By.name("password")).sendKeys("password123");
            driver.findElement(By.name("sub")).click(); // Nút Submit để tạo khách hàng

            WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'Customer Registered Successfully!!!')]")));
            Assert.assertTrue(successMsg.isDisplayed(), "New Customer registration failed!");

            System.out.println("New Customer registration successful!");

        } catch (NoAlertPresentException e) {
            System.out.println("No alert was present.");
        } catch (UnhandledAlertException e) {
            driver.switchTo().alert().accept();
            Assert.fail("An unexpected alert appeared: " + e.getAlertText());
        }
    }

    @Test(dependsOnMethods = {"loginTest"})
    public void newCustomerDuplicateEmailTest() {
        try {
            driver.findElement(By.linkText("New Customer")).click();

            // Điền thông tin khách hàng mới với email đã tồn tại
            driver.findElement(By.name("name")).sendKeys("John Doe");
            driver.findElement(By.xpath("//input[@value='m']")).click(); // Chọn giới tính nam
            driver.findElement(By.name("dob")).sendKeys("01011990");
            driver.findElement(By.name("addr")).sendKeys("123 Street City");
            driver.findElement(By.name("city")).sendKeys("CityName");
            driver.findElement(By.name("state")).sendKeys("StateName");
            driver.findElement(By.name("pinno")).sendKeys("123456");
            driver.findElement(By.name("telephoneno")).sendKeys("1234567890");
            driver.findElement(By.name("emailid")).sendKeys("lanhuong5@gmail.com"); // Sử dụng email trùng lặp
            driver.findElement(By.name("password")).sendKeys("password123");
            driver.findElement(By.name("sub")).click();

            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            Assert.assertNotNull(alert, "Alert did not appear for duplicate email!");

            String alertText = alert.getText();
            Assert.assertEquals(alertText, "Email Address Already Exist !!", "Unexpected alert message!");

            alert.accept();

            System.out.println("Duplicate email alert verified successfully!");

        } catch (NoAlertPresentException e) {
            System.out.println("No alert was present.");
            Assert.fail("Expected alert for duplicate email, but no alert appeared.");
        }
    }


    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
