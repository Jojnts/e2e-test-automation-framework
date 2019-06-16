package com.automation.pageObjects;

import com.automation.helpFunctions.HelpFunctions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.logging.Logger;

public class PatientCreateAccountPage {
    private static final Logger LOG = Logger.getLogger(PatientCreateAccountPage.class.getName());
    protected AppiumDriver driver;
    protected WebDriverWait wait;

    public PatientCreateAccountPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }



    private static final String _inputNameHeaderCss = "div.questions-information__input-header";
    @FindBy(css = _inputNameHeaderCss)
    private List<WebElement> _inputNameHeader;

    private static final String _firstNameCss = "input[name='first_name']";
    @FindBy(css = _firstNameCss)
    private WebElement _firstName;

    private static final String _lastNameCss = "input[name='last_name']";
    @FindBy(css = _lastNameCss)
    private WebElement _lastName;

    private static final String _emailCss = "input[name='email']";
    @FindBy(css = _emailCss)
    private WebElement _email;


    private static final String _passwordCss = "input[name='password']";
    @FindBy(css = _passwordCss)
    private WebElement _password;

    private static final String _phoneNumberCss = "input[name='phone_number']";
    @FindBy(css = _phoneNumberCss)
    private WebElement _phoneNumber;

    private static final String _continueButtonCss = "button.button";
    @FindBy(css = _continueButtonCss)
    private WebElement _continueButton;


    public void fillInTheAccount(final AppiumDriver driver, String firstName, String lastName, String userEmail, String userPassword) {
        LOG.info("Fill in the account tasks");
        HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _firstNameCss, 0);
        _phoneNumber.sendKeys("0701223344");
        _password.sendKeys(userPassword);
        _firstName.sendKeys(firstName);
        _lastName.sendKeys(lastName);
        //to have unique user emails per device in parallel testing
        Capabilities cap = driver.getCapabilities();
        String deviceNamefourCharacters = cap.getCapability("deviceName").toString().substring(0, 3);
        _email.sendKeys(deviceNamefourCharacters + userEmail);
        _inputNameHeader.get(3).click();
        driver.hideKeyboard();
        HelpFunctions.waitForNextViewToBeLoaded(3000);
        //_inputNameHeader.get(3).click();
        _continueButton.click();
    }
}
