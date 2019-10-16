package com.automation.testCases.Payment;

import com.automation.config.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.logging.Logger;

public class PaymentCardAcceptedTest extends BaseTest {
     private final Logger LOG = Logger.getLogger(PaymentCardAcceptedTest.class.getName());

    enum choose3ds {
        Confirm,
        Fail,
        No3dVerification
    }

     enum cardStatus {
        Card_declined,
        Card_succeed
    }

    @Test(dataProvider = "userPerCountryAndFulfilledPayment")
    public void signupFlowAndSucceededPaymentAndDeactivateTheAccount(String countryCode, String firstName, String lastName,String userEmail, String userPassword, String cardNumber,Enum choice, Enum cardValid ){
        SoftAssert softAssertion= new SoftAssert();
        startPage.clickOnDebugButton(driver);
        debugPage.tapEmailSignupButton(driver, countryCode);
        patientCreateAccountPage.fillInTheAccount(driver, firstName, lastName, userEmail,userPassword);
        gdprPage.acceptGdpr(driver);
        questionOnePage.startTheQuestionary(driver, countryCode);
        patientMobileMainPage.clickOkayToTalkWitPhysiotherapist(driver);
        patientMobileMainPage.clickStartPayment(driver);
        paymentMethodPage.useCard(driver);
        paymentPage.cardInputsAndPay(driver, cardNumber);
        softAssertion.assertEquals(paymentPage.getCardNumber(driver),"**** **** **** " + cardNumber.substring(cardNumber.length() - 4),"The card number is not correct");
        softAssertion.assertTrue(paymentPage.confirmPopup3ds(driver, String.valueOf(choice), String.valueOf(cardValid)),"Not correct payment feedback message");
        paymentPage.clickButtonCancel(driver);
        patientMobileMainPage.clickShowMyProfile(driver);
        patientMobileShowProfilePage.clickOnReceipt(driver);
        softAssertion.assertTrue(patientMobileShowProfileReceiptPage.checkForReceipt(driver),"No receipt found");
        patientMobileShowProfileReceiptPage.clickBackButton(driver);
        patientMobileShowProfilePage.clickOnPayment(driver);
        softAssertion.assertEquals( patientMobileShowProfilePaymentPage.getCardNumber(driver),"**** **** **** " + cardNumber.substring(cardNumber.length() - 4), "The card number is not correct");
        patientMobileShowProfilePaymentPage.clickBackButton(driver);
        patientMobileMainPage.clickShowMyProfile(driver);
        patientMobileShowProfilePage.clickOnMyAccountSettings(driver);
        patientMobileShowProfileSettingsPage.deleteMyAccount(driver);
        softAssertion.assertAll();
    }

    @DataProvider(name="userPerCountryAndFulfilledPayment")
    public Object[][] getDataFulfilledPayment() {
        return new Object[][] {
                {"SE", "Required", "3D Secure 2 reg. authentication", "algot@android1.se", "123123123", "4000000000003220", choose3ds.Confirm, cardStatus.Card_succeed},
                {"SE", "Required", "3D Secure req. authentication", "algot@android2.com", "123123123", "4000000000003063", choose3ds.Confirm, cardStatus.Card_succeed},
                {"SE", "Supported", "3D Secure not req. authenticatio", "algot@android3.com", "123123123", "4000000000003055", choose3ds.Confirm, cardStatus.Card_succeed},
                {"SE", "Supported", "Card is not enrolled in 3D Secure.", "algot@android4.com", "123123123", "4242424242424242", choose3ds.Confirm, cardStatus.Card_succeed},
                {"SE", "notSuppored", "3D Secure is not supported", "algot@android5.com", "123123123", "378282246310005", choose3ds.Confirm, cardStatus.Card_succeed}
        };
    }
}
