package com.automation.config;

import com.automation.pageObjects.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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


    public AppiumDriver createOrReuseDriver(final String deviceId, final String nameDevice, final String appiumPort, final String platform, final String platformVersion) {
        if (driverMap.containsKey(nameDevice)) {
            LOG.info("Already INITIATED driver for " + nameDevice);
            driver = (AppiumDriver) driverMap.get(nameDevice);
            System.out.println("reuse appium driver " + driver + "   " + nameDevice);
            driver = reuseDriver(nameDevice, platform);
        } else {
            LOG.info("CREATE driver for " + nameDevice);
            driver = createDriver( deviceId,  nameDevice, appiumPort,  platform,  platformVersion);
        }
        return driver;
    }

     private AppiumDriver createDriver(final String deviceId, final String deviceName, final String appiumPortNr, final String platform, final String platformVersion) {
        AppiumDriver driver = null;
        DesiredCapabilities capabilities = getCapabilities( deviceId, deviceName, appiumPortNr, platform,  platformVersion);
        Thread threadnr = Thread.currentThread();
        LOG.info("-------Starts creating a driver for the device  " + deviceId + " platform  " + platform.toLowerCase());
        String PortNr="5200"; // to run on one appium server
        try {
            if(platform.toLowerCase().compareTo("android") == 0) {
                LOG.info("Create Android driver ");
                driver = new AndroidDriver<MobileElement>(new URL("http://" + appiumServerUrl() + ":" + appiumPortNr + "/wd/hub"), capabilities);
                LOG.info(" Android driver created ");
            } else {
                LOG.info("Create iOS driver ");
                driver = new IOSDriver<MobileElement>(new URL("http://" + appiumServerUrl() + ":" + appiumPortNr + "/wd/hub"), capabilities);
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                LOG.info(" iOS driver created ");
            }
         } catch (MalformedURLException e) {
             e.printStackTrace();
         }
         driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
         driverMap.put(deviceName, driver);
         initiateInstances(driver);
         waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _buttonDebugCss , 10);
        LOG.info("Created driver to RETURN : " + deviceName);
        return driver;
    }

    private DesiredCapabilities getCapabilities (final String deviceId, final String deviceName, final String port, final String platform, final String platformVersion)  {
        LOG.info("Create DesiredCapabilities for device ID " + deviceId );
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.UDID, deviceId);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
        capabilities.setCapability("platform", platform);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability("deviceBrand", deviceName);
        capabilities.setCapability("testEnvUrl", appTestEnvUrlBeUse());
        switch (platform.toLowerCase()) {
            case "ios":
                capabilities.setCapability(MobileCapabilityType.APP, "/Users/arthro/Downloads/JA-PR3233-58842.ipa");
                //capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, appPackageToBeUse());
                capabilities.setCapability(MobileCapabilityType.NO_RESET, "true");
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
                capabilities.setCapability("xcodeOrgId", "V4X4LG9827");
                capabilities.setCapability("xcodeSigningId", "Apple Development");
                capabilities.setCapability("autoWebview", "true");
                capabilities.setCapability("autoAcceptAlerts", "true");
                capabilities.setCapability("wdaLocalPort", port+1);
                break;
            case "android":
                capabilities.setCapability(MobileCapabilityType.APP, "/Users/arthro/Downloads/app-debug3233.apk");
                capabilities.setCapability("newCommandTimeout", 40);
                capabilities.setCapability("autoWebview", "true");
                capabilities.setCapability("appPackage", appPackageToBeUseAndroid());
                capabilities.setCapability("appActivity", APP_ACTIVITY);
                capabilities.setCapability("noReset", "true");
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
                capabilities.setCapability("chromedriverExecutableDir", useChromeDriverPath());
                // chrome driver 75 and 76 have problems with non W3C standard
                //capabilities.setCapability("w3c", "false");
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

    private AppiumDriver reuseDriver(final String deviceName, final String platform) {
        if (platform.toLowerCase().compareTo("android") == 0) {
            AppiumDriver driver = (AppiumDriver) driverMap.get(deviceName);
            AndroidDriver driverA = (AndroidDriver) driver;
            Activity activity = new Activity(appPackageToBeUse(), APP_ACTIVITY);
            driverA.startActivity(activity);
        } else {
            AppiumDriver driver = (AppiumDriver) driverMap.get(deviceName);
            initiateInstances(driver);
        }
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