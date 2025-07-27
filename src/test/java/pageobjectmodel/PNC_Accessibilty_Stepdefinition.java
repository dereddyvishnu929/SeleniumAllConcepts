package pageobjectmodel;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import pnc.qel.cucumber.state.TestState;
import pnc.qel.cucumber.stepdefinitions.AbstractStepDefinitions;
import pnc.qel.framework.CommonElementMethods;
import pnc.qel.framework.JavaScriptMethods;
import pnc.qel.framework.VerificationMethods;
import pnc.qel.framework.WindowFrameSelectMethods;
import java.awt.*;
import java.util.*;
public class AccessibilityStepDefinitions extends AbstractStepDefinitions {
    CommonElementMethods commonMethods;
    JavaScriptMethods jsMethods;
    VerificationMethods verifyMethods;
    WindowFrameSelectMethods windowMethods;
    Scenario scenario;
    public WebDriver driver;
    public AccessibilityStepDefinitions(TestState state) throws AWTException {
        super(state);
    }
    @When("^(?:|we |I )tab")
    public void we_tab() throws Throwable {
        Actions action = new Actions(this.state.getDriver());
        action.sendKeys(Keys.TAB).perform();
    }
    @And("we take a screenshot for {string} each tab is pressed")
    public void weTakeAScreenshotForEachTabIspressed(String scrShotName) throws Throwable {
        final byte[] screenShot = ((TakesScreenshot) this.state.getDriver()).getScreenshotAs(OutputType.BYTES);
        this.scenario.attach(screenShot, "image/png", scrShotName);
    }
    @When("we press backspace {int} time(s)")
    public void PressbackspacenTimes(int n) throws InterruptedException {
        Actions action = new Actions(this.state.getDriver());
        for (int i = 1; i <= n; i++) {
            action.sendKeys(Keys.BACK_SPACE).perform();
            Thread.sleep(2000);
        }
    }
    @When("we press tab {int} time(s)")
    public void PressTabnTimes(int n) throws InterruptedException {
        Actions action = new Actions(this.state.getDriver());
        for (int i = 1; i <= n; i++) {
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(2000);
        }
    }
    @And("we press down arrow {int} time(s)")
    public void we_click_downarrow(int n) {
        Actions action = new Actions(this.state.getDriver());
        for (int i = 1; i <= n; i++) {
            action.sendKeys(Keys.ARROW_DOWN).perform();
        }
    }
    @And("we press right arrow {int} time(s)")
    public void we_click_tightarrow(int n) {
        Actions action = new Actions(this.state.getDriver());
        for (int i = 1; i <= n; i++) {
            action.sendKeys(Keys.ARROW_RIGHT).perform();
        }
    }

    @When("^(?:|we |I )shift tab")
    public void we_shift_tab() throws Throwable {
        Actions action = new Actions(this.state.getDriver());
        action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).build().perform();
    }
    @And("^(?:|we |I )negative magnify the screen")
    public void we_negative_screen(){
        Actions action = new Actions(this.state.getDriver());
        action.keyDown(Keys.CONTROL).sendKeys(Keys.SUBTRACT).keyUp(Keys.CONTROL).build().perform();
    }
    @And("^(?:|we |I )space")
    public void we_space() throws Throwable {
        Actions action = new Actions(this.state.getDriver());
        action.sendKeys(Keys.SPACE).perform();
    }
    @And("^(?:|we |I )insert")
    public void we_insert() throws Throwable {
        Actions action = new Actions(this.state.getDriver());
        action.keyDown(Keys.INSERT).sendKeys("t").keyUp(Keys.INSERT).build().perform();
    }
    @And("^(?:|we |I )enter")
    public void we_enter() throws Throwable {
        Actions action = new Actions(this.state.getDriver());
        action.sendKeys(Keys.ENTER).perform();
    }
//    @And("we press Enter")
//    public void wepressEnter() throws Throwable {
//        Actions action = new Actions(this.state.getDriver());
//        action.sendKeys(Keys.ENTER).perform();
//    }

    @When("we switch back to previous tab")
    public void we_switch_back_to_previous_tab() throws InterruptedException {
        try {
            Set<String> set = driver.getWindowHandles();
            Iterator<String> iterator = set.iterator();
            String tab1 = iterator.next();
            String tab2 = iterator.next();
            driver.close();
            Thread.sleep(3000);
            driver.switchTo().window(tab1);
        }
        catch (Exception e){
            System.out.println("Not Navigated");
        }
    }
    public void PressTabKey() throws Throwable {
        WebDriver driver = (this.state.getDriver());
        Actions actions=new Actions(driver);
        actions.sendKeys(Keys.TAB).build().perform();
        Thread.sleep(2000);
    }
    @When("^(?:|we |I )press Escape key")
    public void PressSpaceKey() throws Throwable {
        WebDriver driver = (this.state.getDriver());
        Actions actions=new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).build().perform();
        Thread.sleep(2000);
    }

    @When("we press shift tab")
    public void WePressShiftTab() throws InterruptedException {
        WebDriver driver = (this.state.getDriver());
        Actions actions=new Actions(driver);
        actions.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).build().perform();
        Thread.sleep(2000);
    }
    @And("we press shift tab {int} times")
    public void wePressShiftTabTimes(int n) {
        Actions action = new Actions(this.state.getDriver());
        for (int i = 1; i <= n; i++) {
            action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).build().perform();
        }
    }

//    @After
//    public void teardown(Scenario scenario) {
//        super.tearDownAfterScenario(scenario);
//    }
}
 
 