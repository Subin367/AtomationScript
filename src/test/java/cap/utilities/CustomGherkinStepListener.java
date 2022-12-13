package cap.utilities;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class CustomGherkinStepListener implements ConcurrentEventListener {

    public static LinkedHashMap<Object, Object> dataMapForTestStepStatus = new LinkedHashMap<>();

    private static List<String> dataMapForImageBytes = new ArrayList<>();
    private static StringBuilder imgLocatorForImageBytes = new StringBuilder();
    private static byte[] imgByte = null;


    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseFinished.class, this::afterScenarioComplete);
        publisher.registerHandlerFor(TestCaseStarted.class, this::beforeScenario);
        publisher.registerHandlerFor(TestStepFinished.class, this::afterGherkinStep);
        publisher.registerHandlerFor(TestStepStarted.class, this::beforeGherkinStep);
        publisher.registerHandlerFor(EmbedEvent.class, this.getEmbedEventHandler());

    }

    private void beforeGherkinStep(TestStepStarted event) {
        dataMapForImageBytes.clear();
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep steps = (PickleStepTestStep) event.getTestStep();
            System.out.println("Before Step getText: "+steps.getStep().getText());
            System.out.println("Before Step getKeyword: "+steps.getStep().getKeyword());
            System.out.println("Before Step getLocation: "+steps.getStep().getLocation());
            System.out.println("Before Step getArgument: "+steps.getStep().getArgument());
            System.out.println("Before Step getLine: "+steps.getStep().getLine());
            System.out.println("Before Step getDefinitionArgument: "+steps.getDefinitionArgument());
            System.out.println("Before Step getPattern: "+steps.getPattern());
            System.out.println("Before Step getUri: "+steps.getUri());
        }
    }

    protected EventHandler<EmbedEvent> getEmbedEventHandler() {
        return (event) -> {
            dataMapForImageBytes.add(event.getData().toString());
            imgLocatorForImageBytes.append(event.getData()).append(", ");
            imgByte = event.getData();
        };
    }


    // This is triggered when TestStep is finished
    private void afterGherkinStep(TestStepFinished event) {
        String stepName = "";
        String keyword = "";


        // We checks whether the event is from a hook or step
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep steps = (PickleStepTestStep) event.getTestStep();
            System.out.println("Step getText: "+steps.getStep().getText());
            System.out.println("Step getKeyword: "+steps.getStep().getKeyword());
            System.out.println("Step getLocation: "+steps.getStep().getLocation());
            System.out.println("Step getArgument: "+steps.getStep().getArgument());
            System.out.println("Step getLine: "+steps.getStep().getLine());
            System.out.println("Step getDefinitionArgument: "+steps.getDefinitionArgument());
            System.out.println("Step getPattern: "+steps.getPattern());
            System.out.println("Step getUri: "+steps.getUri());

            stepName = steps.getStep().getText();
            keyword = steps.getStep().getKeyword();

            if (!(keyword.isEmpty())) {
                String strStep = keyword + stepName;
                String strStatus = event.getResult().getStatus().toString();
                dataMapForTestStepStatus.put(strStep, strStatus);
                try {
                    dataMapForTestStepStatus.put("Exception", event.getResult().getError().toString());
                } catch (Exception e) {
                    //System.out.println("Warning: Exception for Embedding GetError func.");
                }
            }
        } else if (event.getTestStep() instanceof HookTestStep) {
            try {
                if (imgLocatorForImageBytes.toString().length() != 0) {
                    String strScreenshotWithDelimiter = String.join(",", dataMapForImageBytes);
                    dataMapForTestStepStatus.put("Screenshot", imgByte);
                }
            } catch (Exception e) {
            }
        }
    }

    ;

    private void beforeScenario(TestCaseStarted event) {
        dataMapForTestStepStatus.clear();
        System.out.println("Before Scenario Name: "+event.getTestCase().getName());
        System.out.println("Before Scenario getTestSteps: "+event.getTestCase().getTestSteps());
    }

    ;
    //static String strUserDirectory = System.getProperty("user.dir")+"\\reports\\PDF-Report\\";

    private String imgLocation = "E:\\Projects\\QA-Payzli-Paylink\\reports\\images\\";

    private void afterScenarioComplete(TestCaseFinished event) {
        String strScenarioOutline = event.getTestCase().getName();
        System.out.println("Scenario: "+event.getTestCase());
        System.out.println("Scenario name: "+event.getTestCase().getName());
        System.out.println("Scenario getKeyword: "+event.getTestCase().getKeyword());
        System.out.println("Scenario getTestSteps: "+event.getTestCase().getTestSteps().toString());
        System.out.println("Scenario getTags: "+event.getTestCase().getTags());
        System.out.println("Scenario getId: "+event.getTestCase().getId());
        System.out.println("Scenario getLocation: "+event.getTestCase().getLocation());
        System.out.println("Scenario getUri: "+event.getTestCase().getUri());
        System.out.println("Scenario getStatus: "+event.getResult().getStatus());
        System.out.println("Scenario getError: "+event.getResult().getError());
        System.out.println("Scenario getDuration: "+event.getResult().getDuration());



        String strFinalImageLocation = "";

        if (event.getResult().getStatus().isOk() == false) {
            System.out.println("Scenario Outline: " + strScenarioOutline + "\n");
            String strExecutionStepsAndStatus = getScenarioExecutionDetails(dataMapForTestStepStatus);
            System.out.println("\n >>>> Execution Steps & Status : \n" + strExecutionStepsAndStatus);

            if (!(dataMapForTestStepStatus.get("Screenshot") == null)) try {
                byte[] imgBytes = (byte[]) dataMapForTestStepStatus.get("Screenshot");
                strFinalImageLocation = imgLocation + "imgScreenshot.png";
                FileUtils.writeByteArrayToFile(new File(strFinalImageLocation), imgBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("\n >> Image Location : " + strFinalImageLocation);
        }


        // Card Creation(strScenarioOutline, strExecutionStepsAndStatus, member, Label)

    }

    ;

    public String getScenarioExecutionDetails(LinkedHashMap<Object, Object> dataMap) {
        StringBuilder strDescription = new StringBuilder();
        for (Map.Entry<Object, Object> entry : dataMap.entrySet()) {
            try {
                if (!(entry.getKey().toString().equalsIgnoreCase("Exception") || entry.getKey().toString().equalsIgnoreCase("Screenshot"))) {
                    strDescription = strDescription.append(entry.getKey() + " :: " + entry.getValue() + "\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("\n Warning: Unable to retrieve Execution details in Concurrent Event Listener...");
            }
        }
        try {
            String strException = (String) dataMap.get("Exception");
            if (!(strException.isEmpty())) strDescription = strDescription.append("Exception: " + strException);
        } catch (Exception ex) {
        }
        return strDescription.toString();
    }


    /*public void getScenarioExecutionDetails(LinkedHashMap<String, Object> dataMap) {

        LinkedHashMap<String, Object> executionDetails = (LinkedHashMap<String, Object>) getMapAsObject(dataMap, "Scenario.Step");
        StringBuilder strDescription = new StringBuilder();

        for (Map.Entry<String, Object> entry : executionDetails.entrySet()) {
            try {
                if (!(entry.getKey().equalsIgnoreCase("Exception")
                        || entry.getKey().equalsIgnoreCase("Screenshot"))) {
                    strDescription = strDescription.append(entry.getKey() + " :: " + entry.getValue() + "\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("\n Warning: Unable to retrieve Execution details in Concurrent Event Listener...");
            }
        }
        try {
            String strException = (String) executionDetails.get("Exception");
            if (!(strException.isEmpty()))
                strDescription = strDescription.append("Exception: " + strException);
        } catch (Exception ex) {
        } finally {
            if (!((String) executionDetails.get("Screenshot") == null))
                System.out.println("Screenshot:: " + (String) executionDetails.get("Screenshot"));
        }


        System.out.println("\n >>>> Last Info : \n" + strDescription);
    }*/


}
