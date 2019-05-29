package com.automation.testCases.Login;

import com.automation.config.BaseTest;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.logging.Logger;

public class LoginFromStartViewTest extends BaseTest {
     private final Logger LOG = Logger.getLogger(LoginFromStartViewTest.class.getName());

     @Test
    public void fromStartpageLoginWithWrongCredential() {
        String passw = "12345679";
        String username = "hoppsan@tre.com";
        startPage.clickOnDebugButton(driver);
        debugPage.clickToCheckTheDebugValuesAndSeRadioButton(driver);
        debugPage.tapTheButtonLogin(driver);
        authLoginPage.enterLoginCredentials(driver, username, passw);
        Assert.assertEquals(authLoginPage.errorMessageTextIs(driver),"Fel e-postadress eller lösenord." ,"The error message is not correct ");
    }

    @DataProvider(name="loginNegative")
    public Object[][] getData() {
        return new Object[][] {
                {"Dren@email.com", "test"},
                {"test@email.com", "abcabc"},
                {"test@emailcom", " "}
        };
    }

    @Test(dataProvider = "loginNegative", dependsOnMethods = { "fromStartpageLoginWithWrongCredential" })
    public void fromStartpageLoginWitDdifferentWrongCredential(String userEmail, String userPassword) {
        authLoginPage.enterLoginCredentials(driver, userEmail, userPassword);
        Assert.assertEquals(authLoginPage.errorMessageTextIs(driver),"Fel e-postadress eller lösenord." ,"The error message is not correct ");
    }
}
