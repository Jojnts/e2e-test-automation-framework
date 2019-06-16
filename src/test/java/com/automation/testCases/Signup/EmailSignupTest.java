package com.automation.testCases.Signup;

import com.automation.config.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.logging.Logger;

public class EmailSignupTest extends BaseTest {
     private final Logger LOG = Logger.getLogger(EmailSignupTest.class.getName());

    @Test(dataProvider = "userPerCountry", groups = "signupAndDelete")
    public void createAccountInTheSignupFlowAndThenDeactivateTheAccount(String countryCode, String firstName, String lastName,String userEmail, String userPassword){
        startPage.clickOnDebugButton(driver);
        debugPage.tapEmailSignupButton(driver, countryCode);
        patientCreateAccountPage.fillInTheAccount(driver, firstName, lastName, userEmail,userPassword);
        gdprPage.acceptGdpr(driver);
        questionOnePage.startTheQuestionary(driver, countryCode);
        patientMobileMainPage.clickOkayToTalkWitPhysiotherapist(driver);
        patientMobileMainPage.clickShowMyProfile(driver);
        patientMobileShowProfilePage.clickOnMyAccountSettings(driver);
        patientMobileShowProfileSettingsPage.deleteMyAccount(driver);
    }

    @DataProvider(name="userPerCountry")
    public Object[][] getData() {
        return new Object[][] {
                {"SE", "Testse", "test", "Testse.test@testse.com", "12345678"},
                {"US", "Testus", "test","Testus.test@testus.com", "87654321"},
                {"NO", "Testno", "test","Testno.test@testno.com", "87654321"},
        };
    }

    @Test(dataProvider = "invitePatientsNO")
    public void sendInviteAsTherapistInNorway(String countryCode, String firstName, String lastName,String userEmail, String userPassword){
        startPage.clickOnDebugButton(driver);
        debugPage.tapEmailLoginButton(driver, countryCode );
        authLoginPage.enterLoginCredentials(driver, "therapist+12366@ja.com", "123123123");
        therapistStartPage.sendAnInviteToPatientAndLogout(driver, firstName, lastName, userEmail);
    }

    @Test(dataProvider = "invitePatientsNO", dependsOnMethods = { "sendInviteAsTherapistInNorway" })
    public void withInviteSignUpAndDectivateAccountInNorway(String countryCode, String firstName, String lastName,String userEmail, String userPassword){
        startPage.clickOnDebugButton(driver);
        debugPage.tapEmailSignupButton(driver, countryCode);
        patientCreateAccountPage.fillInTheAccount(driver, firstName, lastName, userEmail,userPassword);
        gdprPage.acceptGdpr(driver);
        questionOnePage.startTheQuestionary(driver, countryCode);
        patientMobileMainPage.clickOkayToTalkWitPhysiotherapist(driver);
        patientMobileMainPage.clickShowMyProfile(driver);
        patientMobileShowProfilePage.clickOnMyAccountSettings(driver);
        patientMobileShowProfileSettingsPage.deleteMyAccount(driver);
    }

    @DataProvider(name="invitePatientsNO")
    public Object[][] getPatientsToIniteData() {
        return new Object[][] {
                {"NO", "Sven", "Svan","Sven.Svan@testno.no", "123123123"},
        };
    }
}
