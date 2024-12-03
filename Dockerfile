FROM eclipse-temurin:17 AS build-stage

WORKDIR /app

# copy gradle config
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
RUN chmod +x ./gradlew

# copy source code
COPY src src

# Build
RUN ./gradlew build -x test

# base-image
FROM eclipse-temurin:17-jre
# build file path
WORKDIR /opt/app
# copy jar file to container
COPY --from=build-stage /app/build/libs/*.jar spring-boot-application.jar
EXPOSE 8080
# run jar file
ENTRYPOINT ["java","-jar","/opt/app/spring-boot-application.jar"]