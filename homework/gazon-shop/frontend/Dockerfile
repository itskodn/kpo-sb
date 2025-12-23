FROM gradle:8.10-jdk17 AS builder
WORKDIR /workspace
COPY .. .
RUN gradle :frontend:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /workspace/frontend/build/libs/frontend-0.1.0.jar app.jar
EXPOSE 3000
ENTRYPOINT ["java","-jar","/app/app.jar"]
