package backendtesting;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;

@Log4j2
public class CookieHandler {
    public static String retrieveJSessionIdCookie(String ENVIRONMENT_HOST,
                                                  String endpoint,
                                                  Boolean shouldRequestBeLogged) {

        Response initialRequestResponse = initiateInitialRequest(
                ENVIRONMENT_HOST,
                endpoint,
                shouldRequestBeLogged
        );
        return extractCookieFromResponseHeader(initialRequestResponse);
    }

    private static Response initiateInitialRequest(
            String ENVIRONMENT_HOST,
            String endpoint,
            Boolean logEnabled) {
        if (logEnabled) {
            return RestAssured.given()
                    .log().all()
                    .get(ENVIRONMENT_HOST+endpoint)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract()
                    .response();
        } else {
            return RestAssured.given()
                    .get(ENVIRONMENT_HOST+endpoint)
                    .then()
                    .statusCode(200)
                    .extract().response();
        }
    }

    private static String extractCookieFromResponseHeader (Response response) {
        String jsessionId = null;

        String setCookieHeader = response.header("Set-Cookie");

        if (setCookieHeader != null) {
            jsessionId = Arrays.stream(setCookieHeader.split(";"))
                    .map(String::trim)
                    .filter(cookie -> cookie.startsWith("JSESSIONID"))
                    .findFirst()
                    .map(cookie -> cookie.split("=")[1])
                    .orElse(null);
        }
        return jsessionId;
    }
}
