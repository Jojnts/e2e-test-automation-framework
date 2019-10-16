package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.clickBack;
import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class PatientMobileShowProfilePaymentPage {
    private static final Logger LOG = Logger.getLogger(PatientMobileShowProfilePaymentPage.class.getName());
    protected AppiumDriver driver;

    public PatientMobileShowProfilePaymentPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String _cardNumberCss = "div.payment-details__text";
    @FindBy(css = _cardNumberCss)
    private WebElement _cardNumber;

    public String getCardNumber(final AppiumDriver driver) {
        LOG.info("Get the cad number");
        Boolean found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _cardNumberCss, 5);
        if (found) {
            LOG.info("The card number is " + _cardNumber.getText());
            return _cardNumber.getText();
        }
        return("no card displayed");
    }

    private static final String _backButtonCss = "div.top-nav-bar__back";
    @FindBy(css = _backButtonCss)
    private WebElement _backButton;

    public void clickBackButton(final AppiumDriver driver) {
        LOG.info("Click back buttton");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _backButtonCss, 0);
        clickBack(driver);
    }
}
