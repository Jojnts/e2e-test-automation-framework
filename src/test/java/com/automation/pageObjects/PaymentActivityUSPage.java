package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.waitForNextViewToBeLoaded;
import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class PaymentActivityUSPage {
    private static final Logger LOG = Logger.getLogger(PaymentActivityUSPage.class.getName());

    protected AppiumDriver driver;

    public PaymentActivityUSPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    private static final String  _areCoveredCss = "div[translate='UNLOCK_TREATMENT.COVERED_DIALOG.HEADER']";
    @FindBy(css = _areCoveredCss)  //popup you are covered
    private WebElement _areCovered;

    private static final String  _goToPaymentButtonCss = "button.general-dialog__button";
    @FindBy(css = _goToPaymentButtonCss)
    private WebElement _goToPaymentButton;

    private static final String  _okButtonCss = "button.general-dialog__button";
    @FindBy(css = _okButtonCss)  //popup you are covered
    private WebElement _okButton;

    private static final String  _continueButtonCss = "button.button.mobile-activity-footer__button";
    @FindBy(css = _continueButtonCss)
    private WebElement _continueButton;

    private static final String  _insuranceCompanyFieldCss = "input#companies";
    @FindBy(css = _insuranceCompanyFieldCss)
    private WebElement _insuranceCompanyField;



    public void inputInsuranceCompanyNotOk(AppiumDriver driver) {
        LOG.info("Chose insurance ");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _insuranceCompanyFieldCss, 2);
        _insuranceCompanyField.clear();
        _insuranceCompanyField.sendKeys("Health New England");
        waitForNextViewToBeLoaded(1500);
        _continueButton.click();
    }

    public void inputInsuranceCompanyOk(AppiumDriver driver) {
        LOG.info("Chose insurance ");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _insuranceCompanyFieldCss, 2);
        _insuranceCompanyField.clear();
        _insuranceCompanyField.sendKeys("Humana");
        waitForNextViewToBeLoaded(1500);
        _continueButton.click();
    }
}