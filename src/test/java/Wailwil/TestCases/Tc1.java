package Wailwil.TestCases;

import java.io.IOException;
import java.util.Map;

import org.testng.annotations.*;
import Wailwil.BaseTest.driver;

public class Tc1 extends driver {
     Map<String, String> excelTestData = null;
    @Test
    @Parameters("browser")
    public void login(String browser) throws Exception {
        try{
            System.out.println("Entering in login stage");
            launch(browser);
            excelTestData = dataReader.getKeyValuePairData();
            pageObj.waitForElementToEnable(pageObj.loginTextBox);
            pageObj.setText(pageObj.loginTextBox, excelTestData.get("Username"));
            pageObj.click(pageObj.nextButton);
            pageObj.waitForElementToEnable(pageObj.passwordTextBox);
            pageObj.setText(pageObj.passwordTextBox, excelTestData.get("Password"));
            pageObj.click(pageObj.signInButton);
            pageObj.waitForElementToEnable(pageObj.signinwithwalwilIDBtn);
            pageObj.click(pageObj.signinwithwalwilIDBtn);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @AfterTest(alwaysRun = true)
    public void Lout() throws IOException{
        System.out.println("Entering in logout stage");
        pageObj.waitForElementToEnable(pageObj.userMenu);
        pageObj.click(pageObj.userMenu);
        pageObj.waitForElementToEnable(pageObj.logoutMenu);
        pageObj.click(pageObj.logoutMenu);
        driver.quit();
    }
}
