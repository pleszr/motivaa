package backendtesting.testcases;

import com.skye.backendtesting.CookieHandler;
import com.skye.backendtesting.RequestMaker;
import com.skye.backendtesting.TestUtils;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class ExcelOnModulesTest  {
    String ENVIRONMENT_HOST;
    final String BROWSER_ENDPOINT_FOR_COOKIES = "/page/public/en/US/process/enter/ExcelsOnModulesTestProcess?productId=ExcelsOnModulesTest&activeZone=MyZone";
    final String PROCESS_START_ENDPOINT = "/api/v2/public/en/US/process/start?processTextId=ExcelsOnModulesTestProcess&modelTextId=ExcelsOnModulesTest&activeZone=MyZone";
    final String PROCESS_UPDATE_ENDPOINT = "/api/v2/public/en/US/process/{processUuid}/update?stepId=Process:1";
    String jSessionId;
    final boolean shouldRequestBeLogged = true;

    @BeforeTest
    @Parameters({ "ENVIRONMENT_HOST"})
    void init(@Optional("https://skyeqa2.ext.saas1.innoveo-skye.net") String ENVIRONMENT_HOST){
        this.ENVIRONMENT_HOST = ENVIRONMENT_HOST;
        jSessionId = CookieHandler.retrieveJSessionIdCookie(
                ENVIRONMENT_HOST,
                BROWSER_ENDPOINT_FOR_COOKIES,
                shouldRequestBeLogged);
    }

    @Test
    void testExcels() {
        final String TESTED_EXCEL = "ExcelAttributeOnParent_InputOutputsOnParentExcel";
        final String EXCEL_INPUT_ATTRIBUTE = "ExcelsOnModulesTest.ExcelAttributeOnParent_InputOutputsOnParent.input1_triggersExcel_notOptional";
        final String EXCEL_OUTPUT_ATTRIBUTE = "ExcelsOnModulesTest.ExcelAttributeOnParent_InputOutputsOnParent.output1_shouldBeSameAsInput1";
        final String TESTED_VALUE = "1";
        //Tested logic: Excel should copy the input value to the output value when the input value is set

        String processStart_responseBody = RequestMaker.initiateGetRequest(
                ENVIRONMENT_HOST,
                PROCESS_START_ENDPOINT,
                jSessionId,
                shouldRequestBeLogged);

        String processUuid = TestUtils.retrieveProcessUuidFromResponseBody(processStart_responseBody);
        List<Map<String, String>> lockVersions = TestUtils.retrieveLockVersionsFromResponseBody(processStart_responseBody);

        Map<String, List<String>> updateMap = TestUtils.createUpdateMapFromAttributeIdAndValue(
                EXCEL_INPUT_ATTRIBUTE,
                TESTED_VALUE);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("updateMap", updateMap);
        requestBody.put("lockVersions", lockVersions);

        String responseFromFirstUpdate = RequestMaker.updateExistingProcessObject(
                ENVIRONMENT_HOST,
                PROCESS_UPDATE_ENDPOINT,
                processUuid,
                jSessionId,
                requestBody,
                shouldRequestBeLogged);

        TestUtils.assertJsonPathValue(
                responseFromFirstUpdate,
                "ExcelsOnModulesTest.ExcelAttributeOnParent_InputOutputsOnParent.output1_shouldBeSameAsInput1",
                TESTED_VALUE,
                String.format("When %s gets a value, %s should copy the value to %s", EXCEL_INPUT_ATTRIBUTE, TESTED_EXCEL, EXCEL_OUTPUT_ATTRIBUTE));
    }
}
