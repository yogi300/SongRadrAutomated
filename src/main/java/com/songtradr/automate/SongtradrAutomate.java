package com.songtradr.automate;

import WebDriver.PageBase;
import com.DeathByCaptcha.*;
import com.songtradr.util.Xpaths;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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

	public void loginInSongTradr() throws Exception {
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
        try {
            captchaBypassing(dbCaptchaUserName, dbCaptchaPassword, splitsitekey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Death By captcha failed to solve Recaqptcha");
        }
    }

	private void captchaBypassing(String dbCaptchaUserName, String dbCaptchaPassword,
			String splitsitekey) throws Exception {
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
			}
			JavascriptExecutor js = (JavascriptExecutor) driver;
			try {
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
            } catch (NullPointerException npe) {
			    npe.printStackTrace();
			    reportBadSolutionDbc(captcha, client);
            } catch (TimeoutException te){
                te.printStackTrace();
                reportBadSolutionDbc(captcha.id, client);
            }
			Thread.sleep(20000);
		} catch (com.DeathByCaptcha.Exception e) {
		    e.printStackTrace();
			System.out.println(e);
		} catch (InterruptedException | JSONException | IOException | AccessDeniedException | InvalidCaptchaException | ServiceOverloadException e) {
			e.printStackTrace();
		}
    }

    private void reportBadSolutionDbc(int id, Client client) throws Exception {
        int reportIncorrectSolution = 0;
        while (!client.report(id)) {
            reportIncorrectSolution++;
            if (reportIncorrectSolution >5) {
                throw new Exception("Reporting Incorrect " +
                        "Captcha Attempts exceeded. Check  with DeathByCaptcha.");
            }
        }
    }

	private void reportBadSolutionDbc(Captcha captcha, Client client) throws Exception {
		int reportIncorrectSolution = 0;
		while (!client.report(captcha)) {
			reportIncorrectSolution++;
			if (reportIncorrectSolution >5) {
				throw new Exception("Reporting Incorrect " +
						"Captcha Attempts exceeded. Check  with DeathByCaptcha.");
			}
		}
	}

    public void uploadAllAlbums() {
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
		File setupXml = null;
		File albumImage = null;

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
			albumImage = imageFiles[0];
		}

		if (configXmls.length != 1) {
			throw new Exception("There should be 1 configXml in Album Path." +
					" Either there is no configXml or there is more than 1 configXmls");
		} else {
			setupXml = configXmls[0];
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document domAlbumConfig = builder.parse(setupXml);
		uploadMusicFiles(musicFiles);
        submitMusicFiles(musicFiles);
	}

	private void uploadMusicFiles(File [] musicFiles) throws InterruptedException {
		for (File musicFile : musicFiles) {
			System.out.println("Uploading Music File "+ musicFile.getName()
					+ "from path "+ musicFile.getAbsolutePath());
			Thread.sleep(2000);
			driver.navigate().refresh();
            clickWhenVisibleAndClickable(getXpath.uploadButton);
            waitingForFullPageLoad();
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
		System.out.println("Uploaded all music file");
	}

	public void submitMusicFiles(File [] musicFiles) {
	    for (File musicFile : musicFiles ) {
            String musicFileName = musicFile.getName().split("\\.")[0];
	        System.out.println("Submitting uploaded File "
                    + musicFileName);
	        String xpathPendingMusicSubmission = getXpath.pendingMusicSubmission
                    (musicFileName);
            moveToXpathWe(xpathPendingMusicSubmission);
            WebElement wePendingMusicSubmission = driver.findElement
                    (By.xpath(xpathPendingMusicSubmission));
			scrollByWebElement(wePendingMusicSubmission);
			System.out.println("Submitting Music File " + musicFileName);
			selectFromDropdown(wePendingMusicSubmission,
					getXpath.selectVersion, "Main Mix");
			WebElement weSubmit = wePendingMusicSubmission.findElement
					(By.xpath(getXpath.submitAndContinue));
			act.moveToElement(weSubmit ).perform();
			act.click().perform();
        }
    }
}
