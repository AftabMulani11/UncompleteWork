package Wailwil.BaseTest;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;

public class driver extends Basetest {
    public static WebDriver driver;
    public static TestAction pageObj;
    @Parameters("browser")
    public void launch(String browser) throws IOException {
        switch(browser.toLowerCase()){
            case "firefox":
                FirefoxOptions op = new FirefoxOptions();
                String s = prop.getProperty("browser.firefox.options");
                op.addArguments(s);
                driver = new FirefoxDriver(op);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                break;
            case "chrome":
                ChromeOptions option = new ChromeOptions();
                String g = prop.getProperty("browser.chrome.options");
                option.addArguments(g);
                driver = new ChromeDriver(option);
                driver.manage().window().maximize();
                break;
            case "edge":
                EdgeOptions options = new EdgeOptions();
                String e = prop.getProperty("browser.edge.options");
                options.addArguments(e);
                driver= new EdgeDriver(options);
                driver.manage().window().maximize();
                break;
        }
        driver.get(prop.getProperty("oneBridgeUrl"));
        pageObj = new TestAction(driver);
    }
}

