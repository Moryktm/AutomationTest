package org.example.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    @FindBy(linkText = "Make Appointment")
    private WebElement makeAppointment;

    @FindBy(xpath = "//header/div/h1")
    private WebElement homePageText;

    private WebDriver driver;

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }

    public WebElement homePageIsVesible(){
        return homePageText;
    }

    public LoginPage signUpLoginClick(){
        makeAppointment.click();
        return new LoginPage(driver);
    }
}
