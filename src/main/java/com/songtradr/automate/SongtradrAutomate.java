package com.songtradr.automate;

import WebDriver.PageBase;
import com.DeathByCaptcha.*;
import com.songtradr.util.Xpaths;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.Exception;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SongtradrAutomate extends PageBase {

	String rootUrl = "https://www.songtradr.com/";
	Xpaths getXpath = new Xpaths();

	public void loginInSongTradr() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(rootUrl);
		clickWhenVisibleAndClickable(getXpath.loginButton);
		waitingForJavaScriptAndJqueryToFinish();
		String id = "info@melm.rocks";
		String pwd = "!!Music2020!!";
		String dbCaptchaUserName = "kegelbrother";
		String dbCaptchaPassword = "Steuer99";
		sendKeysWhenElementIntractable(getXpath.id,id);
		sendKeysWhenElementIntractable(getXpath.password,pwd);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath.sitekey)));
		String sitekey = driver.findElement(By.xpath(getXpath.sitekey)).getAttribute("src");
		System.out.println(sitekey);
		String splitsitekey = sitekey.split("&")[1].split("=")[1];
		captchaBypassing(dbCaptchaUserName, dbCaptchaPassword, splitsitekey);
	}

	private void captchaBypassing(String dbCaptchaUserName, String dbCaptchaPassword,
			String splitsitekey) {
		String pageUrl = driver.getCurrentUrl();
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
				try {
					System.out.println("Your balance is " + client.getBalance() + " US cents");
				} catch (AccessDeniedException e) {
					e.printStackTrace();
				} catch (InvalidCaptchaException e) {
					e.printStackTrace();
				} catch (ServiceOverloadException e) {
					e.printStackTrace();
				}
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
			} catch (ServiceOverloadException e) {
				e.printStackTrace();
			} catch (AccessDeniedException e) {
				e.printStackTrace();
			} catch (InvalidCaptchaException e) {
				e.printStackTrace();
			}
			if (null != captcha) {
				System.out.println("CAPTCHA " + captcha.id + " solved: " + captcha.text);
				// TODO : Report bad captcha solutions.
			}
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String solvecaptchascript = "document.getElementById('g-recaptcha-response').innerHTML='"
					+ captcha.text + "';";
			System.out.println(solvecaptchascript);
			js.executeScript(solvecaptchascript);
			js.executeScript("document.forms[0].submit();");
			waitingForFullPageLoad();
			String xpathLoginResult = "//body//*";
			moveToXpathWe(xpathLoginResult);
			String loginResult = driver.findElement(By.xpath(xpathLoginResult)).getText();
			System.out.println(loginResult);
			JSONObject loginResultJson = new JSONObject(loginResult);
			String redirect = loginResultJson.get("redirect").toString();
			System.out.println(rootUrl+redirect);
			driver.get(rootUrl+redirect);
			Thread.sleep(20000);
		} catch (com.DeathByCaptcha.Exception e) {
			System.out.println(e);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void uploadAllAlbums() {
		clickWhenVisibleAndClickable(getXpath.uploadButton);
		File directory = new File(getXpath.allAlbumPath);
		File[] objects = directory.listFiles();
		for (File object: objects) {
			if (object.isDirectory()) {
				try {
					uploadAlbum(object);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if (object.isFile()) {
				System.out.println("Do not place any files here. " +
						"Only all album directories are required here");
			}
			else {
				System.out.println("Do not place any other objects here. " +
						"Only all album directories are required here");
			}
		}
	}

	private void uploadAlbum(File album) throws Exception {
		System.out.println(album.getName());
		String albumId = album.getName();
		File setupXml;

		File [] musicFiles = album.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".wav");
			}
		});

		File [] imageFiles = album.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jpg");
			}
		});

		File [] configXmls = album.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		});

		if (musicFiles.length < 1) {
			throw new Exception("No Music Files to upload in this album." +
					"Please place music file having of type wav in album");
		}

		if (imageFiles.length != 1) {
			throw new Exception("There should be 1 Album cover image in Album Path." +
					" Either there is no image file or there is more than 1 image file");
		} else {
			File imageFile = imageFiles[0];
		}

		if (configXmls.length != 1) {
			throw new Exception("There should be 1 configXml in Album Path." +
					" Either there is no configXml or there is more than 1 configXmls");
		} else {
			File configXml = configXmls[0];
		}

		uploadMusicFiles(musicFiles);
        submitMusicFiles(musicFiles);
	}

	private void uploadMusicFiles(File [] musicFiles) throws InterruptedException {
		for (File musicFile : musicFiles) {
			System.out.println("Uploading Music File "+ musicFile.getName()
					+ "from path "+ musicFile.getAbsolutePath());
			Thread.sleep(2000);
			driver.navigate().refresh();
            clickWhenVisibleAndClickable(getXpath.checkboxTermsAndConditions);
			WebElement fileInput = driver.findElement(By.xpath(getXpath.uploadInput));
			waitingForJavaScriptAndJqueryToFinish();
			fileInput.sendKeys(musicFile.getAbsolutePath());
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
	}

	public void submitMusicFiles(File [] musicFiles) {
	    for (File musicFile : musicFiles ) {
            moveToXpathWe(getXpath.pendingSubmissions);
            List<WebElement> wePendingSubmissions = driver.findElements
                    (By.xpath(getXpath.pendingSubmissions));
            for (WebElement wePendingSubmission : wePendingSubmissions) {
                scrollByWebElement(wePendingSubmission);
                String musicFileName = wePendingSubmission.findElement
                        (By.xpath(getXpath.pendingFilename)).getText();
                String uploadedMusicFileName = musicFile.getName().split("\\.")[0];
                System.out.println("uploadedMusicFileName is " + uploadedMusicFileName
                        + ". Comparing with pending musicFileName " + musicFileName + "." +
                        "If same name, uploaded file is pending, it will be submitted.");
                if (uploadedMusicFileName.equals(musicFileName)) {
                    System.out.println("Submitting Music File " + musicFileName);
                    selectFromDropdown(wePendingSubmission,
                            getXpath.selectVersion, "Main Mix");
                }
                clickWhenVisibleAndClickable(getXpath.submitAndContinue);
            }
        }
    }
}
