package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class PaymentMethodPage {
    private static final Logger LOG = Logger.getLogger(PaymentMethodPage.class.getName());

    protected AppiumDriver driver;

    public PaymentMethodPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String  _GoToPayCss = ".button.button--dark-sky-blue.payment-fee__button";
    @FindBy(css = _GoToPayCss)
    private WebElement _GoToPayButton;

    private static final String  _FrikortYesCss = "#radio--true";
    @FindBy(css = _FrikortYesCss)
    private WebElement _FrikortYesButton;

    private static final String  _FrikortNoCss = "#radio--false";
    @FindBy(css = _FrikortNoCss)
    private WebElement _FrikortNoButton;

    @FindBy(css = "div.check-box__check")
    private List<WebElement> _checkBoxList;


    public void useCard(final AppiumDriver driver) {
        LOG.info("Have no frikort");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _FrikortNoCss, 5);
        _FrikortNoButton.click();
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _GoToPayCss, 5);
        _GoToPayButton.click();
    }

}