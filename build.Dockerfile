FROM gradle:7.2-jdk11-alpine AS build
WORKDIR /home/gradle/src
RUN git clone -b task-docker https://github.com/xbreathoflife/se-course-2021.git .
RUN gradle build

FROM openjdk:11-jre-slim
COPY --from=build /home/gradle/src/build/libs/*.jar university_map_app/app.jar
ENTRYPOINT ["java", "-jar", "university_map_app/app.jar"]