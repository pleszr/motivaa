package com.motivaa.boundary;

import com.motivaa.boundary.dto.HealthCheckResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/monitoring-apis")
@Log4j2
@Tag(name = "HealthCheck API", description = "Endpoint to monitor the health of the application")
public class HealthCheckController {

    @Operation(summary = "Health check")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Health check successful",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HealthCheckResponse.class))
                    })
    })
    @GetMapping("/healthcheck")
    public ResponseEntity<HealthCheckResponse> healthChecker(){
        log.trace("healthcheck successful");
        HealthCheckResponse healthCheckResponse = new HealthCheckResponse("ok");
        return ResponseEntity.ok(healthCheckResponse);
    }
}
