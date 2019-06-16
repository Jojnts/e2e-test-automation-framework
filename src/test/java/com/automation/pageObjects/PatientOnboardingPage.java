package com.automation.pageObjects;

import com.automation.helpFunctions.HelpFunctions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.logging.Logger;

public class PatientOnboardingPage{
    private static final Logger LOG = Logger.getLogger(PatientOnboardingPage.class.getName());
    protected AppiumDriver driver;
    protected WebDriverWait wait;

    public PatientOnboardingPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String _firstNameCss = "input[name='first_name']";
    @FindBy(css = _firstNameCss)
    private WebElement _firstName;

    @FindBy(css = "input[name='last_name']")
    private WebElement _lastName;

    @FindBy(css = "select[name='year']")
    private WebElement _yearList;

    @FindBy(css = "select[name='month']")
    private WebElement _monthList;

    @FindBy(css = "select[name='day']")
    private WebElement _dayList;

    @FindBy(css = "input[name='phone_number']")
    private WebElement _phoneNumber;

    @FindBy(css = "button.button.last-step__continue")
    private WebElement _continueButton;


    public void finnishTheOnboarding(final AppiumDriver driver) {
        LOG.info("Start typing the wrong login credentials");
        HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _firstNameCss, 0);
        _firstName.sendKeys("Test");
        _lastName.sendKeys("Testis");
        Select oSelect = new Select(_yearList);
        List <WebElement> elementCount = oSelect.getOptions();
        LOG.info("In yearOption " + elementCount.get(20).getText());
        elementCount.get(80).click();
        oSelect = new Select(_monthList);
        elementCount = oSelect.getOptions();
        elementCount.get(4).click(); ///).get(3).click();
        oSelect = new Select(_dayList);
        elementCount = oSelect.getOptions();
        elementCount.get(10).click(); //.get(4).click();
        _phoneNumber.sendKeys("0701223344");
        _continueButton.click();
    }
}
