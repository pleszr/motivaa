package backendtesting.testcases;

import com.skye.backendtesting.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class ExcelOnModulesTest {
    String environmentHost;
    final String browserEndpointForCookies = "/page/public/en/US/process/enter/ExcelsOnModulesTestProcess?productId=ExcelsOnModulesTest&activeZone=MyZone";
    final String processStartEndpoint = "/api/v2/public/en/US/process/start?processTextId=ExcelsOnModulesTestProcess&modelTextId=ExcelsOnModulesTest&activeZone=MyZone";
    final String processUpdateEndpoint = "/api/v2/public/en/US/process/{processUuid}/update?stepId=Process:1";
    String jSessionId;

    @BeforeTest
    @Parameters({"ENVIRONMENT_HOST"})
    void init(@Optional("https://skyeqa2.ext.saas1.innoveo-skye.net") String environmentHost) {
        this.environmentHost = environmentHost;
        if ( StringUtils.isEmpty(jSessionId) ) {
                jSessionId = CookieHandler.retrieveJSessionIdCookie(
                    environmentHost,
                    browserEndpointForCookies);
        }
    }

    @Test
    void GIVEN_ExcelAttributeOnParent_InputOutputsOnParent_WHEN_input_value_is_set_THEN_excel_should_copy_inputValue_to_output() {
        final String excelInputAttribute = "ExcelsOnModulesTest.ExcelAttributeOnParent_InputOutputsOnParent.input1_triggersExcel_notOptional";
        final String excelOutputAttribute = "ExcelsOnModulesTest.ExcelAttributeOnParent_InputOutputsOnParent.output1_shouldBeSameAsInput1";
        final String expectedValue = "excelShouldBeAbleToCopyAnyValue";

        ProcessObject processObject = RequestMaker.createProcessObject(
                environmentHost,
                processStartEndpoint,
                jSessionId);

        List<OutputVerification> outputVerifications = Arrays.asList(new OutputVerification(excelOutputAttribute, expectedValue));

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInputAttribute,
                expectedValue,
                outputVerifications
                );
    }


    @Test
    void GIVEN_ExcelAttributeOnParent_InputOutputsOnModule_WHEN_input_value_is_set_THEN_excel_should_copy_inputValue_to_output() {
        final String excelInputAttribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule1Link.ExcelsOnModulesTestModule1Link::ExcelsOnModulesTestModule1.ExcelsOnModulesTestModule1Link::ExcelAttributeOnParent_InputOutputsOnModule.ExcelsOnModulesTestModule1Link::input1_triggersExcel_notOptional";
        final String excelOutputAttribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule1Link.ExcelsOnModulesTestModule1Link::ExcelsOnModulesTestModule1.ExcelsOnModulesTestModule1Link::ExcelAttributeOnParent_InputOutputsOnModule.ExcelsOnModulesTestModule1Link::output1_shouldBeSameAsInput1";
        final String expectedValue = "excelShouldBeAbleToCopyAnyValue";

        ProcessObject processObject = RequestMaker.createProcessObject(
                environmentHost,
                processStartEndpoint,
                jSessionId);

        List<OutputVerification> outputVerifications = Arrays.asList(new OutputVerification(excelOutputAttribute, expectedValue));

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInputAttribute,
                expectedValue,
                outputVerifications
        );
    }
    @Test
    void GIVEN_ExcelAttributeOnParent_HybridInputOutputs_WHEN_first_value_is_set_THEN_both_outputs_should_remain_empty() {
        final String excelInputAttribute = "ExcelsOnModulesTest.ExcelAttributeOnParent_HybridInputOutputs.input1_shouldNotTriggerExcelUntilAll2InputsHaveValue_notOptional";
        final String inputValue = "noValueShouldTriggerTheExcel";
        final String excelOutput1Attribute = "ExcelsOnModulesTest.ExcelAttributeOnParent_HybridInputOutputs.output1_shouldBeSameAsInput1";
        final String excelOutput2Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule1Link.ExcelsOnModulesTestModule1Link::ExcelsOnModulesTestModule1.ExcelsOnModulesTestModule1Link::ExcelAttributeOnParent_HybridInputOutputs.ExcelsOnModulesTestModule1Link::output2_shouldBeSameAsInput2";
        final String expectedValue = "";

        ProcessObject processObject = RequestMaker.createProcessObject(
                environmentHost,
                processStartEndpoint,
                jSessionId);

        List<OutputVerification> outputVerifications = Arrays.asList(
                new OutputVerification(excelOutput1Attribute, expectedValue),
                new OutputVerification(excelOutput2Attribute,expectedValue)
        );

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInputAttribute,
                inputValue,
                outputVerifications
        );
    }
    @Test
    void GIVEN_ExcelAttributeOnParent_HybridInputOutputs_WHEN_second_value_is_set_THEN_both_both_input_values_should_be_copied_to_output() {
        final String excelInput1Attribute = "ExcelsOnModulesTest.ExcelAttributeOnParent_HybridInputOutputs.input1_shouldNotTriggerExcelUntilAll2InputsHaveValue_notOptional";
        final String excelInput2Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule1Link.ExcelsOnModulesTestModule1Link::ExcelsOnModulesTestModule1.ExcelsOnModulesTestModule1Link::ExcelAttributeOnParent_HybridInputOutputs.ExcelsOnModulesTestModule1Link::Input2_shouldTriggerExcel_notOptional";
        final String input1Value = "noValueShouldTriggerTheExcel";
        final String input2Value = "excelShouldBeAbleToCopyAnyValue";
        final String excelOutput1Attribute = "ExcelsOnModulesTest.ExcelAttributeOnParent_HybridInputOutputs.output1_shouldBeSameAsInput1";
        final String excelOutput2Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule1Link.ExcelsOnModulesTestModule1Link::ExcelsOnModulesTestModule1.ExcelsOnModulesTestModule1Link::ExcelAttributeOnParent_HybridInputOutputs.ExcelsOnModulesTestModule1Link::output2_shouldBeSameAsInput2";
        final String expectedValueAfterFirstUpdate = "";

        ProcessObject processObject = RequestMaker.createProcessObject(
                environmentHost,
                processStartEndpoint,
                jSessionId);

        List<OutputVerification> outputVerificationsForFirstUpdate = Arrays.asList(
                new OutputVerification(excelOutput1Attribute, expectedValueAfterFirstUpdate),
                new OutputVerification(excelOutput2Attribute,expectedValueAfterFirstUpdate)
        );

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInput1Attribute,
                input1Value,
                outputVerificationsForFirstUpdate
        );

        List<OutputVerification> outputVerificationsForSecondUpdate = Arrays.asList(
                new OutputVerification(excelOutput1Attribute, input1Value),
                new OutputVerification(excelOutput2Attribute,input2Value)
        );

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInput2Attribute,
                input2Value,
                outputVerificationsForSecondUpdate
        );
    }
    @Test
    void GIVEN_ExcelAttributeOnParent_InputsOnMultipleModules_WHEN_first_value_is_set_THEN_all_4_input_values_should_remain_empty() {
        final String excelInput1Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule1Link.ExcelsOnModulesTestModule1Link::ExcelsOnModulesTestModule1.ExcelsOnModulesTestModule1Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule1Link::input1_shouldNotTriggerExcelUntilAll4InputsHaveValue_notOptional";
        final String input1Value = "noValueShouldTriggerTheExcel";
        final String excelOutput1Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule4Link.ExcelsOnModulesTestModule4Link::ExcelsOnModulesTestModule4.ExcelsOnModulesTestModule4Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule4Link::output1_shouldBeSameAsInput1";
        final String excelOutput2Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule3Link.ExcelsOnModulesTestModule3Link::ExcelsOnModulesTestModule3.ExcelsOnModulesTestModule3Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule3Link::output2_shouldBeSameAsInput2";
        final String excelOutput3Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule2Link.ExcelsOnModulesTestModule2Link::ExcelsOnModulesTestModule2.ExcelsOnModulesTestModule2Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule2Link::output3_shouldBeSameAsInput3";
        final String excelOutput4Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule1Link.ExcelsOnModulesTestModule1Link::ExcelsOnModulesTestModule1.ExcelsOnModulesTestModule1Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule1Link::output4_shouldBeSameAsInput4";
        final String expectedValueAfterFirstUpdate = "";

        ProcessObject processObject = RequestMaker.createProcessObject(
                environmentHost,
                processStartEndpoint,
                jSessionId);

        List<OutputVerification> outputVerificationsForFirstUpdate = Arrays.asList(
                new OutputVerification(excelOutput1Attribute, expectedValueAfterFirstUpdate),
                new OutputVerification(excelOutput2Attribute,expectedValueAfterFirstUpdate),
                new OutputVerification(excelOutput3Attribute,expectedValueAfterFirstUpdate),
                new OutputVerification(excelOutput4Attribute,expectedValueAfterFirstUpdate)
        );

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInput1Attribute,
                input1Value,
                outputVerificationsForFirstUpdate
        );
    }
    @Test
    void GIVEN_ExcelAttributeOnParent_InputsOnMultipleModules_WHEN_second_value_is_set_THEN_all_4_input_values_should_remain_empty() {
        final String excelInput1Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule1Link.ExcelsOnModulesTestModule1Link::ExcelsOnModulesTestModule1.ExcelsOnModulesTestModule1Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule1Link::input1_shouldNotTriggerExcelUntilAll4InputsHaveValue_notOptional";
        final String excelInput2Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule2Link.ExcelsOnModulesTestModule2Link::ExcelsOnModulesTestModule2.ExcelsOnModulesTestModule2Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule2Link::input2_shouldNotTriggerExcelUntilAll4InputsHaveValue_notOptional";

        final String input1to3Value = "noValueShouldTriggerTheExcel";
        final String excelOutput1Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule4Link.ExcelsOnModulesTestModule4Link::ExcelsOnModulesTestModule4.ExcelsOnModulesTestModule4Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule4Link::output1_shouldBeSameAsInput1";
        final String excelOutput2Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule3Link.ExcelsOnModulesTestModule3Link::ExcelsOnModulesTestModule3.ExcelsOnModulesTestModule3Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule3Link::output2_shouldBeSameAsInput2";
        final String excelOutput3Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule2Link.ExcelsOnModulesTestModule2Link::ExcelsOnModulesTestModule2.ExcelsOnModulesTestModule2Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule2Link::output3_shouldBeSameAsInput3";
        final String excelOutput4Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule1Link.ExcelsOnModulesTestModule1Link::ExcelsOnModulesTestModule1.ExcelsOnModulesTestModule1Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule1Link::output4_shouldBeSameAsInput4";
        final String expectedValueAfterFirstThreeUpdate = "";

        ProcessObject processObject = RequestMaker.createProcessObject(
                environmentHost,
                processStartEndpoint,
                jSessionId);

        List<OutputVerification> outputVerificationsForFirstThreeUpdate = Arrays.asList(
                new OutputVerification(excelOutput1Attribute, expectedValueAfterFirstThreeUpdate),
                new OutputVerification(excelOutput2Attribute,expectedValueAfterFirstThreeUpdate),
                new OutputVerification(excelOutput3Attribute,expectedValueAfterFirstThreeUpdate),
                new OutputVerification(excelOutput4Attribute,expectedValueAfterFirstThreeUpdate)
        );

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInput1Attribute,
                input1to3Value,
                outputVerificationsForFirstThreeUpdate
        );

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInput2Attribute,
                input1to3Value,
                outputVerificationsForFirstThreeUpdate
        );
    }
    @Test
    void GIVEN_ExcelAttributeOnParent_InputsOnMultipleModules_WHEN_third_value_is_set_THEN_all_4_input_values_should_remain_empty() {
        final String excelInput1Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule1Link.ExcelsOnModulesTestModule1Link::ExcelsOnModulesTestModule1.ExcelsOnModulesTestModule1Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule1Link::input1_shouldNotTriggerExcelUntilAll4InputsHaveValue_notOptional";
        final String excelInput2Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule2Link.ExcelsOnModulesTestModule2Link::ExcelsOnModulesTestModule2.ExcelsOnModulesTestModule2Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule2Link::input2_shouldNotTriggerExcelUntilAll4InputsHaveValue_notOptional";
        final String excelInput3Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule3Link.ExcelsOnModulesTestModule3Link::ExcelsOnModulesTestModule2.ExcelsOnModulesTestModule3Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule3Link::input3_shouldNotTriggerExcelUntilAll4InputsHaveValue_notOptional";


        final String input1to3Value = "noValueShouldTriggerTheExcel";
        final String excelOutput1Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule4Link.ExcelsOnModulesTestModule4Link::ExcelsOnModulesTestModule4.ExcelsOnModulesTestModule4Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule4Link::output1_shouldBeSameAsInput1";
        final String excelOutput2Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule3Link.ExcelsOnModulesTestModule3Link::ExcelsOnModulesTestModule3.ExcelsOnModulesTestModule3Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule3Link::output2_shouldBeSameAsInput2";
        final String excelOutput3Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule2Link.ExcelsOnModulesTestModule2Link::ExcelsOnModulesTestModule2.ExcelsOnModulesTestModule2Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule2Link::output3_shouldBeSameAsInput3";
        final String excelOutput4Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule1Link.ExcelsOnModulesTestModule1Link::ExcelsOnModulesTestModule1.ExcelsOnModulesTestModule1Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule1Link::output4_shouldBeSameAsInput4";
        final String expectedValueAfterFirstThreeUpdate = "";

        ProcessObject processObject = RequestMaker.createProcessObject(
                environmentHost,
                processStartEndpoint,
                jSessionId);

        List<OutputVerification> outputVerificationsForFirstThreeUpdate = Arrays.asList(
                new OutputVerification(excelOutput1Attribute, expectedValueAfterFirstThreeUpdate),
                new OutputVerification(excelOutput2Attribute,expectedValueAfterFirstThreeUpdate),
                new OutputVerification(excelOutput3Attribute,expectedValueAfterFirstThreeUpdate),
                new OutputVerification(excelOutput4Attribute,expectedValueAfterFirstThreeUpdate)
        );

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInput1Attribute,
                input1to3Value,
                outputVerificationsForFirstThreeUpdate
        );

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInput2Attribute,
                input1to3Value,
                outputVerificationsForFirstThreeUpdate
        );

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInput3Attribute,
                input1to3Value,
                outputVerificationsForFirstThreeUpdate
        );
    }
    @Test
    void GIVEN_ExcelAttributeOnParent_InputsOnMultipleModules_WHEN_fourth_value_is_set_THEN_excel_should_copy_input_values_to_respective_output_attributes() {
        final String excelInput1Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule1Link.ExcelsOnModulesTestModule1Link::ExcelsOnModulesTestModule1.ExcelsOnModulesTestModule1Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule1Link::input1_shouldNotTriggerExcelUntilAll4InputsHaveValue_notOptional";
        final String excelInput2Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule2Link.ExcelsOnModulesTestModule2Link::ExcelsOnModulesTestModule2.ExcelsOnModulesTestModule2Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule2Link::input2_shouldNotTriggerExcelUntilAll4InputsHaveValue_notOptional";
        final String excelInput3Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule3Link.ExcelsOnModulesTestModule3Link::ExcelsOnModulesTestModule3.ExcelsOnModulesTestModule3Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule3Link::input3_shouldNotTriggerExcelUntilAll4InputsHaveValue_notOptional";
        final String excelInput4Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule4Link.ExcelsOnModulesTestModule4Link::ExcelsOnModulesTestModule4.ExcelsOnModulesTestModule4Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule4Link::input4_shouldNotTriggerExcelUntilAll4InputsHaveValue_notOptional";

        final String input1Value = "noValueShouldTriggerTheExcel-1";
        final String input2Value = "noValueShouldTriggerTheExcel-2";
        final String input3Value = "noValueShouldTriggerTheExcel-3";
        final String input4Value = "noValueShouldTriggerTheExcel-4";

        final String excelOutput1Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule4Link.ExcelsOnModulesTestModule4Link::ExcelsOnModulesTestModule4.ExcelsOnModulesTestModule4Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule4Link::output1_shouldBeSameAsInput1";
        final String excelOutput2Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule3Link.ExcelsOnModulesTestModule3Link::ExcelsOnModulesTestModule3.ExcelsOnModulesTestModule3Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule3Link::output2_shouldBeSameAsInput2";
        final String excelOutput3Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule2Link.ExcelsOnModulesTestModule2Link::ExcelsOnModulesTestModule2.ExcelsOnModulesTestModule2Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule2Link::output3_shouldBeSameAsInput3";
        final String excelOutput4Attribute = "ExcelsOnModulesTest.Modules.ExcelsOnModulesTestModule1Link.ExcelsOnModulesTestModule1Link::ExcelsOnModulesTestModule1.ExcelsOnModulesTestModule1Link::ExcelAttributeOnParent_InputsOnMultipleModules.ExcelsOnModulesTestModule1Link::output4_shouldBeSameAsInput4";
        final String expectedValueAfterFirstThreeUpdate = "";

        ProcessObject processObject = RequestMaker.createProcessObject(
                environmentHost,
                processStartEndpoint,
                jSessionId);

        List<OutputVerification> outputVerificationsForFirstThreeUpdate = Arrays.asList(
                new OutputVerification(excelOutput1Attribute, expectedValueAfterFirstThreeUpdate),
                new OutputVerification(excelOutput2Attribute,expectedValueAfterFirstThreeUpdate),
                new OutputVerification(excelOutput3Attribute,expectedValueAfterFirstThreeUpdate),
                new OutputVerification(excelOutput4Attribute,expectedValueAfterFirstThreeUpdate)
        );

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInput1Attribute,
                input1Value,
                outputVerificationsForFirstThreeUpdate
        );

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInput2Attribute,
                input2Value,
                outputVerificationsForFirstThreeUpdate
        );

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInput3Attribute,
                input3Value,
                outputVerificationsForFirstThreeUpdate
        );

        List<OutputVerification> outputVerificationsForFourthUpdate = Arrays.asList(
                new OutputVerification(excelOutput1Attribute, input1Value),
                new OutputVerification(excelOutput2Attribute,input2Value),
                new OutputVerification(excelOutput3Attribute,input3Value),
                new OutputVerification(excelOutput4Attribute,input4Value)
        );

        TestUtils.updateProcessObjectAndVerifyOutputValues(
                environmentHost,
                processUpdateEndpoint,
                jSessionId,
                processObject,
                excelInput4Attribute,
                input4Value,
                outputVerificationsForFourthUpdate
        );
    }
}