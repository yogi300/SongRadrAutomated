package WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageBase {
    public WebDriver driver = Browser.getWebDriver();
    public WebDriverWait wait = new WebDriverWait(driver,60);
    public Actions act = new Actions(driver);

    public void clickWhenVisibleAndClickable (String xpathOfElement) {
        /*
        * Step 1 : Wait for xpath element to be present in page.
        * Step 2 : Find WebElement to work with xpath.
        * Step 3 : Move mouse or hover to WebElement to make sure it becomes visible if invisible.
        * Step 4 : Wait for WebElement to be visible and clickable.
        * Step 5 : Click on WebElement.
        * TODO : Find what are the edge cases when this method fails.
        * TODO : Find ways to eliminate hardcoded sleep as this is unnecessarily slowing down method.
        */
        moveToXpathWe(xpathOfElement);
        //CommonUtils.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfElement)));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathOfElement)));
        //CommonUtils.sleep(1000);
        System.out.println("Clicking on Webelement having xpath " + xpathOfElement);
        act.click().perform();
    }

    public void moveToXpathWe(String xpathWe){
        /*
        * Step 1 : Wait for full page load using Javascript.
        * Step 2 : Wait for all background ajax calls to finish.
        * Step 3 : Wait for presence of WebElement referred by xpath
        * Step 4 : Scroll into visibility of WebElement.
        * Step 4 : Do mouse action and move to WebElement
        * Step 5 : Wait for ajax load and page load to complete, which may have triggered by Mouse Movement.
        * */
        waitingForFullPageLoad();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathWe)));
        WebElement weForXpath = driver.findElement(By.xpath(xpathWe));
        act.moveToElement(weForXpath).perform();
        System.out.println("Hovering on Webelement having xpath " + xpathWe);
        waitingForFullPageLoad();
    }

    public void sendKeysWhenElementIntractable(String xpathWe, String textToSend) {
        clickWhenVisibleAndClickable(xpathWe);
        act.sendKeys(textToSend).perform();
    }

    public boolean waitingForJavaScriptAndJqueryToFinish() {
        System.out.println("Waiting For JavaScript And Jquery Runs To Finish");
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            try {
                assert driver != null;
                return ((Long)((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
            }
            catch (Exception e) {
                return true;
            }
        };
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState")
        .toString().equals("complete");
        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }

    public void waitingForFullPageLoad() {
        System.out.println("Waiting For Full Page Load");
        try {
            wait.until(
                    driver -> ((JavascriptExecutor) driver)
                            .executeScript("return document.readyState")
                            .equals("complete"));
        } catch (Exception e) {
            System.out.println("document.readyState Javascript is invalid on this page. " +e.toString());
        }
    }

    public void scrollByXpath(String xpathOfElement) {
        waitingForFullPageLoad();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfElement)));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathOfElement)));
        WebElement we = driver.findElement(By.xpath(xpathOfElement));
        scrollByWebElement(we);
    }

    public void scrollByWebElement(WebElement we) {
        waitingForFullPageLoad();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", we);
        waitingForFullPageLoad();
    }

    public void selectFromDropdown(WebElement we, String xpathSelect, String chosenValue) {
        WebElement weSelect = we.findElement(By.xpath(xpathSelect));
        Select dropDown = new Select(weSelect);
        dropDown.selectByVisibleText(chosenValue);
    }
}

