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


    public static void waitForThePageObjectToBeLoadedToFindTheWebElement(final AppiumDriver driver, String css ) {
        String device = driver.getCapabilities().getCapability("deviceName").toString();
        //Check  that the page object is created
        Integer counter = 0;
        Integer timeLimit = 40;
        LOG.info("The css string is " + css);
        while (counter < timeLimit ) {
            LOG.info(" Waiting for Page Object to be instantiated " + device + " loop nr " + counter);
            try {
                counter++;
                Thread.sleep(1000);
                if (driver.findElements(By.cssSelector(css)) == null) {
                } else {
                    counter = timeLimit + 1;
                }
            }catch (Exception e) {
                LOG.info(" Catched a null pointer, do nothing");
            }
        }
        //Check  that when the debug button is visible then the driver can be returned to start the test runs
        counter=0;
        timeLimit = 50;
        while (counter < timeLimit ) {
            LOG.info("In the loop  waiting for the Start Page " + device + " loop nr " + counter + " for element " + css );
            if (counter < timeLimit ){
                counter++;
                try {
                    if(driver.findElements(By.cssSelector(css)).size() > 0 ) {
                        counter = timeLimit + 1;
                    }else {
                        Thread.sleep(1000);
                    }
                }catch (Exception e){
                    LOG.info("Catched an exception while in the loop " + device + " driverId " + driver);
                }
            } else if (counter == timeLimit) {
                LOG.info("  Element not found in waitForPageToBeLoaded ");
            }
        }
    }


}
