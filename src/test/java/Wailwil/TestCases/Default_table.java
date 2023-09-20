package Wailwil.TestCases;

import org.testng.annotations.*;
import Wailwil.BaseTest.driver;

public class Default_table extends driver{
    @Test
    public void testDefault_table() throws Exception{
        System.out.println("Entering in Admin stage");
        pageObj.waitForElementToEnable(pageObj.MenuExpand);
        pageObj.click(pageObj.MenuExpand);
        pageObj.waitForElementToEnable(pageObj.AdminMenu);
        pageObj.click(pageObj.AdminMenu);
        System.out.println("Entering in Add Row stage");
        pageObj.waitForElementToEnable(pageObj.AddRow);
        pageObj.click(pageObj.AddRow);
        
    }
}
