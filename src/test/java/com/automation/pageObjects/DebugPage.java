package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class DebugPage{
    private static final Logger LOG = Logger.getLogger(DebugPage.class.getName());

    protected AppiumDriver driver;

    public DebugPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String _checkBoxCss = "div.check-box__check";
    @FindBy(css = _checkBoxCss)
    private WebElement _checkBox;

    @FindBy(css = "div#SE")
    private WebElement _radioButtonSE;

    @FindBy(css = "button.debug-settings__button")
    private List<WebElement> _buttonList;;

    public void clickToCheckTheDebugValuesAndSeRadioButton(final AppiumDriver driver) {
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _checkBoxCss);
        LOG.info("Select the debug check box");
        _checkBox.click();
        _radioButtonSE.click();
    }
    public void tapTheButtonLogin(final AppiumDriver driver) {
        LOG.info("Select the debug check box");
        _checkBox.click();
        _radioButtonSE.click();
        _buttonList.get(2).click();
    }

    public void tapTheButtonSignup(final AppiumDriver driver) {
        LOG.info("Select the debug check box");
        _checkBox.click();
        _radioButtonSE.click();
        _buttonList.get(3).click();
    }
}
