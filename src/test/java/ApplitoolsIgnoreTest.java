import io.appium.java_client.MobileBy;
import java.net.URISyntaxException;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ApplitoolsIgnoreTest extends BaseApplitoolsTest {

    private final static String CHECK_MSG = "saved_message";

    private final static By ECHO_SCREEN = MobileBy.AccessibilityId("Echo Box");
    private final static By MSG_BOX = MobileBy.AccessibilityId("messageInput");
    private final static By SAVE_BTN = MobileBy.AccessibilityId("messageSaveBtn");

    @Override
    protected DesiredCapabilities getCaps() throws URISyntaxException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("app", getResource("apps/TheApp-v2.apk").toString());
        // make sure we uninstall the app before each test regardless of version
        capabilities.setCapability("uninstallOtherPackages", "io.cloudgrey.the_app");

        return capabilities;
    }

    @Test
    public void testIgnoredRegion() {
        String msg = "hello";

        eyes.open(driver, "TheApp", "saved message test");

        WebDriverWait wait = new WebDriverWait(driver, 5);

        // wait for an element that's on the home screen
        wait.until(ExpectedConditions.presenceOfElementLocated(ECHO_SCREEN)).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(MSG_BOX)).sendKeys(msg);

        driver.findElement(SAVE_BTN).click();

        WebElement savedMsg = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(msg)));

        Assert.assertEquals(msg, savedMsg.getText());

        // now we know the home screen is loaded, so do a visual check
        eyes.checkWindow(CHECK_MSG);

        eyes.close();
    }
}