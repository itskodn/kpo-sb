ARG SERVICE=gateway

FROM eclipse-temurin:17-jdk AS build
ARG SERVICE
WORKDIR /app
COPY gradlew gradlew
COPY gradle gradle
COPY settings.gradle build.gradle ./
COPY gateway gateway
COPY filestore filestore
COPY analysis analysis
RUN chmod +x gradlew
RUN ./gradlew :"${SERVICE}":bootJar --no-daemon

FROM eclipse-temurin:17-jre
ARG SERVICE
WORKDIR /app
COPY --from=build /app/${SERVICE}/build/libs/${SERVICE}-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
