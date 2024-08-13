package backendtesting;

import io.restassured.RestAssured;

import java.util.List;
import java.util.Map;

public class RequestMaker {

    public static ProcessObject createProcessObject(
            String environmentHost,
            String processStartEndpoint,
            String jSessionId) {
        String processStart_responseBody = RequestMaker.initiateGetRequest(
                environmentHost,
                processStartEndpoint,
                jSessionId,
                "Error while creating the process object when calling: " + environmentHost+processStartEndpoint);

        String processUuid = TestUtils.retrieveProcessUuidFromResponseBody(processStart_responseBody);
        List<Map<String, String>> lockVersions = TestUtils.retrieveLockVersionsFromResponseBody(processStart_responseBody);
        return new ProcessObject(processUuid, lockVersions);
    }

    private static String initiateGetRequest(String host,
                                            String endpoint,
                                            String jSessionId,
                                            String onFailMessage) {
        return RestAssured.
                given()
                    .log().ifValidationFails()
                    .cookie("JSESSIONID", jSessionId)
                    .post(host+endpoint)
                .then()
                    .onFailMessage(onFailMessage)
                    .log().ifValidationFails()
                    .statusCode(200)
                    .extract().response()
                    .andReturn().asString();
    }

    public static String updateExistingProcessObject(String host,
                                                     String endpoint,
                                                     String processUuid,
                                                     String jSessionId,
                                                     Map<String,Object> requestBody,
                                                     String onFailMessage) {
        String finalEndpoint = endpoint.replace("{processUuid}", processUuid);
        return RestAssured
                .given()
                    .log().ifValidationFails()
                    .contentType("application/json")
                    .cookie("JSESSIONID", jSessionId)
                    .body(requestBody)
                    .post(host+finalEndpoint)
                .then()
                    .log().ifValidationFails()
                    .onFailMessage(onFailMessage)
                    .statusCode(200)
                    .extract().body().asString();
    }
}

