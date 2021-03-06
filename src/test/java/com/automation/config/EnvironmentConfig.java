package com.automation.config;

import org.openqa.selenium.Platform;

import java.util.logging.Logger;
import java.util.regex.Pattern;

public class EnvironmentConfig {
    private static final Logger LOG = Logger.getLogger(EnvironmentConfig.class.getName());
    public static final String APP_ACTIVITY = ".MainActivity";
    private String projectHomePath;
    private String projectName;
    private String hostPlatform;
    private String chromeDriverPath;

    public enum EnvironmentNames {
        SANDBOX,
        STAGE
    }

    private static String whenRunTestNgXmlFiles() {
        EnvironmentNames testEnv  = EnvironmentNames.STAGE;
        return testEnv.toString();
    }

    public final static String appPackageToBeUse(){
        String appPackageGradle = System.getProperty("testEnv");
        if (appPackageGradle == null) {
            //The testEnv is not set through gradle then use stage
            appPackageGradle = whenRunTestNgXmlFiles().toLowerCase();
        }
        switch (appPackageGradle.toLowerCase()) {
            case "sandbox":
                return "ai.arthro.jointacademy-sandbox";
            default:
                return "ai.arthro.jointacademy-stage";
        }
    }

    public final static String appPackageToBeUseAndroid(){
        String appPackageGradle = System.getProperty("testEnv");
        if (appPackageGradle == null) {
            //The testEnv is not set through gradle then use stage
            appPackageGradle = whenRunTestNgXmlFiles().toLowerCase();
        }
        switch (appPackageGradle.toLowerCase()) {
            case "sandbox":
                return "ai.arthro.jointacademy_sandbox";
            default:
                return "ai.arthro.jointacademy_stage";
        }
    }

    public final static String appTestEnvUrlBeUse(){
        String testEnvUrl = System.getProperty("testEnvUrl");
        return testEnvUrl;
    }
    private static String getHostPlatform() {
        Platform hostPlatform = Platform.getCurrent();
        switch (hostPlatform) {
            case MAC:
                return "mac";
            case LINUX:
            case UNIX:
                return "unix";
            case WINDOWS:
            case WIN10:
            case XP:
                return "windows";
            default:
                return "nomatch";
        }
    }

    static public  String useChromeDriverPath() {
        String hostPlatform = getHostPlatform().toLowerCase();
        String projectHomePath = System.getProperty("user.dir");
        System.out.println("The host platform is " + hostPlatform);
           switch (hostPlatform) {
               case "mac":
                   return (projectHomePath + "/chromeDrivers");
                case "unix":
                    return (projectHomePath + "/chromeDrivers");
                case "windows":
                    return (projectHomePath + "\\chromeDrivers");
                default:
                    return "nomatch in project chromeDriver map";
        }
    }

    static public String appiumServerUrl() {
        String appiumIp = System.getProperty("appiumIp");
        if (appiumIp == null) {
            //The appiumIp is not set through gradle then use localhost
            return "127.0.0.1";
        }
        String ipCheck = "([01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])";
        String ipExp = ipCheck + "\\." + ipCheck + "\\." + ipCheck + "\\." + ipCheck;

        Pattern ipPattern = Pattern.compile(ipExp);
        boolean ipMatch = ipPattern.matcher(appiumIp).matches();

        if (ipMatch) {
            LOG.info("appiumIp is " + appiumIp);
            return appiumIp;
        } else {
            LOG.info("appiumIp is " + "127.0.0.1");
            return "127.0.0.1";
        }
    }


}
