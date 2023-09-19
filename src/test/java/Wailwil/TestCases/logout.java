package Wailwil.TestCases;

import java.io.IOException;
import org.testng.annotations.*;
import Wailwil.BaseTest.driver;


public class logout extends driver {
	@Test
    public void Lout() throws IOException{
        pageObj.waitForElementToEnable(pageObj.userMenu);
        pageObj.click(pageObj.userMenu);
        pageObj.waitForElementToEnable(pageObj.userprofileMenu);
        pageObj.click(pageObj.userprofileMenu);
        pageObj.waitForElementToEnable(pageObj.logoutMenu);
        pageObj.click(pageObj.logoutMenu);
    }
}
