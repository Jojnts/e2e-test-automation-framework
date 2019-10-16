package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static com.automation.helpFunctions.HelpFunctions.waitForNextViewToBeLoaded;
import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class QuestionPage {
    private static final Logger LOG = Logger.getLogger(QuestionPage.class.getName());
    private String therapistFirstName = "init";
    private String therapistLastName = "init";

    protected AppiumDriver driver;

    public QuestionPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String getTherapistFirstName() { return therapistFirstName; }

    public String getTherapistLastName() { return therapistLastName; }


    private static final String  _continueQuestionsCss = "button.button.question__button";
    @FindBy(css = _continueQuestionsCss)
    private WebElement _continueQuestions;

    private static final String  _welcomeQuestionsCss = "button.question__button";
    @FindBy(css = _welcomeQuestionsCss)
    private WebElement _welcomeQuestionsButton;

    @FindBy(css = "div#radio--left_hip")
    private WebElement _leftHip;

    @FindBy(css = "div#radio--true")
    private WebElement _radioTrue;

    @FindBy(css = "div#radio--false")
    private WebElement _radioFalse;

    @FindBy(css = ".vas-scale__label")
    private List<WebElement> _scaleList;

    @FindBy(css = "div#gender-female")
    private WebElement _genderFemale;

    @FindBy(css = "div#gender-male")
    private WebElement _genderMale;

    @FindBy(css = "div#suggestion-radio_0")
    private WebElement _timeForMeetingNo;

    @FindBy(css = "div#suggestion-radio_1")
    private WebElement _timeForMeetingYes;

    private static final String _meetingFinnishButtonCss = "button.spinner-button";
    @FindBy(css = _meetingFinnishButtonCss)
    private List<WebElement> _meetingFinnishButton;

    private static final String _registrationFinnishedButtonCss = "button.button.suggestion-done__button";
    @FindBy(css = _registrationFinnishedButtonCss)
    private WebElement _registrationFinnishedButton;

    private static final String _textAreaCommentCss = "textarea#comment";
    @FindBy(css = _textAreaCommentCss)
    private WebElement _textAreaComment;

    private static final String _thanksFromTherapistCss = "button.button.therapist-outro__button";
    @FindBy(css = _thanksFromTherapistCss)
    private WebElement _thanksFromTherapist;

    private void getTheNameOfTheTherapist() {
        Therapist therapist = new Therapist();
        String[] names =  therapist.getTherapistName();
        this.therapistFirstName =  names[0];
        this.therapistLastName = names[1];
    }


    public void startTheQuestionary(final AppiumDriver driver, String countryCode) {

        LOG.info("Start the question flow");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _welcomeQuestionsCss, 0);
        //getTheNameOfTheTherapist(); changed during my vaccation in July
        _welcomeQuestionsButton.click();
        waitForNextViewToBeLoaded(1000);
        _leftHip.click();
        waitForNextViewToBeLoaded(1000);
        //3 Har du fått diagnos artros i din vänstra höft tidigare?
        _radioFalse.click();
        waitForNextViewToBeLoaded(1000);
        //4 Har du smärta i din vänstra höft vi rörelser eller belastning?
        _radioTrue.click();
        waitForNextViewToBeLoaded(1000);
        //5 Upplever du stelhet på morgonen, som går över efter en timme eller mindre?
        _radioTrue.click();
        waitForNextViewToBeLoaded(1000);
        //6 Har du, eller har du haft, ett arbete som medför stående, gående eller roterande uppgifter?
        _radioTrue.click();
        waitForNextViewToBeLoaded(1000);
        //7 Har du vid något tillfälle skadat din vänstra höft så allvarligt att du uppsökt läkare?
        _radioFalse.click();
        waitForNextViewToBeLoaded(1000);
        //8 Markera den ruta som motsvarar din genomsnittliga smärta från din vänstrahöft den senaste veckan.
        _scaleList.get(8).click();
        _nextButton.click();
        waitForNextViewToBeLoaded(1000);
        //9 Vilket är ditt kön?
        _genderMale.click();
        waitForNextViewToBeLoaded(1000);
        yearBirthDay();
        lenghtAndWeight(countryCode);
        moreInfoTOShare();
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _continueQuestionsCss, 4);
        getTheNameOfTheTherapist(); //The new flow changed during my vaccation in July
        _continueQuestions.click();
        waitForNextViewToBeLoaded(1000);
        _timeForMeetingYes.click();
        _textAreaComment.sendKeys("About two weeks from now is okay. ");
        driver.hideKeyboard();
        boolean found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _continueQuestionsCss, 3);
        if(found) { //this is for old Huawei tablet, in the automation it goes back one page
            _continueQuestions.click();
        }        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _meetingFinnishButtonCss, 3);
        LOG.info("Size of _meetingFinnishButton " + _meetingFinnishButton.size());
        _meetingFinnishButton.get(0).click();
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _registrationFinnishedButtonCss, 5);
        _registrationFinnishedButton.click();
        waitForNextViewToBeLoaded(4500);
    }

    @FindBy(css = "select[name='year']")
    private WebElement _yearList;

    @FindBy(css = "select[name='month']")
    private WebElement _monthList;

    @FindBy(css = "select[name='day']")
    private WebElement _dayList;

    @FindBy(css = "div.questions-information__input-header")
    private WebElement _inputNameHeader;

    @FindBy(css = "button.question__button")
    private WebElement _continueButton;

    private void yearBirthDay() {
        Select oSelect = new Select(_yearList);
        List <WebElement> elementCount = oSelect.getOptions();
        LOG.info("In yearOption " + elementCount.get(20).getText());
        elementCount.get(80).click();
        oSelect = new Select(_monthList);
        elementCount = oSelect.getOptions();
        elementCount.get(4).click();
        oSelect = new Select(_dayList);
        elementCount = oSelect.getOptions();
        elementCount.get(10).click();
        //_inputNameHeader.click();
        waitForNextViewToBeLoaded(1000);
        _continueButton.click();
        waitForNextViewToBeLoaded(2000);
    }

    @FindBy(css = "input[name='weight']")
    private WebElement _weight;

    @FindBy(css = "input[name='centimeters'")
    private WebElement _lengh;

    @FindBy(css = "input.height__us-feet")
    private WebElement _lenghUSfeet;

    @FindBy(css = "input[name='inches']")
    private WebElement _lenghUSinches;

    @FindBy(css = "button.question__button")
    private WebElement _nextButton;

    private void lenghtAndWeight(String countryCode) {
        switch (countryCode.toLowerCase()) {
            case "us":
                String weight="80";
                String heightFeet="5";
                String heightIn="6";
                _weight.sendKeys(weight);
                _lenghUSfeet.sendKeys(heightFeet);
                _lenghUSinches.sendKeys(heightIn);
                break;
            default:
                String weightSE="101";
                String heightSE="165";
                _weight.sendKeys(weightSE);
                _lengh.sendKeys(heightSE);
                break;
        }
        waitForNextViewToBeLoaded(1500);
        _nextButton.click();
        waitForNextViewToBeLoaded(2000);
    }

    @FindBy(css = "textarea.symptoms__input-textarea")
    private WebElement _textArea;

    private void moreInfoTOShare () {
        _textArea.sendKeys("Det började en gång med att .....");
        waitForNextViewToBeLoaded(1000);
        _nextButton.click();
        waitForNextViewToBeLoaded(2000);
    }

    private static final String _namesCss = "div.question-outro__name";
    @FindBy(css = _namesCss)
    private WebElement _names;

    private class Therapist {
        private String[] getTherapistName() {
            LOG.info("Get the name of the Therapist");
            waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _namesCss, 5);
            LOG.info("Therapist name " + _names.getText() + "  " + _names.getTagName());
            String regex = "\\s";
            Pattern pattern = Pattern.compile(regex);
            String[] result = pattern.split(_names.getText());
            return result;

        }
    }
}