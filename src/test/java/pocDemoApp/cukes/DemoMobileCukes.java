package pocDemoApp.cukes;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"src/test/resources/features"},
        monochrome = true,
        tags = "@NBE12",
        glue = {"pocDemoApp"},
        plugin = {
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "html:reports/Desk/index.html",
                "json:reports/Desk/cucumber.json"

        })

public class DemoMobileCukes extends AbstractTestNGCucumberTests {

}
