package com.motivaa.control.monitoring;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.net.SocketTimeoutException;

@Log4j2
@Component
public class ElasticsearchHealthCheck {
    String esStatus;

    public void checkElasticsearchHealth() {
        try {
            restCallToElasticsearchHealthCluster();
        } catch (ResourceAccessException e) {
            esStatus = "error";
            if (isTimeoutException(e)) {
                log.error("Elastic Search down. Timeout exception occurred.");
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
        String healthCheckUrl = "http://localhost:9200/_cluster/health";
        ResponseEntity<String> esResponse = restTemplate.getForEntity(healthCheckUrl, String.class);
        esStatus = JsonPath.read(esResponse.getBody(), "$.status");
    }

    private boolean isTimeoutException(ResourceAccessException e) {
        return e.getCause() instanceof SocketTimeoutException;
    }

    private void logEsStatus(String esStatus) {
        if (esStatus.equals("green") || esStatus.equals("yellow")) {
            log.info("Elasticsearch healthcheck ok, health status: {}", esStatus);
            return;
        }
        if (esStatus.equals("red")) {
            log.error("Elasticsearch connection failed, health status: {}", esStatus);
        }
    }
}