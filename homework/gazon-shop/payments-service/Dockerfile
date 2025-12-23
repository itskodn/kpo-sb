FROM gradle:8.10-jdk17 AS builder
WORKDIR /workspace
COPY .. .
RUN gradle :payments-service:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /workspace/payments-service/build/libs/payments-service-0.1.0.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
