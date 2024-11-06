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

public class LogOutTest {
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
    public void logoutSuccessMessageTest() {
        // Nhấp vào liên kết "Log out"
        driver.findElement(By.linkText("Log out")).click();

        // Kiểm tra thông báo đăng xuất thành công
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertMessage = alert.getText();
        alert.accept(); // Nhấn nút OK trên thông báo

        // Xác minh thông báo đăng xuất thành công
        Assert.assertEquals(alertMessage, "You Have Successfully Logged Out!", "Logout success message is incorrect!");
        System.out.println("Logout success message test verified successfully!");
    }

    @Test(dependsOnMethods = { "logoutSuccessMessageTest" })
    public void redirectToLoginPageTest() {
        // Kiểm tra xem người dùng đã được chuyển hướng về trang đăng nhập
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.demo.guru99.com/V4/index.php", "User was not redirected to the login page!");

        System.out.println("Redirect to login page test verified successfully!");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
