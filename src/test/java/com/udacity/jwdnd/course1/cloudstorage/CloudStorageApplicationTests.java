package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseURL;

	private String testUserFirstName = "James";
	private String testUserLastName= "Gosling";
	private String testUsername = "drjava";
	private String testPassword = "TheSunAlsoRises";
	private String testUrl = "www.sun.com";


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testUserSignupLoginLogoutAndUnauthorizedAccess() {

		// test User Signup
		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(testUserFirstName, testUserLastName, testUsername, testPassword);

		// test User Login
		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(testUsername, testPassword);

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		assertEquals("Upload a New File:", homePage.getUploadLabel());

		// test User Logout
		homePage.logout();

		String homePageUrl = baseURL + "/home";
		driver.get(homePageUrl);
		String currentPageUrl = driver.getCurrentUrl();
		System.out.println("Current page " + currentPageUrl);
		assertNotEquals(homePageUrl, currentPageUrl);
	}

	@Test
	public void createViewEditAndDeleteNote() throws InterruptedException {

		// create user and sign in
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(testUserFirstName, testUserLastName, testUsername, testPassword);
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(testUsername, testPassword);


		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		// create note
		String title = "Test Note 1";
		String description = "This is a test note from Selenium.";
		homePage.addNote(title, description);

		Thread.sleep(1000);

		// confirm note was added to the page
		assertEquals(homePage.findNoteText(title), true);


		Thread.sleep(500);
		// delete note
		homePage.deleteNote();

		Thread.sleep(500);

		// confirm note was deleted from the page
		assertEquals(homePage.findNoteText(title), false);
	}


	@Test
	public void createViewEditAndDeleteCredential() throws InterruptedException {

		// create user and sign in
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(testUserFirstName, testUserLastName, testUsername, testPassword);
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(testUsername, testPassword);


		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		// create credential
		homePage.addCredential(testUrl, testUsername, testPassword);

		Thread.sleep(500);

		// confirm credential was added to the page
		assertEquals(homePage.findCredentialText(testUrl), true);

		// confirm that password is not visible in table, should be false
		assertEquals(homePage.passwordVisibleInTable(testPassword), false);

		Thread.sleep(500);

		// delete credential
		homePage.deleteCredential();

		assertEquals(homePage.findCredentialText(testUrl), false);
	}

}
