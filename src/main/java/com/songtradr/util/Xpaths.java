package com.songtradr.util;

public class Xpaths {
	public String loginButton = "//button[text()='Log In']";
	public String id = "//input[@id='EmailAddress']";
	public String password = "//input[@id='Password']";
	public String sitekey = "//iframe[contains(@src, 'https://www.google.com/recaptcha/api2')]";
	public String login = "//span[text()='Log In']";
	//public String browsebutton = "//div[contains(@class,'upload_version_row')]//button";
	public String termCondition = "//div[contains(@class,'upload-section')]//span";

	public String uploadButton = "//*[contains(text(),'Upload')]";


	public String inputMulit = "//*[@id=\"UltrawideContentLoad\"]/div/div/div[2]/div[2]/div/div/div[2]/div[1]/input" ;
	// UPLOAD MUSIC Module
	
	public String checkboxTermsAndConditions = "//span[contains(@class,'checkbox')]";
	public String browseButton = "//button[text()='Browse']";
	public String allAlbumPath = "C:\\Users\\Public\\songtradr-releases";
	public String version = "//div[@class='songs_submission_section']//select";
	public String submitAndContinue = "//button[text()='Submit & Continue']";

	// Recording Page

	public String songTitle_RecordingPage = "//div[@class='card-body']//input";
	public String recordingYear_RecordingPage = "//div[@class='card-body']/div[1]/div[2]//div[2]/div//input";
	public String releaseDate_RecordingPage = "//input[contains(@class,'form-control') and @placeholder='Select Date' ]";
	public String countryOfOrigin_RecordingPage = "//div[@class='card-body']//div[4]//select";
	public String artWorkimage_RecordingPage = "//div[@class='card-body']//img";
	public String artist_RecordingPage = "//div[@class='card-body']/div[2]//select";
	public String artistName_RecordingPage = "//div[@class='card-body']/div[2]/div[2]//input";
	public String artistNameAddButton_RecordingPage = "//div[@class='card-body']/div[2]/div[2]//button";
	public String masterOwnershipName_RecordingPage = "//div[@class='card-body']/div[3]//input";
	public String masterOwnershipAddButton_RecordingPage = "//div[@class='card-body']/div[3]//button";
	public String proposedpercentage_RecordingPage = "//input[@type='text'and@name='masterOwners']";
	public String visibilityHideSong_RecordingPage = "//div[@class='card-body']/div[5]/label[2]/span[1]";
	public String saveAndNextSameForAllPages = "//button[text()='Save & Next']";

	// COPYRIGHT PAGE
	public String copyRightTitle = "//div[@class='card-body']/div[3]//input";
	public String copyRightYear = "//input[@name='copyrightYear']";
	public String publicDomain_CopyRightPage = "//select[@name='publicDomain']";
	public String copyrightOwnershipName = "//div[@class='card-body']/div[4]//input";
	public String copyrightOwnershipADD = "//div[@class='card-body']/div[4]//button";
	public String proposedPercentage_CopyRightPage = "//td[@class='percentage-column']//input";

	// METADATA PAGE
	public String VocalType_MetaDataPage = "//button[text()='Instrumental']";
	public String explicit_MetaDataPage = "//button[text()='Clean']";
	public String tempo_MetaDataPage = "//button[text()='Up']";
	public String genre_MetaDataPage = "//button[text()='Dance/Electronic']";

	// AUDIO PAGE

	// Same SAVE AND NEXT x-path
	// PRICING PAGE

	// Same SAVE AND NEXT x-path

	// APPROVAL PAGE

	public String returnMusicUpload = "//button[text()='Return to Music Uploads']";

	// ==================================================================================================================================

	// 2nd MODULE- DISTRIBUTION MODULE

	public String distribution = "//div[@class='nav-top']//a[8]";
	public String createNewAlbum_DistributionPage = "//div[@class='row __distro_ovrv_select']/div[2]/div";
	public String releaseTitle = "//div[@class='form-group']//input";
	public String createRelease = "//div[@class='modal-footer']/button[2]";

	// Details PAGE

	public String countryOfOrigin_DetailsPage = "//div[@class='__distro_tab_fields']//div[3]/select";
	public String recordLevel_DetailsPage = "//div[@class='__distro_tab_fields']/div[2]/div[2]/input";
	public String mainArtist_DetailsPage = "//div[@class='dropdown __distro_edt_artistslct __main']/button";
	public String saveAndContinueSameforAll_DetailsPage = "//button[@id='bsa_distro_edt_fm']/span[1]";

}
