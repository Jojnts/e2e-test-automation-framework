package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.waitForNextViewToBeLoaded;
import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class PaymentInsuranceActivityUSPage {
    private static final Logger LOG = Logger.getLogger(PaymentInsuranceActivityUSPage.class.getName());

    protected AppiumDriver driver;

    public PaymentInsuranceActivityUSPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String  _tryAgainButtonCss = "button.general-dialog__button";
    @FindBy(css = _tryAgainButtonCss)
    private WebElement _tryAgainButton;

    private static final String  _continueButtonCss = "button.button.mobile-activity-footer__button";
    @FindBy(css = _continueButtonCss)
    private WebElement _continueButton;

    private static final String  _memberFieldCss = "input#companies";
    @FindBy(css = _memberFieldCss)
    private WebElement _memberField;

    public void inputInsurance(AppiumDriver driver) {
        LOG.info("Chose insurance ");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _memberFieldCss, 2);
        _memberField.clear();
        _memberField.sendKeys("123123");
        waitForNextViewToBeLoaded(1500);
        _continueButton.click();
    }
}