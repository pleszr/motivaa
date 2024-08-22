package com.motivaa.ReniReview;

import com.skye.errorHandling.Exceptions.IndexUploadFailedException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


public class SkyeIndexManager {
    public static Logger logger = LogManager.getLogger(SkyeIndexManager.class);

    public static void uploadIndexIfDoesntExists (
            String indexName,
            String filePath,
            String endpoint
    ) {
        String emptyIndex   Version = "";
        uploadIndexIfDoesntExists(
                indexName,
                emptyIndexVersion,
                filePath,
                endpoint
        );
    }

    public static void uploadIndexIfDoesntExists (
            String indexName,
            String indexVersion,
            String filePath,
            String endpoint
    ) {
        boolean doesIndexExistOnStage = verifyIndexExistsOnStage(
                indexName,
                indexVersion,
                endpoint
        );

        if ( doesIndexExistOnStage ) {
            logger.info("Index '" + indexName + "' with version '" + indexVersion + "' already exists.");
        }
        else {
            uploadCsvToSkyeAsIndex(
                    indexName,
                    indexVersion,
                    filePath,
                    endpoint
            );
            logger.info("Index '" + indexName + "' wutg version '" + indexVersion + "'  successfully created.");
        }
    }

    private static boolean verifyIndexExistsOnStage(
            String indexName,
            String indexVersion,
            String endpoint) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("indexName", indexName);
        requestBody.put("pageSize", "1");
        requestBody.put("pageNumber", "1");
        if ( !indexVersion.isEmpty() ) {
            requestBody.put("indexVersion",indexVersion);
        }

        Response response = RestAssured
                .given()
                    .body(requestBody)
                    .post(endpoint)
                .then()
                    .extract().response();
        return response.getStatusCode() == 200;
    }

    private static void uploadCsvToSkyeAsIndex(
            String indexName,
            String indexVersion,
            String filePath,
            String endpoint
    ) {

        Map<String, Object> requestBody = prepareRequestBodyFromCsv(
                indexName,
                indexVersion,
                filePath
        );

        sendCsvToSkye(
                endpoint,
                requestBody
        );
    }

    private static Map<String,Object> prepareRequestBodyFromCsv(
            String indexName,
            String indexVersion,
            String filePath
    ) {
        byte[] fileContentBytes;
        try {
            fileContentBytes = Files.readAllBytes(Paths.get(filePath));
        } catch ( IOException ioException) {
            logger.error("Error while reading the file from: \" + filePath",ioException);
            throw new IndexUploadFailedException("Error while reading the file from: " + filePath);
        }

        String encodedString = Base64.getEncoder().encodeToString(fileContentBytes);

        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("indexName",indexName);
        requestBody.put("indexContent",encodedString);
        if ( !indexVersion.isEmpty() ) {
            requestBody.put("indexVersion",indexVersion);
        }
        return requestBody;
    }

    private static void sendCsvToSkye(String endpoint, Map<String,Object> requestBody) {
        RestAssured.
                given()
                    .log().ifValidationFails()
                    .body(requestBody)
                    .post(endpoint)
                .then()
                    .log().ifValidationFails()
                    .statusCode(200);
    }
}