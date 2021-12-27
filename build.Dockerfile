FROM openjdk:11-jre-slim AS build
WORKDIR /home/gradle/src
COPY . .
RUN ./gradlew assemble

FROM openjdk:11-jre-slim
COPY --from=build /home/gradle/src/build/libs/*.jar university_map_app/app.jar
ENTRYPOINT ["java", "-jar", "university_map_app/app.jar"]