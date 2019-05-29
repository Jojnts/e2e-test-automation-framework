package com.automation.testCases.Signup;

import com.automation.config.BaseTest;
import org.testng.annotations.Test;

import java.util.logging.Logger;

public class SignUpTest extends BaseTest {
     private final Logger LOG = Logger.getLogger(SignUpTest.class.getName());

    @Test(groups = "signupAndDelete")
    public void createAccountInTheSignupFlowAndThenDeactivateTheAccount(){
        startPage.clickOnDebugButton(driver);
        debugPage.clickToCheckTheDebugValuesAndSeRadioButton(driver);
        debugPage.tapTheButtonSignup(driver);
        questionOnePage.startTheQuestionary(driver);
        authSignupPage.createAccount(driver, "hoppsan@tre.com", "12345678" );
        patientOnboardingPage.finnishTheOnboarding(driver);
        patientMobileMainPage.clickOkayToTalkWitPhysiotherapist(driver);
        patientMobileMainPage.clickShowMyProfile(driver);
        patientMobileShowProfilePage.clickOnMyAccountSettings(driver);
        patientMobileShowProfileSettingsPage.deleteMyAccount(driver);
    }
}
