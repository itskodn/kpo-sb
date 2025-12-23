FROM gradle:8.10-jdk17 AS builder
WORKDIR /workspace
COPY .. .
RUN gradle :api-gateway:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /workspace/api-gateway/build/libs/api-gateway-0.1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
