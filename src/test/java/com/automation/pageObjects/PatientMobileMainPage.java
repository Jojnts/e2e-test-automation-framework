package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class PatientMobileMainPage {
    private static final Logger LOG = Logger.getLogger(PatientMobileMainPage.class.getName());
    protected AppiumDriver driver;
    protected WebDriverWait wait;

    public PatientMobileMainPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String _showProfileButtonCss = "div.app-top-bar__profile";
    @FindBy(css = _showProfileButtonCss)
    private WebElement _showProfileButton;

    private static final String _talkToPhysiotherapistButtonCss = "button.button.button--outlined-white";
    @FindBy(css = _talkToPhysiotherapistButtonCss)
    private WebElement _talkToPhysiotherapistButton;


    public void clickShowMyProfile(final AppiumDriver driver) {
        LOG.info("Show my profile");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _showProfileButtonCss, 5);
       _showProfileButton.click();
    }

    public void clickOkayToTalkWitPhysiotherapist(final AppiumDriver driver) {
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _showProfileButtonCss, 5);
        LOG.info("click message from Therapist");
        try {
            if (waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _talkToPhysiotherapistButtonCss, 3)) {
                _talkToPhysiotherapistButton.click();
            }
        } catch (Exception e) {
            //do nothing
        }
    }

    private static final String _buttonStartCss = "div.activity-card-mobile__content";
    @FindBy(css = _buttonStartCss)
    private WebElement _buttonStart;

    public void clickStartPayment(final AppiumDriver driver){
        LOG.info("Click Start for payment");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _buttonStartCss, 5);
        _buttonStart.click();
    }
}
