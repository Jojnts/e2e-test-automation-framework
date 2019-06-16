package com.automation.pageObjects;

import com.automation.helpFunctions.HelpFunctions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.waitForNextViewToBeLoaded;

public class PatientMobileShowProfileSettingsPage {
    private static final Logger LOG = Logger.getLogger(PatientMobileShowProfileSettingsPage.class.getName());
    protected AppiumDriver driver;

    public PatientMobileShowProfileSettingsPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String _deleteAccountButtonCss = "icon-link.icon-link--delete";
    @FindBy(css = _deleteAccountButtonCss)
    private WebElement _deleteAccountButton;

    @FindBy(css = "button.button[translate='DEACTIVATE']")
    private WebElement _deactivateButton;


    public void deleteMyAccount(final AppiumDriver driver) {
        LOG.info("Start to delete my account");
        HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _deleteAccountButtonCss,0);
        _deleteAccountButton.click();
        waitForNextViewToBeLoaded(1000);
        _deactivateButton.click();
    }
}
