package com.songtradr.automate;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.songtradr.metadata.pojo.METADATA;

import com.songtradr.metadata.pojo.Product;
import com.songtradr.metadata.pojo.Track;
import com.songtradr.metadata.service.XMLFileReader;
import com.songtradr.util.Xpaths;

public class SongRecording {
	public void inputMetaDataInfoAndRecord(WebDriver driver, Xpaths fetchxpath) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(fetchxpath.version)));

		XMLFileReader xmlFileRedader = new XMLFileReader();
		METADATA metaData = xmlFileRedader.readXmlFile();
		Product productDetails = metaData.getProduct();

		Track[] trackObject = productDetails.getTrack();
		for (int i = 0; i < trackObject.length; i++) {
			Track obj = trackObject[i];
			recordingAutomation(driver, fetchxpath, productDetails, obj);

		}

	}

	private void recordingAutomation(WebDriver driver, Xpaths fetchxpath, Product productDetails, Track obj) {
		Select selectVersion = new Select(driver.findElement(By.xpath(fetchxpath.version)));
		selectVersion.selectByValue("0");
		driver.findElement(By.xpath(fetchxpath.submitAndContinue)).click();

		driver.findElement(By.xpath(fetchxpath.songTitle_RecordingPage)).sendKeys(obj.getTrack_title().getTitle());
		driver.findElement(By.xpath(fetchxpath.artist_RecordingPage)).sendKeys(obj.getRecordingyear());

		driver.findElement(By.xpath(fetchxpath.releaseDate_RecordingPage)).sendKeys(obj.getRelease().getRelease_date());

		Select countryOfOrigin = new Select(driver.findElement(By.xpath(fetchxpath.countryOfOrigin_RecordingPage)));
		countryOfOrigin.selectByValue(obj.getRecordingcountry());

		driver.findElement(By.xpath(fetchxpath.artWorkimage_RecordingPage))
				.sendKeys(productDetails.getProduct_items().getItem_fileObject().getContent());

		Select mainArtistRecordingPage = new Select(driver.findElement(By.xpath(fetchxpath.mainArtist_DetailsPage)));
		mainArtistRecordingPage.selectByValue("0");

		driver.findElement(By.xpath(fetchxpath.artistName_RecordingPage))
				.sendKeys(obj.getTrack_artist().getShow_as_artist());

		driver.findElement(By.xpath(fetchxpath.artistNameAddButton_RecordingPage)).click();

		driver.findElement(By.xpath(fetchxpath.masterOwnershipName_RecordingPage))
				.sendKeys(productDetails.getProduct_artist().getShow_as_artist());

		driver.findElement(By.xpath(fetchxpath.masterOwnershipAddButton_RecordingPage)).click();

		driver.findElement(By.xpath(fetchxpath.visibilityHideSong_RecordingPage)).click();
		driver.findElement(By.xpath(fetchxpath.saveAndNextSameForAllPages)).click();
	}

	private void copyrightInformationAutomation(WebDriver driver, Xpaths fetchxpath, Product productDetails,
			Track obj) {

		driver.findElement(By.xpath(fetchxpath.copyRightTitle))
				.sendKeys(productDetails.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.copyRightYear))
				.sendKeys(productDetails.getCopyright().getInfo().getYear());

		driver.findElement(By.xpath(fetchxpath.copyRightYear))
				.sendKeys(productDetails.getCopyright().getInfo().getYear());

		Select publicDomain = new Select(driver.findElement(By.xpath(fetchxpath.publicDomain_CopyRightPage)));
		publicDomain.selectByValue(obj.getPublic_domain_composition());

		driver.findElement(By.xpath(fetchxpath.copyrightOwnershipName))
				.sendKeys(obj.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.copyrightOwnershipADD))
				.sendKeys(obj.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.saveAndNextSameForAllPages)).click();
	}
	
	private void metaDataAutomation(WebDriver driver, Xpaths fetchxpath, Product productDetails,
			Track obj) {

		driver.findElement(By.xpath(fetchxpath.copyRightTitle))
				.sendKeys(productDetails.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.copyRightYear))
				.sendKeys(productDetails.getCopyright().getInfo().getYear());

		driver.findElement(By.xpath(fetchxpath.copyRightYear))
				.sendKeys(productDetails.getCopyright().getInfo().getYear());

		Select publicDomain = new Select(driver.findElement(By.xpath(fetchxpath.publicDomain_CopyRightPage)));
		publicDomain.selectByValue(obj.getPublic_domain_composition());

		driver.findElement(By.xpath(fetchxpath.copyrightOwnershipName))
				.sendKeys(obj.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.copyrightOwnershipADD))
				.sendKeys(obj.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.saveAndNextSameForAllPages)).click();
	}
	
	private void audioFileAutomation(WebDriver driver, Xpaths fetchxpath, Product productDetails,
			Track obj) {

		driver.findElement(By.xpath(fetchxpath.copyRightTitle))
				.sendKeys(productDetails.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.copyRightYear))
				.sendKeys(productDetails.getCopyright().getInfo().getYear());

		driver.findElement(By.xpath(fetchxpath.copyRightYear))
				.sendKeys(productDetails.getCopyright().getInfo().getYear());

		Select publicDomain = new Select(driver.findElement(By.xpath(fetchxpath.publicDomain_CopyRightPage)));
		publicDomain.selectByValue(obj.getPublic_domain_composition());

		driver.findElement(By.xpath(fetchxpath.copyrightOwnershipName))
				.sendKeys(obj.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.copyrightOwnershipADD))
				.sendKeys(obj.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.saveAndNextSameForAllPages)).click();
	}
	
	private void priceAutomation(WebDriver driver, Xpaths fetchxpath, Product productDetails,
			Track obj) {

		driver.findElement(By.xpath(fetchxpath.copyRightTitle))
				.sendKeys(productDetails.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.copyRightYear))
				.sendKeys(productDetails.getCopyright().getInfo().getYear());

		driver.findElement(By.xpath(fetchxpath.copyRightYear))
				.sendKeys(productDetails.getCopyright().getInfo().getYear());

		Select publicDomain = new Select(driver.findElement(By.xpath(fetchxpath.publicDomain_CopyRightPage)));
		publicDomain.selectByValue(obj.getPublic_domain_composition());

		driver.findElement(By.xpath(fetchxpath.copyrightOwnershipName))
				.sendKeys(obj.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.copyrightOwnershipADD))
				.sendKeys(obj.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.saveAndNextSameForAllPages)).click();
	}
	
	private void approvalsAutomation(WebDriver driver, Xpaths fetchxpath, Product productDetails,
			Track obj) {

		driver.findElement(By.xpath(fetchxpath.copyRightTitle))
				.sendKeys(productDetails.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.copyRightYear))
				.sendKeys(productDetails.getCopyright().getInfo().getYear());

		driver.findElement(By.xpath(fetchxpath.copyRightYear))
				.sendKeys(productDetails.getCopyright().getInfo().getYear());

		Select publicDomain = new Select(driver.findElement(By.xpath(fetchxpath.publicDomain_CopyRightPage)));
		publicDomain.selectByValue(obj.getPublic_domain_composition());

		driver.findElement(By.xpath(fetchxpath.copyrightOwnershipName))
				.sendKeys(obj.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.copyrightOwnershipADD))
				.sendKeys(obj.getCopyright().getInfo().getContent());

		driver.findElement(By.xpath(fetchxpath.saveAndNextSameForAllPages)).click();
	}
}
