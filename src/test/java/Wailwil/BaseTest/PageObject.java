package Wailwil.BaseTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class PageObject {
    public WebDriver driver;
    public PageObject(WebDriver driver){

        this.driver=driver;
        PageFactory.initElements(driver, this);
    }
    // ------------------------------------------Login Page--------------------------------------------------------------
    @FindBy(xpath = "//input[@type='email']")
	public WebElement loginTextBox;

	@FindBy(xpath = "//input[@value='Next']")
	public WebElement nextButton;

	@FindBy(xpath = "//input[@type='password']")
	public WebElement passwordTextBox;

	@FindBy(xpath = "//input[@value='Sign in']")
	public WebElement signInButton;

	@FindBy(xpath = "//div[contains(@class,'avatar')]")
	public WebElement userMenu;

	@FindBy(xpath = "//app-login/div/div/div[1]/div[2]/div/button/span")
	public WebElement signinwithwalwilIDBtn;

    // ------------------------------------------Login Page End--------------------------------------------------------------
	// ------------------------------------------Logout Elements-------------------------------------------------------------
	@FindBy(xpath = "//div[contains(@class,'my-auto text-avatar')]")
	public WebElement userprofileMenu;

	@FindBy(xpath = "//a[@mattooltip='Log Out']")
	public WebElement logoutMenu;

	@FindBy(xpath = "//div[@class='mx-4 my-auto heading ng-star-inserted'][normalize-space()='Admin']")
	public WebElement AdminMenu;

	@FindBy(xpath = "//a[@class='cursor-pointer mb-3.5 menu-toggle mx-auto']")
	public WebElement MenuExpand;

	@FindBy(xpath = "//button[@id='tooltip']//*[name()='svg']")
	public WebElement AddRow;
}