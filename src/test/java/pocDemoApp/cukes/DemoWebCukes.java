package pocDemoApp.cukes;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"src/test/resources/features"},
        monochrome = true,
        tags = "@WEB",
        glue = {"pocDemoApp"},
        plugin = {
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "html:reports/Desk/index.html",
                "json:reports/Desk/cucumber.json","cap.utilities.CustomGherkinStepListener"

        })

public class DemoWebCukes extends AbstractTestNGCucumberTests {

}
