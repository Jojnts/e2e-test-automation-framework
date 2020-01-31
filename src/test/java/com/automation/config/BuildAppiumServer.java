package com.automation.config;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class BuildAppiumServer {

    private AppiumDriverLocalService service;
    private AppiumServiceBuilder builder;

    public void startServer(final String ipAddress, final String portNumber) {

        Integer port = Integer.parseInt(portNumber);
        System.out.println("appium server to be started " + ipAddress + "  " + portNumber);
        builder = new AppiumServiceBuilder();
        builder.usingDriverExecutable(new File("/usr/local/bin/node"));
        builder.withIPAddress(ipAddress);
        builder.usingPort(port);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
        service = AppiumDriverLocalService.buildService(builder);
        System.out.println("appium starts ");
        service.start();
    }

    public void stopServer(final String ipAddress, final String portNumber) {
        Integer port = Integer.parseInt(portNumber);

        builder = new AppiumServiceBuilder();
        builder.withIPAddress(ipAddress);
        builder.usingPort(port);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
        service = AppiumDriverLocalService.buildService(builder);
        service.stop();
    }

    public boolean checkIfServerIsRunnning(int port) {

        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            //If control comes here, then it means that the port is in use
            isServerRunning = true;
        } finally {
            serverSocket = null;
        }
        return isServerRunning;
    }
}
