package com.songtradr.automate;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.openqa.selenium.JavascriptExecutor;

import com.songtradr.util.Xpaths;

import org.openqa.selenium.support.ui.ExpectedConditions;

import com.DeathByCaptcha.AccessDeniedException;
import com.DeathByCaptcha.Client;
import com.DeathByCaptcha.HttpClient;
import com.DeathByCaptcha.SocketClient;
import com.google.common.util.concurrent.Uninterruptibles;
import com.DeathByCaptcha.Captcha;

public class SongtradrAutomate {

	public void loginInSongTradr() {
		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\Rakesh Sharma\\Downloads\\geckodriver-v0.26.0-win64\\geckodriver.exe");

		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		Xpaths fetchxpath = new Xpaths();
		driver.get("https://www.songtradr.com/");
		// xpath of login : //button[text()='Log In']
		driver.findElement(By.xpath(fetchxpath.loginButton)).click();
		// xpath if id: //input[@id='EmailAddress']
		// id: info@melm.rocks pwd:dfRtg0#%
		String id = "info@melm.rocks";
		String pwd = "!!Music2020!!";
		String dbCaptchaUserName = "kegelbrother";
		String dbCaptchaPassword = "Steuer99";
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(fetchxpath.id)));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(fetchxpath.id)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fetchxpath.id)));
		WebElement mp = driver.findElement(By.xpath(fetchxpath.id));

		Actions act = new Actions(driver);
		act.moveToElement(mp).sendKeys(id).build().perform();
		driver.findElement(By.xpath(fetchxpath.password)).sendKeys(pwd);
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(fetchxpath.Captchaclick)));
		// WebElement mp1 = driver.findElement(By.xpath(fetchxpath.Captchaclick));
		// Actions act1 = new Actions(driver);
		// act1.moveToElement(mp1).click().perform();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(fetchxpath.sitekey)));
		String sitekey = driver.findElement(By.xpath(fetchxpath.sitekey)).getAttribute("src");
		System.out.println(sitekey);
		String splitsitekey = sitekey.split("&")[1].split("=")[1];
		String pageUrl = driver.getCurrentUrl();
		// driver.findElement(By.xpath(fetchxpath.Captchaclick)).click();

		captchaBypassing(driver, dbCaptchaUserName, dbCaptchaPassword, splitsitekey, pageUrl);

	}

	private void captchaBypassing(WebDriver driver, String dbCaptchaUserName, String dbCaptchaPassword,
			String splitsitekey, String pageUrl) {
		JSONObject tokenParams = new JSONObject();
		try {
			tokenParams.put("proxy", "");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			tokenParams.put("proxytype", "");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			tokenParams.put("googlekey", splitsitekey);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			tokenParams.put("pageurl", pageUrl);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.print(tokenParams);
		Client client = (Client) (new HttpClient(dbCaptchaUserName, dbCaptchaPassword));
		try {
			try {
				System.out.println("Your balance is " + client.getBalance() + " US cents");
			} catch (IOException e) {
				System.out.println("Failed fetching balance: " + e.toString());
				return;
			}

			Captcha captcha = null;
			try {
				// Upload a reCAPTCHA and poll for its status with 120 seconds timeout.
				// Put your proxy, proxy type, page googlekey, page url and solving timeout (in
				// seconds)
				// 0 or nothing for the default timeout value.
				captcha = client.decode(tokenParams);
			} catch (InterruptedException e) {
				System.out.println("Failed uploading CAPTCHA");
				return;
			} catch (FileNotFoundException e) {
				System.out.println("Failed uploading CAPTCHA");
				return;
			} catch (IOException e) {
				System.out.println("Failed uploading CAPTCHA");
				return;
			}
			if (null != captcha) {
				System.out.println("CAPTCHA " + captcha.id + " solved: " + captcha.text);
			}
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String solvecaptchascript = "document.getElementById('g-recaptcha-response').innerHTML='" + captcha.text
					+ "';";
			System.out.println(solvecaptchascript);
			js.executeScript(solvecaptchascript);
			js.executeScript("document.forms[0].submit();");
			Thread.sleep(20000);
			openSellerDashboard(driver);
		} catch (com.DeathByCaptcha.Exception e) {
			System.out.println(e);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	private void openSellerDashboard(WebDriver driver) {
		driver.get("https://www.songtradr.com/user/dashboard/seller");
		List<WebElement> anchors = driver.findElements(By.tagName("a"));
		Iterator<WebElement> i = anchors.iterator();
		

		while (i.hasNext()) {
			WebElement anchor = i.next();
			if (anchor.getAttribute("href").contains("/user/songv2/uploads")) {
				anchor.click();
				break;
			}
		}
		driver.get("https://www.songtradr.com/user/songv2/uploads");
		Xpaths fetchxpath = new Xpaths();
		/*
		 * WebElement webElement =
		 * driver.findElement(By.xpath(fetchxpath.termCondition)); for (int count = 0;
		 * count < 2; count++) { webElement.click(); }
		 */

		WebElement webElement = driver.findElement(By.xpath("//input[@type='checkbox']"));
		for (int count = 0; count < 1; count++) {
			webElement.click();
		}

		driver.findElement(By.xpath(fetchxpath.browsebutton)).click();
	}

}
