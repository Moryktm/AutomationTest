package org.example.test;

import io.qameta.allure.*;
import org.example.page.AppointmentPage;
import org.example.page.HomePage;
import org.example.page.LoginPage;
import org.example.utils.XrayListener;
import org.example.utils.XrayTest;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

@Listeners(XrayListener.class)
public class CorrectLoginTest extends TestBasic{

    @Test(description = "Test case: User appointment with correct login")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User Appointment")
    @Description("""
            1- Acces au site au site https://katalon-demo-cura.herokuapp.com/.
            2- Cliquer sur le bouton "Make Appointment"
            3- Saisir le username "John Doe'
            4- Saisir le password "ThisIsNotAPassword"
            5- Cliquer sur le bouton "Login"
            6- Choisir la facility "Hongkong CURA Healthcare Center"
            7- Cliquer sur "Apply for hospital readmission"
            8- Cliquer sur "Medicaid"
            9- Saisir la date "04/12/2024"
            10-Saisir le commentaire "Je souhaite faire une consultation ophtalmologique"
            11-Cliquer sur "Book Appointment"
            12-Verifier que le texte sur la page est "Appointment Confirmation"
            """)
    @XrayTest(key = "QA-17")
    public void userAppointmentWithCorrectLogin() throws IOException, ParseException {
        verifyHomePageIsVisible();
        verifyLoginPageIsVisible();
        verifyAppointmentPageIsVisible();
        verifyAppointmentPageConfirmIsVisible();
    }

    @Step("Verifier que le HomePage est visible")
    public static void verifyHomePageIsVisible(){
        boolean pageVisible=new HomePage(driver)
                .homePageIsVesible()
                .isDisplayed();
        Assert.assertTrue(pageVisible,"Verifier que le HomePage est visible");

    }

    @Step("Verifier que la page login est visible")
    public static void verifyLoginPageIsVisible(){
        String login=new HomePage(driver)
                .signUpLoginClick()
                .getLoginText()
                .getText();
        Assert.assertEquals(login,"Login","Verifier que la page login est visible");
    }

    @Step("Verifier que la page Appointment est visible")
    private void verifyAppointmentPageIsVisible() throws IOException, ParseException {
        String appointment=new LoginPage(driver)
                .fillCorrectLogin()
                .getMakeAppointmentText()
                .getText();
        Assert.assertEquals(appointment,"Make Appointment","Verifier que la page Appointment est visible");

    }

    @Step("Verifier que la page Make Appointment Confirm est visible")
    private void verifyAppointmentPageConfirmIsVisible() throws IOException, ParseException {
        String appointmentConfirm=new AppointmentPage(driver)
                .makeAppointment()
                .getAppointmentConfirmText()
                .getText();
        Assert.assertEquals(appointmentConfirm,"Appointment Confirmation","Verifier que la page Make Appointment Confirm est visible");
    }



}
