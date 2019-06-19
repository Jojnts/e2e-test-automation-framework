package com.automation.pageObjects;

import com.automation.helpFunctions.HelpFunctions;
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
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _showProfileButtonCss, 0);
       _showProfileButton.click();
    }

    public void clickOkayToTalkWitPhysiotherapist(final AppiumDriver driver) {
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _showProfileButtonCss, 0);
        LOG.info("click message from Therapist");
        try {
            if (HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _talkToPhysiotherapistButtonCss, 5)) {
                _talkToPhysiotherapistButton.click();
            }
        } catch (Exception e) {
            //do nothing
        }
    }

    private void waitForPageToBeLoaded() {
        Integer i = 0;
        while (i < 20 ) {
            LOG.info("Loading the page");
            try {
                i++;
                if (_talkToPhysiotherapistButton.isDisplayed()) {
                    i = 20;
                }
            }catch (Exception e) {
                LOG.info("The loop is ongoing " + i);
                if(i== 20) {
                    throw e;
                }
            }
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            LOG.info(_talkToPhysiotherapistButton + "  not found in waitForPageToBeLoaded ");
        }
    }
}
