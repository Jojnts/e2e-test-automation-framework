package com.automation.pageObjects;

import com.automation.helpFunctions.HelpFunctions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.logging.Logger;

public class PatientMobileShowProfilePage {
    private static final Logger LOG = Logger.getLogger(PatientMobileShowProfilePage.class.getName());
    protected AppiumDriver driver;
    protected WebDriverWait wait;

    public PatientMobileShowProfilePage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String _profileSettingsListCss = "button.mobile-profile__item.mobile-profile__item--chevron";
    @FindBy(css = _profileSettingsListCss)
    private List<WebElement> _profileSettingsList;

    @FindBy(css = "button.button.mobile-profile__logout-button")
    private WebElement _logoutButton;


    public void clickOnMyAccountSettings(final AppiumDriver driver) {
        LOG.info("Start to delete my account");
        HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _profileSettingsListCss, 0);
        _profileSettingsList.get(0).click();
    }
}
