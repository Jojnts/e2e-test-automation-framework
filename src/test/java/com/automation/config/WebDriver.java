package com.automation.config;

import com.automation.pageObjects.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static com.automation.config.EnvironmentConfig.*;
import static com.automation.helpFunctions.HelpFunctions.waitForThePageObjectToBeLoadedToFindTheWebElement;

public class WebDriver {
    private static final Logger LOG = Logger.getLogger(WebDriver.class.getName());
    protected static Map<Object, Object> driverMap = Collections.synchronizedMap (new HashMap<>());

    private static final String _buttonDebugCss = "button.registration__debug-settings-button";

    protected AuthLoginPage authLoginPage;
    protected DebugPage debugPage;
    protected GdprPage gdprPage;
    protected PatientCreateAccountPage patientCreateAccountPage;
    protected PatientOnboardingPage patientOnboardingPage;
    protected PatientMobileMainPage patientMobileMainPage;
    protected PatientMobileShowProfilePage patientMobileShowProfilePage;
    protected PatientMobileShowProfilePaymentPage patientMobileShowProfilePaymentPage;
    protected PatientMobileShowProfileReceiptPage patientMobileShowProfileReceiptPage;
    protected PatientMobileShowProfileSettingsPage patientMobileShowProfileSettingsPage;
    protected PaymentActivityUSPage paymentActivityUSPage;
    protected PaymentInsuranceActivityUSPage paymentInsuranceActivityUSPage;
    protected PaymentMethodPage paymentMethodPage;
    protected PaymentPage paymentPage;
    protected QuestionPage questionOnePage;
    protected StartPage startPage;
    protected TherapistStartPage therapistStartPage;
    protected ZipCodePage zipCodePage;

    private AppiumDriver driver;


    public AppiumDriver createOrReuseDriver(final String deviceId, final String appiumPort, final String platform, final String platformVersion) {
        if (driverMap.containsKey(deviceId)) {
            LOG.info("Already INITIATED driver for " + deviceId);
            driver = reuseDriver(deviceId);
        } else {
            LOG.info("CREATE driver for " + deviceId);
            driver = createDriver( deviceId,  appiumPort,  platform,  platformVersion);
        }
        return driver;
    }

     private AppiumDriver createDriver(final String deviceId, final String appiumPortNr, final String platform, final String platformVersion) {
        AppiumDriver driver = null;
        DesiredCapabilities capabilities = getCapabilities( deviceId,  platform,  platformVersion);
        Thread threadnr = Thread.currentThread();
        LOG.info("-------Starts creating a driver for the device  " + deviceId + " thread  " + threadnr);
         try {
             driver = new AndroidDriver<MobileElement>(new URL("http://" + appiumServerUrl() + ":" + appiumPortNr + "/wd/hub"), capabilities);
         } catch (MalformedURLException e) {
             e.printStackTrace();
         }
         driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
         driverMap.put(deviceId, driver);
         initiateInstances(driver);
         boolean found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _buttonDebugCss , 10);
         //GOV
         /*boolean found = waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _buttonDebugCss , 0);
          if(found) {
             waitForNextViewToBeLoaded(4000);
         } else {
             throw new NotFoundException("The debugButton was not found in the startup for the first test!");
         }*/
        LOG.info("Created driver to RETURN : " + deviceId);
        return driver;
    }

    private DesiredCapabilities getCapabilities (final String deviceId, final String platform, final String platformVersion)  {
        LOG.info("Create DesiredCapabilities for device ID " + deviceId );

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("deviceName", deviceId);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
        switch (platform.toLowerCase()) {
            case "ios":
                //iOS capabilities here
                break;
            case "android":
                capabilities.setCapability(MobileCapabilityType.UDID, deviceId);
                capabilities.setCapability("newCommandTimeout", 50);
                capabilities.setCapability("autoWebview", "true");
                capabilities.setCapability("appPackage", appPackageToBeUse());
                capabilities.setCapability("appActivity", APP_ACTIVITY);
                capabilities.setCapability("noReset", "false");
                capabilities.setCapability("chromedriverExecutableDir", useChromeDriverPath());
                //capabilities.setCapability("unicodeKeyboard", "true");
                //capabilities.setCapability("resetKeyboard", "true");
                break;
            default:
                try {
                    throw new Exception("Platform not supported!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return capabilities;
    }

    private void initiateInstances(final AppiumDriver driver){
        debugPage = new DebugPage(driver);
        authLoginPage = new AuthLoginPage(driver);
        gdprPage = new GdprPage(driver);
        patientCreateAccountPage = new PatientCreateAccountPage(driver);
        patientOnboardingPage = new PatientOnboardingPage(driver);
        patientMobileMainPage = new PatientMobileMainPage(driver);
        patientMobileShowProfilePage = new PatientMobileShowProfilePage(driver);
        patientMobileShowProfilePaymentPage = new PatientMobileShowProfilePaymentPage(driver);
        patientMobileShowProfileReceiptPage = new PatientMobileShowProfileReceiptPage(driver);
        patientMobileShowProfileSettingsPage = new PatientMobileShowProfileSettingsPage(driver);
        paymentActivityUSPage = new PaymentActivityUSPage((driver));
        paymentInsuranceActivityUSPage = new PaymentInsuranceActivityUSPage(driver);
        paymentMethodPage = new PaymentMethodPage(driver);
        paymentPage = new PaymentPage(driver);
        questionOnePage = new QuestionPage(driver);
        startPage = new StartPage(driver);
        therapistStartPage = new TherapistStartPage(driver);
        zipCodePage = new ZipCodePage(driver);
    }

    private AppiumDriver reuseDriver(final String deviceName) {
        AppiumDriver driver = (AppiumDriver) driverMap.get(deviceName);
        AndroidDriver driverA = (AndroidDriver) driver;
        Activity activity = new Activity(appPackageToBeUse(), APP_ACTIVITY);
        driverA.startActivity(activity);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver;
    }

    public void closeDriver(final AppiumDriver driver) {
        String deviceName = driver.getCapabilities().getCapability("deviceName").toString();
        driverMap.remove(deviceName);
        driver.quit();
    }
}