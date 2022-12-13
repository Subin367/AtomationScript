package pocDemoApp;

import cap.utilities.SharedDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import pocDemoApp.pages.HomePage;
import pocDemoApp.pages.RegistrationPage;

import java.util.LinkedHashMap;
import java.util.Map;


public class DemoPageContainer {
    private WebDriver driver;
    public static Scenario myScenario;
    public static LinkedHashMap<String, String> printTestDataMap = new LinkedHashMap<String, String>();

    public HomePage homePage;
    public RegistrationPage registrationPage;

    public DemoPageContainer() {
        driver = SharedDriver.getDriver();
        initPages();
    }

    private void initPages() {
        homePage = new HomePage(driver);
        registrationPage = new RegistrationPage(driver);
    }

    @After("@WEB")
    public void afterScenario(Scenario scenario) {
        if (printTestDataMap.size() > 0) {
            for (Map.Entry<String, String> entry : printTestDataMap.entrySet()) {
                scenario.log(entry.getKey().concat(": ").concat(entry.getValue()));
            }
        }

        byte[] imageBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(imageBytes, "image/png", "");

//        if (scenario.isFailed()) {
//            byte[] imageBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//            scenario.attach(imageBytes, "image/png", "");
//        }
        System.out.println("\n Scenario Outline: " + scenario.getName());
        System.out.println("\n Scenario Status: " + scenario.getStatus());
    }

    //  @Before("not @API and not @Mobile and not @Desktop")
    @Before("@WEB")
    public void before(Scenario scenario1) {
        printTestDataMap.clear();
        myScenario = scenario1;
    }
}
