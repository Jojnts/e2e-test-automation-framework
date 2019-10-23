package com.automation.testCases.Payment;

import com.automation.config.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.logging.Logger;

public class PaymentCardDeclinedTest extends BaseTest {
     private final Logger LOG = Logger.getLogger(PaymentCardDeclinedTest.class.getName());

    enum choose3ds {
        Confirm,
        Fail,
        No3dVerification
    }

     enum cardStatus {
        Card_declined,
        Card_succeed
    }

    @Test (priority = -5, groups = {"groupCardDecline1"})
    public void beforeForFailedPayment( ){
        SoftAssert softAssertion = new SoftAssert();
        startPage.clickOnDebugButton(driver);
        debugPage.tapEmailSignupButton(driver, "SE");
        patientCreateAccountPage.fillInTheAccount(driver, "se", "se", "se@se.se","123123123");
        gdprPage.acceptGdpr(driver);
        questionOnePage.startTheQuestionary(driver, "SE");
        patientMobileMainPage.clickOkayToTalkWitPhysiotherapist(driver);
        patientMobileMainPage.clickStartPayment(driver);
        paymentMethodPage.useCard(driver);
        softAssertion.assertAll();
    }

    @Test (priority = -5, groups = {"groupCardDecline2"}, dependsOnGroups = { "groupCardDecline1" }, dataProvider = "userPerCountryAndFailedPayment")
    public void messageForFailedPayment(String countryCode, String cardNumber, String cardrdDescr, Enum choice, Enum cardValid  ) {
        SoftAssert softAssertion = new SoftAssert();
        paymentPage.cardInputsAndPay(driver, cardNumber);
        softAssertion.assertTrue(paymentPage.confirmPopup3ds(driver, String.valueOf(choice), String.valueOf(cardValid)), "Not correct payment feedback message");
        paymentPage.clickButtonChangeCard(driver);
        softAssertion.assertAll();
    }
    @Test (priority = -5, groups = {"groupCardDecline3"} , dependsOnGroups = { "groupCardDecline2" })
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
                {"SE", "4000000000003220", "", choose3ds.Fail, cardStatus.Card_succeed},
                {"SE", "4000008400001629", "", choose3ds.Confirm, cardStatus.Card_declined},
                {"SE", "4100000000000019", "The charge is blocked as it's considered fraudulent", choose3ds.No3dVerification, cardStatus.Card_declined},
                {"SE", "4000000000000002", "Charge is declined with a card_declined code", choose3ds.No3dVerification, cardStatus.Card_declined},
                {"SE", "4000000000009995", "The decline_code attribute is insufficient_funds", choose3ds.No3dVerification, cardStatus.Card_declined},
                {"SE", "4000000000009987", "The decline_code attribute is lost_card", choose3ds.No3dVerification, cardStatus.Card_declined},
                {"SE", "4000000000009979", "The decline_code attribute is stolen_card", choose3ds.No3dVerification, cardStatus.Card_declined},
                {"SE", "4000000000000069", "Charge is declined with an expired_card code", choose3ds.No3dVerification, cardStatus.Card_declined},
                {"SE", "4000000000000127", "Charge is declined with an incorrect_cvc code", choose3ds.No3dVerification, cardStatus.Card_declined},
                {"SE", "4000000000000119", "Charge is declined with a processing_error code", choose3ds.No3dVerification, cardStatus.Card_declined}
        };
    }
    //Betalningen nekades. Kontrollera att ditt kort är öppet för internetköp eller kontakta din bank för mer information.
    // 4000008400001629 4100000000000019 4000000000000002 4000000000009995 4000000000009987 4000000000009979 4000000000000069 4000000000000127
    //Betalningen misslyckades. Var god försök igen eller kontakta Joint Academy om problem kvarstår.
    //4000000000000119 4000000000003220(when clicking fail)
}
