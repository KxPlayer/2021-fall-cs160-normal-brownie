/* commented out to run*/

package com.example.carfinder.frontendtesting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class FrontEndTesting {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDrivers\\chromedriver.exe"); // probably should change to location of your own driver
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080");

        if (!testQuizResponse(driver)) {
            System.out.println("Error with quiz response.");
        }

        driver.navigate().refresh();

        if (!testSignUp(driver)) {
            System.out.println("Error with signing up.");
        }

        if (!testSignUpWithDuplicateName(driver)) {
            System.out.println("Error with signing up with duplicate name.");
        }

        if (!testSignUpWithNewName(driver)) {
            System.out.println("Error with signing up with new name.");
        }

        if (!testLogIn(driver)) {
            System.out.println("Error with logging in.");
        }

        driver.quit();
    }

    // testing that quiz inputs return expected results
    static boolean testQuizResponse(WebDriver driver) {
        WebElement element = driver.findElement(By.xpath("//button[@name='quizButton']"));
        element.click();

        element = driver.findElement(By.xpath("//button[@id='priceRange7500,35000']"));
        element.click();

        element = driver.findElement(By.xpath("//button[@id='yearRange2000,2022']"));
        element.click();

        element = driver.findElement(By.xpath("//button[@id='doors4']"));
        element.click();

        element = driver.findElement(By.xpath("//button[@id='transmissionAutomatic']"));
        element.click();

        element = driver.findElement(By.xpath("//button[@id='fueltypeGas']"));
        element.click();

        element = driver.findElement(By.xpath("//button[@id='minmpgcombined30']"));
        element.click();

        element = driver.findElement(By.xpath("//div[@id='results']"));

        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div[@class='result']")));
        List<WebElement> webEleList = driver.findElements(By.xpath("//div/div[@class='result']"));

        // as of now, expected result is 18 entries
        // i'll probably update this when more cars get added
        //System.out.println(webEleList.size());
        return webEleList.size() == 18;
    }

    // failing the sign up before sending in a valid result
    static boolean testSignUp(WebDriver driver) {
        WebElement element = driver.findElement(By.name("signUpButton"));
        element.click();

        element = driver.findElement(By.name("username"));
        element.clear();
        element.sendKeys("");

        element = driver.findElement(By.name("password"));
        element.clear();
        element.sendKeys("");

        element = driver.findElement(By.id("submitSignup"));
        element.click();

        element = driver.findElement(By.id("usernameLengthError"));
        if (element.getText().compareTo("You need to enter a username.") != 0) {
            return false;
        }

        element = driver.findElement(By.id("passwordLengthError"));
        if (element.getText().compareTo("The password needs to be at least 8 characters.") != 0) {
            return false;
        }

        element = driver.findElement(By.name("username"));
        element.clear();
        element.sendKeys("");

        element = driver.findElement(By.name("password"));
        element.clear();
        element.sendKeys("testtest");

        element = driver.findElement(By.id("usernameLengthError"));
        if (element.getText().compareTo("You need to enter a username.") != 0) {
            return false;
        }

        element = driver.findElement(By.name("username"));
        element.clear();
        element.sendKeys("");

        element = driver.findElement(By.name("password"));
        element.clear();
        element.sendKeys("testtestt");

        element = driver.findElement(By.id("usernameLengthError"));
        if (element.getText().compareTo("You need to enter a username.") != 0) {
            return false;
        }

        element = driver.findElement(By.name("username"));
        element.clear();
        element.sendKeys("test");

        element = driver.findElement(By.name("password"));
        element.clear();
        element.sendKeys("1234567");

        element = driver.findElement(By.id("passwordLengthError"));
        if (element.getText().compareTo("The password needs to be at least 8 characters.") != 0) {
            return false;
        }

        element = driver.findElement(By.name("username"));
        element.clear();
        element.sendKeys("#!@#$%^&*()_+");

        element = driver.findElement(By.name("password"));
        element.clear();
        element.sendKeys("password");

        element = driver.findElement(By.id("submitSignup"));
        element.click();

        return true;
    }

    // sign up with the same name from the previous test but under a different password
    static boolean testSignUpWithDuplicateName(WebDriver driver) {
        WebElement element = driver.findElement(By.name("signUpButton"));
        element.click();

        element = driver.findElement(By.name("username"));
        element.clear();
        element.sendKeys("#!@#$%^&*()_+");

        element = driver.findElement(By.name("password"));
        element.clear();
        element.sendKeys("password1");

        element = driver.findElement(By.id("submitSignup"));
        element.click();

        return true;
    }

    static boolean testSignUpWithNewName(WebDriver driver) {
        WebElement element = driver.findElement(By.name("signUpButton"));
        element.click();

        element = driver.findElement(By.name("username"));
        element.clear();
        element.sendKeys("testing!");

        element = driver.findElement(By.name("password"));
        element.clear();
        element.sendKeys("passwordd");

        element = driver.findElement(By.id("submitSignup"));
        element.click();

        return true;
    }

    // log in with a username that doesn't exist, the one that does but no password, then with correct password
    static boolean testLogIn(WebDriver driver) {
        WebElement element = driver.findElement(By.name("logInButton"));
        element.click();

        element = driver.findElement(By.name("username"));
        element.clear();
        element.sendKeys("");

        element = driver.findElement(By.name("password"));
        element.clear();
        element.sendKeys("");

        element = driver.findElement(By.id("submitLogin"));
        element.click();

        element = driver.findElement(By.name("username"));
        element.clear();
        element.sendKeys("");

        element = driver.findElement(By.name("password"));
        element.clear();
        element.sendKeys("pass?");

        element = driver.findElement(By.id("submitLogin"));
        element.click();

        element = driver.findElement(By.name("username"));
        element.clear();
        element.sendKeys("tom");

        element = driver.findElement(By.name("password"));
        element.clear();
        element.sendKeys("");

        element = driver.findElement(By.id("submitLogin"));
        element.click();

        element = driver.findElement(By.name("username"));
        element.clear();
        element.sendKeys("tom");

        element = driver.findElement(By.name("password"));
        element.clear();
        element.sendKeys("password");

        element = driver.findElement(By.id("submitLogin"));
        element.click();

        return true;
    }
}

/**/
