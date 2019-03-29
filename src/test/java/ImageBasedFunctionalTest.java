import io.appium.java_client.MobileBy;
import io.appium.java_client.Setting;
import java.net.URISyntaxException;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ImageBasedFunctionalTest extends BaseTest {

    private WebDriverWait shortWait;

    @Override
    protected DesiredCapabilities getCaps() throws URISyntaxException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("app", getResource("apps/calculator.apk").toString());
        capabilities.setCapability("appWaitActivity", "com.unity3d.player.UnityPlayerActivity");
        capabilities.setCapability("mjpegScreenshotUrl", "http://172.20.10.4:8080/stream.mjpeg");

        return capabilities;
    }

    @Test
    public void testCalculatorGame() throws Exception {
        // set the image match threshold for these finds
        driver.setSetting(Setting.IMAGE_MATCH_THRESHOLD, 0.85);
        shortWait = new WebDriverWait(driver, 2);

        navToFirstLevel();
        playLevel(1);
        playLevel(2);

        try { Thread.sleep(3000); } catch (Exception ign) {}
    }

    private WebElement waitForImage(WebDriverWait wait, String image) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.image(image)));
    }

    private void clickImage(String image) {
        waitForImage(shortWait, image).click();
        actionDelay();
    }

    private void actionDelay() {
        try { Thread.sleep(1000); } catch (Exception ign) {}
    }

    private void navToFirstLevel() throws Exception {
        final String greenButton = getReferenceImageB64("green-button.png");
        final String settings = getReferenceImageB64("settings.png");

        boolean settingsVisible = false;
        int count = 0, maxCount = 20;

        while (!settingsVisible && count < maxCount) {
            count += 1;
            try {
                clickImage(greenButton);
            } catch (TimeoutException skip) {
                continue;
            }

            try {
                driver.findElement(MobileBy.image(settings));
                settingsVisible = true;
            } catch (NoSuchElementException ign) {}
        }

        if (count >= maxCount) {
            throw new Exception("Could not navigate to first level");
        }
    }

    private void playLevel(int level) throws Exception {
        final String plus1 = getReferenceImageB64("plus-1.png");
        final String plus2 = getReferenceImageB64("plus-2.png");
        final String plus3 = getReferenceImageB64("plus-3.png");
        final String okButton = getReferenceImageB64("ok.png");
        final String greenButton = getReferenceImageB64("green-button.png");

        WebElement el;

        switch(level) {
            case 1:
                el = waitForImage(shortWait, plus1);
                el.click(); actionDelay();
                el.click(); actionDelay();
                break;
            case 2:
                el = waitForImage(shortWait, plus3);
                el.click(); actionDelay();
                el.click(); actionDelay();
                clickImage(plus2);
                break;
            default:
                throw new Exception("Don't know how to play that level yet");
        }

        // complete level
        clickImage(okButton);

        // go to next level
        clickImage(greenButton);
    }


}