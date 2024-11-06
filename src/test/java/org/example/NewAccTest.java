package org.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NewAccTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String username = "mngr599452";
    private String password = "pEtUmeh";
    private String loginUrl = "https://www.demo.guru99.com/V4";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(loginUrl);
        login();
    }

    public void login() {
        driver.findElement(By.name("uid")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("btnLogin")).click();
    }

    public void resetForm() {
        WebElement resetButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("reset")));
        resetButton.click();
    }

    public void clickNewAccountLink() {
        WebElement newAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("New Account")));
        newAccountLink.click();
    }

    public void fillCustomerId(String customerId) {
        driver.findElement(By.name("cusid")).sendKeys(customerId);
    }

    public void fillInitialDeposit(String initialDeposit) {
        driver.findElement(By.name("inideposit")).sendKeys(initialDeposit);
    }

    public void submitForm() {
        driver.findElement(By.name("button2")).click();
    }

    public void triggerValidation() {
        driver.findElement(By.name("inideposit")).sendKeys(Keys.TAB); // Move focus away
    }

    public void checkErrorMessage(String expectedMessage, String messageId) {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(messageId)));
        Assert.assertEquals("Error message is incorrect or not displayed!", expectedMessage, errorMsg.getText().trim());
    }

    public void handleAlert() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept(); // Dismiss the alert
        } catch (Exception e) {
            System.out.println("No alert was present after form submission.");
        }
    }

    @Test
    public void testCustomerIdIsRequired() {
        System.out.println("Running test: Customer ID is required");
        clickNewAccountLink();
        fillCustomerId(""); // Leave blank
        triggerValidation();
        checkErrorMessage("Customer ID is required", "message14");
        resetForm();
        System.out.println("Test passed");
    }

    @Test
    public void testInitialDepositIsRequired() {
        System.out.println("Running test: Initial Deposit is required");
        clickNewAccountLink();
        fillCustomerId("1234");
        fillInitialDeposit(""); // Leave blank
        triggerValidation();
        checkErrorMessage("Initial Deposit must not be blank", "message19");
        resetForm();
        System.out.println("Test passed");
    }

    @Test
    public void testCustomerIdCannotHaveCharacters() {
        System.out.println("Running test: Customer ID cannot have characters");
        clickNewAccountLink();
        fillCustomerId("abc"); // Invalid characters
        triggerValidation();
        checkErrorMessage("Characters are not allowed", "message14");
        resetForm();
        System.out.println("Test passed");
    }

    @Test
    public void testInitialDepositCannotHaveCharacters() {
        System.out.println("Running test: Initial Deposit cannot have characters");
        clickNewAccountLink();
        fillCustomerId("1234");
        fillInitialDeposit("abc"); // Invalid characters
        triggerValidation();
        checkErrorMessage("Characters are not allowed", "message19");
        resetForm();
        System.out.println("Test passed");
    }

    @Test
    public void testCustomerIdCannotHaveSpecialCharacters() {
        System.out.println("Running test: Customer ID cannot have special characters");
        clickNewAccountLink();
        fillCustomerId("!@#"); // Special characters
        triggerValidation();
        checkErrorMessage("Special characters are not allowed", "message14");
        resetForm();
        System.out.println("Test passed");
    }

    @Test
    public void testInitialDepositCannotHaveSpecialCharacters() {
        System.out.println("Running test: Initial Deposit cannot have special characters");
        clickNewAccountLink();
        fillCustomerId("1234");
        fillInitialDeposit("!@#"); // Special characters
        triggerValidation();
        checkErrorMessage("Special characters are not allowed", "message19");
        resetForm();
        System.out.println("Test passed");
    }

    @Test
    public void testValidInputCreatesAccount() {
        System.out.println("Running test: Valid input creates account");
        clickNewAccountLink();
        fillCustomerId("1234");
        fillInitialDeposit("7000");

        // Select account type
        WebElement accountTypeSelect = driver.findElement(By.name("selaccount"));
        accountTypeSelect.click();
        WebElement savingsOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[@value='Savings']")));
        savingsOption.click();

        submitForm();
        handleAlert();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(),'Account Created Successfully')]")));
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "https://www.demo.guru99.com/V4/manager/insertAccount.php";
        Assert.assertEquals("Account creation failed; not redirected correctly.", expectedUrl, currentUrl);
        System.out.println("Test passed");
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}