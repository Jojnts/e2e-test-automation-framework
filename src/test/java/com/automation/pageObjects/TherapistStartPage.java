package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static com.automation.helpFunctions.HelpFunctions.waitForNextViewToBeLoaded;
import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;


public class TherapistStartPage {
    private static final Logger LOG = Logger.getLogger(TherapistStartPage.class.getName());
    private String therapistFirstName;
    private String therapistLastName;
    protected AppiumDriver driver;

    public TherapistStartPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String getTherapistFirstName () { return this.therapistFirstName; }

    public String getTherapistLastName () {
        return this.therapistLastName;
    }

    private static final String  _topNavListCss = "ul.app-nav__list.app-nav__list--main li.app-nav__item  a.app-nav__link--padding-xs";
    @FindBy(css = _topNavListCss)
    public List<WebElement> _topNavList;

    private static final String  _patientTabCss = "ul.app-nav__list.app-nav__list--main li.app-nav__item a.app-nav__link--active";
    @FindBy(css = _patientTabCss)
    public WebElement _patientTab;

    private static final String  _inviteCss = "invite-link.patient-list__invite-link icon-link div.icon-link";
    @FindBy(css = _inviteCss)
    public WebElement _invite;

    private static final String  _firstNameCss = "fullscreen-popup.invite-user__mobile [name='first_name']";
    @FindBy(css = _firstNameCss)
    public WebElement _firstName;

    private static final String  _lastNameCss = "fullscreen-popup.invite-user__mobile [name='last_name']";
    @FindBy(css = _lastNameCss)
    public WebElement _lastName;

    private static final String  _emailCss = "fullscreen-popup.invite-user__mobile [name='email']";
    @FindBy(css = _emailCss)
    public WebElement _email;


    private static final String  _textInputListCss = "fullscreen-popup.invite-user__mobile input.ng-pristine";
    @FindBy(css = _textInputListCss)
    public List<WebElement> _textInputList;

    private static final String  _headerCss = "div.fullscreen-header__title";
    @FindBy(css = _headerCss)
    public WebElement _header;

    @FindBy (css = "button.button.invite-user__button")
    public List<WebElement> _inviteButton;

    private static final String _dropDownMenuChoicesCss = ".app-nav__link";
    @FindBy(css = _dropDownMenuChoicesCss)
    public List<WebElement> _dropDownMenuChoices;

    private void navigateToPatient(final AppiumDriver driver) {
        LOG.info("Navigate to patient tab");
        boolean found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _topNavListCss, 5);
        if (found) {
            _topNavList.get(1).click();
        } else {
            throw new ElementNotVisibleException("Top menu not found");
        }
    }

    private void getTheTherapistName() {
        Therapist therapist = new Therapist();
        String[] names =  therapist.getTherapistName();
        this.therapistFirstName =  names[0];
        this.therapistLastName = names[1];
    }

    public void sendAnInviteToPatientAndLogout(final AppiumDriver driver, String firstName, String lastName, String userEmail) {
        clickDropDownMenu();
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _dropDownMenuChoicesCss, 5);
        _dropDownMenuChoices.get(0).click();
        getTheTherapistName();
        navigateToPatient(driver);
        LOG.info("Send invitation to a patient");
        boolean found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _inviteCss, 5);
        if (found) {
            _invite.click();
        } else {
            throw new ElementNotVisibleException("The invite button was not found");
        }
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _textInputListCss, 5);
        _firstName.sendKeys(firstName);
        waitForNextViewToBeLoaded(500);
        _lastName.sendKeys(lastName);
        waitForNextViewToBeLoaded(500);
        Capabilities cap = driver.getCapabilities();
        String deviceNamefourCharacters = cap.getCapability("deviceBrand").toString().substring(0, 3);
        _email.sendKeys(deviceNamefourCharacters + userEmail);
        waitForNextViewToBeLoaded(500);
        _inviteButton.get(1).click();
        int counter = 0;
        while ( counter < 2) { //a bugg in the app, need to click twise
            counter++;
            clickDropDownMenu();
            waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _dropDownMenuChoicesCss, 4);
            _dropDownMenuChoices.get(4).click();
        }
    }

    private static final String  _myNameCss = "h3.profile-header__full-name";
    @FindBy(css = _myNameCss)
    public WebElement _myName;

    private static final String  _dropDownMenuCss = "div.app-nav__dropdown a.app-nav__dropdown__toggle";
    @FindBy(css = _dropDownMenuCss)
    public WebElement _dropDownMenu;

    private void clickDropDownMenu() {
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _dropDownMenuCss, 10);
        _dropDownMenu.click();
    }

    private class Therapist {
        private String[] getTherapistName() {
            LOG.info("Get the name of the Therapist");
            waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _myNameCss, 5);
            LOG.info("Therapist name " + _myName.getText());
            String regex = "\\s";
            Pattern pattern = Pattern.compile(regex);
            String[] result = pattern.split(_myName.getText());
            return result;
        }
    }
}
