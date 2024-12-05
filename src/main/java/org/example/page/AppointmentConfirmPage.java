package org.example.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AppointmentConfirmPage {

    @FindBy(xpath ="//div/h2")
    private WebElement appointmentConfirmText;

    private WebDriver driver;

    public AppointmentConfirmPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }

    public WebElement getAppointmentConfirmText(){
        return appointmentConfirmText;
    }
}
