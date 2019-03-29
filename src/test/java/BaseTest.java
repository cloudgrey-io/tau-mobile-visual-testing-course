import io.appium.java_client.android.AndroidDriver;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BaseTest {

    AndroidDriver driver;

    protected DesiredCapabilities getCaps() throws Exception {
        throw new Exception("Must implement getCaps");
    }

    @Before
    public void setUp() throws Exception {
        URL server = new URL("http://localhost:4723/wd/hub");
        driver = new AndroidDriver(server, getCaps());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    Path getResource(String fileName) throws URISyntaxException {
        URL refImgUrl = getClass().getClassLoader().getResource(fileName);
        return Paths.get(refImgUrl.toURI()).toFile().toPath();
    }

    private String getResourceB64(String fileName) throws URISyntaxException, IOException {
        Path refImgPath = getResource(fileName);
        return Base64.getEncoder().encodeToString(Files.readAllBytes(refImgPath));
    }

    String getReferenceImageB64(String fileName) throws URISyntaxException, IOException {
        return getResourceB64("images/" + fileName);
    }

}
