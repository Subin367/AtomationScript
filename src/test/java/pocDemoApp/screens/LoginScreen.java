package pocDemoApp.screens;

import cap.common.BaseScreen;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginScreen extends BaseScreen {

    public LoginScreen(WebDriver driver) {
        super(driver);
    }

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Skip']")
    protected WebElement btnSkip;

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='ap_email']")
    protected WebElement elmntTop;

    String strOptionsInHomeScreenLocator = new StringBuilder()
            .append("//android.widget.TextView[@text=\"")
            .append("<<OPTION>>").append("\"]").toString();

    public void verifyHomeScreen() {
        waitForElement(elmntTop);
        enterValue(elmntTop,"111111111111");
//        takeScreenshot(driver);
        verifyElement(elmntTop);
    }

    public void navigateHomeScreen() {
        waitForElement(btnSkip);
        click(btnSkip);
        waitForElement(elmntTop);
        verifyElement(elmntTop);
    }


    public void tapOptionsInHomeScreen(String strOptions) {
//        reLaunchAppAndroid();
        WebElement elmntOption = waitForElement(By.xpath(strOptionsInHomeScreenLocator.replace("<<OPTION>>", strOptions)));
        click(elmntOption);
    }

    public void verifyOptionsInHomeScreen(String strOptions) {
//        reLaunchAppAndroid();
        WebElement elmntOption = waitForElement(By.xpath(strOptionsInHomeScreenLocator.replace("<<OPTION>>", strOptions)));
        verifyElement(elmntOption);
        takeScreenshot(driver);
        waitForSecond(1);
        driver.navigate().back();
    }

}

