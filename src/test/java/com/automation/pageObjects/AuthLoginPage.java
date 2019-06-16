package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.waitForNextViewToBeLoaded;
import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class AuthLoginPage {
    private static final Logger LOG = Logger.getLogger(AuthLoginPage.class.getName());
    protected AppiumDriver driver;
    protected WebDriverWait wait;

    public AuthLoginPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String _emailLabeCss = "div.email__label";
    @FindBy(css = _emailLabeCss)
    private WebElement _emailLabel;

    @FindBy(css = "[name=email]")
    private WebElement _emailField;

    @FindBy(css = "[name=password]")
    private WebElement _passwordField;

    @FindBy(css = "button.spinner-button")
    private WebElement _loginButton;

    private static final String _errorMessageCss = "div.email__error";
    @FindBy(css = _errorMessageCss)
    private WebElement _errorMessage;

    public void enterLoginCredentials(final AppiumDriver driver, String userEmail, String userPassword) {
        LOG.info("Start typing the login credentials");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _emailLabeCss, 0);
        _emailLabel.click();
        _emailField.clear();
        _emailField.sendKeys(userEmail);
        _passwordField.clear();
        _passwordField.sendKeys(userPassword);
        waitForNextViewToBeLoaded(1500);
        _loginButton.click();
    }

    public String errorMessageTextIs(final AppiumDriver driver) {
        LOG.info("Get the error message to be verified ");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _errorMessageCss, 0);
        return _errorMessage.getText();
    }
}
