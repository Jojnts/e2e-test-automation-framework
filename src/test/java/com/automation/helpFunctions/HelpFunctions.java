package com.automation.helpFunctions;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

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

    //Only for found elements
    public static boolean isTheFoundElementDisplayed(final AppiumDriver driver, String css ) {
        String device = driver.getCapabilities().getCapability("deviceName").toString();
        int counter = 0;
        int timeLimit = 10;

        while (counter < timeLimit) {
            LOG.info(" Is the element displayed " + css + " on device " + device + " loop nr " + counter);
            try {
                counter= counter +1;
                Thread.sleep(1000);
                if (!driver.findElement(By.cssSelector(css)).isDisplayed()) {

                } else {
                    return true;
                }
            } catch (Exception e) {
                LOG.info(" Catched a null pointer, do nothing");
                return false;
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

        //isTheFoundElementDisplayed(driver, css);

        int counter=0;
        if( timeOutSeconds < 1 ) {
            timeLimit = 40;
        } else {
            timeLimit = timeOutSeconds;
        }
        while (counter < timeLimit ) {
            LOG.info("In the loop  waiting for the Page Object on " + device + " loop nr " + counter + " for element " + css );
            if (counter < timeLimit ){
                counter = counter +2;
                try {
                    if(driver.findElements(By.cssSelector(css)).size() > 0 ) {
                        counter = timeLimit + 2;
                        return isTheFoundElementDisplayed(driver, css);
                    }else {
                        Thread.sleep(2000);
                    }
                }catch (Exception e){
                    LOG.info("Catched an exception while in the loop " + device + " driverId " + driver);
                }
            } else if (counter == timeLimit) {
                LOG.info("  Element not found in waitForPageToBeLoaded ");
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
                counter= counter +2;
                Thread.sleep(2000);
                if (driver.findElements(By.cssSelector(css)) == null) {
                } else {
                    counter = timeLimit + 1;
                }
            } catch (Exception e) {
                LOG.info(" Catched a null pointer, do nothing");
            }
        }
    }


}
