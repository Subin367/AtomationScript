package pocDemoApp.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pocDemoApp.DemoScreenContainer;

public class MobileSteps {

    private DemoScreenContainer demoScreenContainer;


    public MobileSteps(DemoScreenContainer demoScreenContainer) {
        this.demoScreenContainer = demoScreenContainer;
    }

    @Given("As a user I am on Zomoto Home Screen")
    public void asAUserIAmOnZomotoHomeScreen() {
        demoScreenContainer.loginScreen.verifyHomeScreen();
    }

    @When("I tap on {string}")
    public void iTapOn(String strTab) {
        demoScreenContainer.loginScreen.tapOptionsInHomeScreen(strTab);
    }

    @Then("I should see respective {string} in mobile")
    public void iShouldSeeRespectiveInMobile(String strTab) {
        demoScreenContainer.loginScreen.verifyOptionsInHomeScreen(strTab);
    }

    @Given("As a user I am on navigate Home Screen")
    public void asAUserIAmOnNavigateHomeScreen() {
        demoScreenContainer.loginScreen.navigateHomeScreen();
    }

    @Then("I should see home screen")
    public void iShouldSeeHomeScreen() {
        demoScreenContainer.loginScreen.verifyHomeScreen();
    }
}
