package com.motivaa.control.monitoring;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchHealthCheckScheduler {
    ElasticsearchHealthCheck elasticsearchHealthCheck;

    public ElasticsearchHealthCheckScheduler(ElasticsearchHealthCheck elasticsearchHealthCheck) {
        this.elasticsearchHealthCheck = elasticsearchHealthCheck;
    }

    @Scheduled(fixedRateString = "${monitoring.esHealthCheckInterval}")
    public void checkElasticsearchHealth() {
        elasticsearchHealthCheck.checkElasticsearchHealth();
    }
}
