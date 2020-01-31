package com.automation.pageObjects;

import com.automation.config.WebDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.remote.HideKeyboardStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.naming.Context;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.automation.helpFunctions.HelpFunctions.*;
import static io.appium.java_client.touch.offset.PointOption.point;

public class PaymentPage {
    private static final Logger LOG = Logger.getLogger(PaymentPage.class.getName());

    protected AppiumDriver driver;

    public PaymentPage(final AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    private static final String  _headerTitleCss = "div.fullscreen-header__title";
    @FindBy(css = _headerTitleCss)
    private WebElement _headerTitle;

    private static final String  _cardIconsCss = "div.card-payment-info__icons";
    @FindBy(css = _cardIconsCss)
    private WebElement _cardIcons;

    private static final String  _checkValidCompleteCss = "span.InputContainer input.is-complete.Input";
    @FindBy(css = _checkValidCompleteCss)
    private MobileElement _checkValidComplete;

    private static final String  _payCss = "button.spinner-button";
    @FindBy(css = _payCss)
    private WebElement _payButton;



    private static final String  _inputCardfieldsCss = "div.card-payment-input";
    @FindBy(css = _inputCardfieldsCss)
    private List<WebElement> _inputCardfields;

    private static final String _inputCardfieldCss =   "input.InputElement"; //"span.InputContainer";//".card-payment-input__stripe.card-payment-input__field--number";
    @FindBy(css = _inputCardfieldCss)
    private WebElement _inputCardfield;

    private static final String _emptyfieldCss =   "div.text--B2";
    @FindBy(css = _emptyfieldCss)
    private WebElement _emptyfield;

    private static final String _visaCardCss =   "div.card-payment-input__card--visa";
    @FindBy(css = _visaCardCss)
    private WebElement _visaCard;


    public void cardInputsAndPay(final AppiumDriver driver, String cardNumber) {
        LOG.info("Wait for the card form");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _cardIconsCss, 5);
        LOG.info("Insert card data");
        String deviceName = driver.getCapabilities().getCapability("deviceName").toString();
        String regex = "^emu";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(deviceName);
        System.out.println("filds in the list " + _inputCardfields.size());

        List<WebElement> iframeList = driver.findElements(By.tagName("iframe"));
        int size = iframeList.size();
        //System.out.println("Antal iframe " + size);
        int i=0;
        for(WebElement iframe : iframeList){
            //System.out.println("iframe name " + iframe.getAttribute("name"));
            driver.switchTo().frame(iframe);
            try {
                driver.findElement(By.cssSelector(_inputCardfieldCss));
                i++;
            }
            catch ( Exception e) {
            }
            if(i==1) {
                _inputCardfield.click();
                if(driver.getCapabilities().getCapability("platform").toString().toLowerCase().compareTo("android") == 0) {
                    //System.out.println("FIELD 1");
                    _inputCardfield.clear();
                    _inputCardfield.sendKeys(cardNumber);
                }
                else {
                    _inputCardfield.clear();
                    _inputCardfield.sendKeys(cardNumber);
                }
            }
            if(i==2) {
                _inputCardfield.click();
                //System.out.println("FIELD 2");
                _inputCardfield.sendKeys("1223");
            }
            if(i==3) {
                _inputCardfield.click();
                //System.out.println("FIELD 3");
                _inputCardfield.sendKeys("123");
                i=99;
            }

            if(i==1 ) {
                driver.switchTo().defaultContent();
                _headerTitle.click();
                driver.switchTo().frame(iframe);
                Boolean found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _checkValidCompleteCss, 1);
                while(!found) {
                    _inputCardfield.clear();
                    _inputCardfield.sendKeys(cardNumber);
                    driver.switchTo().defaultContent();
                    found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _headerTitleCss, 2);
                    _headerTitle.click();
                    driver.switchTo().frame(iframe);
                     found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _checkValidCompleteCss, 2);
                }
            }
            driver.switchTo().defaultContent();
            _headerTitle.click();
        }
        _headerTitle.click();
        _payButton.click();
    }

    private static final String  _completeBttonCss = "button.mobile-activity-footer__button";
    @FindBy(css = _completeBttonCss)
    private WebElement _completeButton;

    public void clickButtonComplete(final AppiumDriver driver) {
        LOG.info("Click Payment comleted");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _completeBttonCss, 2);
        _completeButton.click();
    }

    private static final String  _goToPaymentBttonCss = ".payment-fee__button";
    @FindBy(css = _goToPaymentBttonCss)
    private WebElement _goToPaymentButton;

    public void clickButtonGoToThePayment(final AppiumDriver driver) {
        LOG.info("Click Go to payment");
        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _goToPaymentBttonCss, 2);
        _goToPaymentButton.click();
    }

    private static final String  _auth3dsCompleteButtonCss = "button#test-source-authorize-3ds";
    @FindBy(css = _auth3dsCompleteButtonCss)
    private WebElement _auth3dsCompleteButton;

    private static final String  _auth3dsFailButtonCss = "button#test-source-fail-3ds";
    @FindBy(css = _auth3dsFailButtonCss)
    private WebElement _auth3dsFailButton;

    private static final String  _auth3dsCompleteButtonXpathAndroid = "//*[contains(@text,'COMPLETE')]"; // "test-source-authorize-3ds"; //[contains(text(),’COMPLETE’)]"; //*[@name='COMPLETE']
    @FindBy(xpath = _auth3dsCompleteButtonXpathAndroid)
    private WebElement _auth3dsCompleteButtonAndroid;

    private static final String  _auth3dsCompleteButtonXpathIos = "//*[contains(@name,'COMPLETE')]"; // "test-source-authorize-3ds"; //[contains(text(),’COMPLETE’)]"; //*[@name='COMPLETE']
    @FindBy(xpath = _auth3dsCompleteButtonXpathIos)
    private WebElement _auth3dsCompleteButtonIos;

     private static final String  _auth3dsFailButtonXpathAndroid = "//*[contains(@text,'FAIL')]"; // "test-source-authorize-3ds"; //[contains(text(),’COMPLETE’)]"; //*[@name='COMPLETE']
     @FindBy(xpath = _auth3dsFailButtonXpathAndroid)
     private WebElement _auth3dsFailButtonAndroid;

     private static final String  _auth3dsFailButtonXpathIos = "//*[contains(@name,'FAIL')]";//"//*[contains(@name,'FAIL')]"; // "test-source-authorize-3ds"; //[contains(text(),’COMPLETE’)]"; //*[@name='COMPLETE']
     @FindBy(xpath = _auth3dsFailButtonXpathIos)
     private WebElement _auth3dsFailButtonIos;

    private static final String  _webElementCss = "div.ButtonGroup.source-action-3ds";
    @FindBy(css = _webElementCss)
    private WebElement _webElement;


    public Boolean confirmPopup3ds(final AppiumDriver driver, String cardValidation, String cardValid) {
        LOG.info("Popup 3ds ");
        Map<Object, Object> webContextMap = Collections.synchronizedMap (new HashMap<>());
        Boolean found = false;
        LOG.info("Popup 3ds CONT:");
        if("No3dVerification".compareTo(cardValidation) != 0) {
            //**** To find the Webviews implemented to fix Nordea 3Ds card payment  ***
            int maxTime2wait4Popup3Ds=20;
            int maxNeededWebviews=3;
            int sizeOfWebViewContexts = 0;
            Set<String> webviewContexts=null;
            int i = 0;
            if (driver.getCapabilities().getCapability("platform").toString().toLowerCase().compareTo("android") == 0) {
                maxNeededWebviews=3;
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //**** Wait until the second webview is shown ****//
            while (sizeOfWebViewContexts < maxNeededWebviews && i < maxTime2wait4Popup3Ds) {
                try {
                    Thread.sleep(1000);
                    i++;
                    System.out.println("timeout waiting for three webview contexts " + i);
                    int nr=0;
                    webviewContexts = driver.getContextHandles();
                    sizeOfWebViewContexts = webviewContexts.size();
                    for (Object contextNameo : webviewContexts) {
                        String setContext = webviewContexts.toArray()[nr].toString();
                        webContextMap.put(nr, setContext);
                        System.out.println(contextNameo + "  nr: " + nr + " setContext " + setContext);
                        //driver.context(setContext);
                        nr++;
                    }
                    if(sizeOfWebViewContexts!=0){
                        for(int r=0; r<sizeOfWebViewContexts ; r++) {
                            System.out.println("Mapped contextName " + r + "  " + webContextMap.get(r));
                        }
                        driver.context((String) webContextMap.get(sizeOfWebViewContexts-1));
                        System.out.println("set webcontext  " + (String) webContextMap.get(sizeOfWebViewContexts-1));
                    }
                    System.out.println("context size  " + sizeOfWebViewContexts );
                    try{waitForThePageObjectToBeLoadedToFindTheWebElementXpath(driver, _auth3dsCompleteButtonXpathAndroid, 1);
                        i=maxTime2wait4Popup3Ds;
                    }
                    catch(Exception e ){waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _auth3dsCompleteButtonXpathAndroid, 1);
                        i=maxTime2wait4Popup3Ds;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } //This to find the second webview
            switch (cardValidation) {
                case "Confirm":
                    driver.context("NATIVE_APP");
                    if (driver.getCapabilities().getCapability("platform").toString().toLowerCase().compareTo("android") == 0) {
                        try{waitForThePageObjectToBeLoadedToFindTheWebElementXpath(driver, _auth3dsCompleteButtonXpathAndroid, 10);}
                        catch(Exception e ){waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _auth3dsCompleteButtonXpathAndroid, 2);}

                        _auth3dsCompleteButtonAndroid.click();
                        System.out.println("Android clicked COMPLETE");
                    } else {
                        waitForThePageObjectToBeLoadedToFindTheWebElementXpath(driver, _auth3dsCompleteButtonXpathIos, 10);
                        _auth3dsCompleteButtonIos.click();
                        System.out.println("iPhone clicked COMPLETE");
                    }
                    driver.context((String) webContextMap.get(1));
                    break;
                case "Fail":
                    driver.context("NATIVE_APP");
                    if (driver.getCapabilities().getCapability("platform").toString().toLowerCase().compareTo("android") == 0) {
                        waitForThePageObjectToBeLoadedToFindTheWebElementXpath(driver, _auth3dsFailButtonXpathAndroid, 10);
                        _auth3dsFailButtonAndroid.click();
                        System.out.println("Android clicked FAIL");
                    } else {

                        waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _auth3dsFailButtonXpathIos, 10);
                        _auth3dsFailButtonIos.click();
                        System.out.println("iPhone clicked FAIL");
                    }
                    driver.context((String) webContextMap.get(1));
                    break;
            }
            //ref1 down in the end of this file how to find iframes
            waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _generalMessageCss, 10);
            switch (cardValid) {
                case "Card_succeed":
                    found = checkForPaymentFeedbackMessageSuccess(driver);
                    break;
                case "Card_declined":
                    found = checkForPaymentFeedbackMessageFailure(driver);
                    break;
                default:
                    break;
            }
        }
         else {
            waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _generalMessageCss, 10);
            switch (cardValid) {
                case "Card_succeed":
                    found = checkForPaymentFeedbackMessageSuccess(driver);
                    break;
                case "Card_declined":
                    found = checkForPaymentFeedbackMessageFailure(driver);
                    break;
                default:
                    found=false;
                    break;
            }
        }
        return found;
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
        return waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _successMessageCss, 8);
    }

    private static final String  _failureMessageCss = "div.margin-bottom-small.alert-message.alert-message--failure";
    @FindBy(css = _failureMessageCss)
    private WebElement _failureMessage;

    public Boolean checkForPaymentFeedbackMessageFailure(final AppiumDriver driver) {
        LOG.info("Get the payment status message ");
        return waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _failureMessageCss, 10);
    }

    private static final String _cardNumberCss = "div.text--B2.text--normal";
    @FindBy(css = _cardNumberCss)
    private WebElement _cardNumber;

    public String getCardNumber(final AppiumDriver driver) {
        LOG.info("Get the cad number");
        Boolean found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _cardNumberCss, 4);
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



