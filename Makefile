docker-up-es-keycloak:
	@echo "Starting docker and keycloak..."
	@docker-compose -f docker-compose.yml up -d elasticsearch motivaa-keycloak
	@echo "Waiting for dependencies to start..."
	@sleep 60
docker-up-frontend:
	@echo "Starting frontend"
	@docker-compose -f docker-compose.yml up --build -d frontend
	@echo "Waiting for frontend to start...."
	@sleep 20
	@echo "Frontend started: endpoint: http://localhost:3000"
docker-up-backend:
	@echo "Starting backend"
	@docker-compose -f docker-compose.yml up --build -d backend
	@echo "Waiting for backend to start...."
	@sleep 60
	@echo "Backend started: endpoint: http://localhost:8093"
docker-down:
	@echo "Stopping Docker containers..."
	@docker-compose -f docker-compose.yml down

