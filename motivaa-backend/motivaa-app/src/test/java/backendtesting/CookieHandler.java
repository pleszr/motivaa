package backendtesting;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

import java.net.HttpCookie;

@Log4j2
public class CookieHandler {
    public static String retrieveJSessionIdCookie(String environmentHost,
                                                  String endpoint) {

        Response initialRequestResponse = initiateInitialRequest(
                environmentHost,
                endpoint);
        return extractCookieFromResponseHeader(initialRequestResponse);
    }

    private static Response initiateInitialRequest(String environmentHost,
                                                   String endpoint) {
            return RestAssured
                    .given()
                        .log().ifValidationFails()
                        .get(environmentHost+endpoint)
                    .then()
                        .log().ifValidationFails()
                        .onFailMessage("Error while retrieving JSESSIONID cookie from the browser-friendly endpoint")
                        .statusCode(200)
                        .extract()
                        .response();
    }

    private static String extractCookieFromResponseHeader (Response response) {
        String setCookieHeader = response.header("Set-Cookie");
        HttpCookie jsessionId = HttpCookie.parse(setCookieHeader).get(0);
        return jsessionId.getValue();
    }
}
