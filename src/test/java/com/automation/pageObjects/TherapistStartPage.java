package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.Capabilities;

import java.util.List;
import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.waitForNextViewToBeLoaded;
import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;


public class TherapistStartPage {
    private static final Logger LOG = Logger.getLogger(TherapistStartPage.class.getName());

    protected AppiumDriver driver;

    public TherapistStartPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    static final String  _topNavListCss = "li.app-nav__item";
    @FindBy(css = _topNavListCss)
    public List<WebElement> _topNavList;

    static final String  _inviteCss = ".patient-list__invite-link";
    @FindBy(css = _inviteCss)
    public WebElement _invite;

    static final String  _textInputListCss = ".margin-bottom-tiny";
    @FindBy(css = _textInputListCss)
    public List<WebElement> _textInputList;

    static final String  _headerCss = "div.fullscreen-header__title";
    @FindBy(css = _headerCss)
    public WebElement _header;

    @FindBy (css = "button.button.invite-user__button")
    public List<WebElement> _inviteButton;


    static final String  _dropDownMenuCss = "div.app-nav__dropdown";
    @FindBy(css = _dropDownMenuCss)
    public WebElement _dropDownMenu;

    static final String  _logoutCss = ".app-nav__link";
    @FindBy(css = _logoutCss)
    public List<WebElement> _logout;

    private void navigateToPatient(final AppiumDriver driver) {
        LOG.info("Navigate to patient tab");
        boolean found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _topNavListCss, 10);
        if (found) {
            _topNavList.get(1).click();
        } else {
            throw new ElementNotVisibleException("Top menu not found");
        }
    }

    public void sendAnInviteToPatientAndLogout(final AppiumDriver driver, String firstName, String lastName, String userEmail) {
        navigateToPatient(driver);
        LOG.info("Sen invitation to a patient");
        boolean found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _inviteCss, 10);
        if (found) {
            _invite.click();
        } else {
            throw new ElementNotVisibleException("The dorpdown menu was not found");
        }
        waitForNextViewToBeLoaded(1000);
        //_header.click();

        _textInputList.get(3).sendKeys(firstName);
        _textInputList.get(4).sendKeys(lastName);
        Capabilities cap = driver.getCapabilities();
        String deviceNamefourCharacters = cap.getCapability("deviceName").toString().substring(0, 3);
        _textInputList.get(5).sendKeys(deviceNamefourCharacters + userEmail);
        waitForNextViewToBeLoaded(1500);
        LOG.info(" te size of invote buttons " + _inviteButton.size());
        _inviteButton.get(1).click();
        int coumnter = 0;
        while ( coumnter < 2) { //a bugg in the app, nned to click twise
            coumnter++;
            waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _dropDownMenuCss, 10);
            _dropDownMenu.click();
            waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _dropDownMenuCss, 5);
            LOG.info(" te size of div.app-nav__dropdown " + _logout.size());
            //_logout.get(1).click();
            //_logout.get(3).click();
            _logout.get(4).click();
            //_logout.get(5).click();
            //_logout.get(6).click();
        }
    }
}
