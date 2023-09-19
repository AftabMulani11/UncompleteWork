package Wailwil.BaseTest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import org.testng.annotations.*;

import Wailwil.SupportFiles.ExcelDataReader;

public class Basetest {
    public static Properties prop = new Properties();
    public static ExcelDataReader dataReader;
    public static TestAction pageObj;
    Map<String, String> excelTestData = null;
    @BeforeSuite(alwaysRun = false)
    public void setup() throws IOException {
        FileReader reader=new FileReader("src/test/resources/config.properties");
        prop.load(reader);
        File currDir = new File(".");
        System.out.println("system path=" + System.getProperty("user.dir"));
        String path = currDir.getAbsolutePath();
        System.out.println("path=" + path);
        dataReader = new ExcelDataReader(prop.getProperty("ExcelDataPath"))
        .loadFile().loadExcel(this.getClass().getSimpleName());
    }
}