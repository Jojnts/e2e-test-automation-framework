package com.automation.helpFunctions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.logging.Logger;

public abstract class HelpFunctions {
    private static final Logger LOG = Logger.getLogger(HelpFunctions.class.getName());


    public static void waitForNextViewToBeLoaded(Integer timeTowait) {
        try {
            Thread.sleep(timeTowait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void clickBack (final AppiumDriver driver) {
        LOG.info("Click Back");
        String platform = driver.getCapabilities().getCapability(MobileCapabilityType.PLATFORM_NAME).toString().toLowerCase();
        switch(platform) {
            case "android":
                AndroidDriver driverA = (AndroidDriver) driver;
                driverA= (AndroidDriver<?>) driver;
                driverA.pressKey(new KeyEvent(AndroidKey.BACK));
                break;
            case "ios":
                break;
        }

    }

    public static void clickEnter (final AppiumDriver driver) {
        LOG.info("Click Enter");
        AndroidDriver driverA = (AndroidDriver) driver;
        driverA= (AndroidDriver<?>) driver;
        driverA.pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    public static boolean isTheFoundElementDisplayed(final AppiumDriver driver, String css ) {
        String device = driver.getCapabilities().getCapability("deviceName").toString();
        int counter = 0;
        int timeLimit = 5;

        while (counter < timeLimit) {
            LOG.info(" Is the element displayed  " + css + " on the device " + device + " :loop nr " + counter);
            try {
                counter= counter + 1;
                Thread.sleep(1000);
                if (!driver.findElement(By.cssSelector(css)).isDisplayed()) {

                } else {
                    //System.out.println("The element is displayed");
                    return true;
                }
            } catch (Exception e) {
                LOG.info(" Catched a null pointer, do nothing");
            }
        }
        return false;
    }


    public static boolean waitForThePageObjectToBeLoadedToFindTheWebElement(final AppiumDriver driver, String css, int  timeOutSeconds ) {
        String device = driver.getCapabilities().getCapability("deviceName").toString();

        int timeLimit = 0;
        if( timeOutSeconds < 1 ) {
            timeLimit = 20;
        } else {
            timeLimit = timeOutSeconds;
        }
        checkInitiationofPageObject(driver, css, timeLimit);

        int counter=0;
        if( timeOutSeconds < 1 ) {
            timeLimit = 20;
        } else {
            timeLimit = timeOutSeconds;
        }
        while (counter < timeLimit ) {
            LOG.info("In the loop  waiting for the element to be displayed  on " + device + " loop nr " + counter + " for element " + css );
            if (counter < timeLimit ){
                counter = counter + 1;
                try {
                    if(driver.findElements(By.cssSelector(css)).size() > 0 ) {
                        counter = timeLimit + 2;
                        //check if the element is displayed
                        return isTheFoundElementDisplayed(driver, css);
                    }else {
                        Thread.sleep(1000);
                    }
                }catch (Exception e){
                    //css not found
                }
            } else if (counter == timeLimit) {
                //LOG.info("  Element not found in waitForPageToBeLoaded ");
            }
        }
        return false;
    }

    private static void checkInitiationofPageObject(AppiumDriver driver, String css, int timeLimit) {
        String device = driver.getCapabilities().getCapability("deviceName").toString();
        int counter = 0;
        while (counter < timeLimit) {
            LOG.info(" Waiting for Page Object to be instantiated " + device + " loop nr " + counter);
            try {
                counter= counter + 1;
                Thread.sleep(1000);
                if (driver.findElements(By.cssSelector(css)) == null) {
                } else {
                    counter = timeLimit + 1;
                }
            } catch (Exception e) {
                LOG.info(" Catched a null pointer, do nothing");
            }
        }
    }

    //for the xpath
    public static boolean waitForThePageObjectToBeLoadedToFindTheWebElementXpath(final AppiumDriver driver, String xpath, int  timeOutSeconds ) {
        String device = driver.getCapabilities().getCapability("deviceName").toString();
        Boolean found=false;
        int timeLimit = 0;
        if( timeOutSeconds < 1 ) {
            timeLimit = 20;
        } else {
            timeLimit = timeOutSeconds;
        }
        //checkInitiationofPageObjectXpath(driver, xpath, timeLimit);
        int counter=0;
        if( timeOutSeconds < 1 ) {
            timeLimit = 20;
        } else {
            timeLimit = timeOutSeconds;
        }
        while (counter < timeLimit ) {
            LOG.info("In the loop  waiting for the element to be displayed  on " + device + " loop nr " + counter + " for element " + xpath );
            if (counter < timeLimit ){
                counter = counter + 1;
                try {
                    if(driver.findElements(By.xpath(xpath)).size() > 0 ) {
                        counter = timeLimit + 2;
                        //check if the element is displayed
                        found=isTheFoundElementDisplayedXpath(driver, xpath);
                    }else {
                        Thread.sleep(1000);
                    }
                }catch (Exception e){
                    //css not found
                }
            } else if (counter == timeLimit) {
                //LOG.info("  Element not found in waitForPageToBeLoaded ");
            }
        }
        return found;
    }

    private static void checkInitiationofPageObjectXpath(AppiumDriver driver, String xpath, int timeLimit) {
        String device = driver.getCapabilities().getCapability("deviceName").toString();
        int counter = 0;
        while (counter < timeLimit) {
            LOG.info(" Waiting for Page Object to be instantiated " + device + " loop nr " + counter);
            try {
                counter= counter + 1;
                Thread.sleep(1000);
                if (driver.findElements(By.xpath(xpath)) == null) {
                } else {
                    counter = timeLimit + 1;
                }
            } catch (Exception e) {
                LOG.info(" Catched a null pointer, do nothing");
            }
        }
    }

    public static boolean isTheFoundElementDisplayedXpath(final AppiumDriver driver, String xpath ) {
        String device = driver.getCapabilities().getCapability("deviceName").toString();
        int counter = 0;
        int timeLimit = 5;

        while (counter < timeLimit) {
            LOG.info(" Is the element " + xpath + " displayed on the device " + device + " :loop nr " + counter);
            try {
                counter= counter + 1;
                Thread.sleep(500);
                WebElement e = driver.findElement(By.xpath(xpath));
                if (!e.isDisplayed()) {
                    System.out.println("Not displayed " + e + " " + e.isDisplayed());
                } else {
                    System.out.println("The element is displayed");
                    return true;
                }
            } catch (Exception e) {
                LOG.info(" Catched a null pointer, do nothing");
                return false;
            }
        }
        return false;
    }

}
