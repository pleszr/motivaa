package backendtesting;

import io.restassured.RestAssured;

import java.util.Map;

public class RequestMaker {

    public static String initiateGetRequest(String host,
                                            String endpoint,
                                            String jSessionId,
                                            boolean shouldRequestBeLogged) {
        if (shouldRequestBeLogged) {
            return RestAssured.given()
                    .log().all()
                    .cookie("JSESSIONID", jSessionId)
                    .post(host+endpoint)
                    .then()
                    .statusCode(200)
                    .log().all()
                    .extract().response()
                    .andReturn().asString();
        } else {
            return RestAssured.given()
                    .cookie("JSESSIONID", jSessionId)
                    .post(endpoint)
                    .then()
                    .statusCode(200)
                    .extract().response()
                    .andReturn().asString();
        }
    }



    public static String updateExistingProcessObject(String host,
                                                     String endpoint,
                                                     String processUuid,
                                                     String jSessionId,
                                                     Map<String,Object> requestBody,
                                                     boolean shouldRequestBeLogged) {
        String finalEndpoint = endpoint.replace("{processUuid}", processUuid);
        if (shouldRequestBeLogged) {
            return RestAssured.given()
                    .log().all()
                    .contentType("application/json")
                    .cookie("JSESSIONID", jSessionId)
                    .body(requestBody)
                    .post(host+finalEndpoint)
                    .then()
                    .log().all()
                    .extract().response()
                    .andReturn().asString();
        } else {
            return RestAssured.given()
                    .contentType("application/json")
                    .cookie("JSESSIONID", jSessionId)
                    .body(requestBody)
                    .post(host+finalEndpoint)
                    .then()
                    .extract().response()
                    .andReturn().asString();
        }
    }
}