//ref1 to find iframes in ios and android
/*   int icounter = 0;
                    int frameSize=3;
                    if (driver.getCapabilities().getCapability("platform").toString().toLowerCase().compareTo("android") == 0) {
                        frameSize=2;
                    }
                    int countFrameSize = 0;
                    List<WebElement> iframeList1 = null;
                    while (countFrameSize < frameSize && icounter < 20) {
                        try {
                            Thread.sleep(1000);
                            iframeList1 = driver.findElements(By.tagName("iframe"));
                            icounter++;
                            countFrameSize = iframeList1.size();
                            System.out.println("icounter " + icounter + " size of iframes " + countFrameSize);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (countFrameSize > 1) {
                        driver.context("NATIVE_APP");
                        if (driver.getCapabilities().getCapability("platform").toString().toLowerCase().compareTo("android") == 0) {
                            waitForThePageObjectToBeLoadedToFindTheWebElementXpath(driver, _auth3dsFailButtonXpathAndroid, 8);
                            _auth3dsFailButtonAndroid.click();
                            System.out.println("Android clicked FAIL");
                        } else {
                            waitForThePageObjectToBeLoadedToFindTheWebElementXpath(driver, _auth3dsFailButtonXpathIos, 8);
                            _auth3dsFailButtonIos.click();
                            System.out.println("iPhone clicked FAIL");
                        }
                        driver.context((String) webContextMap.get(1));
                    }
                    break;
            }
            sizeOfWebViewContexts = 0;
            i=0;*/
//**** Wait until the second webview is shown ****//
            /*while (sizeOfWebViewContexts < 2 && i < 5) {
                try {
                    Thread.sleep(1000);
                    i++;
                    System.out.println("timeout waiting for three webview contexts " + i);
                    int nr=0;
                    webviewContexts = driver.getContextHandles();
                    sizeOfWebViewContexts = webviewContexts.size();
                    for (Object contextNameo : webviewContexts) {
                        String setContext = webviewContexts.toArray()[nr].toString();
                        webContextMap.put(nr, setContext);
                        System.out.println(contextNameo + "  nr: " + nr + " setContext " + setContext);
                        driver.context(setContext);
                        nr++;
                    }
                    System.out.println("context size  " + sizeOfWebViewContexts );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/ //This to find the second webview