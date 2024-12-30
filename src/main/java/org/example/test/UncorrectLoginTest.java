package org.example.test;

import io.qameta.allure.*;
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
public class UncorrectLoginTest extends TestBasic {

    @Test(description = "Test case: User appointment with uncorrect login")
    @Severity(SeverityLevel.NORMAL)
    @Story("User Appointment")
    @Description("""
            1- Acces au site au site https://katalon-demo-cura.herokuapp.com/.
            2- Cliquer sur le bouton "Make Appointment"
            3- Saisir le username "John Steve'
            4- Saisir le password "ThisIsAPassword"
            5- Cliquer sur le bouton "Login"
            12-Verifier que le texte sur la page est "Login failed! Please ensure the username and password are valid."
            """)
    @XrayTest(key = "QA-18")
    public void userAppointmentWithNoneCorrectLogin() throws IOException, ParseException {
        verifyHomePageIsVisible();
        verifyLoginPageIsVisible();
        verifyLoginPageIsVisibleWithMessageError();

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

    @Step("Verifier que la page login est visible avec le message d'erreur")
    private void verifyLoginPageIsVisibleWithMessageError() throws IOException, ParseException {
        String error=new LoginPage(driver)
                .fillIncorrectLogin()
                        .getErrorMessage()
                                .getText();
        Assert.assertEquals(error,"Login failed! Please ensure the username and password are valid.","Verifier que la page login est visible avec le message d'erreur");

    }


}
