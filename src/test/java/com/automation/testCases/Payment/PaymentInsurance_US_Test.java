package com.automation.testCases.Payment;

import com.automation.config.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.logging.Logger;

public class PaymentInsurance_US_Test extends BaseTest {
     private final Logger LOG = Logger.getLogger(PaymentInsurance_US_Test.class.getName());

    enum choose3ds {
        Confirm,
        Fail,
        No3dVerification
    }

     enum cardStatus {
        Card_declined,
        Card_succeed
    }

    @Test(priority = -5, dataProvider = "userPerCountry", groups = "signupAndDelete")
    public void createAccountInTheSignupFlow(String countryCode, String firstName, String lastName,String userEmail, String userPassword){
        SoftAssert softAssertion= new SoftAssert();
        startPage.clickOnDebugButton(driver);
        debugPage.tapEmailSignupButton(driver, countryCode);
        patientCreateAccountPage.fillInTheAccount(driver, firstName, lastName, userEmail,userPassword);
        gdprPage.acceptGdpr(driver);
        questionOnePage.startTheQuestionary(driver, countryCode);
        patientMobileMainPage.clickOkayToTalkWitPhysiotherapist(driver);
        patientMobileMainPage.clickStartPayment(driver);
        paymentMethodPage.useInsurance(driver);
        softAssertion.assertAll();
    }

    @DataProvider(name="userPerCountry")
    public Object[][] getData() {
        return new Object[][] {
                {"US", "Testus", "test","Testus.test@testus.com", "123123123"}
        };
    }

    @Test (priority = -5, dependsOnMethods = {"createAccountInTheSignupFlow" }, dataProvider = "userPerCountryAndFailedPayment")
    public void messageForFailedPayment(String countryCode, String cardNumber, String cardDescr, Enum choice, Enum cardValid  ) {
        SoftAssert softAssertion = new SoftAssert();
        paymentActivityUSPage.inputInsuranceCompanyNotOk(driver);
        paymentPage.cardInputsAndPay(driver, cardNumber);
        softAssertion.assertTrue(paymentPage.confirmPopup3ds(driver, String.valueOf(choice), String.valueOf(cardValid)), "Not correct payment feedback message");
        paymentPage.clickButtonChangeCard(driver);
        softAssertion.assertAll();
    }
    @Test (priority = -5, dependsOnMethods = {"messageForFailedPayment" })
    public void afterForFailedPayment(){
        paymentPage.clickCrossButton(driver);
        paymentPage.clickButtonCancel(driver);
        patientMobileMainPage.clickShowMyProfile(driver);
        patientMobileShowProfilePage.clickOnMyAccountSettings(driver);
        patientMobileShowProfileSettingsPage.deleteMyAccount(driver);
    }


    @DataProvider(name="userPerCountryAndFailedPayment")
    public Object[][] getDataFailedPayment() {
        return new Object[][] {
                {"US", "4100000000000019", "The charge is blocked as it's considered fraudulent", choose3ds.No3dVerification, cardStatus.Card_declined},
                {"US", "4000000000000119", "Charge is declined with a processing_error code", choose3ds.No3dVerification, cardStatus.Card_declined}
        };
    }
    //Betalningen nekades. Kontrollera att ditt kort är öppet för internetköp eller kontakta din bank för mer information.
    // 4000008400001629 4100000000000019 4000000000000002 4000000000009995 4000000000009987 4000000000009979 4000000000000069 4000000000000127
    //Betalningen misslyckades. Var god försök igen eller kontakta Joint Academy om problem kvarstår.
    //4000000000000119 4000000000003220(when clicking fail)
}
