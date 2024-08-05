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
    String environmentHost;
    final String browserEndpointForCookies = "/page/public/en/US/process/enter/ExcelsOnModulesTestProcess?productId=ExcelsOnModulesTest&activeZone=MyZone";
    final String processStartEndpoint = "/api/v2/public/en/US/process/start?processTextId=ExcelsOnModulesTestProcess&modelTextId=ExcelsOnModulesTest&activeZone=MyZone";
    final String processUpdateEndpoint = "/api/v2/public/en/US/process/{processUuid}/update?stepId=Process:1";
    String jSessionId;

    @BeforeTest
    @Parameters({ "ENVIRONMENT_HOST"})
    void init(@Optional("https://skyeqa2.ext.saas1.innoveo-skye.net") String environmentHost){
        this.environmentHost = environmentHost;
        jSessionId = CookieHandler.retrieveJSessionIdCookie(
                environmentHost,
                browserEndpointForCookies);
    }

    @Test
    void ExcelAttributeOnParent_InputOutputsOnParent() {
        final String testedExcel = "ExcelAttributeOnParent_InputOutputsOnParentExcel";
        final String excelInputAttribute = "ExcelsOnModulesTest.ExcelAttributeOnParent_InputOutputsOnParent.input1_triggersExcel_notOptional";
        final String excelOutputAttribute = "ExcelsOnModulesTest.ExcelAttributeOnParent_InputOutputsOnParent.output1_shouldBeSameAsInput1";
        final String testedValue = "1";
        //Tested logic: Excel should copy the input value to the output value when the input value is set

        String processStart_responseBody = RequestMaker.initiateGetRequest(
                environmentHost,
                processStartEndpoint,
                jSessionId,
                "Error while creating the process object");

        String processUuid = TestUtils.retrieveProcessUuidFromResponseBody(processStart_responseBody);
        List<Map<String, String>> lockVersions = TestUtils.retrieveLockVersionsFromResponseBody(processStart_responseBody);

        Map<String, List<String>> updateMap = TestUtils.createUpdateMapFromAttributeIdAndValue(
                excelInputAttribute,
                testedValue);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("updateMap", updateMap);
        requestBody.put("lockVersions", lockVersions);

        String responseFromFirstUpdate = RequestMaker.updateExistingProcessObject(
                environmentHost,
                processUpdateEndpoint,
                processUuid,
                jSessionId,
                requestBody,
                "Error when setting " + testedValue + " to " + excelInputAttribute);

        String assertionErrorMessage = String.format("When %s gets a value, %s should copy the value to %s", excelInputAttribute, testedExcel, excelOutputAttribute);

        TestUtils.assertJsonPathValue(
                responseFromFirstUpdate,
                excelOutputAttribute,
                testedValue,
                assertionErrorMessage);
    }
}
