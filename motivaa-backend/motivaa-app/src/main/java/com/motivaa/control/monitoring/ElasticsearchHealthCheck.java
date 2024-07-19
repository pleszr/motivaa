package com.motivaa.control.monitoring;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;

@Log4j2
@Component
public class ElasticsearchHealthCheck {

    private String esStatus;

    @Value("${elasticsearch.host}")
    String ELASTICSEARCH_HOST;

    public void checkElasticsearchHealth() {
        try {
            restCallToElasticsearchHealthCluster();
        } catch (ResourceAccessException e) {
            esStatus = "error";
            if (isTimeoutException(e)) {
                log.error("Elasticsearch down. Timeout exception occurred.");
            } else {
                log.error("Failed to check Elasticsearch health: {}", e.getMessage());
            }
        }
        logEsStatus(esStatus);
    }

    private void restCallToElasticsearchHealthCluster() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String healthCheckUrl = ELASTICSEARCH_HOST + "/_cluster/health";
        ResponseEntity<String> esResponse = restTemplate.getForEntity(healthCheckUrl, String.class);
        esStatus = JsonPath.read(esResponse.getBody(), "$.status");
    }

    private boolean isTimeoutException(ResourceAccessException e) {
        return e.getCause() instanceof SocketTimeoutException;
    }

    private void logEsStatus(String esStatus) {
        if ("green".equals(esStatus) || "yellow".equals(esStatus)) {
            log.info("Elasticsearch healthcheck ok, health status: {}", esStatus);
        } else if ("red".equals(esStatus)) {
            log.error("Elasticsearch connection failed, health status: {}", esStatus);
        }
    }
}
