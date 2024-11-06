package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DepositTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String username = "mngr599452";
    private String password = "pEtUmeh";
    private String loginUrl = "https://www.demo.guru99.com/V4";

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get(loginUrl);
        login();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void login() {
        driver.findElement(By.name("uid")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("btnLogin")).click();
    }

    private void resetForm() {
        wait.until(ExpectedConditions.elementToBeClickable(By.name("reset"))).click();
    }

    private void clickNewAccountLink() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("New Account"))).click();
    }

    private void fillCustomerId(String customerId) {
        driver.findElement(By.name("cusid")).sendKeys(customerId);
    }

    private void fillInitialDeposit(String initialDeposit) {
        driver.findElement(By.name("inideposit")).sendKeys(initialDeposit);
    }

    private void fillDescription(String description) {
        driver.findElement(By.name("desc")).sendKeys(description);
    }

    private void triggerValidation() {
        driver.findElement(By.name("inideposit")).sendKeys(Keys.TAB);
    }

    private void checkErrorMessage(String expectedMessage, String messageId) {
        String actualMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(messageId))
        ).getText().trim();
        assertEquals("Error message is incorrect: expected '" + expectedMessage + "', got '" + actualMessage + "'!", expectedMessage, actualMessage);
    }

    // Test cases

    @Test
    public void testCustomerIdIsRequired() {
        clickNewAccountLink();
        fillCustomerId(""); // Leave blank
        triggerValidation();
        checkErrorMessage("Customer ID is required", "message14");
        resetForm();
    }

    @Test
    public void testInitialDepositIsRequired() {
        clickNewAccountLink();
        fillInitialDeposit(""); // Leave blank
        triggerValidation();
        checkErrorMessage("Initial Deposit must not be blank", "message19");
        resetForm();
    }

    @Test
    public void testDescriptionIsRequired() {
        clickNewAccountLink();
        fillDescription(""); // Leave blank
        triggerValidation();
        checkErrorMessage("Description can not be blank", "message17");
        resetForm();
    }

    @Test
    public void testCustomerIdCannotHaveCharacters() {
        clickNewAccountLink();
        fillCustomerId("abcd1234"); // Invalid characters
        triggerValidation();
        checkErrorMessage("Characters are not allowed", "message14");
        resetForm();
    }

    @Test
    public void testInitialDepositCannotHaveCharacters() {
        clickNewAccountLink();
        fillInitialDeposit("abcd1234"); // Invalid characters
        triggerValidation();
        checkErrorMessage("Characters are not allowed", "message19");
        resetForm();
    }

    @Test
    public void testCustomerIdCannotHaveSpecialCharacters() {
        clickNewAccountLink();
        fillCustomerId("!@#$%"); // Special characters
        triggerValidation();
        checkErrorMessage("Special characters are not allowed", "message14");
        resetForm();
    }

    @Test
    public void testInitialDepositCannotHaveSpecialCharacters() {
        clickNewAccountLink();
        fillInitialDeposit("!@#$%"); // Special characters
        triggerValidation();
        checkErrorMessage("Special characters are not allowed", "message19");
        resetForm();
    }

    @Test
    public void testDepositSuccessful() {
        clickNewAccountLink();
        fillCustomerId("12345");
        fillInitialDeposit("7000");
        fillDescription("abc");
        // Click the submit button
        driver.findElement(By.name("buttonSubmit")).click();

        // Check if the success dialog is displayed
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success_dialog"))).isDisplayed());
        String successMessage = driver.findElement(By.id("success_dialog")).getText();
        assertEquals("Deposit successful", successMessage);

        // Close the success dialog
        driver.findElement(By.cssSelector("#success_dialog button.close")).click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}