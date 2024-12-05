package org.example.page;

import org.example.utils.JsonReader;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;

public class AppointmentPage {

    @FindBy(xpath ="//div/h2")
    private WebElement makeAppointmentText;

    @FindBy(id ="combo_facility")
    private WebElement selectCenter;

    @FindBy(css ="input[id='chk_hospotal_readmission']")
    private WebElement hospitalReadmisssion;

    @FindBy(css ="input[id='radio_program_medicare']")
    private WebElement medicareChoice;

    @FindBy(css ="input[id='radio_program_medicaid']")
    private WebElement medicaidChoice;

    @FindBy(css ="input[id='radio_program_none']")
    private WebElement noneChoice;

    @FindBy(css ="input[id='txt_visit_date']")
    private WebElement visitDateChoice;

    @FindBy(id ="txt_comment")
    private WebElement commentText;

    @FindBy(css="button[id='btn-book-appointment']")
    private WebElement bookAppointment;

    private String comment="Je souhaite faire une consultation ophtalmologique";

    private WebDriver driver;

    public AppointmentPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }


    public AppointmentConfirmPage makeAppointment() throws IOException, ParseException {
        WebElement selectElement=getSelectCenter();
        Select select=new Select(selectElement);
        select.selectByVisibleText("Hongkong CURA Healthcare Center");
        getHospitalReadmisssion().click();
        getMedicaidChoice().click();
        visitDateChoiceText();
        commentEnter(comment);
        bookAppointmentPage();
        return new AppointmentConfirmPage(driver);

    }

    public WebElement getMakeAppointmentText(){
        return makeAppointmentText;
    }

    public WebElement getSelectCenter(){
        return selectCenter;
    }

    public WebElement getHospitalReadmisssion(){
        return hospitalReadmisssion;
    }

    public WebElement getMedicareChoice(){
        return medicareChoice;
    }

    public WebElement getMedicaidChoice(){
        return medicaidChoice;
    }

    public WebElement getNoneChoice(){
        return noneChoice;
    }

    public void visitDateChoiceText() throws IOException, ParseException {
        String data= JsonReader.getData("date");
        visitDateChoice.sendKeys(data);
    }

    public void commentEnter(String comment){
        commentText.sendKeys(comment);
    }

    public void bookAppointmentPage(){
        bookAppointment.click();
    }

}
