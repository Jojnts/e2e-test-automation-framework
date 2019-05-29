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
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static com.automation.helpFunctions.HelpFunctions.*;

public class WebDriver {
    private static final Logger LOG = Logger.getLogger(WebDriver.class.getName());
    protected static Map<Object, Object> driverMap = Collections.synchronizedMap (new HashMap<>());

    static final String _buttonListCss = ".registration__debug-settings-button";

    protected AuthSignupPage authSignupPage;
    protected AuthLoginPage authLoginPage;
    protected DebugPage debugPage;
    protected PatientOnboardingPage patientOnboardingPage;
    protected PatientMobileMainPage patientMobileMainPage;
    protected PatientMobileShowProfilePage patientMobileShowProfilePage;
    protected PatientMobileShowProfileSettingsPage patientMobileShowProfileSettingsPage;
    protected QuestionPage questionOnePage;
    protected StartPage startPage;

    private AppiumDriver driver;


    public AppiumDriver createOrReuseDriver(final String UDID_, final String URL_, final String platform, final String platformVersion) {
        if (driverMap.containsKey(UDID_)) {
            LOG.info("Already INITIATED driver for " + UDID_);
            driver = reuseDriver(UDID_);
        } else {
            LOG.info("CREATE driver for " + UDID_);
            driver = createDriver( UDID_,  URL_,  platform,  platformVersion);
        }
        return driver;
    }

     private AppiumDriver createDriver(final String UDID_, final String URL_, final String platform, final String platformVersion) {
        AppiumDriver driver = null;

        DesiredCapabilities capabilities = getCapabilities( UDID_,  platform,  platformVersion);
        Thread threadnr = Thread.currentThread();
        LOG.info("-------Starts creating a driver for the device  " + UDID_ + " thread  " + threadnr);
         try {
             driver = new AndroidDriver<MobileElement>(new URL("http://" + URL_), capabilities);
         } catch (MalformedURLException e) {
             e.printStackTrace();
         }
         driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
         driverMap.put(UDID_, driver);
         initiateInstances(driver);
         waitForThePageObjectToBeLoadedToFindTheWebElement(driver, _buttonListCss);
        LOG.info("Created driver to RETURN : " + UDID_);
        return driver;
    }

    private DesiredCapabilities getCapabilities (final String UDID_, final String platform, final String platformVersion)  {
        LOG.info("Create DesiredCapabilities for device ID " + UDID_ );
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("deviceName", UDID_);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
        switch (platform.toLowerCase()) {
            case "ios":
                //iOS capabilities here
                break;
            case "android":
                capabilities.setCapability(MobileCapabilityType.UDID, UDID_);
                capabilities.setCapability("newCommandTimeout", 50);
                capabilities.setCapability("autoWebview", "true");
                capabilities.setCapability("appPackage", "ai.arthro.jointacademy_stage");
                capabilities.setCapability("appActivity", ".MainActivity");
                capabilities.setCapability("noReset", "false");
                capabilities.setCapability("chromedriverExecutableDir",System.getenv("HOME_GITHUB") + "\\e2e-test\\chromeDrivers");
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
        authSignupPage = new AuthSignupPage(driver);
        debugPage = new DebugPage(driver);
        authLoginPage = new AuthLoginPage(driver);
        patientOnboardingPage = new PatientOnboardingPage(driver);
        patientMobileMainPage = new PatientMobileMainPage(driver);
        patientMobileShowProfilePage = new PatientMobileShowProfilePage(driver);
        patientMobileShowProfileSettingsPage = new PatientMobileShowProfileSettingsPage(driver);
        questionOnePage = new QuestionPage(driver);
        startPage = new StartPage(driver);
    }

    private AppiumDriver reuseDriver(final String deviceName) {
        AppiumDriver driver = (AppiumDriver) driverMap.get(deviceName);
        initiateInstances(driver); //Don't needed anny more
        AndroidDriver driverA = (AndroidDriver) driver;
        Activity activity = new Activity("ai.arthro.jointacademy_stage", ".MainActivity");
        driverA.startActivity(activity);
        initiateInstances(driver);
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
