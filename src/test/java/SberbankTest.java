import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class SberbankTest {
    protected static WebDriver driver;
    protected static String baseUrl;
    public static Properties properties = TestProperties.getInstance().getProperties();
    private WebDriverWait wait = new WebDriverWait(driver, 3);
    private WebElement webElement;

    @BeforeClass
    public static void setUp() throws Exception {
        switch (properties.getProperty("browser")) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", properties.getProperty("webdriver.gecko.driver"));
                driver = new FirefoxDriver();
                break;
            default:
            case "chrome":
                System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
                driver = new ChromeDriver();
                break;
        }
        baseUrl = properties.getProperty("app.url");
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void SBTest() throws InterruptedException {

        //Connection to Sberbank site
        driver.get(baseUrl);

        clickButtonOfListLocations();
        scrollToBottom();
        typeRegion();
        clickCoinc();
        checkLoc();
        checkSN();


    }

    /**
     *  Clicking on button,which is used for choosing location
      */
    private void clickButtonOfListLocations(){
        webElement = driver.findElement(By.xpath("//span[@class=\"region-list__arrow\"]"));
        webElement.click();
    }
    /**
     * Scrolling to the bottom
     */
    private void scrollToBottom() {
        webElement = driver.findElement(By.xpath("//div[@class=\"sbrf-div-list-inner --area bp-area footer-white\"]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();"
                , webElement);
    }

    /**
     * Typing Nizhegorodskay Region in search field and
     */
    private void typeRegion() {
        webElement = driver.findElement(By.xpath("//input[@placeholder=\"Введите название региона\"]"));
        webElement.sendKeys("Нижегородская область");
    }

    /**
     * Clicking on coincidence of Nizhegorodskaya region
     */
    private void clickCoinc() {
        webElement = driver.findElement((By.xpath("//u")));
        webElement.click();
    }

    /**
     * Checking if this location is Nizhegorodskaya region
     */
    private void checkLoc() {
        webElement = driver.findElement(By.xpath("//span[@class=\"region-list__name\"]"));
        Assert.assertEquals("Нижегородская область", webElement.getText());
    }

    /**
     * Checking if block of social nets is displayed
     */
    private void checkSN() {

        webElement = driver.findElement((By.xpath("//div[@class=\"social__wrapper\"]/ul")));
        Assert.assertTrue(webElement.isDisplayed());
    }

    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }
}
