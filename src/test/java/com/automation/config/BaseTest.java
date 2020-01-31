package com.automation.config;

import io.appium.java_client.AppiumDriver;
import org.testng.annotations.*;

import java.util.logging.Logger;

import static com.automation.config.EnvironmentConfig.*;


public class BaseTest extends WebDriver {
    private static final Logger LOG = Logger.getLogger(BaseTest.class.getName());
    protected AppiumDriver driver;

    @Parameters({ "APPIUM_PORT_" })
    @BeforeTest(alwaysRun = true)
    public void startAppiumServers( final String APPIUM_PORT_ ) {
      /*  BuildAppiumServer appiumServer = new BuildAppiumServer();
        Integer port = Integer.parseInt(APPIUM_PORT_);
        if (!appiumServer.checkIfServerIsRunnning(port)) {
                appiumServer.startServer(appiumServerUrl(), APPIUM_PORT_);
                System.out.println("Appium Server started - " + port);
        } else {
            System.out.println("Appium Server already running on Port - " + port);
        }*/
    }

    @Parameters({"UDID_", "NAME_", "APPIUM_PORT_", "PLATFORM_", "PLATFORM_VERSION_"})
    @BeforeClass(alwaysRun = true)
    public void setup(final String UDID_, final String NAME_, final String APPIUM_PORT_, final String PLATFORM_, final String PLATFORM_VERSION_ ) {
        driver = createOrReuseDriver(UDID_, NAME_, APPIUM_PORT_, PLATFORM_, PLATFORM_VERSION_);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        closeDriver(driver);
    }

    @Parameters( "APPIUM_PORT_" )
    @AfterSuite(alwaysRun = true)
    public void stopAppiumServers( final String APPIUM_PORT_ ) {
        //BuildAppiumServer appiumServer = new BuildAppiumServer();
        //appiumServer.stopServer(appiumServerUrl(), APPIUM_PORT_);
        System.out.println("Stop Appium server is disabled");
    }


}

