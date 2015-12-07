package be.uantwerpen.iw.ei.se.webdriver.testcases;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Thomas on 23/11/2015.
 */
public class WebDriverTestCases
{
    private static String baseURL;
    private static WebDriver driver;
    private static Wait<WebDriver> wait;

    public WebDriverTestCases(String baseURL, WebDriver driver)
    {
        this.baseURL = baseURL;
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 6);

        //Maximize window for testing
        driver.manage().window().maximize();
    }

    public void loginWithCredentialsThomasHuybrechts()
    {
        //Login page
        driver.get(baseURL + "/Login?lang=en");

        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("thomas.huybrechts");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("test");
        driver.findElement(By.id("login")).click();

        //Wait for main portal page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("homePage")));

        Assert.assertTrue("Title should start with 'Click-O-Matic'. Result: " + driver.getTitle(), driver.getTitle().startsWith("Click-O-Matic"));
    }

    public void createNewUser()
    {
        //Homepage
        driver.get(baseURL + "/");

        //Click Users in navbar
        driver.findElement(By.linkText("Users")).click();

        //Wait for Users page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Create user")));

        //Click Create user link
        driver.findElement(By.linkText("Create user")).click();

        //Wait for Registration page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("firstName")));

        //Fill in firstname
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("selenium");

        //Fill in lastname
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("test");

        //Fill in username
        driver.findElement(By.id("userName")).clear();
        driver.findElement(By.id("userName")).sendKeys("test");

        //Fill in password
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("test");

        //Click create button
        driver.findElement(By.id("submit")).click();

        //Wait for reply server with alert
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alert")));

        Assert.assertTrue("Alert should start with 'New user has been created'. Result: " + driver.findElement(By.id("userAddedAlert")).getText(), driver.findElement(By.id("userAddedAlert")).getText().startsWith("New user has been created"));
    }
    
    public void editUser()
    {
        //Homepage
        driver.get(baseURL + "/");

        //Click Users in navbar
        driver.findElement(By.linkText("Users")).click();

        //Wait for User page to load
        wait.until(ExpectedConditions.titleContains("User settings"));

        //Click on Edit user of User Timothy.Verstraete
        driver.findElement(By.xpath("//tr[4]/td[3]/a/span")).click();

        //Wait for User edit page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("firstName")));

        //Fill in firstname
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Tim");

        //Fill in lastname
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("Verstraete");

        //Fill in password
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("test");

        //Click save button
        driver.findElement(By.id("submit")).click();

        //Wait for redirect to User page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("usersPage")));

        Assert.assertTrue("User Timothy Verstraete should be renamed to Tim Verstraete. Result: " + driver.findElement(By.xpath("//tr[4]/td[1]")).getText(), driver.findElement(By.xpath("//tr[4]/td[1]")).getText().contentEquals("Tim Verstraete"));
    }

    public void deleteUser()
    {
        //Homepage
        driver.get(baseURL + "/");

        //Click Users in navbar
        driver.findElement(By.linkText("Users")).click();

        //Wait for User page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("usersPage")));

        //Click on Delete user of User Timothy.Verstraete
        driver.findElement(By.xpath("//tr[4]/td[5]/a/span")).click();

        //Wait for redirect to User page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alert")));

        Assert.assertFalse("User Timothy.Verstraete should be removed. Result: user timothy available: "  + driver.findElement(By.xpath("//tr[4]/td[2]")).getText().contentEquals("Timothy.Verstraete"), driver.findElement(By.xpath("//tr[4]/td[2]")).getText().contentEquals("Timothy.Verstraete"));
    }

    public void testCreate()
    {
        //Homepage
        driver.get(baseURL + "/");

        //Click Create test in navbar
        driver.findElement(By.linkText("Create test")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fittsTestCreator")));

        //Fill in Fitts test ID
        driver.findElement(By.id("fittsTestID")).clear();
        driver.findElement(By.id("fittsTestID")).sendKeys("seleniumTest");

        //Click on Add stage (Add stage 1)
        driver.findElement(By.id("AddStage")).click();

        //Click on Add stage (Add stage 2)
        driver.findElement(By.id("AddStage")).click();

        //Select first stage
        new Select(driver.findElement(By.id("stagesSelector"))).selectByVisibleText("Stage 1");

        //Click on Remove stage (Remove stage 1)
        driver.findElement(By.id("DeleteStage")).click();

        //Click on Add stage (Add stage 2)
        driver.findElement(By.id("AddStage")).click();

        Assert.assertTrue("Stage selector box contains 2 stages. Result: " + new Select(driver.findElement(By.id("stagesSelector"))).getOptions().size(), new Select(driver.findElement(By.id("stagesSelector"))).getOptions().size() == 2);

        //Click Create button
        driver.findElement(By.id("submit")).click();

        //Wait for redirect to Test portal page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alert")));

        Assert.assertTrue("Alert should start with 'New test has been created'. Result: " + driver.findElement(By.id("testAddedAlert")).getText(), driver.findElement(By.id("testAddedAlert")).getText().startsWith("New test has been created"));
    }

    public void assignTest()
    {
        //Homepage
        driver.get(baseURL + "/");

        //Click Users in navbar
        driver.findElement(By.linkText("Users")).click();

        //Wait for User page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("usersPage")));

        //Click on Assign test of user Thomas Huybrechts
        driver.findElement(By.xpath("//tr[1]/td[4]/a/span")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("assignTestPage")));

        //Click on the left all button to move all items from right to left
        driver.findElement(By.id("search_leftAll")).click();

        //Select seleniumTest out of the left list
        Select fromSelect = new Select(driver.findElement(By.id("search")));
        fromSelect.deselectAll(); //Deselect all items
        fromSelect.selectByVisibleText("seleniumTest"); //Select the one with the displayed text of "seleniumTest"

        //Click on the right button to move the selected test from the search selection to the search_to selection
        driver.findElement(By.id("search_rightSelected")).click();

        Assert.assertTrue("The item in the To[] selection box must contain only the seleniumTest. Result: " + new Select(driver.findElement(By.id("search_to"))).getOptions().get(0).getText(), new Select(driver.findElement(By.id("search_to"))).getOptions().get(0).getText().contentEquals("seleniumTest"));

        //Click Assign button
        driver.findElement(By.id("submit")).click();

        //Wait for redirect to User page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("usersPage")));

        //Click Test portal in navbar
        driver.findElement(By.linkText("Test portal")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("testPortalPage")));

        Assert.assertTrue("The list of tests must contain only the seleniumTest. Result: " + driver.findElement(By.xpath("//div[3]/table/tbody/tr/td[1]")).getText(), driver.findElement(By.xpath("//div[3]/table/tbody/tr/td[1]")).getText().contentEquals("seleniumTest"));
    }

    public void editTest()
    {
        //Homepage
        driver.get(baseURL + "/");

        //Not available to manipulate javascript without Karma
        /*
        driver.get(baseURL + "/Users");
        driver.findElement(By.linkText("Test portal")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("detailsModal")));

        driver.findElement(By.id("editTest")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("testPortalPage")));

        driver.findElement(By.id("fittsTestID")).clear();
        driver.findElement(By.id("fittsTestID")).sendKeys("Edited Test");

        driver.findElement(By.id("numberOfDotsSlider")).clear();
        driver.findElement(By.id("numberOfDotsSlider")).sendKeys("5");

        driver.findElement(By.id("dotRadiusSlider")).clear();
        driver.findElement(By.id("dotRadiusSlider")).sendKeys("20");

        driver.findElement(By.id("dotDistanceSlider")).clear();
        driver.findElement(By.id("dotDistanceSlider")).sendKeys("50");

        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
       wait.until(ExpectedConditions.presenceOfElementLocated(By.id("detailsModal")));

        Assert.assertTrue("Title should start with 'Test portal'. Result: " + driver.getTitle(), driver.getTitle().startsWith("Test portal"));
        */
    }
}
