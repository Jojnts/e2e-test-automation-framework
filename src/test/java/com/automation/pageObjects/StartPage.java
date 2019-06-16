package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;


public class StartPage {
    private static final Logger LOG = Logger.getLogger(StartPage.class.getName());

    protected AppiumDriver driver;

    public StartPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    static final String  _buttonListCss = "button.registration__debug-settings-button";
    @FindBy(css = _buttonListCss)
    public List<WebElement> _buttonList;

    public void clickOnDebugButton(final AppiumDriver driver) {
        LOG.info("Click Debug button");
        boolean found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _buttonListCss, 0);
        if (found) {
            _buttonList.get(0).click();
        }else {
            throw new NotFoundException();
        }

    }
}
