package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.waitForNextViewToBeLoaded;
import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class ZipCodePage {
    private static final Logger LOG = Logger.getLogger(ZipCodePage.class.getName());

    protected AppiumDriver driver;

    public ZipCodePage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String  _zipCodeCss = "input[name='zip_code']";
    @FindBy(css = _zipCodeCss)
    private WebElement _zipCode;

    private static final String  _inputNameHeaderCss = "div.zipcode__input-title";
    @FindBy(css = _inputNameHeaderCss)
    private WebElement _inputNameHeader;


    @FindBy(css = "button.button")
    private WebElement _nextButton;

    public void enterTheZipCode(final AppiumDriver driver, String countryCode) {
        String cc = countryCode.toLowerCase();
        if(cc.compareTo("us") == 0) {
            LOG.info("Enter the zip code");
            //_inputNameHeader.click();
            waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _zipCodeCss, 5);
            _zipCode.sendKeys("90001");
            _nextButton.click();
            waitForNextViewToBeLoaded(1000);
        }
    }
}