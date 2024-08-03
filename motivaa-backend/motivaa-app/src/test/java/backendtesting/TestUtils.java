package backendtesting;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.testng.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtils {

    public static List<Map<String,String>> retrieveLockVersionsFromResponseBody(String responseBody) {
        return JsonPath.parse(responseBody).read("$.lockVersions");
    }

    public static String retrieveProcessUuidFromResponseBody(String responseBody) {
        DocumentContext jsonContext = JsonPath.parse(responseBody);
        return jsonContext.read("$.uuid");
    }

    public static void assertJsonPathValue(String responseBody,
                                           String attributeFullTextIdPath,
                                           String expectedValue,
                                           String message) {

        String jsonPath = String.format("$..components[?(@.id=='%s')].value", attributeFullTextIdPath);

        List<String> jsonPathResultAsList = JsonPath.parse(responseBody).read(jsonPath);
        if (jsonPathResultAsList.isEmpty()) {
            Assert.fail("No value found for json path: " + jsonPath + " in response body: " + responseBody);
        }
        String jsonPathResult = jsonPathResultAsList.get(0);
        Assert.assertEquals(
                jsonPathResult,
                expectedValue,
                message);
    }

    public static Map<String,List<String>> createUpdateMapFromAttributeIdAndValue(String attributeFullTextIdPath, String value) {
        Map<String,List<String>> updateMap = new HashMap<>();
        updateMap.put(attributeFullTextIdPath, Arrays.asList(value));
        return updateMap;
    }
}


