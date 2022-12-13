package pocDemoApp.stepDefinitions;


import cap.utilities.TestDataUtil;
import com.atlassian.ta.wiremockpactgenerator.WireMockPactGenerator;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.junit.ClassRule;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.testng.Assert;
import pocDemoApp.DemoPageContainer;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;


public class WebSteps {


    private DemoPageContainer demoPageContainer;


    public WebSteps(DemoPageContainer demoPageContainer) {
        this.demoPageContainer = demoPageContainer;
    }



    @Given("As a user I am on google home Page")
    public void asAUserIAmOnNektarHomePage() {
        demoPageContainer.homePage.visit();
    }


    @Then("I should see google logo")
    public void iShouldSeeGoogleLogo() {
        Assert.assertTrue(demoPageContainer.homePage.titleWelcome());
    }













    @Then("I should see sales page")
    public void iShouldSeeSalesPage() {
        Assert.assertTrue(demoPageContainer.homePage.verifySales());
    }

    @And("I click on Talk to our team")
    public void iClickOnTalkToOurTeam() {
        demoPageContainer.homePage.clickTalkToOurTeam();
    }

    @And("I enter the {string} {string} and {string} in Talk to our team")
    public void iEnterTheAndInTalkToOurTeam(String strFirstName, String strEmail, String strCRMSoftware) {
        demoPageContainer.homePage.enterTalkToOurTeamDetails(TestDataUtil.getValue(strFirstName), TestDataUtil.getValue(strEmail), TestDataUtil.getValue(strCRMSoftware));
    }

    @Then("I should see Submit button")
    public void iShouldSeeSubmitButton() {
        Assert.assertTrue(demoPageContainer.homePage.verifySubmitButton());
    }

    @Then("I should see About us page")
    public void iShouldSeeAboutUsPage() {
        Assert.assertTrue(demoPageContainer.homePage.verifyAboutUs());
    }

    @When("I click on {string} under {string}")
    public void iClickOnUnder(String strSubTab, String strTab) {
        demoPageContainer.homePage.mouseOverToTab(TestDataUtil.getValue(strTab));
        demoPageContainer.homePage.navigateToSubTab(TestDataUtil.getValue(strSubTab));
    }


}
