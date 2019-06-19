package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.waitForNextViewToBeLoaded;
import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class GdprPage {
    private static final Logger LOG = Logger.getLogger(GdprPage.class.getName());

    protected AppiumDriver driver;

    public GdprPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String  __continueButtonCss = "button.spinner-button";
    @FindBy(css = __continueButtonCss)
    private WebElement _continueButton;

    @FindBy(css = "div.check-box__check")
    private List<WebElement> _checkBoxList;


    public void acceptGdpr(final AppiumDriver driver) {
        LOG.info("Start the question flow");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, __continueButtonCss, 0);
        // Godkänn ... läs igenom våra villkor innan du fortsätter.
        _checkBoxList.get(0).click();
        _checkBoxList.get(1).click();
        _continueButton.click();
        waitForNextViewToBeLoaded(3500);
    }
}