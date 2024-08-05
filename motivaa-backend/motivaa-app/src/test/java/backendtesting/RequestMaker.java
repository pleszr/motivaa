package backendtesting;

import io.restassured.RestAssured;

import java.util.Map;

public class RequestMaker {

    public static String initiateGetRequest(String host,
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

