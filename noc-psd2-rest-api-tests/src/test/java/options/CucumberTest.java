package options;

import com.capco.noc.psd2.Psd2Application;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        glue = {"stepdefs"},
        features = {"src/test/features"})
public class CucumberTest {
    @BeforeClass
    public static void prepareServer() {
        Psd2Application.main(new String[] {""});
    }
}
