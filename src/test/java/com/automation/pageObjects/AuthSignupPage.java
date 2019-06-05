package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class AuthSignupPage {

    private static final Logger LOG = Logger.getLogger(AuthSignupPage.class.getName());

    protected AppiumDriver driver;
    protected WebDriverWait wait;

    public AuthSignupPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String _emailInputListCss = "input.email__field[name='email']";
    @FindBy(css = _emailInputListCss)
    private List<WebElement> _emailInputList;

    @FindBy(css = "input.email__field[name='password']")
    private WebElement _password;

    @FindBy(css = "input.email__field[name='password_confirmation']")
    private WebElement _passwordConfirmation;

    @FindBy(css = "button.spinner-button")
    private WebElement _creatAccountButton;

    public void createAccount(final AppiumDriver driver, String userEmail, String userPassword) {
        LOG.info("Create an account in the Signup flow");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _emailInputListCss);
        Capabilities cap = driver.getCapabilities();
        String deviceNamefourCharacters = cap.getCapability("deviceName").toString().substring(0, 3);
        //to have unique user names per device when signing up
        userEmail = deviceNamefourCharacters + userEmail ;
        _emailInputList.get(0).sendKeys(userEmail);
        _password.sendKeys(userPassword);
        _passwordConfirmation.sendKeys(userPassword);
        _creatAccountButton.click();
    }
}