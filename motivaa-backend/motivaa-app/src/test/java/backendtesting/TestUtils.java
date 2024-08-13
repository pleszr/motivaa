package backendtesting;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.testng.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtils {

    public static final String LOCKVERSIONS = "lockVersions";
    public static final String UPDATEMAP = "updateMap";


    public static void updateProcessObjectAndVerifyOutputValues(String environmentHost,
                                                                String processUpdateEndpoint,
                                                                String jSessionId,
                                                                ProcessObject processObject,
                                                                String inputAttribute,
                                                                String inputValue,
                                                                List<OutputVerification> inputOutputExpectedValues) {

            List<Map<String, String>> lockVersions = processObject.getLockVersions();
            String processUuid = processObject.getProcessUuid();

            Map<String, List<String>> updateMap = TestUtils.createUpdateMapFromAttributeIdAndValue(
                    inputAttribute,
                    inputValue);

            Map<String, Object> requestBody = TestUtils.createRequestBodyFromUpdateMapAndLockVersions(
                    updateMap,
                    lockVersions);

            String actualUpdateResponse = RequestMaker.updateExistingProcessObject(
                    environmentHost,
                    processUpdateEndpoint,
                    processUuid,
                    jSessionId,
                    requestBody,
                    "Error when setting " + inputAttribute + " to " + inputValue);



            for (OutputVerification inputOutputExpectedValue : inputOutputExpectedValues) {
                String outputAttribute = inputOutputExpectedValue.getOutputAttribute();
                String expectedValue = inputOutputExpectedValue.getExpectedValue();

                String actualValue = TestUtils.extractAttributeValueFromResponse(
                        actualUpdateResponse,
                        outputAttribute
                        );

                Assert.assertEquals(
                            actualValue,
                            inputOutputExpectedValue.getExpectedValue(),
                            "When " + inputAttribute + " gets a value, then the value of " + outputAttribute + "should be: " + expectedValue);
            }
        }



    public static List<Map<String,String>> retrieveLockVersionsFromResponseBody(String responseBody) {
        return JsonPath.parse(responseBody).read("$.lockVersions");
    }

    public static String retrieveProcessUuidFromResponseBody(String responseBody) {
        DocumentContext jsonContext = JsonPath.parse(responseBody);
        return jsonContext.read("$.uuid");
    }

    private static String extractAttributeValueFromResponse(String responseBody,
                                                           String attributeFullTextIdPath) {

        String jsonPath = String.format("$..components[?(@.id=='%s')].value", attributeFullTextIdPath);

        List<String> jsonPathResultAsList = JsonPath.parse(responseBody).read(jsonPath);
        if (jsonPathResultAsList.isEmpty()) {
            Assert.fail("No value found for json path: " + jsonPath + " in response body: " + responseBody);
        }

        if (jsonPathResultAsList.size() > 1) {
            Assert.fail("Multiple values found for json path: " + jsonPath + " in response body: " + responseBody);
        }

        return jsonPathResultAsList.get(0);
    }


    private static Map<String,List<String>> createUpdateMapFromAttributeIdAndValue(String attributeFullTextIdPath, String value) {
        Map<String,List<String>> updateMap = new HashMap<>();
        updateMap.put(attributeFullTextIdPath, Arrays.asList(value));
        return updateMap;
    }

    private static Map<String,Object> createRequestBodyFromUpdateMapAndLockVersions(Map<String,List<String>> updateMap, List<Map<String,String>> lockVersions) {
        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put(UPDATEMAP, updateMap);
        requestBody.put(LOCKVERSIONS, lockVersions);
        return requestBody;
    }


}


