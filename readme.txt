Commands to run the tests


#To start the tests from the command line------
>gradlew test (on platform Windows)
>./gradlew test (on platform Unix)

#To run a specific test suite (default is testng.xml)
-PsuiteFile=testng_oneDevice.xml

#To change appium server url from local host to somewhere else (default i 127.0.0.1)
-PappiumIp=192.168.33.101

#To run an app for test environment Sandbox (default is stage)
-PtestEnv=sandbox

#To get information printed out while the tests are running
--info or --debug

#Examples on different combinations
>gradlew test
>gradlew test --info
>gradlew test -PtestEnv=sandbox --info
>gradlew test -PtestEnv=sandbox -PsuiteFile=testng_oneDevice.xml --debug
>gradlew test -PappiumIp=192.168.33.101

#CircleCI will run
gradlew test -PappiumIp=x.x.x.x  (defaul the stage app and the test suite testng.xml)
