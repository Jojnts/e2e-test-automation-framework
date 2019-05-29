package com.automation.config;

import io.appium.java_client.AppiumDriver;
import org.testng.annotations.*;

import java.util.logging.Logger;


public class BaseTest extends WebDriver {
    private static final Logger LOG = Logger.getLogger(BaseTest.class.getName());
    protected AppiumDriver driver;

    @Parameters({"APPIUM_IPADDRESS_", "APPIUM_PORT_"})
    @BeforeTest(alwaysRun = true)
    public void startAppiumServers(final String APPIUM_IPADDRESS_, final String APPIUM_PORT_) {
        BuildAppiumServer appiumServer = new BuildAppiumServer();
        Integer port = Integer.parseInt(APPIUM_PORT_);
        if (!appiumServer.checkIfServerIsRunnning(port)) {
                    appiumServer.startServer(APPIUM_IPADDRESS_, APPIUM_PORT_);
        } else {
            System.out.println("Appium Server already running on Port - " + port);
        }
    }


    @Parameters({"UDID_", "PLATFORM_VERSION_", "PLATFORM_", "URL_"})
    @BeforeClass(alwaysRun = true)
    public void setup(final String UDID_, final String PLATFORM_VERSION_, final String PLATFORM_, final String URL_) {
        driver = createOrReuseDriver(UDID_, URL_, PLATFORM_, PLATFORM_VERSION_);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        closeDriver(driver);
    }

    @Parameters({"APPIUM_IPADDRESS_", "APPIUM_PORT_"})
    @AfterTest(alwaysRun = true)
    public void stopAppiumServers(final String APPIUM_IPADDRESS_, final String APPIUM_PORT_) {
        BuildAppiumServer appiumServer = new BuildAppiumServer();
        appiumServer.stopServer(APPIUM_IPADDRESS_, APPIUM_PORT_);
    }


}

