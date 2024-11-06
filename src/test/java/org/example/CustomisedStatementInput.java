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

public class CustomisedStatementInput {
    WebDriver driver;
    WebDriverWait wait;
    String baseUrl = "http://www.demo.guru99.com/V4/";

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
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

        userId.sendKeys("mngr596387"); // User ID
        password.sendKeys("hAsuqAr"); // Password
        loginButton.click();

        String expectedTitle = "Guru99 Bank Manager HomePage";
        Assert.assertEquals(driver.getTitle(), expectedTitle, "Login failed!");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void verifyBlankNameFieldMessage() {
        verifyBlankFieldMessage("name", "Customer name must not be blank");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void verifyBlankDOBFieldMessage() {
        verifyBlankFieldMessage("dob", "Date Field must not be blank");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void verifyBlankAddressFieldMessage() {
        verifyBlankFieldMessage("addr", "Address Field must not be blank");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void verifyBlankCityFieldMessage() {
        verifyBlankFieldMessage("city", "City Field must not be blank");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void verifyBlankStateFieldMessage() {
        verifyBlankFieldMessage("state", "State must not be blank");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void verifyBlankPINFieldMessage() {
        verifyBlankFieldMessage("pinno", "PIN Code must not be blank");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void verifyBlankMobileFieldMessage() {
        verifyBlankFieldMessage("telephoneno", "Mobile no must not be blank");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void verifyBlankEmailFieldMessage() {
        verifyBlankFieldMessage("emailid", "Email-ID must not be blank");
    }

    @Test(dependsOnMethods = { "loginTest" })
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


    @Test(dependsOnMethods = { "loginTest" })
    public void validInputTest() {
        driver.findElement(By.name("accountno")).sendKeys("12345");
        driver.findElement(By.name("fdate")).sendKeys("01/01/2023");
        driver.findElement(By.name("tdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("100");
        driver.findElement(By.name("numtransaction")).sendKeys("5");
        // Nhấn nút Submit và kiểm tra kết quả
        driver.findElement(By.name("Submit")).click();
        // Xác nhận kết quả hiển thị hợp lệ
        // Thêm kiểm tra sau khi nhấn Submit (nếu cần)
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void emptyAccountNoTest() {
        driver.findElement(By.name("fdate")).sendKeys("01/01/2023");
        driver.findElement(By.name("tdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("100");
        driver.findElement(By.name("numtransaction")).sendKeys("5");
        // Nhấn nút Submit và kiểm tra cảnh báo
        driver.findElement(By.name("Submit")).click();
        verifyAlert("Account No must not be blank!");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void negativeAccountNoTest() {
        driver.findElement(By.name("accountno")).sendKeys("-12345");
        driver.findElement(By.name("fdate")).sendKeys("01/01/2023");
        driver.findElement(By.name("tdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("100");
        driver.findElement(By.name("numtransaction")).sendKeys("5");
        // Nhấn nút Submit và kiểm tra cảnh báo
        driver.findElement(By.name("Submit")).click();
        verifyAlert("Account No must be a positive number!");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void alphabeticAccountNoTest() {
        driver.findElement(By.name("accountno")).sendKeys("1234a");
        driver.findElement(By.name("fdate")).sendKeys("01/01/2023");
        driver.findElement(By.name("tdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("100");
        driver.findElement(By.name("numtransaction")).sendKeys("5");
        // Nhấn nút Submit và kiểm tra cảnh báo
        driver.findElement(By.name("Submit")).click();
        verifyAlert("Account No must be numeric!");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void emptyFromDateTest() {
        driver.findElement(By.name("accountno")).sendKeys("12345");
        driver.findElement(By.name("tdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("100");
        driver.findElement(By.name("numtransaction")).sendKeys("5");
        // Nhấn nút Submit và kiểm tra cảnh báo
        driver.findElement(By.name("Submit")).click();
        verifyAlert("From Date must not be blank!");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void invalidFromDateTest() {
        driver.findElement(By.name("accountno")).sendKeys("12345");
        driver.findElement(By.name("fdate")).sendKeys("31/02/2023");
        driver.findElement(By.name("tdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("100");
        driver.findElement(By.name("numtransaction")).sendKeys("5");
        // Nhấn nút Submit và kiểm tra cảnh báo
        driver.findElement(By.name("Submit")).click();
        verifyAlert("Invalid From Date!");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void emptyToDateTest() {
        driver.findElement(By.name("accountno")).sendKeys("12345");
        driver.findElement(By.name("fdate")).sendKeys("01/01/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("100");
        driver.findElement(By.name("numtransaction")).sendKeys("5");
        // Nhấn nút Submit và kiểm tra cảnh báo
        driver.findElement(By.name("Submit")).click();
        verifyAlert("To Date must not be blank!");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void toDateBeforeFromDateTest() {
        driver.findElement(By.name("accountno")).sendKeys("12345");
        driver.findElement(By.name("fdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("tdate")).sendKeys("01/01/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("100");
        driver.findElement(By.name("numtransaction")).sendKeys("5");
        // Nhấn nút Submit và kiểm tra cảnh báo
        driver.findElement(By.name("Submit")).click();
        verifyAlert("To Date must be after From Date!");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void validMinimumTransactionValueTest() {
        driver.findElement(By.name("accountno")).sendKeys("12345");
        driver.findElement(By.name("fdate")).sendKeys("01/01/2023");
        driver.findElement(By.name("tdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("100");
        driver.findElement(By.name("numtransaction")).sendKeys("5");
        // Nhấn nút Submit và kiểm tra kết quả
        driver.findElement(By.name("Submit")).click();
        // Thêm kiểm tra sau khi nhấn Submit (nếu cần)
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void negativeMinimumTransactionValueTest() {
        driver.findElement(By.name("accountno")).sendKeys("12345");
        driver.findElement(By.name("fdate")).sendKeys("01/01/2023");
        driver.findElement(By.name("tdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("-100");
        driver.findElement(By.name("numtransaction")).sendKeys("5");
        // Nhấn nút Submit và kiểm tra cảnh báo
        driver.findElement(By.name("Submit")).click();
        verifyAlert("Minimum Transaction Value must be positive!");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void emptyMinimumTransactionValueTest() {
        driver.findElement(By.name("accountno")).sendKeys("12345");
        driver.findElement(By.name("fdate")).sendKeys("01/01/2023");
        driver.findElement(By.name("tdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("numtransaction")).sendKeys("5");
        // Nhấn nút Submit và kiểm tra cảnh báo
        driver.findElement(By.name("Submit")).click();
        verifyAlert("Minimum Transaction Value must not be blank!");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void alphabeticMinimumTransactionValueTest() {
        driver.findElement(By.name("accountno")).sendKeys("12345");
        driver.findElement(By.name("fdate")).sendKeys("01/01/2023");
        driver.findElement(By.name("tdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("abc");
        driver.findElement(By.name("numtransaction")).sendKeys("5");
        // Nhấn nút Submit và kiểm tra cảnh báo
        driver.findElement(By.name("Submit")).click();
        verifyAlert("Minimum Transaction Value must be numeric!");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void emptyNumberOfTransactionTest() {
        driver.findElement(By.name("accountno")).sendKeys("12345");
        driver.findElement(By.name("fdate")).sendKeys("01/01/2023");
        driver.findElement(By.name("tdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("100");
        // Nhấn nút Submit và kiểm tra cảnh báo
        driver.findElement(By.name("Submit")).click();
        verifyAlert("Number of Transactions must not be blank!");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void specialCharacterInNumberOfTransactionTest() {
        driver.findElement(By.name("accountno")).sendKeys("12345");
        driver.findElement(By.name("fdate")).sendKeys("01/01/2023");
        driver.findElement(By.name("tdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("100");
        driver.findElement(By.name("numtransaction")).sendKeys("!@#$");
        // Nhấn nút Submit và kiểm tra cảnh báo
        driver.findElement(By.name("Submit")).click();
        verifyAlert("Number of Transactions must be numeric!");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void resetButtonFunctionalityTest() {
        driver.findElement(By.name("accountno")).sendKeys("12345");
        driver.findElement(By.name("fdate")).sendKeys("01/01/2023");
        driver.findElement(By.name("tdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("100");
        driver.findElement(By.name("numtransaction")).sendKeys("5");
        driver.findElement(By.name("Reset")).click();
        // Kiểm tra xem tất cả các trường đã được làm trống
        Assert.assertEquals(driver.findElement(By.name("accountno")).getText(), "");
        Assert.assertEquals(driver.findElement(By.name("fdate")).getText(), "");
        Assert.assertEquals(driver.findElement(By.name("tdate")).getText(), "");
        Assert.assertEquals(driver.findElement(By.name("amountlowerlimit")).getText(), "");
        Assert.assertEquals(driver.findElement(By.name("numtransaction")).getText(), "");
    }

    @Test(dependsOnMethods = { "loginTest" })
    public void validSubmitButtonFunctionalityTest() {
        driver.findElement(By.name("accountno")).sendKeys("12345");
        driver.findElement(By.name("fdate")).sendKeys("01/01/2023");
        driver.findElement(By.name("tdate")).sendKeys("10/10/2023");
        driver.findElement(By.name("amountlowerlimit")).sendKeys("100");
        driver.findElement(By.name("numtransaction")).sendKeys("5");
        driver.findElement(By.name("Submit")).click();
        // Thêm kiểm tra sau khi nhấn Submit (nếu cần)
    }




    private void verifyAlert(String expectedMessage) {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String actualMessage = alert.getText();
            Assert.assertEquals(actualMessage, expectedMessage);
            alert.accept();
        } catch (NoAlertPresentException e) {
            Assert.fail("Expected alert not present!");
        }
    }
}