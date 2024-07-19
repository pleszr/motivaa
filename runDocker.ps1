param (
    [string]$Action
)

if ($null -eq $Action) {
    Write-Host "Invalid action specified. Please use one of the following options: -Action --docker-up-es-keycloak or -Action --docker-up-frontend or -Action --docker-up-backend or -Action --docker-down"
    exit 1
}

switch ($Action) {
    "--docker-up-es-keycloak" {
        Write-Host "Starting Docker, ES and Keycloak..."
        docker-compose -f docker-compose.yml up -d elasticsearch motivaa-keycloak
        Write-Host "Waiting for dependencies to start..."
        Start-Sleep -Seconds 60
    }
    "--docker-up-frontend" {
        Write-Host "Starting frontend..."
        docker-compose -f docker-compose.yml up --build -d frontend
        Write-Host "Waiting for frontend to start..."
        Start-Sleep -Seconds 20
        Write-Host "Frontend started: endpoint: http://localhost:3000"
    }
    "--docker-up-backend" {
        Write-Host "Starting backend..."
        docker-compose -f docker-compose.yml up --build -d backend
        Write-Host "Waiting for backend to start..."
        Start-Sleep -Seconds 60
        Write-Host "Backend started: endpoint: http://localhost:8093"
    }
    "--docker-down" {
        Write-Host "Stopping Docker containers..."
        docker-compose -f docker-compose.yml down
    }
    default {
        Write-Host "Invalid action specified. Please use one of the following options, please include -Action as well!: -Action --docker-up-es-keycloak or -Action --docker-up-frontend or -Action --docker-up-backend or -Action --docker-down"
    }
}


