package pageobjectmodel;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.kafka.common.utils.Java;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.util.introspection.UberspectImpl;
import org.apache.xmlgraphics.util.dijkstra.Edge;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.Connection;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v135.network.Network;
import org.openqa.selenium.devtools.v135.network.model.ConnectionType;
import org.openqa.selenium.devtools.v135.network.model.RequestId;
import org.openqa.selenium.devtools.v135.network.model.Response;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pnc.qel.configurations.ConfigurationException;
import pnc.qel.cucumber.state.TestState;
import pnc.qel.cucumber.stepdefinitions.AbstractStepDefinitions;
import pnc.qel.excel.utilities.ExcelDriver;
import pnc.qel.exceptions.QELEncryptionException;
import pnc.qel.framework.CommonElementMethods;
import pnc.qel.framework.JavaScriptMethods;
import pnc.qel.framework.VerificationMethods;
import pnc.qel.framework.WindowFrameSelectMethods;
import pnc.qel.frameworktests.TestEnvironmentConfigurations;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import pnc.qel.exceptions.QELEncryptionException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Color;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v133.emulation.Emulation;
import static org.openqa.selenium.devtools.v135.network.Network.emulateNetworkConditions;
import static org.openqa.selenium.devtools.v135.network.Network.loadingFailed;
import static org.testng.Assert.assertEquals;
public class ProjectStepDefinitions extends AbstractStepDefinitions {
    CommonElementMethods commonMethods;
    JavaScriptMethods jsMethods;
    VerificationMethods verifyMethods;
    WindowFrameSelectMethods windowMethods;
    Scenario scenario;
    public static WebDriver driver;
    private String totalString = "";
    private String totalString1 = "";
    static String environment = "";
    static String CaseNumber = "";
    static List<Map<String, Object>> list;
    static Map<String, Object> row1;
    String Login_UserName = "";
    String Login_Password = "";
    String enterText = "";
    DevTools devTools;
    DevTools getDevTools;
    private static final String KEY = "FFNura782EsasDnKUEhJ4FhV";
    private static byte[] keyArray = Base64.getDecoder().decode(KEY);
    public ProjectStepDefinitions(TestState state) throws AWTException {
        super(state);
    }
    // ----Before methods section----
    @Before(order = 1)
    public void setup(Scenario scenario) throws IOException, ConfigurationException, InterruptedException {
        String str = scenario.getName();
        scenarioName = scenario.getName();
        String parts[] = str.split(" ");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].matches("\\d+")) {
                //system.out.println(parts[i]);
                CaseNumber = parts[i];
                try {
                    ExcelDriver rows = new ExcelDriver(System.getProperty("user.dir") + "//TestData//Logins.xlsx", "KTLO", true);
                    // Taking raw data from test data sheet
                    list = rows.getRawData();
                    for (Map<String, Object> row : list) {
                        row1 = row;
//                      get next non-empty row
                        if (!row.get("Username").toString().equalsIgnoreCase("")) {
                            // check if S.No == CaseNumber
                            if (row.get("S.No").toString().contains(CaseNumber)) {
                                // Assign this row as our data row for this test case
                                row1 = row;
                                // set environment/config values
                                if (!row.get("Envi").toString().isBlank()) {
                                    environment = row.get("Envi").toString().toUpperCase();
                                    configurations.setPropertyAndValue("environment", environment);
                                } else {
                                }
                            }
                            // add break to stop looping after we have the test data row
                            break;
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        super.setupBeforeScenario(scenario);
        this.state.setDriver(super.getDriver());
        this.verifyMethods = new VerificationMethods(this.state.getDriver());
        this.commonMethods = new CommonElementMethods(this.state.getDriver());
        this.windowMethods = new WindowFrameSelectMethods(this.state.getDriver());
        this.jsMethods = new JavaScriptMethods(this.state.getDriver());
        this.driver = this.state.getDriver();
        this.navigateToURL(TestEnvironmentConfigurations.getTestURL(getBaseURL()), (this.state.getDriver()));
        this.scenario = scenario;
    }
    public static class Decrypt {
        private Decrypt() {
            throw new IllegalStateException("Utility class");
        }
    }
    public static String decryptText(String cipherText) throws QELEncryptionException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            byte[] iv = {1, 2, 3, 4, 5, 6, 6, 5, 4, 3, 2, 1, 7, 7, 7, 7};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            keyArray = Arrays.copyOf(keyArray, 16);
            Key secretKey = new SecretKeySpec(keyArray, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
        } catch (Exception e) {
            throw new QELEncryptionException("Invalid Password to Decrypt " + e.getMessage(), e);
        }
    }
    @When("we login to application with the {string}")
    public void weLoginToApplicationWithEnvironmentAndCase(String SNo) throws Throwable {
        ExcelDriver rows = new ExcelDriver(System.getProperty("user.dir") + "//TestData//Logins.xlsx", "KTLO", true);
        // Taking raw data from test data sheet
        list = rows.getRawData();
        CaseNumber = SNo;
        try {
            By UserId = this.state.getPage().get("UserId");
            By Password = this.state.getPage().get("Password");
            By signOn = this.state.getPage().get("SignOnButton");
            try {
                for (Map<String, Object> row : list) {
                    row1 = row;
                    if (!row.get("Username").toString().equalsIgnoreCase("")) {
                        if (row.get("S.No").toString().contains(CaseNumber)) {
                            if (!row.get("Envi").toString().isBlank()) {
                                Login_UserName = (String) row.get("Username");
                                Login_Password = (String) row.get("Password");
                                String decrypt_Login_Password = decryptText(Login_Password);
                                this.commonMethods.elementIsVisible((By) this.state.getPage().get("UserId"), 5);
                                getDriver().findElement(UserId).sendKeys(Login_UserName);
                                getDriver().findElement(Password).sendKeys(decrypt_Login_Password);
                                weTakeAScreenshotFor("User Credentials entered");
//                                Thread.sleep(3000);
                                getDriver().findElement(signOn).click();
                                Thread.sleep(3000);
                                By phoneOTP = this.state.getPage().get("OTP");
                                boolean b = getDriver().findElement(phoneOTP).isDisplayed();
                                if (b) {
                                    clickIfVisible("OTP");
                                    clickIfVisible("TextMeInput");
                                    clickIfVisible("Next button");
                                    weTakeAScreenshotFor("OTP Screen");
                                    this.commonMethods.elementIsVisible((By) this.state.getPage().get("OTPInput"), 5);
                                    we_wait();
                                    getDriver().findElement((By) this.state.getPage().get("OTPInput")).sendKeys("111111");
                                    weTakeAScreenshotFor("OTP entered");
                                    clickIfVisible("OTPVerifyButton");
                                    weTakeAScreenshotFor("OTP Verify button clicked");
                                    Thread.sleep(3000);
                                } else {
                                }
                                element_is_visible("Close button");
                                this.commonMethods.elementIsVisible((By) this.state.getPage().get("Welcome User Text"), 30);
                                Thread.sleep(2000);
                                weTakeAScreenshotFor("User Logged In");
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
//        Thread.sleep(3000);
//        if(driver.findElement(By.xpath("//span[contains(text(),'Close') or contains(text(),'CLOSE')]//ancestor::button")).isDisplayed());
//
    }
    @And("we close update your browser popup")
    public void wecloseupdateyourbrowserpopup() {
        try {
            if (driver.findElement(By.xpath("//h1[contains(text(),' Update Your Browser ')]")).isDisplayed()) ;
            {
                WebElement Closebutton = driver.findElement(By.xpath("//span[contains(text(),'Close') or contains(text(),'CLOSE')]//ancestor::button"));
                Closebutton.click();
            }
        } catch (Exception e) {
            System.out.println("Popup closed");
        }

    }
    //  ---- External parameter passing from Datasheet ----
    @And("we give text from datasheet in {string}")
    public void weGiveTextFromDatasheetIn(String object) {
        try {
            By text = this.state.getPage().get(object);
            enterText = (String) row1.get(object);
            getDriver().findElement(text).sendKeys(enterText);
        } catch (Exception e) {
            System.out.println("data was not taking from datasheet");
        }
    }
    // ---- Mouse Actions ----
//    @When("^(?:|we |I )double click (.*)$")
//    public void doubleClick(String id) throws IOException {
//        this.commonMethods.clickWhenVisible(this.state.getPage().get(id), this.state.getTimeout());
//        this.commonMethods.clickWhenVisible(this.state.getPage().get(id), this.state.getTimeout());
//    }
    @When("^(?:|we |I )clear (.*)$")
    public void clear_data(String textBox) throws Throwable {
        By textBoxLocator = this.state.getPage().get(textBox);
        this.commonMethods.clearWhenVisible(textBoxLocator);
    }
    @And("^(?:|we |I )mouse hover on(.*)$")
    public void mouseHoverAt(String obj) {
        By by = this.state.getPage().get(obj);
        WebElement ele = getDriver().findElement(by);
        Actions action = new Actions(this.state.getDriver());
        action.moveToElement(ele).perform();
    }

@When("^(?:|we |I )confirm click on (.*)$")
    public void weConfirmClick(String object) throws Throwable {
        By elementObject = this.state.getPage().get(object);
        if (commonMethods.elementIsVisible(elementObject)) {
            this.commonMethods.clickWhenVisible(elementObject);
        } else {
            this.commonMethods.clickWhenVisibleAbort(elementObject, 1);
        }
    }
    @And("^(?:|we |I )select the value (.*)$")
    public void weSelectTheValueSpendToReserve(String object) throws Throwable {
        By elementObject = this.state.getPage().get(object);
        this.commonMethods.clickWhenVisible(elementObject);
    }

    // ---- Keyboard Actions----
    @Then("I press tab")
    public void iPressTab() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
    }
//    //Using Robot class
//    @And("we press backspace")
//    public void wePressBackspace() throws AWTException {
//        Robot robot = new Robot();
//        robot.keyPress(KeyEvent.VK_BACK_SPACE);
//    }
    //Using Actions class
    @And("we press backspace")
    public void wePressBackspace() throws AWTException {
        Actions act = new Actions(driver);
        act.sendKeys(Keys.BACK_SPACE).perform();
    }
    @And("we press spacebar")
    public void wePressspacebar() throws AWTException {
        Actions act = new Actions(driver);
        act.sendKeys(Keys.SPACE).perform();
    }

    @And("I press shift+tab")
    public void iPressShiftTab() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }
    @And("I press upArrow")
    public void iPressUpArrow() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_UP);
    }
    @And("I press downArrow")
    public void iPressDownArrow() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
    }
//    @And("I press enter")
//    public void iPressEnter() throws AWTException {
//        Robot robot = new Robot();
//        robot.keyPress(KeyEvent.VK_ENTER);
//        robot.keyRelease(KeyEvent.VK_DOWN);
//    }
    @And("we press space")
    public void iPressSpace() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.keyRelease(KeyEvent.VK_SPACE);
    }
    // ---- Waits ----
    @And("^we pause$")
    public void we_pause() throws InterruptedException {
        Thread.sleep(1000);
    }
    @Then("^we wait$")
    public void we_wait() throws InterruptedException {
        Thread.sleep(5000);
    }
    @BeforeEach
    void setUp() {
    }
    @Then("^we sleep$")
    public void we_sleep() throws InterruptedException {
        Thread.sleep(905000);
    }
    @Then("^we dream$")
    public void we_dream() throws InterruptedException {
        Thread.sleep(450000);
    }
    @Then("^we debug$")
    public void we_debug() throws InterruptedException {
        Thread.sleep(6000000);
    }
    // ---- Scroll ----
    @And("we scroll to {string} down")
    public void weScrollToDown(String value) {
        JavascriptExecutor jseScrollDown = (JavascriptExecutor) driver;
        jseScrollDown.executeScript("window.scrollBy(0," + value + ")", "");
    }
    @When("^(?:|we |I )scroll down")
    public void weScrollDown() throws Throwable {
        JavascriptExecutor jseScrollDown = (JavascriptExecutor) driver;
        jseScrollDown.executeScript("window.scrollBy(0,500)", "");
    }
    @When("^(?:|we |I )scroll up by (.*)")
    public void weScrollUpBy(String value) throws Throwable {
        JavascriptExecutor jseScrollUp = (JavascriptExecutor) driver;
        jseScrollUp.executeScript("window.scrollBy(0," + value + ")");
    }
    @When("^(?:|we |I )scroll down by (.*)")
    public void weScrollDownBy(String value) throws Throwable {
        JavascriptExecutor jseScrollUp = (JavascriptExecutor) driver;
        jseScrollUp.executeScript("window.scrollBy(0,-" + value + ")");
    }
    @When("^(?:|we |I )scroll up")
    public void weScrollUp() throws Throwable {
        JavascriptExecutor jseScrollUp = (JavascriptExecutor) driver;
        jseScrollUp.executeScript("window.scrollBy(0,-500)");
    }
    @When("^(?:|we |I )scroll right")
    public void weScrollRight() throws Throwable {
        JavascriptExecutor jseScrollRight = (JavascriptExecutor) driver;
        jseScrollRight.executeScript("window.scrollBy(500,0)");
    }
    @When("^(?:|we |I )scroll left")
    public void weScrollLeft() throws Throwable {
        JavascriptExecutor jseScrollLeft = (JavascriptExecutor) driver;
        jseScrollLeft.executeScript("window.scrollBy(-500,0)");
    }

    @And("^(?:|we |I )scroll to element (.*) by (.*)")
    public void weScrollToElement(String element, String value) throws Throwable {
        try {
            By elementBy = this.state.getPage().get(element);
            WebElement webElement = getDriver().findElement(elementBy);
            Actions act = new Actions(driver);
            act.scrollToElement(webElement).build().perform();
        } catch (Exception e) {
            System.out.println(("Not scrolled"));
        }
    }

    // ---- Zoom ----
//    @And("we zoomout to 75")
//    public void zoomout_to_75() throws InterruptedException, AWTException {
//        Robot robot = new Robot();
//        for (int i = 0; i < 3; i++) {
//            robot.keyPress(KeyEvent.VK_CONTROL);
//            robot.keyPress(KeyEvent.VK_SUBTRACT);
//            robot.keyRelease(KeyEvent.VK_SUBTRACT);
//            robot.keyRelease(KeyEvent.VK_CONTROL);
//            we_pause();
//        }
//    }
    @And("we zoomout to 75")
    public void zoomout_to_75() throws InterruptedException, AWTException {
        we_pause();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='75'");
//        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='75%'");
    }
    @And("we zoom to {string}")
    public void zoomout_to_requiredZoomLevel(String zoomLevel) throws InterruptedException, AWTException {
    }
    // ---- Screenshot ----
    @Then("we take a screenshot")
    public void we_take_a_screenshot() throws IOException {
        this.scenario.attach(((TakesScreenshot) this.state.getDriver()).getScreenshotAs(OutputType.BYTES), "image/png", "nameofthescreenshot");
    }
    @And("we take a screenshot for {string}")
    public void weTakeAScreenshotFor(String scrShotName) throws Throwable {
        final byte[] screenShot = ((TakesScreenshot) this.state.getDriver()).getScreenshotAs(OutputType.BYTES);
        this.scenario.attach(screenShot, "image/png", scrShotName);
    }
    // ---- Reporting ----
    @And("we take a screenshot to report with title {string}")
    public void addScreenshotOfCurrenPageToReportWithTitle(String imageTitle) throws IOException {
        Path screenshotPath = Paths.get("Cucumber_Reports/screenshot-"
                + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH-mm-ss.SSS"))
                + ".jpg");
        Screenshot screenshot = (new AShot()).shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(getDriver());
        ImageIO.write(screenshot.getImage(), "jpg", screenshotPath.toFile());
        addCustomLogToReportHtml("Screenshot of page during current step:");
        ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(screenshotPath.toAbsolutePath().toString(), imageTitle);
    }
    public void addCustomLogToReportHtml(String line) {
        ExtentCucumberAdapter.addTestStepLog(line);
    }
    // ---- frames ----
    @Given("we switch to the (.*) frame")
    public void weEnterTheValue(String object) throws IOException {
        By objectBy = this.state.getPage().get(object);
        this.windowMethods.switchToFrame(objectBy);
    }
    // ---- page navigation ----
    @And("we switch to the (.*) page")
    public void weGoToTheValue(String object) throws IOException {
        this.windowMethods.switchToMainWindow();
    }
//    @When("we navigate to (.*)")
//    public void weNavigateToTheValue(String object) throws IOException {
//        this.navigateToURL(object, this.state.getDriver());
//    }
    // ---- visibility ----
    @Then("^(?:|we |I )wait for (.*) to be invisible")
    public void waitForAnimationCompletion(String element) throws Throwable {
        By elementObject = this.state.getPage().get(element);
        WebDriverWait wait = new WebDriverWait(this.state.getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(elementObject));
    }
    @Then("the (.*) is visible")
    public void seconderror_should_show(String object) throws IOException {
        By elementObject = this.state.getPage().get(object);
        commonMethods.elementIsVisible(elementObject);
    }
    @Then("^(?:|we |I )wait for (.*) to be visible")
    public void waitForVisible(String element) throws Throwable {
        By elementObject = this.state.getPage().get(element);
        WebDriverWait wait = new WebDriverWait(this.state.getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(elementObject));
    }
    @Given("if {string} is visible")
    public void element_is_visible(String object) throws Throwable {
        By elementObject = this.state.getPage().get(object);
        if (commonMethods.elementIsVisible(elementObject)) {
            this.commonMethods.clickWhenVisible(elementObject);
        } else if (commonMethods.elementIsInvisible(elementObject)) {
            Thread.sleep(100);
        }
    }
    @And("(.*)is displayed at(.*)$")
    public void is_displayed_at(String name, String object) {
        WebElement elementObject = driver.findElement(this.state.getPage().get(object));
        String eleName = elementObject.getText();
        if (eleName.equals(name)) {
        } else {
        }
    }
    @And("{string} should be displayed")
    public void ShouldDisplayed(String obj) {
        By element = this.state.getPage().get(obj);
        getDriver().findElement(element).isDisplayed();
    }
    @And("^(.*) should not be displayed$")
    public void ShouldNotBeDisplayed(String obj) throws Exception {
        By element = this.state.getPage().get(obj);
        try {
            boolean isEleFound = getDriver().findElement(element).isDisplayed();
            Assert.assertFalse(isEleFound, "");
        } catch (Exception ignored) {
        }
    }
 
 @And("click if visible {string}")
    public void clickIfVisible(String object) throws InterruptedException {
        By by = this.state.getPage().get(object);
        WebElement webElement = getDriver().findElement(by);
        Thread.sleep(2000);
        if (webElement.isDisplayed()) {
            //system.out.println("true");
            webElement.click();
        }
    }
    // ---- Select ----
    @When("^(?:|we |I )enter1 \"([^\"]*)\" in the (.*) field$")
    public void weEnterInto1(String text, String textBox) throws IOException {
        this.commonMethods.enterValueWhenVisible((By) this.state.getPage().get(textBox), text, this.state.getTimeout());
    }
    @And("^(?:|we |I )enter \"([^\"]*)\" into the (.*) field$")
    public void weEnterInto(String text, String textBox) throws IOException {
        this.commonMethods.enterValueWhenVisible((By) this.state.getPage().get(textBox), text, this.state.getTimeout());
    }
    @When("^(?:|we |I )choose1 (.*)$")
    public void weChoose1(String id) throws IOException {
        this.commonMethods.clickWhenVisible((By) this.state.getPage().get(id), this.state.getTimeout());
    }
    @When("^(?:|we |I )check1 (.*)$")
    public void weCheckOn1(String checkBox) throws IOException {
        this.commonMethods.clickWhenVisible((By) this.state.getPage().get(checkBox), this.state.getTimeout());
    }
    @When("^(?:|we |I )uncheck1 (.*)$")
    public void weUncheckOn1(String checkBox) throws IOException {
        this.commonMethods.clickWhenVisible((By) this.state.getPage().get(checkBox), this.state.getTimeout());
    }
    @And("we select by value {string} at {string}")
    public void weSelectByValueAtBusinessStructure(String value, String object) {
        By elementObject = this.state.getPage().get(object);
        WebElement we = getDriver().findElement(elementObject);
        Select select = new Select(we);
        select.selectByValue(value);
    }
    @Then("we select by text {string} at {string}")
    public void weSelectByTextAtBusinessStructure(String value, String object) {
        By elementObject = this.state.getPage().get(object);
        WebElement we = getDriver().findElement(elementObject);
        Select select = new Select(we);
        select.selectByVisibleText(value);
    }
    @When("^(?:|we |I )sign (.*)$")
    public void weSign(String object) throws Throwable {
        WebElement elementObject = driver.findElement(this.state.getPage().get(object));
        Actions action = new Actions(this.state.getDriver());
        action.contextClick(elementObject).clickAndHold().moveToElement(elementObject, 5, 5).moveByOffset(5, -5).moveByOffset(-5, 5).release().build();
    }
//    @When("^(?:|we |I )get text (.*)$")
//    public void weGetText(String obj) {
//        By by = this.state.getPage().get(obj);
//        WebElement ele = getDriver().findElement(by);
//        String value = ele.getText();
//    }
    @And("we upload doc at {string}")
    public void weUploadDocAtLocation(String location) throws AWTException, InterruptedException {
        WebElement droparea = driver.findElement(By.xpath("//button[@id='docupload-choose-file-button-0']"));
        droparea.click();
        Robot robot = new Robot();
        Thread.sleep(3000);
        //system.out.println("Location: " + location);
        StringSelection strSel = new StringSelection(location);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(strSel, null);
        robot.delay(500);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(200);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    @Given("the current page is on {string}")
    public void currentPageIsOn(String url) throws Throwable {
        driver.get(url);
    }

    @Then("we select account {string}")
    public void weSelectAccountAccountRadio(String account) throws InterruptedException {
        String loc = "//div[contains(text(),'Account Opening Bus')]";
        List<WebElement> we = driver.findElements(By.xpath(loc));
        int size = we.size();
        //system.out.println("Total accounts: " + size);
        driver.findElement(By.xpath("(" + loc + ")[" + size + "]")).click();
        Thread.sleep(3000);
    }
    @Then("I will see {int} steps under What to Expect {string}")
    public void iWillSeeStepsUnderWhatToExpectWhatToExpect(int steps, String object) {
        By elementObject = this.state.getPage().get(object);
        List<WebElement> we = getDriver().findElements(elementObject);
        int list_Steps = we.size();
        assertEquals(steps, list_Steps);
        for (WebElement web : we) {
            String p = web.getText();
            totalString = totalString.concat(p);
        }
    }
    @And("we select the {string}")
    public void weSelectTheBusinessStructure(String object) throws InterruptedException {
        String s2 = "";
        String points = "";
        By elementObject = this.state.getPage().get(object);
        WebElement we = getDriver().findElement(elementObject);
        Select s1 = new Select(we);
        for (int i = 1; i <= 7; i++) {
            s1.selectByValue(i + ": Object");
            Thread.sleep(1000);
            WebElement w3 = s1.getFirstSelectedOption();
            s2 = w3.getText();
            List<WebElement> we1 = getDriver().findElements(By.xpath("(//h3)/following-sibling::p"));
            for (WebElement web : we1) {
                points = web.getText();
                if (!totalString.equals(totalString1)) {
                    totalString1 = totalString1.concat(points);
                }
            }
            assertEquals(totalString1, totalString);
        }
    }
    @Then("we get the list of docs in {string}")
    public void weGetTheListOfDocsInDocumentContent(String object) {
        By elementObject = this.state.getPage().get(object);
        List<WebElement> we = getDriver().findElements(elementObject);
        int list_Steps = we.size();
        for (WebElement web : we) {
            String p = web.getText();
        }
    }
    @Then("we assert the URL {string}")
    public void weAssertTheURL(String url) {
        String parentWindow = getDriver().getWindowHandle();
        Set<String> childWindow = getDriver().getWindowHandles();
        for (String allwindows : childWindow) {
            if (!parentWindow.contentEquals(allwindows)) {
                driver.switchTo().window(allwindows);
                String currentURL = getDriver().getCurrentUrl();
                System.out.println("currentURL :" + currentURL);
                assertEquals(currentURL, url);
            }
        }
    }
    @When("we press Space Bar")
    public void pressSpaceBar() {
        Actions action = new Actions(this.state.getDriver());
        action.sendKeys(Keys.SPACE).perform();
    }
    @And("we get the {string} from dropDown")
    public void weGetTheFromDropDown(String object) {
        By by = this.state.getPage().get(object);
        WebElement webElement = getDriver().findElement(by);
        Select s1 = new Select(webElement);
        WebElement s2 = s1.getFirstSelectedOption();
    }
    @And("we validate the focus is on {string}")
    public void weValidateTheFocusIsOnLearnHowToGetStartedLink(String object) throws InterruptedException {
        Thread.sleep(5000);
        By elementObject = this.state.getPage().get(object);
        String actualElement = driver.findElement(elementObject).getText();
//        System.out.println("actrgbacolor: " + actrgbacolor);
//        By by = this.state.getPage().get(object);
//        WebElement webElement = getDriver().findElement(by);
//        String s2 = webElement.getText();
        System.out.println("text: " + actualElement);
//        Assert.assertEquals(actualElement, getDriver().switchTo().activeElement());
        if (actualElement.equals(getDriver().switchTo().activeElement())) {
            System.out.println("Element is focused");
        } else {
            System.out.println("Element is not focused");
        }
    }
    @Then("^(.*) element should be focused$")
    public void ElementShouldBeFocused(String elementFromYml) throws Throwable {
        WebElement element = getElement(elementFromYml);
        WebElement focusedEle = this.state.getDriver().switchTo().activeElement();
//        System.out.println("my element ===>" + element.getText());
//        System.out.println("focused element ===>" + focusedEle.getText());
        boolean hasFocus = element.getText().equals(focusedEle.getText());
//        System.out.println("focused====>" + hasFocus);
        Assert.assertTrue(hasFocus, elementFromYml + "element should be focused");
        weTakeAScreenshotFor("element should focused");
    }

    @And("we upload doc from {string} at {string}")
    public void weUploadDocFromAt(String location, String object) throws AWTException, InterruptedException {
        By elementObject = this.state.getPage().get(object);
        WebElement we = getDriver().findElement(elementObject);
        we.click();
        Robot robot = new Robot();
        Thread.sleep(3000);
        StringSelection strSel = new StringSelection(location);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(strSel, null);
        robot.delay(500);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(200);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
    @When("we fill the following if {string} is visible:")
    public void weFillTheFollowingIfIsVisible(String object, DataTable table) throws IOException, InterruptedException {
        By elementObject = this.state.getPage().get(object);
        if (commonMethods.elementIsVisible(elementObject)) {
            List<List<String>> data = table.asLists();
            for (List<String> row : data) {
                String element = (String) row.get(0);
                String type = ((String) row.get(1)).trim();
                String function = (String) row.get(2);
                if (type.equals("text")) {
                    this.weEnterInto1(function, element);
                } else {
                    if (type.equals("select")) {
                        throw new UnsupportedOperationException("Not yet implemented");
                    }
                    if (type.equals("radio")) {
                        this.weChoose1(element);
                    } else {
                        if (!type.equals("check")) {
                            throw new IllegalArgumentException("Type parameter not supported: " + type);
                        }
                        if (function.equals("check")) {
                            this.weCheckOn1(element);
                        } else {
                            this.weUncheckOn1(element);
                        }
                    }
                }
            }
        } else if (commonMethods.elementIsInvisible(elementObject)) {
            Thread.sleep(100);
        }
    }
    @And("we adjust height and width of page to {int} and {int}")
    public void weAdjustHeightAndWidthOfPageToHeightAndWidth(int height, int width) throws Throwable {
        Dimension d = new Dimension(width, height);
        driver.manage().window().setSize(new Dimension(width, height));
    }
    @And("we go to {string} account")
    public void weGoTo(String account) throws Throwable {
        we_pause();
        weLoginToApplicationWithEnvironmentAndCase(CaseNumber);
        weSelectSpendAccount(account);
    }
    @And("^we validate (.*) dropdown values$")
    public void weshouldseedropdownvalues(String object) {
        //    By elementObject = this.state.getPage().get(object);
        List<WebElement> dropdownOptions = driver.findElements(By.xpath("//li[contains(@class, 'ng-star-inserted')]//child::a[contains(text(), '20')]"));
        int expectedOptions = 24;
        int actualOptions = dropdownOptions.size();
        System.out.println(actualOptions);
        assertEquals(actualOptions, expectedOptions);
    }
    @And("^we double click (.*)$")
    public void weDoubleClickandClearTheInput(String object) throws InterruptedException {
        By elementObject = this.state.getPage().get(object);
        WebElement element = driver.findElement(elementObject);
        try {
            Point location = element.getLocation();
            Actions act = new Actions(driver);
            act.doubleClick(element).dragAndDropBy(element, location.x, location.y).build().perform();
            Thread.sleep(2000);
            element.clear();
        } catch (Exception e) {
            System.out.println("Input not cleared");
        }
    }
 
@And("we select {string} account")
    public void weSelectSpendAccount(String account) throws Throwable {
        we_pause();
        zoomout_to_75();
        switch (account) {
            case "Spend" -> {
                By spendButton = this.state.getPage().get("Spend");
                this.commonMethods.elementIsVisible(spendButton, this.state.getTimeout());
                this.commonMethods.clickWhenVisible(spendButton, this.state.getTimeout());
                Thread.sleep(2000);
                weTakeAScreenshotFor("Spend Button Clicked");
            }
        }
    }
    @And("we launch application and click (.*)$")
    public void weLaunchApplication(String object) {
        try {
            By UserId = this.state.getPage().get("UserId");
            By Password = this.state.getPage().get("Password");
            By signOn = this.state.getPage().get("SignOnButton");
            Thread.sleep(2000);
            weTakeAScreenshotFor("Login Page");
            weScrollToDown("500");
            Thread.sleep(2000);
            By elementObject = this.state.getPage().get(object);
            WebElement element = driver.findElement(elementObject);
            element.click();
        } catch (Exception e) {
            System.out.println("Application not launched");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    @When("we launch application$")
    public void weLaunchApplication() {
        try {
            By UserId = this.state.getPage().get("UserId");
            By Password = this.state.getPage().get("Password");
            By signOn = this.state.getPage().get("SignOnButton");
            Thread.sleep(2000);
            weTakeAScreenshotFor("Login Page");
//            weScrollToDown("500");
//            Thread.sleep(2000);
//            By elementObject = this.state.getPage().get(object);
//            WebElement element = driver.findElement(elementObject);
//            element.click();
        } catch (Exception e) {
            System.out.println("Application not launched");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    @And("we navigate to (.*) and select (.*) and click on (.*)$")
    public void weNavigatetoProfileandSelectoptionandclickontab(String Profile, String Options, String Tab) throws Throwable {
        try {
            By elementObject = this.state.getPage().get(Profile);
            WebElement element = driver.findElement(elementObject);
            Thread.sleep(2000);
            this.commonMethods.clickWhenVisible(element);
//            element.click();
            weTakeAScreenshotFor("Profile Button Clicked");
            Thread.sleep(2000);
            By elementOption = this.state.getPage().get(Options);
            WebElement option = driver.findElement(elementOption);
            Thread.sleep(2000);
            this.commonMethods.clickWhenVisible(option);
            Thread.sleep(2000);
//            option.click();
            weTakeAScreenshotFor("Option Clicked");
            By elementtab = this.state.getPage().get(Tab);
            WebElement tab = driver.findElement(elementtab);
            Thread.sleep(2000);
            this.commonMethods.clickWhenVisible(elementtab);
            Thread.sleep(2000);
//            tab.click();
            weTakeAScreenshotFor("Tab Clicked");
        } catch (Exception e) {
            System.out.println("Not Navigated");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    @And("we go to (.*) while clicking (.*) from (.*)$")
    public void weGoToPreferenceTab(String Tab, String Options, String Profile) throws Throwable {
        we_pause();
        weLoginToApplicationWithEnvironmentAndCase(CaseNumber);
        Thread.sleep(2000);
        weNavigatetoProfileandSelectoptionandclickontab(Profile, Options, Tab);
//       weTakeAScreenshotFor("Profile Button Clicked");
    }
    @And("we give text from datasheet in {string} the value {string}")
    public void
    weGiveTextFromDatasheetIn(String object, String value) {
        By text = this.state.getPage().get(object);
        enterText = (String) row1.get(value);
        System.out.println(enterText);
        getDriver().findElement(text).sendKeys(enterText);
        String inviteCode = "Your delegate’s Invitation Code is LRLGVDYS.";
        char[] cde = inviteCode.toCharArray();
    }
    @And("we validate (.*) green background color$")
    public void weValidateGreenBackgroundColor(String object) throws InterruptedException {
        String exprgbcolor = "#008300";
        System.out.println(exprgbcolor);
        By elementObject = this.state.getPage().get(object);
        String actrgbcolor = driver.findElement(elementObject).getCssValue("background-color");
        System.out.println(actrgbcolor);
        if (actrgbcolor == exprgbcolor) {
            System.out.println("Background Color: " + actrgbcolor);
        } else {
//            System.out.println("Background Color not macthed");
        }
        Thread.sleep(2000);
    }
    @And("we validate (.*) red background color$")
    public void weValidateRedBackgroundColor(String object) throws InterruptedException {
        String exprgbcolor = "#B75308";
        System.out.println(exprgbcolor);
        By elementObject = this.state.getPage().get(object);
        String actrgbcolor = driver.findElement(elementObject).getCssValue("background-color");
        System.out.println(actrgbcolor);
        if (actrgbcolor == exprgbcolor) {
            System.out.println("Background Color" + actrgbcolor);
        } else {
            System.out.println("Background Color not macthed");
        }
        Thread.sleep(2000);
    }
   /* @io.cucumber.java.en.Then("we wait {int} seconds")
    public void we_wait_seconds(java.lang.Integer int1) throws java.lang.InterruptedException { *//* compiled code *//* }
     */

    @And("we navigate to (.*) under (.*)$")
    public void wenavigatetoAlertsAndCommunicationunderProfile(String Profile, String AlertsAndCommunication) throws Throwable {
//        we_wait();
        weLoginToApplicationWithEnvironmentAndCase(CaseNumber);
        Thread.sleep(2000);
        try {
            By elementObject = this.state.getPage().get(Profile);
            WebElement element = driver.findElement(elementObject);
            this.commonMethods.clickWhenVisible(element);
            Thread.sleep(2000);
//            element.click();
            weTakeAScreenshotFor("Button Clicked");
//            Thread.sleep(1000);
            By elementOption = this.state.getPage().get(AlertsAndCommunication);
            WebElement option = driver.findElement(elementOption);
            this.commonMethods.clickWhenVisible(option);
            Thread.sleep(2000);
//            Thread.sleep(1000);
            weTakeAScreenshotFor("Button Clicked");
        } catch (Exception e) {
            System.out.println("Not Navigated");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    @And("we navigate to {string} while clicking {string} with the {string}")
    public void weGoToWireansInternationalTransfers(String AlertsAndCommunication, String Profile, String SNO) throws Throwable {
//        we_pause();
        we_wait();
        weLoginToApplicationWithEnvironmentAndCase(SNO);
        Thread.sleep(1000);
        wecloseupdateyourbrowserpopup();
        wenavigatetoAlertsAndCommunicationunderProfile(Profile, AlertsAndCommunication);
    }
    @And("we navigate back$")
    public void wenavigateback() throws InterruptedException {
        driver.navigate().back();
        Thread.sleep(5000);
    }
    private WebElement getElement(String elementLocatorNameFromYAML) {
        return this.state.getDriver().findElement(this.state.getPage().get(elementLocatorNameFromYAML));
    }

    @Then("^(.*) static element should not be focused$")
    public void staticElementShouldNotBeFocused(String elementFromYml) throws Throwable {
        WebElement element = getElement(elementFromYml);
        WebElement focusedEle = this.state.getDriver().switchTo().activeElement();
//        System.out.println("my element ===>" + element.getText());
//        System.out.println("focused element ===>" + focusedEle.getText());
        boolean hasFocus = element.getText().equals(focusedEle.getText());
//        System.out.println("focused====>" + hasFocus);
        Assert.assertFalse(hasFocus, elementFromYml + "static element should not focused");
        weTakeAScreenshotFor("Static element should not focused");
    }
    @And("^we validate the bold for (.*)")
    public void weValidateTheCursor(String object) {
        By elementTOValidate = this.state.getPage().get(object);
        String expectedfontweight = "600";
        String actualfontweight = driver.findElement(elementTOValidate).getCssValue("font-weight");
        System.out.println(actualfontweight);
        assertEquals(actualfontweight, expectedfontweight);
    }
    @And("^we validate the font style for (.*)")
    public void weValidateTheFontStyle(String object) {
        By elementTOValidate = this.state.getPage().get(object);
        String Expected_font_style = "italic";
        String actual_font_style = driver.findElement(elementTOValidate).getCssValue("font-style");
        System.out.println(actual_font_style);
        assertEquals(actual_font_style, Expected_font_style);
    }
    @And("we get text from (.*) and compare with (.*)$")
    public void wegettextandvalidatetext(String object, String value) {
        WebElement elementObject = driver.findElement(this.state.getPage().get(object));
        String ActualText = elementObject.getText();
        System.out.println(ActualText);
        assertEquals(ActualText, value);
    }
    @And("^we switch to new window when we click on (.*)$")
    public void weswitchtonewwindow(String object) throws InterruptedException, IOException {
        WebElement obj = getElement(object);
        obj.click();
        Thread.sleep(3000);
        String parent = getDriver().getWindowHandle();
        for (String handle : getDriver().getWindowHandles()) {
            if (!handle.equals(parent)) {
                getDriver().switchTo().window(handle);
                break;
            }
        }
    }
    @And("^we navigate to new window when we click on (.*) and validate url (.*)$")
    public void weswitchtonnewwindowandvalidateurl(String object, String url) throws InterruptedException, IOException {
        WebElement obj = getElement(object);
        obj.click();
        Thread.sleep(3000);
        String parent = getDriver().getWindowHandle();
        for (String handle : getDriver().getWindowHandles()) {
            if (!handle.equals(parent)) {
                getDriver().switchTo().window(handle);
                String actualurl = getDriver().getCurrentUrl();
                System.out.println(actualurl);
                assertEquals(actualurl, url);
                break;
            }
        }
    }
    @And("^we validate browser tab title (.*)$")
    public void wevalidatebrowsertabtitle(String Title) {
        String ActualTitle = getDriver().getTitle();
        System.out.println(ActualTitle);
        assertEquals(ActualTitle, Title);
    }
    @And("^we press down arrow$")
    public void we_click_downarrow() {
        Actions action = new Actions(this.state.getDriver());
        action.sendKeys(Keys.ARROW_DOWN).perform();
    }
    @And("^we press up arrow$")
    public void we_click_uparrow() {
        Actions action = new Actions(this.state.getDriver());
        action.sendKeys(Keys.ARROW_UP).perform();
    }
    @When("we validate the text of the element {string} with the {string}")
    public void validatingtheactualvalue(String elementID, String expectedvalue) {
        WebElement obj = getElement(elementID);
        String elementText = obj.getText();
        System.out.println(elementText);
        if (elementText.equals(expectedvalue)) {
            System.out.println("text matched");
        } else {
            System.out.println("Text does Not matched");
        }
    }
    @And("^we enter the (.*) by using (.*)$")
    public void weEnterTheByUsing(String name, String elementID) {
        WebElement obj = getElement(elementID);
        obj.sendKeys(name);
    }
 
 @And("we select {string} account")
    public void weSelectSpendAccount(String account) throws Throwable {
        we_pause();
        zoomout_to_75();
        switch (account) {
            case "Spend" -> {
                By spendButton = this.state.getPage().get("Spend");
                this.commonMethods.elementIsVisible(spendButton, this.state.getTimeout());
                this.commonMethods.clickWhenVisible(spendButton, this.state.getTimeout());
                Thread.sleep(2000);
                weTakeAScreenshotFor("Spend Button Clicked");
            }
        }
    }
    @And("we launch application and click (.*)$")
    public void weLaunchApplication(String object) {
        try {
            By UserId = this.state.getPage().get("UserId");
            By Password = this.state.getPage().get("Password");
            By signOn = this.state.getPage().get("SignOnButton");
            Thread.sleep(2000);
            weTakeAScreenshotFor("Login Page");
            weScrollToDown("500");
            Thread.sleep(2000);
            By elementObject = this.state.getPage().get(object);
            WebElement element = driver.findElement(elementObject);
            element.click();
        } catch (Exception e) {
            System.out.println("Application not launched");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    @When("we launch application$")
    public void weLaunchApplication() {
        try {
            By UserId = this.state.getPage().get("UserId");
            By Password = this.state.getPage().get("Password");
            By signOn = this.state.getPage().get("SignOnButton");
            Thread.sleep(2000);
            weTakeAScreenshotFor("Login Page");
//            weScrollToDown("500");
//            Thread.sleep(2000);
//            By elementObject = this.state.getPage().get(object);
//            WebElement element = driver.findElement(elementObject);
//            element.click();
        } catch (Exception e) {
            System.out.println("Application not launched");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    @And("we navigate to (.*) and select (.*) and click on (.*)$")
    public void weNavigatetoProfileandSelectoptionandclickontab(String Profile, String Options, String Tab) throws Throwable {
        try {
            By elementObject = this.state.getPage().get(Profile);
            WebElement element = driver.findElement(elementObject);
            Thread.sleep(2000);
            this.commonMethods.clickWhenVisible(element);
//            element.click();
            weTakeAScreenshotFor("Profile Button Clicked");
            Thread.sleep(2000);
            By elementOption = this.state.getPage().get(Options);
            WebElement option = driver.findElement(elementOption);
            Thread.sleep(2000);
            this.commonMethods.clickWhenVisible(option);
            Thread.sleep(2000);
//            option.click();
            weTakeAScreenshotFor("Option Clicked");
            By elementtab = this.state.getPage().get(Tab);
            WebElement tab = driver.findElement(elementtab);
            Thread.sleep(2000);
            this.commonMethods.clickWhenVisible(elementtab);
            Thread.sleep(2000);
//            tab.click();
            weTakeAScreenshotFor("Tab Clicked");
        } catch (Exception e) {
            System.out.println("Not Navigated");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    @And("we go to (.*) while clicking (.*) from (.*)$")
    public void weGoToPreferenceTab(String Tab, String Options, String Profile) throws Throwable {
        we_pause();
        weLoginToApplicationWithEnvironmentAndCase(CaseNumber);
        Thread.sleep(2000);
        weNavigatetoProfileandSelectoptionandclickontab(Profile, Options, Tab);
//       weTakeAScreenshotFor("Profile Button Clicked");
    }
    @And("we give text from datasheet in {string} the value {string}")
    public void
    weGiveTextFromDatasheetIn(String object, String value) {
        By text = this.state.getPage().get(object);
        enterText = (String) row1.get(value);
        System.out.println(enterText);
        getDriver().findElement(text).sendKeys(enterText);
        String inviteCode = "Your delegate’s Invitation Code is LRLGVDYS.";
        char[] cde = inviteCode.toCharArray();
    }
    @And("we validate (.*) green background color$")
    public void weValidateGreenBackgroundColor(String object) throws InterruptedException {
        String exprgbcolor = "#008300";
        System.out.println(exprgbcolor);
        By elementObject = this.state.getPage().get(object);
        String actrgbcolor = driver.findElement(elementObject).getCssValue("background-color");
        System.out.println(actrgbcolor);
        if (actrgbcolor == exprgbcolor) {
            System.out.println("Background Color: " + actrgbcolor);
        } else {
//            System.out.println("Background Color not macthed");
        }
        Thread.sleep(2000);
    }
    @And("we validate (.*) red background color$")
    public void weValidateRedBackgroundColor(String object) throws InterruptedException {
        String exprgbcolor = "#B75308";
        System.out.println(exprgbcolor);
        By elementObject = this.state.getPage().get(object);
        String actrgbcolor = driver.findElement(elementObject).getCssValue("background-color");
        System.out.println(actrgbcolor);
        if (actrgbcolor == exprgbcolor) {
            System.out.println("Background Color" + actrgbcolor);
        } else {
            System.out.println("Background Color not macthed");
        }
        Thread.sleep(2000);
    }
   /* @io.cucumber.java.en.Then("we wait {int} seconds")
    public void we_wait_seconds(java.lang.Integer int1) throws java.lang.InterruptedException { *//* compiled code *//* }
     */

    @And("we navigate to (.*) under (.*)$")
    public void wenavigatetoAlertsAndCommunicationunderProfile(String Profile, String AlertsAndCommunication) throws Throwable {
//        we_wait();
        weLoginToApplicationWithEnvironmentAndCase(CaseNumber);
        Thread.sleep(2000);
        try {
            By elementObject = this.state.getPage().get(Profile);
            WebElement element = driver.findElement(elementObject);
            this.commonMethods.clickWhenVisible(element);
            Thread.sleep(2000);
//            element.click();
            weTakeAScreenshotFor("Button Clicked");
//            Thread.sleep(1000);
            By elementOption = this.state.getPage().get(AlertsAndCommunication);
            WebElement option = driver.findElement(elementOption);
            this.commonMethods.clickWhenVisible(option);
            Thread.sleep(2000);
//            Thread.sleep(1000);
            weTakeAScreenshotFor("Button Clicked");
        } catch (Exception e) {
            System.out.println("Not Navigated");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    @And("we navigate to {string} while clicking {string} with the {string}")
    public void weGoToWireansInternationalTransfers(String AlertsAndCommunication, String Profile, String SNO) throws Throwable {
//        we_pause();
        we_wait();
        weLoginToApplicationWithEnvironmentAndCase(SNO);
        Thread.sleep(1000);
        wecloseupdateyourbrowserpopup();
        wenavigatetoAlertsAndCommunicationunderProfile(Profile, AlertsAndCommunication);
    }
    @And("we navigate back$")
    public void wenavigateback() throws InterruptedException {
        driver.navigate().back();
        Thread.sleep(5000);
    }
    private WebElement getElement(String elementLocatorNameFromYAML) {
        return this.state.getDriver().findElement(this.state.getPage().get(elementLocatorNameFromYAML));
    }

    @Then("^(.*) static element should not be focused$")
    public void staticElementShouldNotBeFocused(String elementFromYml) throws Throwable {
        WebElement element = getElement(elementFromYml);
        WebElement focusedEle = this.state.getDriver().switchTo().activeElement();
//        System.out.println("my element ===>" + element.getText());
//        System.out.println("focused element ===>" + focusedEle.getText());
        boolean hasFocus = element.getText().equals(focusedEle.getText());
//        System.out.println("focused====>" + hasFocus);
        Assert.assertFalse(hasFocus, elementFromYml + "static element should not focused");
        weTakeAScreenshotFor("Static element should not focused");
    }
    @And("^we validate the bold for (.*)")
    public void weValidateTheCursor(String object) {
        By elementTOValidate = this.state.getPage().get(object);
        String expectedfontweight = "600";
        String actualfontweight = driver.findElement(elementTOValidate).getCssValue("font-weight");
        System.out.println(actualfontweight);
        assertEquals(actualfontweight, expectedfontweight);
    }
    @And("^we validate the font style for (.*)")
    public void weValidateTheFontStyle(String object) {
        By elementTOValidate = this.state.getPage().get(object);
        String Expected_font_style = "italic";
        String actual_font_style = driver.findElement(elementTOValidate).getCssValue("font-style");
        System.out.println(actual_font_style);
        assertEquals(actual_font_style, Expected_font_style);
    }
    @And("we get text from (.*) and compare with (.*)$")
    public void wegettextandvalidatetext(String object, String value) {
        WebElement elementObject = driver.findElement(this.state.getPage().get(object));
        String ActualText = elementObject.getText();
        System.out.println(ActualText);
        assertEquals(ActualText, value);
    }
    @And("^we switch to new window when we click on (.*)$")
    public void weswitchtonewwindow(String object) throws InterruptedException, IOException {
        WebElement obj = getElement(object);
        obj.click();
        Thread.sleep(3000);
        String parent = getDriver().getWindowHandle();
        for (String handle : getDriver().getWindowHandles()) {
            if (!handle.equals(parent)) {
                getDriver().switchTo().window(handle);
                break;
            }
        }
    }
    @And("^we navigate to new window when we click on (.*) and validate url (.*)$")
    public void weswitchtonnewwindowandvalidateurl(String object, String url) throws InterruptedException, IOException {
        WebElement obj = getElement(object);
        obj.click();
        Thread.sleep(3000);
        String parent = getDriver().getWindowHandle();
        for (String handle : getDriver().getWindowHandles()) {
            if (!handle.equals(parent)) {
                getDriver().switchTo().window(handle);
                String actualurl = getDriver().getCurrentUrl();
                System.out.println(actualurl);
                assertEquals(actualurl, url);
                break;
            }
        }
    }
    @And("^we validate browser tab title (.*)$")
    public void wevalidatebrowsertabtitle(String Title) {
        String ActualTitle = getDriver().getTitle();
        System.out.println(ActualTitle);
        assertEquals(ActualTitle, Title);
    }
    @And("^we press down arrow$")
    public void we_click_downarrow() {
        Actions action = new Actions(this.state.getDriver());
        action.sendKeys(Keys.ARROW_DOWN).perform();
    }
    @And("^we press up arrow$")
    public void we_click_uparrow() {
        Actions action = new Actions(this.state.getDriver());
        action.sendKeys(Keys.ARROW_UP).perform();
    }
    @When("we validate the text of the element {string} with the {string}")
    public void validatingtheactualvalue(String elementID, String expectedvalue) {
        WebElement obj = getElement(elementID);
        String elementText = obj.getText();
        System.out.println(elementText);
        if (elementText.equals(expectedvalue)) {
            System.out.println("text matched");
        } else {
            System.out.println("Text does Not matched");
        }
    }
    @And("^we enter the (.*) by using (.*)$")
    public void weEnterTheByUsing(String name, String elementID) {
        WebElement obj = getElement(elementID);
        obj.sendKeys(name);
    }
 
 @And("we verify Debit card Edit delivery and alerts option clicked and cancel the model")
    public void weverifydebitcardEditdeliveryandalertsoptionclickable() throws IOException {
        try {
            weScrollToDown("1500");
//        clicking debit card
            we_wait();
            WebElement Debitcard = driver.findElement(By.xpath("//h3[contains(text(),'Debit Card')]//following::button"));
            Debitcard.click();
            weTakeAScreenshotFor("Debit card opened");
            we_wait();
            this.commonMethods.clickWhenVisible(By.xpath("//*[@aria-label='Edit delivery and alert options for  Account service changes']"));
            we_pause();
            weTakeAScreenshotFor("Account service changes model opened");
            this.commonMethods.clickWhenVisible(By.xpath("//span[contains(text(),'Cancel') or contains(text(),'CANCEL')]//ancestor::button"));
            we_pause();
            weTakeAScreenshotFor("Account service changes model closed");
            this.commonMethods.clickWhenVisible(By.xpath("//*[@aria-label='Edit delivery and alert options for  Visa Transactions']"));
            we_pause();
            weTakeAScreenshotFor("Visa Transactions model opened");
            this.commonMethods.clickWhenVisible(By.xpath("//span[contains(text(),'Cancel') or contains(text(),'CANCEL')]//ancestor::button"));
            we_pause();
            weTakeAScreenshotFor("Visa Transactions model closed");
//          closing debit card
            Debitcard.click();
            weTakeAScreenshotFor("Debit card closed");
        } catch (Exception e) {
            System.out.println("Edit delivery and alerts option models not opened");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    @And("we verify Savings Account edit delivery and alerts option clicked and cancel the model")
    public void wevalidatesavingseditandalertsoptionclickable() throws Throwable {
        try {
//        Clicking spend account
            WebElement Savingsacc = driver.findElement(By.xpath("//h3[contains(text(),'Savings')]//following::button"));
            Savingsacc.click();
            weTakeAScreenshotFor("Savings account Edit delivery and alerts option models opened");
            we_wait();
            this.commonMethods.clickWhenVisible(By.xpath("//*[@aria-label='Edit delivery and alert options for  Account service changes']"));
            we_pause();
            weTakeAScreenshotFor("Account service changes model opened");
            this.commonMethods.clickWhenVisible(By.xpath("//span[contains(text(),'Cancel') or contains(text(),'CANCEL')]//ancestor::button"));
            we_pause();
            weTakeAScreenshotFor("Account service changes model closed");
            this.commonMethods.clickWhenVisible(By.xpath("//*[@aria-label='Edit delivery and alert options for  Balance information']"));
            we_pause();
            weTakeAScreenshotFor("Balance information model opened");
            this.commonMethods.clickWhenVisible(By.xpath("//span[contains(text(),'Cancel') or contains(text(),'CANCEL')]//ancestor::button"));
            we_pause();
            weTakeAScreenshotFor("Balance information model closed");
            this.commonMethods.clickWhenVisible(By.xpath("//*[@aria-label='Edit delivery and alert options for  Deposits']"));
            we_pause();
            weTakeAScreenshotFor("Deposits model opened");
            this.commonMethods.clickWhenVisible(By.xpath("//span[contains(text(),'Cancel') or contains(text(),'CANCEL')]//ancestor::button"));
            we_pause();
            weTakeAScreenshotFor("Deposits model closed");
            this.commonMethods.clickWhenVisible(By.xpath("//*[@aria-label='Edit delivery and alert options for  Overdrafts']"));
            we_pause();
            weTakeAScreenshotFor("Overdrafts model opened");
            this.commonMethods.clickWhenVisible(By.xpath("//span[contains(text(),'Cancel') or contains(text(),'CANCEL')]//ancestor::button"));
            we_pause();
            weTakeAScreenshotFor("Overdrafts model closed");
            this.commonMethods.clickWhenVisible(By.xpath("//*[@aria-label='Edit delivery and alert options for  Payments']"));
            we_pause();
            weTakeAScreenshotFor("Payments model opened");
            this.commonMethods.clickWhenVisible(By.xpath("//span[contains(text(),'Cancel') or contains(text(),'CANCEL')]//ancestor::button"));
            we_pause();
            weTakeAScreenshotFor("Payments model closed");
            this.commonMethods.clickWhenVisible(By.xpath("//*[@aria-label='Edit delivery and alert options for  Transactions']"));
            we_pause();
            weTakeAScreenshotFor("Transactions model opened");
            this.commonMethods.clickWhenVisible(By.xpath("//span[contains(text(),'Cancel') or contains(text(),'CANCEL')]//ancestor::button"));
            we_pause();
            weTakeAScreenshotFor("Transactions model closed");
//        closing Savingsaccount
            Savingsacc.click();
            weTakeAScreenshotFor("Savings account closed");
        } catch (Exception e) {
            System.out.println("Savings Edit delivery and alerts option models not opened");
        }
    }
    @And("we verify Credit card edit delivery and alerts option clicked and cancel the model")
    public void wevalidatecreditcardeditandalertsoptionclickable() throws Throwable {
        try {
//        Clicking spend account
            WebElement Creditcard = driver.findElement(By.xpath("//h3[contains(text(),'Credit Card')]//following::button"));
            Creditcard.click();
            weTakeAScreenshotFor("credit card Edit delivery and alerts option models opened");
            we_wait();
            this.commonMethods.clickWhenVisible(By.xpath("//*[@aria-label='Edit delivery and alert options for  Balance information']"));
            we_pause();
            weTakeAScreenshotFor("Balance information model opened");
            this.commonMethods.clickWhenVisible(By.xpath("//span[contains(text(),'Cancel') or contains(text(),'CANCEL')]//ancestor::button"));
            we_pause();
            weTakeAScreenshotFor("Balance information model closed");
            this.commonMethods.clickWhenVisible(By.xpath("//*[@aria-label='Edit delivery and alert options for  Payments']"));
            we_pause();
            weTakeAScreenshotFor("Payments model opened");
            this.commonMethods.clickWhenVisible(By.xpath("//span[contains(text(),'Cancel') or contains(text(),'CANCEL')]//ancestor::button"));
            we_pause();
            weTakeAScreenshotFor("Payments model closed");
            this.commonMethods.clickWhenVisible(By.xpath("//*[@aria-label='Edit delivery and alert options for  Transactions']"));
            we_pause();
            weTakeAScreenshotFor("Transactions opened");
            this.commonMethods.clickWhenVisible(By.xpath("//span[contains(text(),'Cancel') or contains(text(),'CANCEL')]//ancestor::button"));
            we_pause();
            weTakeAScreenshotFor("Transactions model closed");
            this.commonMethods.clickWhenVisible(By.xpath("//*[@aria-label='Edit delivery and alert options for  Visa Transactions']"));
            we_pause();
            weTakeAScreenshotFor("Visa Transactions model opened");
            this.commonMethods.clickWhenVisible(By.xpath("//span[contains(text(),'Cancel') or contains(text(),'CANCEL')]//ancestor::button"));
            we_pause();
            weTakeAScreenshotFor("Visa Transactions model closed");
//        closing Creditcard
            Creditcard.click();
            weTakeAScreenshotFor("credit card closed");
        } catch (Exception e) {
            System.out.println("Credit card Edit delivery and alerts option models not opened");
        }
    }

    @And("we deselct checkboxes should see select a phone number error message")
    public void selectaphonenumbererrormessage() throws InterruptedException, IOException {
        List Checkboxes = driver.findElements(By.xpath("//div[contains(@class, 'wbb-checkbox-group-container')]//child::input[contains(@id, 'wbb-checkbox')]"));
        WebElement Cancelbutton = driver.findElement(By.xpath("//span[contains(text(),'CANCEL') or contains(text(),'Cancel')]//ancestor::button"));
        int box = Checkboxes.size();
        System.out.println(box);
        //try {
        for (int i = 1; i <= box; i++) {
            WebElement element = driver.findElement(By.xpath("(//span[contains(text(),'•••-•••-')]//ancestor::label)[" + i + "]"));
            Thread.sleep(2000);
            this.commonMethods.clickWhenVisible(element);
//                box(i).click();
            if (driver.findElement(By.xpath("//span[contains(text(),'Select a phone number.')]")).isDisplayed()) {
                we_take_a_screenshot();
                Cancelbutton.click();
            }
            we_take_a_screenshot();
        }
//        } catch (Exception e) {
//            System.out.println("Checkboxes not clicked");
//        }
    }
    @And("we validate Checkbox is checked")
    public void Checkboxischecked() throws Throwable {
        var checkbox = driver.findElement(By.xpath("//label[contains(@class,'mat-checkbox-layout')]//following::span[contains(text(),'@pnc.com')]"));
        if (checkbox.isDisplayed()) {
            weTakeAScreenshotFor("Primary mail check box alreay checked");
        }
    }
//    DevTools devTools = WBADevTools.attachDevToolsToDriver(driver);
//
//    @And("user click on (.*) and get API response (.*)$")
//    public void userClickOnAndGetAPIResponse(String button, int statusCode) throws IOException, InterruptedException {
//        devTools.createSession();
//        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
//
//        devTools.addListener(Network.requestWillBeSent(), request ->
//        {
//            Request req = request.getRequest();
//            System.out.println("Request URL: " + req.getUrl());
//            System.out.println("Request Headers: " + req.getHeaders());
//            System.out.println("Request Method: " + req.getMethod());
//
//        });
//        // Listen for network responses
//        devTools.addListener(Network.responseReceived(), response -> {
//            System.out.println("---listener triggered---");
//            Response res = response.getResponse();
//            if(statusCode==res.getStatus()) {
//                System.out.println("Response Status: " + res.getStatus());
//            }
//
//        });
//        WebElement Button = driver.findElement(By.xpath("//span[contains(text(),'Save') or contains(text(),'SAVE')]//ancestor::button"));
//        Button.click();
//        Thread.sleep(2000);
//    }
//
    @And("we Press Enter")
    public void wePressEnter() {
        Actions act = new Actions(driver);
        act.keyDown(Keys.ENTER).build().perform();
    }
    //==================================UI/UX=====================================//
    @And("^we validate the (.*) - (.*) for the (.*)")
    public void weValidatetheattributevalues(String attribute, String Expected_attribute_value, String object) {
        By elementTOValidate = this.state.getPage().get(object);
        String actual_attribute_value = driver.findElement(elementTOValidate).getCssValue(attribute);
        System.out.println(actual_attribute_value);
        assertEquals(actual_attribute_value, Expected_attribute_value);
    }
    //    Validating RGB/RGBA/Back-ground color validation
    @And("we validate rgb (.*) - (.*) for the (.*)$")
    public void wevalidatergbcolorconverter(String attribute, String exprgbcolor, String object) {
        By elementObject = this.state.getPage().get(object);
        String actrgbacolor = driver.findElement(elementObject).getCssValue(attribute);
        System.out.println("actrgbacolor: " + actrgbacolor);
//        Convert RGB/RGBA to HEX code
        String hexColor = converterRGBAToHex((actrgbacolor));
        System.out.println("Hex Color: " + hexColor);
//        Validating actual rgb/rgba to Hex code
        assertEquals(hexColor, exprgbcolor);
    }
    //    Convert RGB/RGBA to HEX code
    public static String converterRGBAToHex(String exprgbcolor) {
//        Extract the numbers
        exprgbcolor = exprgbcolor.replace("rgba(", "").replace("rgb(", "").replace(")", "");
        String[] rgbValues = exprgbcolor.split(",");
        int r = Integer.parseInt(rgbValues[0].trim());
        int g = Integer.parseInt(rgbValues[1].trim());
        int b = Integer.parseInt(rgbValues[2].trim());
//        convert to Hex
        return String.format("#%02x%02x%02x", r, g, b);
    }
    @Then("^we click ENTER on (.*) once found$")
    public void weClickENTERonOnceFound(String obj) throws Throwable {
        Actions action = new Actions(this.state.getDriver());
        for (int i = 0; i <= 250; i++) {
            WebElement targetElement = getElement(obj);
            String targetElementText = targetElement.getText();
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(1000);
            WebElement activeElement = this.state.getDriver().switchTo().activeElement();
            String currentElementText = activeElement.getText();
            if (targetElementText.equals(currentElementText)) {
                action.sendKeys(Keys.ENTER).perform();
                break;
            }
        }
    }
    @Then("^we press space bar on (.*) once found$")
    public void wePressSpaceBaronOnceFound(String obj) throws Throwable {
        Actions action = new Actions(this.state.getDriver());
        for (int i = 0; i <= 250; i++) {
            WebElement targetElement = getElement(obj);
            String targetElementText = targetElement.getText();
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(1000);
            WebElement activeElement = this.state.getDriver().switchTo().activeElement();
            String currentElementText = activeElement.getText();
            if (targetElementText.equals(currentElementText)) {
                action.sendKeys(Keys.SPACE).perform();
                break;

            }
        }
    }
    @Then("^we press down arrow on (.*) once found$")
    public void wePressdownarrowonOnceFound(String obj) throws Throwable {
        Actions action = new Actions(this.state.getDriver());
        for (int i = 0; i <= 250; i++) {
            WebElement targetElement = getElement(obj);
            String targetElementText = targetElement.getText();
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(1000);
            WebElement activeElement = this.state.getDriver().switchTo().activeElement();
            String currentElementText = activeElement.getText();
            if (targetElementText.equals(currentElementText)) {
                action.sendKeys(Keys.ARROW_DOWN).perform();
                break;
            }
        }
    }
 
 @And("we refresh Account summary page")
    public void werefreshaccountsummarypage() throws Throwable {
//            for(int i=0; i<=N; i++)
//        String Announcement_pod_text2;
        int offerslink = driver.findElements(By.xpath("//span[contains(text(),'View All')]//ancestor::a")).size();
        weScrollDown();
        System.out.println(offerslink);
        while (offerslink > 0) {
            String Announcement_pod_text1 = driver.findElement(By.xpath("//h2[contains(@class,'header')]")).getText();
            System.out.println(Announcement_pod_text1);
//                String Announcement_pod_text2 = Announcement_pod_text1;
//                  clicking on Offers link
            driver.findElement(By.xpath("//span[contains(text(),'View All')]//ancestor::a")).click();
            we_wait();
            weTakeAScreenshotFor("Offers clicked");
//                  Validate Offers and Products large pods
            WebElement Offers_Large_pod_page_title = driver.findElement(By.xpath("//h1[contains(text(),' Offers and Products')]"));
//                    Offers_Large_pod_page_title.click();
            we_wait();
            weTakeAScreenshotFor("Navigated to Large Pods");

//                  clicking on Account tab
            WebElement Accounts_tab = driver.findElement(By.xpath("//a[contains(text(),' Accounts ')]"));
            Accounts_tab.click();
            we_wait();
            weTakeAScreenshotFor("Navigated back to Account Summary page");
//                int Annountment_pod_container = driver.findElements(By.xpath("//wbb-standard-container[contains(@class,'wbb-standard-container announcement-container')]")).size();
//                System.out.println(Annountment_pod_container);
//                while (Annountment_pod_container>0)
//                if(Announcement_pod_text1!=Announcement_pod_text2)
//                String Announcement_pod_text2 = driver.findElement(By.xpath("//h2[contains(@class,'header')]")).getText();
//                Validating Offers pod rotation
//                System.out.println(Annountment_pod_container);
//                String Annountment_pod_text2 = driver.findElement(By.xpath("//h2[contains(@class,'header')]")).getText();
//                System.out.println(Annountment_pod_text2);
//                while (Annountment_pod_container>=1) {
//                    if (Annountment_pod_text1 != Annountment_pod_text2) {
//                        weTakeAScreenshotFor("Offers are rotating");
//                        continue;
//                    }
            offerslink = driver.findElements(By.xpath("//span[contains(text(),'View All')]//ancestor::a")).size();
            System.out.println(offerslink);
//                }
        }
    }
    public static void ElementClick(WebDriver driver, WebElement element) throws Throwable {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        Thread.sleep(500);
    }
    @And("we should be able to click on (.*)$")
    public void shouldBeAbleToClick(String clickable_element) throws Throwable {
        WebElement ele = getElement(clickable_element);
        Assert.assertTrue(ele.isEnabled());
        ElementClick(this.getDriver(), ele);
//        takeScreenshot();
    }
//======================================NVDA==============================================//
    public static String scenarioName;
//    @And("we launch NVDA")
//    public void nvdaStart() throws InterruptedException {
//
//        String nvdaPath = "C:\\Program Files (x86)\\NVDA\\nvda.exe";
//        String logFile = System.getProperty("user.dir") + "\\AccessibilityResults\\nvda_logs\\nvda_log.txt";
//
//        try {
////            JavascriptExecutor js = (JavascriptExecutor) driver;
////            js.executeScript("getDriver().window.focus();");
//            // Execute the command through cmd
//            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "start", "", nvdaPath, "--log-file=" + logFile);
//            processBuilder.redirectErrorStream(true); // Redirect errors to the same stream as output
//            Process process = processBuilder.start();
//
//            // Print confirmation
//            System.out.println("NVDA started with logging enabled. Logs are being saved to: " + logFile);
//
//            // Wait for NVDA process to exit (optional)
//            int exitCode = process.waitFor();
//            System.out.println("NVDA process exited with code: " + exitCode);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//
//        }
//        TimeUnit.SECONDS.sleep(10);
//    }
//    @And("we validate NVDA log with {string}")
//    public void weProcessNVDALog1(String expectedLogFileName) throws InterruptedException, IOException {
//        TimeUnit.SECONDS.sleep(5);
//
//        String nvdaPath = "C:\\Program Files (x86)\\NVDA\\nvda.exe";
//        String logFilePath = System.getProperty("user.dir") + "\\AccessibilityResults\\nvda_logs\\";
//        String logFile = logFilePath + "nvda_log.txt";
//
//        try {
//            // Execute the command through cmd
//            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", nvdaPath, "--quit");
//            processBuilder.redirectErrorStream(true); // Redirect errors to the same stream as output
//            Process process = processBuilder.start();
//
//            // Print confirmation
//            System.out.println("NVDA QUIT");
//
//            //Wait for NVDA process to exit (optional)
//            int exitCode = process.waitFor();
//            System.out.println("NVDA process exited with code: " + exitCode);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//
//        }
//
//        File filteredFile = new File(logFilePath, "filtered_nvda_log.txt");
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(logFile));
//             BufferedWriter writer = new BufferedWriter(new FileWriter(filteredFile))) {
//
//            // Filter and write lines starting with "Speaking"
//            String line;
//            while ((line = reader.readLine()) != null) {
//                if (line.startsWith("Speaking")) {
//                    line = line.replace("Speaking", "");
//                    line = line.replace("LangChangeCommand ('en_US'), ", "");
//                    line = line.replace("CallbackCommand(name=say-all:lineReached), ", "");
//                    line = line.replace(", CallbackCommand(name=say-all:lineReached)", "");
//                    line = line.replace(", CancellableSpeech (still valid)", "");
//                    line = line.replace("PitchCommand(offset=30), ", "");
//                    line = line.replace("CharacterModeCommand(True), ", "");
//                    line = line.replace(", PitchCommand(), ", "");
//                    line = line.replace(", EndUtteranceCommand()", "");
//                    line = line.replace("EndUtteranceCommand()", "");
//                    writer.write(line);
//                    writer.newLine();
//                }
//            }
//
//            System.out.println("Filtered log saved to: " + filteredFile.getAbsolutePath());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String expectedLogFile = logFilePath + expectedLogFileName;
//        System.out.println("Expected log file: " + expectedLogFile);
//        String actualLogFile = logFilePath + "filtered_nvda_log.txt";
//        List<String> expectedLines = Files.readAllLines(Paths.get(expectedLogFile));
//        List<String> actualLines = Files.readAllLines(Paths.get(actualLogFile));
//
//        nvdaComp(logFilePath, expectedLines, actualLines);
//
//    }
//
//    private void nvdaComp(String logFilePath, List<String> expectedLines, List<String> actualLines) throws IOException {
//
//
//        // Prepare the result list
//        List<List<String>> results = new ArrayList<>();
//
//        // Create a copy of the actual lines to avoid modifying the original list while iterating
//        List<String> actualCopy = new ArrayList<>(actualLines);
//
//        // Compare the expected lines with the actual lines
//        for (String expected : expectedLines) {
//            String status = "Mismatch"; // Default to mismatch
//            expected = expected.trim();
//            boolean matched = false;
//
//            // Try to find the expected line in the actual lines (case insensitive matching)
//            Iterator<String> iterator = actualCopy.iterator();
//            while (iterator.hasNext()) {
//                String actual = iterator.next().trim();
//                if (expected.equalsIgnoreCase(actual)) {
//                    // Found a match, remove the actual line from the list and mark as matched
//                    status = "Match";
//                    iterator.remove();
//                    matched = true;
//                    break;
//                }
//            }
//
//            results.add(Arrays.asList(expected, matched ? expected : "", status));
//        }
//
//        // Handle the unexpected lines in the actual file (lines that were not matched)
//        for (String unexpected : actualCopy) {
//            results.add(Arrays.asList("", unexpected.trim(), "Unexpected"));
//        }
//
//        // Create the Excel workbook
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Comparison Results");
//
//        // Set column widths
//        sheet.setColumnWidth(0, 50 * 256); // Expected column
//        sheet.setColumnWidth(1, 50 * 256); // Actual column
//        sheet.setColumnWidth(2, 15 * 256); // Status column
//
//        // Create header row with styling
//        Row headerRow = sheet.createRow(0);
//        CellStyle headerStyle = createHeaderStyle(workbook);
//        String[] headers = {"Expected", "Actual", "Status"};
//        for (int i = 0; i < headers.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(headers[i]);
//            cell.setCellStyle(headerStyle);
//        }
//
//        // Create cell styles for status column
//        CellStyle matchStyle = createStatusStyle(workbook, IndexedColors.LIGHT_GREEN);
//        CellStyle mismatchStyle = createStatusStyle(workbook, IndexedColors.RED);
//        CellStyle unexpectedStyle = createStatusStyle(workbook, IndexedColors.LIGHT_YELLOW);
//
//        // Write the comparison results to the sheet
//        int rowNum = 1;
//        for (List<String> result : results) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(result.get(0)); // Expected
//            row.createCell(1).setCellValue(result.get(1)); // Actual
//
//            Cell statusCell = row.createCell(2); // Status
//            statusCell.setCellValue(result.get(2));
//
//            // Apply styling based on the status
//            switch (result.get(2)) {
//                case "Match":
//                    statusCell.setCellStyle(matchStyle);
//                    break;
//                case "Mismatch":
//                    statusCell.setCellStyle(mismatchStyle);
//                    break;
//                case "Unexpected":
//                    statusCell.setCellStyle(unexpectedStyle);
//                    break;
//            }
//        }
//
//
//        boolean hasError = false;
//
//        for (Row row : sheet) {
//            Cell cell = row.getCell(2);
//
//            if (cell != null) {
//                String cellValue = cell.getStringCellValue();
//
//                if (cellValue.equalsIgnoreCase("mismatch") || cellValue.equalsIgnoreCase("unexpected")) {
//                    hasError = true;
//                    break;
//                }
//            }
//        }
//
//        scenarioName = scenarioName.replace(" ", "_");
//        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd_HH.mm"));
//        String fileName = scenarioName + "-NVDA-" + timestamp + ".xlsx";
//
//        // Write the workbook to a file
//        String compResult = logFilePath + fileName;
//        try (FileOutputStream fileOut = new FileOutputStream(compResult)) {
//            workbook.write(fileOut);
//        }
//
//        workbook.close();
//        System.out.println("Excel file created: " + compResult);
//
//        if (hasError) {
//            throw new AssertionError("Mismatch or Unexpected value found!");
//        } else {
//            System.out.println("Test passed: No Mismatch or Unexpected values found.");
//        }
//    }
//
//    // Method to create a header style (light grey background, centered text)
//    private static CellStyle createHeaderStyle(Workbook workbook) {
//        CellStyle style = workbook.createCellStyle();
//        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//
//        return style;
//    }
//
//    // Method to create a status style (colored background, centered text)
//    private static CellStyle createStatusStyle(Workbook workbook, IndexedColors color) {
//        CellStyle style = workbook.createCellStyle();
//        style.setFillForegroundColor(color.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//
//        return style;
//    }
//
//    @And("we focus to current window")
//    public void wefocusttocurrentwindow()
//    {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.focus();");
//    }
//==========================NVDA==============================================//
//=========================================Color Contrast Validation==========================================================//
    @And("we validate ColorContrast$")
    public void wevalidatecolorcontrast() {
        // Locate the element
//        WebElement element = driver.findElement(By.xpath("//span[contains(text(),'View All')]//ancestor::a"));
        WebElement element = driver.findElement(By.xpath("//span[contains(text(),'Swetha2')]"));
        // get computed CSS colors
        String textcolor = element.getCssValue("color");
        String backgroundcolor = element.getCssValue("background-color");
        // Convert colors to luminance
        double textLuminance = getLuminance(Color.decode(converterRGBAToHex(textcolor)));
        double bgLuminance = getLuminance(Color.decode(converterRGBAToHex(backgroundcolor)));
        // Calculate contrast ratio
        double contrastRatio = (Math.max(textLuminance, bgLuminance) + 0.05) /
                (Math.max(textLuminance, bgLuminance) + 0.05);
        // Print result
        System.out.println("Contrast Ratio: " + contrastRatio);
        // Validate against WCAG
        if (contrastRatio >= 4.5) {
            System.out.println(("Passes WCAG AA contrast standards."));
        } else {
            System.out.println(("Fails WCAG AA contrast standards."));
        }
        Assert.assertTrue(contrastRatio>=4.5);
    }
    private static String getCssValue(WebDriver driver, WebElement element, String property)
    {
        JavascriptExecutor js = (JavascriptExecutor)  driver;
//        return (String) ;
        js.executeScript("return window.getComputedStyle(arguments[0]).getPropertyValue(arguments[1]);", element, property);
        return (getCssValue(driver, element, property));
    }
    private  static double getLuminance(Color color)
    {
        double r = adjustColorComponent(color.getRed());
        double g = adjustColorComponent(color.getGreen());
        double b = adjustColorComponent(color.getBlue());
        return 0.2126 * r + 0.7152 * g + 0.0722 * b;
    }
    private static double adjustColorComponent(int colorComponent)
    {
        double c =colorComponent / 255.0;
        return (c <= 0.03928) ? (c / 12.92) : Math.pow((c + 0.055) / 1.055, 2.4);
    }
//============================================Color Contrast Validation==============================================================//
//================================================NetWork Tab Validation===================================================//
    AtomicInteger errorDetectedFlag = new AtomicInteger(0);
    @Then("we should start monitoring the network tab")
    public void weValidateTheNetworkTab() {
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(),Optional.empty(), Optional.empty()));
    }
    @Then("we should stop validating the network tab")
    public void weShouldStopValidatingTheNetworkTab() throws AWTException {
        devTools.close();
        int val = errorDetectedFlag.get();
        if (val == 0){
            throw new AWTException("Wasn't able to catch the error code specified ");
        }else {
            errorDetectedFlag.set(0);
        }
    }
    @Then("we should observe a success {string} api call in network tab")
    public void weShouldObserveAsuccessApiCallInNetworkTab(String arg0) {
        devTools.addListener(Network.responseReceived(), responseConsumer -> {
            Response response = responseConsumer.getResponse();
            if (response.getUrl().equals(arg0)) {
                if (response.getStatus() == 200){
                    String URL = response.getUrl();
                    RequestId requestId = responseConsumer.getRequestId();
                    System.out.println("URL :" + URL);
                    String responseBody = devTools.send(Network.getResponseBody(requestId)).getBody();
                    JSONParser parser = new JSONParser();
                    JSONObject json;
                    try {
                        json = (JSONObject) parser.parse(responseBody);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(json);
                    JSONArray status = (JSONArray) json.get("status");
                    System.out.println(status);
                    assertEquals(status,"SUCCESS");

//          if (status.equals("Success")){
//            System.out.println(status);
//          }
//          else{
//            throw new RuntimeException("status not matched");
//          }

                }
            }
        });
    }
    @Then("we should observe a failed {string} api call and {string} in network tab")
    public void weShouldObserveAFailedApiCallInNetworkTab(String arg0, String code) throws AWTException{
        devTools.addListener(Network.responseReceived(), responseConsumer -> {
            Response response = responseConsumer.getResponse();
            if (response.getUrl().contains(arg0)) {
                if (response.getStatus() >= 400 && response.getStatus() >= 500){
                    String URL = response.getUrl();
                    RequestId requestId = responseConsumer.getRequestId();
                    System.out.println("URL :" + URL);
                    String responseBody = devTools.send(Network.getResponseBody(requestId)).getBody();
                    JSONParser parser = new JSONParser();
                    JSONObject json;
                    try {
                        json = (JSONObject) parser.parse(responseBody);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(json);
                    JSONArray errors = (JSONArray) json.get("errors");
                    JSONObject error = (JSONObject) errors.get(0);
                    String errorCode = (String) error.get("code");
                    System.out.println(errors);
                    System.out.println(errorCode);
//          Assert.assertEquals(errorCode,code);
                    if (errorCode.equalsIgnoreCase(code)){
                        System.out.println(errorDetectedFlag.incrementAndGet());
                        System.out.println(errorCode);
                    }
                    else{
                        try {
                            throw new AWTException("Code didnt match");
                        } catch (AWTException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }
        });
    }
    @And("we should verify if {string} is {string} and {string} is {string} in the {string}")
    public void weShouldVerifyIfFlowtypeIsAndTaxidTypeIs(String arg0, String arg1, String arg2,String arg3,String arg4) {
        devTools.addListener(Network.responseReceived(), responseConsumer -> {
            Response response = responseConsumer.getResponse();
            if (response.getUrl().contains(arg4)) {
                if (response.getStatus() >= 200) {
                    String URL = response.getUrl();
                    RequestId requestId = responseConsumer.getRequestId();
                    System.out.println("URL :" + URL);
                    String requestPostData = devTools.send(Network.getRequestPostData(requestId)).toString();
                    JSONParser parser = new JSONParser();
                    JSONObject json;
                    try {
                        json = (JSONObject) parser.parse(requestPostData);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(json);
                    String flowType = (String) json.get(arg0);
                    if (flowType.equalsIgnoreCase(arg1)){
                        System.out.println(flowType);
                        errorDetectedFlag.incrementAndGet();
                    }
                    else{
                        try {
                            throw new AWTException("Flow Type didnt match");
                        } catch (AWTException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(json);
                    String taxIdType = (String) json.get(arg2);
                    if (taxIdType.equalsIgnoreCase(arg3)){
                        System.out.println(taxIdType);
                        errorDetectedFlag.incrementAndGet();
                    }
                    else{
                        try {
                            throw new AWTException("TaxId Type didnt match");
                        } catch (AWTException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }
        });
    }
//================================================NetWork Down Validation===================================================//
    @And("we validate network down Offline mode test$")
    public void wevalidatenetworkdownOfflinemodetest()
    {
        if(getBrowserName().equalsIgnoreCase("chrome")) {
            // Initialize DevTools
            DevTools devTools1 = ((ChromeDriver) driver).getDevTools();
            devTools1.createSession();
            // Enable Network and set to Offline mode
            devTools1.send(Network.enable(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()));
            devTools1.send(Network.emulateNetworkConditions(
                    true,
                    10,
                    100,
                    50,
                    Optional.of(ConnectionType.WIFI),
                    Optional.empty(), Optional.empty(), Optional.empty()
            ));
            devTools1.addListener(loadingFailed(), loadingFailed -> assertEquals(loadingFailed.getErrorText(), "net::ERR_INTERNET_DISCONNECTED"));
        }
        else if(getBrowserName().equalsIgnoreCase("edge"))
        {
            DevTools devTools1 = ((EdgeDriver) driver).getDevTools();
            devTools1.createSession();
            // Enable Network and set to Offline mode
            devTools1.send(Network.enable(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()));
            devTools1.send(Network.emulateNetworkConditions(
                    true,
                    10,
                    100,
                    50,
                    Optional.of(ConnectionType.WIFI),
                    Optional.empty(), Optional.empty(), Optional.empty()
            ));
            devTools1.addListener(loadingFailed(), loadingFailed -> assertEquals(loadingFailed.getErrorText(), "net::ERR_INTERNET_DISCONNECTED"));
        }
    }
    @And("we bring network back online mode test$")
    public void webringnetworkbackonlinemodetest()
    {
        if(getBrowserName().equalsIgnoreCase("chrome")) {
            // Initialize DevTools
            DevTools devTools1 = ((ChromeDriver) driver).getDevTools();
            devTools1.createSession();
            // Enable Network and set to Offline mode
            devTools1.send(Network.enable(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()));
            devTools1.send(Network.emulateNetworkConditions(
                    false,
                    10,
                    100,
                    50,
                    Optional.of(ConnectionType.WIFI),
                    Optional.empty(), Optional.empty(), Optional.empty()
            ));
            devTools1.addListener(loadingFailed(), loadingFailed -> assertEquals(loadingFailed.getErrorText(), "net::ERR_INTERNET_DISCONNECTED"));
        }
        else if(getBrowserName().equalsIgnoreCase("edge"))
        {
            DevTools devTools1 = ((EdgeDriver) driver).getDevTools();
            devTools1.createSession();
            // Enable Network and set to Offline mode
            devTools1.send(Network.enable(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()));
            devTools1.send(Network.emulateNetworkConditions(
                    false,
                    10,
                    100,
                    50,
                    Optional.of(ConnectionType.WIFI),
                    Optional.empty(), Optional.empty(), Optional.empty()
            ));
            devTools1.addListener(loadingFailed(), loadingFailed -> assertEquals(loadingFailed.getErrorText(), "net::ERR_INTERNET_DISCONNECTED"));
        }
    }
//================================================NetWork Down Validation===================================================//
//================================================Blocking end point URL===================================================//
    @And("^we block the (.*) url$")
    public void weblockurl(String url) {
        if (getBrowserName().equalsIgnoreCase("chrome")) {
            // Initialize DevTools
            DevTools devTools1 = ((ChromeDriver) driver).getDevTools();
            devTools1.createSession();
            // Enable Network and block particular url
            devTools1.send(Network.enable(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()));
            // Block specific URL's(wildcards allowed)
            devTools1.send(Network.setBlockedURLs(List.of(url)));
        }
        else if (getBrowserName().equalsIgnoreCase("edge"))
        {
            // Initialize DevTools
            DevTools devTools1 = ((EdgeDriver) driver).getDevTools();
            devTools1.createSession();
            // Enable Network and block particular url
            devTools1.send(Network.enable(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()));
            // Block specific URL's(wildcards allowed)
            devTools1.send(Network.setBlockedURLs(List.of(url)));
        }
    }
    @And("^we Un-block the (.*) url$")
    public void weUnblockurl(String url)
    {
        // Initialize DevTools
        DevTools devTools1 = ((ChromeDriver) driver).getDevTools();
        devTools1.createSession();
        // Enable Network and un-block particular url
        devTools1.send(Network.enable(
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));
        // Block specific URL's(wildcards allowed)
//        devTools1.send(Network.setBlockedURLs(List.of(url)));

        // Block specific URL's(wildcards allowed)
        devTools1.send(Network.setBlockedURLs(List.of(url)));
//        devTools1.send(emulateNetworkConditions(Network))

    }

//    @After
//    public  void teardown(Scenario scenario){
//        super.tearDownAfterScenario(scenario);
//    }

}
 
 
 