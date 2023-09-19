package Wailwil.BaseTest;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;



public class TestAction extends PageObject {
	JavascriptExecutor js;
	Actions act;
    public TestAction(WebDriver driver) {
        super(driver);
		js = (JavascriptExecutor) driver;
    }
    public void setText(WebElement element, String text) {
		try {

			if (element.isDisplayed() && element.isEnabled()) {

				highLightWebElement(element, true);
				Thread.sleep(1000);
				element.clear();
				element.sendKeys(text);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getText(WebElement element) {
		String text = null;
		try {

			highLightWebElement(element, true);
			Thread.sleep(1000);
			// element.clear();
			text = element.getText();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	public void waitForTime(int timeout) {

		try {
			Thread.sleep(timeout * 1000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void click(WebElement element) {
		highLightWebElement(element, true);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		element.click();

	}

	public boolean waitForElementToEnable(WebElement element) {
		boolean flag = false;
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
			wait.withTimeout(Duration.ofSeconds(30));
			wait.pollingEvery(Duration.ofSeconds(2));
			wait.ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			flag = true;

		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public void elementVisible(WebElement elementToBeVisible) {

		try {

			// Initialising explicit wait
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

			// Wait for the element clickable
			wait.until(ExpectedConditions.visibilityOf(elementToBeVisible));

		} catch (NoSuchElementException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void compareText(String expectedText, String actualText, String failureMessage) {
		Assert.assertEquals(expectedText, actualText, failureMessage);
		System.out.println("Assertion passed");

	}

	public boolean isElementDisplayed(WebElement element) {

		return element.isDisplayed();
	}

	public void scrollHorizontal(WebElement element) {
		js.executeScript("arguments[0].scrollBy(2000,0)", element);
	}

	public void scrollToElement(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView();", element);

	}

	// Highlighting element for demos
	public void highLightWebElement(WebElement WebelementToHiglight, boolean highlight) {

		try {
			// below is used to highlight only when flag is true
			if (highlight == true) {
				// waiting for user experience
				Thread.sleep(300);
				// highlighting element
				js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
						WebelementToHiglight);
				// setting value as false
				highlight = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		public WebElement getElementSafely(By path) {
			return getElementSafely(path, 30);
		}

	/**
	 * This method gets the WebElement by the specified locator and returns it, or
	 * throws an exception if the element is not found after a specified timeout.
	 * @param path
	 * @param timeoutInSeconds
	 * @return e1
	 */
	public WebElement getElementSafely(By path, long timeoutInSeconds) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeoutInSeconds))
				.pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);
		WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(path));
		// highLightWebElement(el,true);
		return el;
	}

	
	/**
	 * This method gets the WebElement by the specified WebElement and returns it,
	 * or throws an exception if the element is not found after a specified timeout.
	 * 
	 * @param elem
	 * @return getElementSafely(elem, 30);
	 */
	public WebElement getElementSafely(WebElement elem) {
		return getElementSafely(elem, 30);
	}

	/**
	 *  This method gets the WebElement by the specified WebElement and returns it,
	 * or
	 * throws an exception if the element is not found after a specified timeout.
	 * @param elem
	 * @param timeoutInSeconds
	 * @return
	 */
	public WebElement getElementSafely(WebElement elem, long timeoutInSeconds) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeoutInSeconds))
				.pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);
		WebElement el = wait.until(ExpectedConditions.visibilityOf(elem));
		// highLightWebElement(el,true);
		return el;
	}

	/**
	 * Method to click element with polling and timeout in 30 seconds
	 * 
	 * @param path
	 */
	public void clickElementSafely(By path) {
		clickElementSafely(path, 30);
	}

	// method to click element with polling and given time in seconds
	public void clickElementSafely(By path, long timeoutInSeconds) {
		FluentWait<WebDriver> ClickableWait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeoutInSeconds)).pollingEvery(Duration.ofSeconds(2))
				.ignoring(NoSuchElementException.class);

		WebElement el = ClickableWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(path)));
		// highLightWebElement(el,true);
		el.click();
	}

	/**
	 * This method to click element with polling and wait for 30 sec
	 * @param e
	 */
	public void clickElementSafely(WebElement e) {
		FluentWait<WebDriver> ClickableWait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);

		WebElement el = ClickableWait.until(ExpectedConditions.elementToBeClickable(e));
		// highLightWebElement(el, true);
		el.click();
	}

	/**
	 * This method to move cursor to the element used for hovering to filters
	 * @param e
	 */
	public void moveCursorToElement(WebElement e) {
		// highLightWebElement(e, true);
		act.moveToElement(e).build().perform();
	}

	/**
	 * This method to zoom in or out
	 * @param no
	 */
	public void browserZoom(String no) {
		js.executeScript("document.body.style.zoom = '" + no + "'");
	}

	/**
	 * This method scroll to right of page
	 * 
	 */
	public void scrollright() {
		Actions act = new Actions(driver);
		WebElement ele = driver.findElement(By.xpath("//div[@ref='eRightSpacer']"));
		act.moveToElement(ele, -10, 0).click().build().perform();
		waitForTime(2);
	}

	/**
	 * This method scroll to left of page
	 */
	public void scrollleft() {
		Actions act = new Actions(driver);
		WebElement ele = driver.findElement(By.xpath("//div[@ref='eLeftSpacer']"));
		act.moveToElement(ele, 10, 0).click().build().perform();
		waitForTime(2);

	}
	
	/**
	 * This method safely retrieves a list of web elements located by a specified By
	 * path, ensuring visibility within a defined timeout.
	 * Uses FluentWait to wait for elements to become visible, providing robustness
	 * during dynamic interactions.
	 * 
	 * @param path
	 * @param timeoutInSeconds
	 * @return el
	 * 
	 */
	
	public  List<WebElement> getElementsSafely(By path, long timeoutInSeconds) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeoutInSeconds))
				.pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);
		List<WebElement> el = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(path));
		// highLightWebElement(el,true);
		return el;
	}
	
	/**
	 * This method retrieves a list of web elements located by a specified By path,
	 * ensuring visibility
	 * within a default timeout of 30 seconds.
	 * Provides a convenient way to interact with elements reliably, especially in
	 * dynamic scenarios.
	 * 
	 * @param path
	 * @return
	 */
	public List<WebElement> getElementsSafely(By path) {
		return getElementsSafely(path, 30);
	}
	
}
