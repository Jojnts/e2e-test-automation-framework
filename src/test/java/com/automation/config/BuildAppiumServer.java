package com.automation.config;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class BuildAppiumServer {

    private AppiumDriverLocalService serviceA;
    private AppiumDriverLocalService serviceB;
    private AppiumDriverLocalService service;
    private AppiumServiceBuilder builder;

    HashMap<Integer, AppiumDriverLocalService> map = new HashMap<Integer, AppiumDriverLocalService>();

    public void startServer(final String ipAddress, final String portNumber) {

        Integer port = Integer.parseInt(portNumber);

        builder = new AppiumServiceBuilder();
        builder.withIPAddress("127.0.0.1");
        builder.usingPort(port);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
    }

    public void stopServer(final String ipAddress, final String portNumber) {
        Integer port = Integer.parseInt(portNumber);

        builder = new AppiumServiceBuilder();
        builder.withIPAddress("127.0.0.1");
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
