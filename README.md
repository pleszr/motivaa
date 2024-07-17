# motivaa


# API mocker available
It is bundled with frontend, if you start it will start the mocker too.

```java
Mock API server is running on http://localhost:3001
In order to use API mocker, please run motivaa-backend, Swagger UI available at http://localhost:8093/v3/api-docs 
```
# HowTo Run Motivaa DOCKER - MacOS/Linux

If you haven't installed it, please install 'make'

1. To start with the dependencies, <b>to run ElasticSearch and Keycloak</b>, execute the following: (make sure Docker Compose is installed)

```c#
make docker-up-es-keycloak
```
2. To run <b>motivaa-backend</b>
```c#
make docker-up-backend
```
3. To run <b>motivaa-frontend</b>
```c#
make docker-up-frontend
```

4. To <b>stop it</b>
```c#
make docker-down
```
# HowTo Run Motivaa DOCKER - Windows

Download and install Docker-Desktop -> https://docs.docker.com/desktop/install/windows-install/

To start with the dependencies, <b>to run ElasticSearch and Keycloak</b>, execute the following: (make sure Docker Desktop & Compose is installed)

1. Open <b>powershell</b> and execute the following.

```c#
powershell -ExecutionPolicy ByPass -File .\runDocker.ps1 -Action --docker-es-mysql-keycloak
```

2. To run <b>motivaa-backend</b>
```c#
powershell -ExecutionPolicy ByPass -File .\runDocker.ps1 -Action --docker-up-backend
```

3. To run <b>motivaa-frontend</b>

```c#
powershell -ExecutionPolicy ByPass -File .\runDocker.ps1 -Action --docker-up-frontend
```

4. To <b>stop it</b>
```c#
powershell -ExecutionPolicy ByPass -File .\runDocker.ps1 -Action --docker-down
```
# Backend documentation
Application prints the Swagger URI to the console. See example: 
```log
2024-07-13T23:59:32.891+02:00  INFO 73517 --- [           main] c.m.c.m.PrintSwaggerUriPostStartup       : Swagger UI: http://localhost:8093/swagger-ui.html
```
