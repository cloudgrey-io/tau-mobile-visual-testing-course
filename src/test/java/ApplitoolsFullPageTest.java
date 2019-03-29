import io.appium.java_client.MobileBy;
import java.net.URISyntaxException;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ApplitoolsFullPageTest extends BaseApplitoolsTest {

    private final static String CHECK_HOME = "home_screen_full";

    private final static By LOGIN_SCREEN = MobileBy.AccessibilityId("Login Screen");

    @Override
    protected DesiredCapabilities getCaps() throws URISyntaxException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("app", getResource("apps/TheApp-v1.apk").toString());
        // make sure we uninstall the app before each test regardless of version
        capabilities.setCapability("uninstallOtherPackages", "io.cloudgrey.the_app");

        return capabilities;
    }

    private WebElement waitForElement(WebDriverWait wait, By selector) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(selector));
        try { Thread.sleep(750); } catch (InterruptedException ign) {}
        return el;
    }

    @Test
    public void testFullPageHomeScreen() {
        eyes.setForceFullPageScreenshot(true);
        eyes.open(driver, "TheApp", "full page home screen test");

        WebDriverWait wait = new WebDriverWait(driver, 5);

        // wait for an element that's on the home screen
        waitForElement(wait, LOGIN_SCREEN);

        // now we know the home screen is loaded, so do a visual check
        eyes.checkWindow(CHECK_HOME);

        eyes.close();
    }
}