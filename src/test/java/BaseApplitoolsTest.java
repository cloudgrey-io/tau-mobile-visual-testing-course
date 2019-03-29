import com.applitools.eyes.StdoutLogHandler;
import com.applitools.eyes.appium.Eyes;
import org.junit.After;
import org.junit.Before;

public class BaseApplitoolsTest extends BaseTest {

    Eyes eyes;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        // Initialize the Eyes SDK with our API key (set it as an environment variable)
        eyes = new Eyes();
        eyes.setLogHandler(new StdoutLogHandler());
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
    }

    @Override
    @After
    public void tearDown() {
        eyes.abortIfNotClosed();
        super.tearDown();
    }

}
