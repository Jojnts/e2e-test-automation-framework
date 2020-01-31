package com.automation.testCases.Signup;

import com.automation.config.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.automation.config.EnvironmentConfig.appPackageToBeUse;

public class EmailSignupTest extends BaseTest {
    private final Logger LOG = Logger.getLogger(EmailSignupTest.class.getName());

    @Test(groups = {"groupSignup"}, dataProvider = "invitePatientsNO")
    public void sendInviteAsTherapistInNorway(String countryCode, String firstName, String lastName,String userEmail, String userPassword){
        startPage.clickOnDebugButton(driver);
        debugPage.tapEmailLoginButton(driver, countryCode );
        String bundldeId = appPackageToBeUse();
        if (bundldeId.contains("sandbox")) {
            authLoginPage.enterLoginCredentials(driver, "therapist+5248@ja.com", "123123123");
        }
        else if (bundldeId.contains("stage")) {
            authLoginPage.enterLoginCredentials(driver, "therapist+5248@ja.com", "123123123");
        }
        else {
            //uLOCALDATA or SCRUBDATA
            authLoginPage.enterLoginCredentials(driver, "therapist+1@jojnts.com", "123123123");
        }
        therapistStartPage.sendAnInviteToPatientAndLogout(driver, firstName, lastName, userEmail);
        SoftAssert softAssertion= new SoftAssert();
        startPage.clickOnDebugButton(driver);
        debugPage.tapEmailSignupButton(driver, countryCode);
        patientCreateAccountPage.fillInTheAccount(driver, firstName, lastName, userEmail,userPassword);
        gdprPage.acceptGdpr(driver);
        questionOnePage.startTheQuestionary(driver, countryCode);
        LOG.info(" the therapist first name is " + therapistStartPage.getTherapistFirstName());
        LOG.info(" the therapist last name is " + therapistStartPage.getTherapistLastName());
        patientMobileMainPage.clickOkayToTalkWitPhysiotherapist(driver);
        patientMobileMainPage.clickShowMyProfile(driver);
        patientMobileShowProfilePage.clickOnMyAccountSettings(driver);
        patientMobileShowProfileSettingsPage.deleteMyAccount(driver);
        softAssertion.assertEquals(questionOnePage.getTherapistFirstName(), therapistStartPage.getTherapistFirstName(),"Not the same therapist who sent the invite");
        softAssertion.assertEquals(questionOnePage.getTherapistLastName(), therapistStartPage.getTherapistLastName(),"Not the same therapist who sent the invite");
        softAssertion.assertAll();
    }

    @DataProvider(name="invitePatientsNO")
    public Object[][] getPatientsToInviteData() {
        return new Object[][] {
                {"SE", "Sven", "Svan","Sven.Svan@test.se", "+46777174301"},
        };
    }
}
