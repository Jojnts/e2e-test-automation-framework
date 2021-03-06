package com.automation.pageObjects;

import com.automation.helpFunctions.HelpFunctions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.waitForNextViewToBeLoaded;
import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class DebugPage{
    private static final Logger LOG = Logger.getLogger(DebugPage.class.getName());

    protected AppiumDriver driver;

    public DebugPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String _checkBoxCss = "div.check-box__check"; //check-box--checked
    @FindBy(css = _checkBoxCss)
    private WebElement _checkBox;

    @FindBy(css = "div#SE")
    private WebElement _radioButtonSE;

    @FindBy(css = "div#US")
    private WebElement _radioButtonUS;

    @FindBy(css = "div#NO")
    private WebElement _radioButtonNO;

    @FindBy(css = "input[name='baseUrl']")
    private WebElement _url;

    @FindBy(css = "button.debug-settings__button")
    private List<WebElement> _buttonList;;

    private void clickToCheckTheDebugValuesAndSeRadioButton(final AppiumDriver driver, String countryCode) {
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _checkBoxCss, 2);
        Boolean found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, "div.check-box--checked", 1);
        LOG.info("Select the debug check box");
        if (!found){_checkBox.click();}
        switch (countryCode.toLowerCase()) {
            case "se":
                _radioButtonSE.click();
                break;
            case "us":
                _radioButtonUS.click();
                break;
            case "no":
                _radioButtonNO.click();
                break;
            default:
                _radioButtonSE.click();
                break;
        }
        String url = driver.getCapabilities().getCapability("testEnvUrl").toString();
        if(url.compareTo("")!=0) {
            _url.clear();
            _url.sendKeys(url);
            _buttonList.get(0).click();  //reload button
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //waitForNextViewToBeLoaded(4000);
        _checkBox.click();
    }
    public void tapEmailLoginButton(final AppiumDriver driver, String countryCode) {
        LOG.info("Tap on Email Login button");
        clickToCheckTheDebugValuesAndSeRadioButton(driver, countryCode);
        _buttonList.get(2).click();
    }

    public void tapEmailSignupButton(final AppiumDriver driver, String countryCode) {
        LOG.info("Tap on Email Signup button");
        clickToCheckTheDebugValuesAndSeRadioButton(driver, countryCode);
        _buttonList.get(3).click();
        String cc = countryCode.toLowerCase();
        if(cc.compareTo("us") == 0) {
            ZipCodePage zip = new ZipCodePage(driver);
            zip.enterTheZipCode(driver, countryCode);
        }
    }
}
