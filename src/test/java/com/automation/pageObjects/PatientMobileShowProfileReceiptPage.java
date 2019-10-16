package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.clickBack;
import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class PatientMobileShowProfileReceiptPage {
    private static final Logger LOG = Logger.getLogger(PatientMobileShowProfileReceiptPage.class.getName());
    protected AppiumDriver driver;

    public PatientMobileShowProfileReceiptPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String _receiptCss = "div.invoices__entry";
    @FindBy(css = _receiptCss)
    private WebElement _receipt;

    public Boolean checkForReceipt(final AppiumDriver driver) {
        LOG.info("Check for receipt");
        return waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _receiptCss, 3);
    }

    private static final String _backButtonCss = "div.top-nav-bar__back";
    @FindBy(css = _backButtonCss)
    private WebElement _backButton;

    public void clickBackButton(final AppiumDriver driver) {
        LOG.info("Click back buttton");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _backButtonCss, 2);
        clickBack(driver);
    }
}
