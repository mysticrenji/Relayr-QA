package com.relayr.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import com.relayr.genericlib.SolventSelenium;
import com.relayr.pagelib.GoogleHomePage;

public class VerifySearch {
	
	private GoogleHomePage googleHomePage = null;
	
	/**
	 * This is the setup method to launch the browser and open Google Home page.
	 * Initilizing Page Objcets and API utility class
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@BeforeMethod
	public void launchBrowser() {
		SolventSelenium.launchBrowser();
		googleHomePage = new GoogleHomePage();
	}
	
	/**
	 * This is the data provider to test for multiple search strings.
	 * Data can be provided using XML or excel or other means.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@DataProvider(name = "Single_Search_Strings")
	public static String[] searchString() {
	        return new String[] { "Relayr", "facebook" };
	  }
	
	/**
	 * This is the data provider to test for multiple search string having no search results.
	 * Data can be provided using XML or excel or other means.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@DataProvider(name = "No_Search_Strings")
	public static String[] noSearchString() {
	        return new String[] { "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$", "SDF#$T#ERT%$ÊYGFRÊXDSDXGFDER%TEYERDYERSDGVBDXCFGBF"};
	  }
	
	/**
	 * This is first test to verify the search results. 
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@Test(dataProvider = "Single_Search_Strings")
	public void verifySearch(String searchString) {
		googleHomePage.performSearch(searchString);
		googleHomePage.verifySearchResultsForSingleString(searchString);
	}
	
	/**
	 * This is the second test to verify no search results. 
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@Test(dataProvider = "No_Search_Strings")
	public void verifyNoSearchResults(String noSearchString) {
		googleHomePage.performSearch(noSearchString);
		AssertJUnit.assertTrue("No search result failed!!", googleHomePage.getAllSearchResults().size() == 0);
		AssertJUnit.assertTrue("No search result message is not correct", googleHomePage.verifyNoSearchResults().contains(noSearchString));
	}
	
	/**
	 * This is the second test to verify I am feeling lucky button to bypass the search result and take user to the first search result. 
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@Test
	public void verifyFeelingLucky() {
		googleHomePage.enterSearchText("facebook");
		googleHomePage.clickOnFeelingLuckyButton();
		Assert.assertTrue(SolventSelenium.driver.getCurrentUrl().equals("https://www.facebook.com/"), "I am feeling lucky button is not taking user to correct URL");
	}
	
	/**
	 * Closing Browser.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@AfterMethod
	public void tearDown() {
		SolventSelenium.driver.quit();
	}

}
