package org.example.page;

import org.example.utils.JsonReader;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class LoginPage {
    @FindBy(xpath ="//p[@class='lead text-danger']")
    private WebElement errorMessage;

    @FindBy(xpath ="//div/h2")
    private WebElement loginText;

    @FindBy(css ="input[id='txt-username']")
    private WebElement userName;

    @FindBy(css ="input[id='txt-password']")
    private WebElement passWord;

    @FindBy(css ="button[id='btn-login']")
    private WebElement loginButton;

    private WebDriver driver;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }

    public WebElement getLoginText(){
        return loginText;
    }

    public void fillLogin(String username, String password){
        userName.sendKeys(username);
        passWord.sendKeys(password);
        loginButton.click();
    }

    public AppointmentPage fillCorrectLogin () throws IOException, ParseException {
        fillLogin(JsonReader.getData("goodUsername"),JsonReader.getData("goodPassword"));
        return new AppointmentPage(driver);
    }

    public LoginPage fillIncorrectLogin() throws IOException, ParseException {
        fillLogin(JsonReader.getData("wrongUsername"),JsonReader.getData("wrongPassword"));
        return this;
    }

    public WebElement getErrorMessage(){
        return errorMessage;
    }


}
