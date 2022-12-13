package pocDemoApp.pages;

import cap.common.BasePage;
import cap.utilities.TestDataUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;


public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    protected String strTabLocator = new StringBuilder().append("(//span[text()='")
            .append("<<REPLACEMENT>>").append("'])[1]").toString();


    protected String strSubTabLocator = new StringBuilder().append("(//h5[text()='")
            .append("<<REPLACEMENT>>").append("'])[1]").toString();

    @FindBy(how = How.XPATH, using = "//h1[normalize-space(text())='Close revenue faster with']")
    protected WebElement elmntCloseRevenue;

    @FindBy(how = How.XPATH, using = "(//a[text()='Talk to our team'])[1]")
    protected WebElement elmntTalkToOurTeam;

    @FindBy(how = How.XPATH, using = "//strong[text()='Sales']")
    protected WebElement elmntSales;

    @FindBy(how = How.XPATH, using = "//input[@type='submit']")
    protected WebElement btnSubmit;

    @FindBy(how = How.XPATH, using = "//h1[normalize-space(text())='Rebuilding The Modern']")
    protected WebElement elmntAboutUs;

    @FindBy(how = How.NAME, using = "firstname")
    protected WebElement txtFirstname;

    @FindBy(how = How.NAME, using = "email")
    protected WebElement txtEmail;

    @FindBy(how = How.NAME, using = "sample_test")
    protected WebElement chkSampleTest;

    @FindBy(how = How.NAME, using = "crm_software_n")
    protected WebElement drpCRMSoftware;

    @FindBy(how = How.XPATH, using = "//a[text()='Accept']")
    protected WebElement btnAccept;


    By byDrpCRMSoftware = By.name("crm_software_n");


    public void visit() {
        visit(TestDataUtil.getValue("http://localhost:3000/TestWebsite"));
    }

    public void googleLogo() {
        System.out.println("WEBPAGE LAUNCHED");
        WebElement title = driver.findElement(By.xpath("//h1[text()='Welcome!']"));
        verifyElement(title);
        System.out.println("Element Found");
    }

    public void mouseOverToTab(String strTab) {
        waitForElement(elmntCloseRevenue);
        mouseOver(elmntCloseRevenue);
        waitForElement(btnAccept);
        if (verifyElement(btnAccept)) {
            click(btnAccept);
        }
        System.out.println("elmntTab>> "+By.xpath(strTabLocator.replace("<<REPLACEMENT>>", strTab)));
        WebElement elmntTab = waitForElement(By.xpath(strTabLocator.replace("<<REPLACEMENT>>", strTab)));
        mouseOver(elmntTab);
    }

    public void navigateToSubTab(String strTab) {
        System.out.println("elmntSubTab>> "+By.xpath(strSubTabLocator.replace("<<REPLACEMENT>>", strTab)));
        WebElement elmntSubTab = waitForElement(By.xpath(strSubTabLocator.replace("<<REPLACEMENT>>", strTab)));
        click(elmntSubTab);
    }

    public boolean verifySales() {
        waitForElement(elmntSales);
        takeScreenshot(driver);
        return verifyElement(elmntSales);
    }

    public void clickTalkToOurTeam() {
        waitForElement(elmntTalkToOurTeam);
        click(elmntTalkToOurTeam);
    }

    public void enterTalkToOurTeamDetails(String strFirstName, String strEmail, String strCRMSoftware) {
        waitForElement(elmntTalkToOurTeam);
        waitForSeconds(1);
        mouseOver(elmntTalkToOurTeam);
        waitForElement(txtFirstname);
        waitForElement(btnAccept);
        if (verifyElement(btnAccept)) {
            click(btnAccept);
        }
        enterValue(txtFirstname, strFirstName);
        enterValue(txtEmail, strEmail);
        click(chkSampleTest);
        ElementselectByVisibleBy(byDrpCRMSoftware, strCRMSoftware);
    }

    public boolean verifySubmitButton() {
        waitForElement(btnSubmit);
        jsScrollDown();
        takeScreenshot(driver);
        attachStepLog("Note","As this Demo, We are not submitting the request...");
        return verifyElement(btnSubmit);
    }

    public boolean verifyAboutUs() {
        waitForElement(elmntAboutUs);
        takeScreenshot(driver);
        jsScrollDown();
        jsScrollDown();
        jsScrollDown();
        jsScrollDown();
        return verifyElement(elmntAboutUs);
    }

}



