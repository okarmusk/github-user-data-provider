# github-user-provider for Empik

## Requirements
To run the application you need to have installed:
- Docker
- Docker Compose
- Gradle
- Java 21

## How to run the application
Please start needed containers by running the following command:
```
docker compose up -d
```
Next build the application by running the following command:
```
./gradlew clean build
```
Finally, run the application by running the following command:
```
./gradlew bootRun
```
