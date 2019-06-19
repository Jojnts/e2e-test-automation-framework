package com.automation.testCases.Login;

import com.automation.config.BaseTest;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.logging.Logger;

public class EmailLoginTest extends BaseTest {
     private final Logger LOG = Logger.getLogger(EmailLoginTest.class.getName());

    @Test
    public void fromStartpageLoginWithWrongCredentialSE() {
        String passw = "12345679";
        String username = "hoppsan@tre.com";
        startPage.clickOnDebugButton(driver);
        debugPage.tapEmailLoginButton(driver, "SE");
        authLoginPage.enterLoginCredentials(driver, username, passw);
        Assert.assertEquals(authLoginPage.errorMessageTextIs(driver),"Fel e-postadress eller lösenord." ,"The error message is not correct ");
    }

    @DataProvider(name="loginAccountsSE")
    public Object[][] getData() {
        return new Object[][] {
                {"SE", "Dren@email.com", "test"},
                {"SE", "test@emailcom", " "}
        };
    }

    @Test(dataProvider = "loginAccountsSE", dependsOnMethods = { "fromStartpageLoginWithWrongCredentialSE" })
    public void fromStartpageLoginWitDdifferentWrongCredential(String cc, String userEmail, String userPassword) {
        authLoginPage.enterLoginCredentials(driver, userEmail, userPassword);
        Assert.assertEquals(authLoginPage.errorMessageTextIs(driver),"Fel e-postadress eller lösenord." ,"The error message is not correct ");
    }
}
