package org.example.test;

import org.example.utils.PropertiesLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.time.Duration;

public class TestBasic {

    protected static WebDriver driver;

    @BeforeTest
    public void setup() throws IOException{
        String url= PropertiesLoader.loadProperty("url");
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get(url);
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
