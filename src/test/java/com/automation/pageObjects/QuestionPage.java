package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.logging.Logger;
import static com.automation.helpFunctions.HelpFunctions.waitForNextViewToBeLoaded;
import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class QuestionPage {
    private static final Logger LOG = Logger.getLogger(QuestionPage.class.getName());

    protected AppiumDriver driver;

    public QuestionPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    static final String  _therapistButtonCss = "button.therapist__button";
    @FindBy(css = _therapistButtonCss)
    private WebElement _therapistButton;

    @FindBy(css = "div#radio--left_hip")
    private WebElement _leftHip;

    @FindBy(css = "div#radio--true")
    private WebElement _radioTrue;

    @FindBy(css = "div#radio--false")
    private WebElement _radioFalse;

    @FindBy(css = ".vas-scale__label")
    private List<WebElement> _scaleList;

    @FindBy(css = "button.question__button.question__button--first")
    private WebElement _nextButton;

    @FindBy(css = "div#gender-female")
    private WebElement _genderFemale;

    @FindBy(css = "div#gender-male")
    private WebElement _genderMale;

    @FindBy(css = "input[name='weight']")
    private WebElement _weight;

    @FindBy(css = "input[name='centimeters'")
    private WebElement _lengh;

    @FindBy(css = "textarea.symptoms__input-textarea")
    private WebElement _textArea;

    @FindBy(css = "div.check-box__check")
    private List<WebElement> _checkBoxList;


    public void startTheQuestionary(final AppiumDriver driver) {
        LOG.info("Start the question flow");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _therapistButtonCss);
        //1 Du kan börja din artrosbehandling direkt?
        _therapistButton.click();
        waitForNextViewToBeLoaded(1500);
        //2 vilken ledbesvärar dig mest?
        _leftHip.click();
        waitForNextViewToBeLoaded(1500);
        //3 Har du fått diagnos artros i din vänstra höft tidigare?
        _radioFalse.click();
        waitForNextViewToBeLoaded(1500);
        //4 Har du smärta i din vänstra höft vi rörelser eller belastning?
        _radioTrue.click();
        waitForNextViewToBeLoaded(1500);
        //5 Upplever du stelhet på morgonen, som går över efter en timme eller mindre?
        _radioTrue.click();
        waitForNextViewToBeLoaded(1500);
        //6 Har du, eller har du haft, ett arbete som medför stående, gående eller roterande uppgifter?
        _radioTrue.click();
        waitForNextViewToBeLoaded(1500);
        //7 Har du vid något tillfälle skadat din vänstra höft så allvarligt att du uppsökt läkare?
        _radioFalse.click();
        waitForNextViewToBeLoaded(1500);
        //8 Markera den ruta som motsvarar din genomsnittliga smärta från din vänstrahöft den senaste veckan.
        _scaleList.get(8).click();
        _nextButton.click();
        waitForNextViewToBeLoaded(2000);
        //9 Vilket är ditt kön?
        _genderMale.click();
        waitForNextViewToBeLoaded(2000);
        //10 Weighr and _lengh
        _weight.sendKeys("145");
        _lengh.sendKeys("167");
        waitForNextViewToBeLoaded(1500);
        _nextButton.click();
        waitForNextViewToBeLoaded(2000);
        //11 Nedan kan du beskriva dina symptom och informera oss om allt gällande din hälsa
        _textArea.sendKeys("Det började en gång med att .....");
        _nextButton.click();
        waitForNextViewToBeLoaded(2000);
        //12 Godkänn ... läs igenom våra villkor innan du fortsätter.
        _checkBoxList.get(0).click();
        _checkBoxList.get(1).click();
        _nextButton.click();
        waitForNextViewToBeLoaded(3500);
    }
}