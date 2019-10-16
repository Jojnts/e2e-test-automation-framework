package com.automation.pageObjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;
import static io.appium.java_client.touch.offset.PointOption.point;

public class PaymentPage {
    private static final Logger LOG = Logger.getLogger(PaymentPage.class.getName());

    protected AppiumDriver driver;

    public PaymentPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String  _cardIconsCss = "div.card-payment-info__icons";
    @FindBy(css = _cardIconsCss)
    private WebElement _cardIcons;

    private static final String  _payCss = "button.spinner-button";
    @FindBy(css = _payCss)
    private WebElement _payButton;

    @FindBy(css = "div.card-payment-input__wrap")
    private List<WebElement> _inputCardfields;

    public void cardInputsAndPay(final AppiumDriver driver, String cardNumber) {
        LOG.info("Wait for the card form");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _cardIconsCss, 5);
        LOG.info("Insert card data");
        String deviceName = driver.getCapabilities().getCapability("deviceName").toString();
        String regex = "^emu";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(deviceName);
        LOG.info("bolean " + matcher.matches() + " devicename " + deviceName + " regexp  " + regex);
        //if (!matcher.matches()) {  //emulator
         //   _inputCardfields.get(0).click();
         //   _inputCardfields.get(0).sendKeys("400000000003220");
         //   //driver.getKeyboard().sendKeys(cardNumber);
        //} else {  //device
            char[] cardnumber = cardNumber.toCharArray();
            //_inputCardfields.get(0).clear();
            _inputCardfields.get(0).click();
            for (char output : cardnumber) {
                driver.getKeyboard().pressKey("" + output);
            }
        //}
        //waitForThePageObjectToBeLoadedToFindTheWebElement(driver, "div.card-payment-input__icon.card-payment-input__icon--complete", 1);
        _inputCardfields.get(1).click();
        driver.getKeyboard().sendKeys("0222");
        //waitForThePageObjectToBeLoadedToFindTheWebElement(driver, "div.card-payment-input__icon.card-payment-input__icon--complete", 1);
        _inputCardfields.get(2).click();
        driver.getKeyboard().sendKeys("123");
        //waitForThePageObjectToBeLoadedToFindTheWebElement(driver, "div.card-payment-input__icon.card-payment-input__icon--complete", 1);
        _payButton.click();
    }

    private static final String  _completeBttonCss = "button.mobile-activity-footer__button";
    @FindBy(css = _completeBttonCss)
    private WebElement _completeBtton;

    public void clickButtonComplete(final AppiumDriver driver) {
        LOG.info("Click Payment comleted");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _completeBttonCss, 2);
        _completeBtton.click();
    }

    private static final String  _goToPaymentBttonCss = ".payment-fee__button";
    @FindBy(css = _goToPaymentBttonCss)
    private WebElement _goToPaymentBtton;

    public void clickButtonGoToThePayment(final AppiumDriver driver) {
        LOG.info("Click Go to payment");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _goToPaymentBttonCss, 2);
        _goToPaymentBtton.click();
    }

    private static final String  _auth3dsCompleteButtonCss = "#test-source-authorize-3ds";
    @FindBy(css = _auth3dsCompleteButtonCss)
    private WebElement _auth3dsCompleteBotton;

    private static final String  _auth3dsFailButtonCss = "#test-source-fail-3ds";
    @FindBy(css = _auth3dsFailButtonCss)
    private WebElement _auth3dsFailButton;

    public Boolean confirmPopup3ds(final AppiumDriver driver, String val, String cardValid) {
        LOG.info("Popup 3ds ");
        Boolean found = false;
        TouchAction touchAction=new TouchAction(driver);
        driver.context("NATIVE_APP");
        String deviceName = driver.getCapabilities().getCapability("deviceName").toString();
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        int x3 = 0;
        int y3 = 0;
        int xf1 = 0;
        int yf1 = 0;
        int xf2 = 0;
        int yf2 = 0;
        int xf3 = 0;
        int yf3 = 0;
        switch (deviceName) {
            case "CB512DERZH":
                x1 =  850;  //Sony
                y1 = 1150;  //Sony
                x2 = 400;  //Sony
                y2 = 1585;  //Sony
                xf1 = 570; //Sony
                yf1 = 1170; //Sony
                break;
            default:
                x1 = 460;;
                y1 = 650;
                x2 = 460;
                y2 = 850;
                x3 = 230;
                y3 = 800;
                xf1 = 330;
                yf1 = 845;
                xf2 = 225;
                yf2 = 880;
                xf3 = (int) 0.38  * driver.manage().window().getSize().getWidth();;
                yf3 = (int) 0.91 * driver.manage().window().getSize().getHeight();
                break;
        }
        driver.context("WEBVIEW");

        ///####### find all iframes and check for the css
       /* List<WebElement> iframeList = driver.findElements(By.tagName("iframe"));
        int size = iframeList.size();
        System.out.println("Antal iframe " + size);
        for(int i=0; i<size; i++){
            WebElement iframeElement = iframeList.get(i);
            driver.switchTo().frame(iframeElement);
            //int total=driver.findElements(By.cssSelector("button#test-source-authorize-3ds")).size();
            try {
                driver.findElement(By.cssSelector("form.ActionForm  .Button#test-source-authorize-3ds"));  //button#test-source-authorize-3ds
            }
            catch ( Exception e) {
                System.out.println("Didnt find confirm in ifram nr " + i);
            }
            //System.out.println(total);
            driver.switchTo().defaultContent();
        }*/
        ///#######
        switch (val) {
            case "Confirm":
                LOG.info("Popup 3ds press Confirm ");
                switch (cardValid) {
                    case "Card_succeed":
                        found=false;
                        int i=0;
                        while (!found) {
                            touchAction=new TouchAction(driver);
                            touchAction.press(point(x1, y1)).release().perform(); //for 3ds 2 auth 460, 650
                            touchAction=new TouchAction(driver);
                            touchAction.press(point(x2, y2)).release().perform(); //for 3ds 2 auth 460, 850
                            touchAction=new TouchAction(driver);
                            touchAction.press(point(x3, y3)).release().perform(); //for 3ds  230, 800
                            found = checkForPaymentFeedbackMessage(driver);
                            i++;
                            System.out.println("i is : " + i  + " and is it found : " + found);
                        }
                        found = checkForPaymentFeedbackMessageSuccess(driver);
                        return found;
                    case "Card_declined":
                        found=false;
                        i=0;
                        while (!found) {
                            touchAction=new TouchAction(driver);
                            touchAction.press(point(x1, y1)).release().perform(); //for 3ds 2 auth
                            touchAction=new TouchAction(driver);
                            touchAction.press(point(x2, y2)).release().perform(); //for 3ds 2 auth
                            touchAction=new TouchAction(driver);
                            touchAction.press(point(x3, y3)).release().perform(); //for 3ds
                            found = checkForPaymentFeedbackMessageFailure(driver);
                            i++;
                            System.out.println("i is : " + i  + " and is it found : " + found);
                        }
                        return found;
                    default:
                        return true;
                }
            case "Fail":
                LOG.info("Popup 3ds press Fail");
                found=false;
                int i=0;
                while (!found) {
                    touchAction=new TouchAction(driver);
                    touchAction.press(point(xf2, yf2)).release().perform(); //for 3ds 2 auth
                    touchAction=new TouchAction(driver);
                    touchAction.press(point(xf1, yf1)).release().perform(); //for 3ds 2 auth
                    touchAction=new TouchAction(driver);
                    touchAction.press(point(xf3, yf3)).release().perform(); //for 3ds
                    found = checkForPaymentFeedbackMessageFailure(driver);
                    i++;
                    System.out.println("i is : " + i  + " and is it found : " + found);
                }
                return found;
            default:
                LOG.info("No Popup 3ds ");
                return true;
        }
    }

    private static final String  _generalMessageCss = ".alert-message";
    @FindBy(css = _generalMessageCss)
    private WebElement _generalMessage;

    public Boolean checkForPaymentFeedbackMessage(final AppiumDriver driver) {
        LOG.info("Is there a payment status message ");
        return waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _generalMessageCss, 2);
    }

    private static final String  _successMessageCss = ".alert-message.alert-message--success";
    @FindBy(css = _successMessageCss)
    private WebElement _successMessage;

    public Boolean checkForPaymentFeedbackMessageSuccess(final AppiumDriver driver) {
        LOG.info("Get the payment status message ");
        return waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _successMessageCss, 2);
    }

    private static final String  _failureMessageCss = ".alert-message.alert-message--failure";
    @FindBy(css = _failureMessageCss)
    private WebElement _failureMessage;

    public Boolean checkForPaymentFeedbackMessageFailure(final AppiumDriver driver) {
        LOG.info("Get the payment status message ");
        return waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _failureMessageCss, 2);
    }

    private static final String _cardNumberCss = "div.text--B2.text--normal";
    @FindBy(css = _cardNumberCss)
    private WebElement _cardNumber;

    public String getCardNumber(final AppiumDriver driver) {
        LOG.info("Get the cad number");
        Boolean found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _cardNumberCss, 2);
        if (found) {
            LOG.info("The card number is " + _cardNumber.getText());
            return _cardNumber.getText();
        }
        return("no card displayed");
    }

    private static final String  _cancleButtonCss = "div.top-nav-bar__cancel";
    @FindBy(css = _cancleButtonCss)
    private WebElement _cancleButton;

    public void clickButtonCancel(AppiumDriver driver) {
        LOG.info("Click Cancel ");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _cancleButtonCss, 1);
        _cancleButton.click();
    }

    private static final String  _crossButtonCss = "div.fullscreen-header__icon";
    @FindBy(css = _crossButtonCss)
    private WebElement _crossButton;

    public void clickCrossButton(AppiumDriver driver) {
        LOG.info("Click Cross ");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _crossButtonCss, 1);
        _crossButton.click();
    }

    private static final String  _changeCardButtonCss = "div.payment-fee__card-link";
    @FindBy(css = _changeCardButtonCss)
    private WebElement _changeCardButton;

    public void clickButtonChangeCard(AppiumDriver driver) {
        LOG.info("Click Cancel ");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _changeCardButtonCss, 1);
        _changeCardButton.click();
    }

    public Boolean[] useCardsInputAndPay(AppiumDriver driver, String cardNumber, String choice, String cardValid) {
        LOG.info("Use different cards");
        Boolean cardInfo[] = null;
        cardInputsAndPay(driver, cardNumber);
        return cardInfo;
    }
}