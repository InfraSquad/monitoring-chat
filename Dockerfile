# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/sqlite-health-check-0.0.1-SNAPSHOT.jar .
ENV JAVA_TOOL_OPTIONS="-Dcom.sun.net.ssl.checkRevocation=false -Djdk.internal.httpclient.disableHostnameVerification=true"
EXPOSE 8080
CMD ["java", "-jar", "sqlite-health-check-0.0.1-SNAPSHOT.jar"]
