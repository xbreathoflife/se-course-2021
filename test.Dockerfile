FROM openjdk:11-jre-slim
WORKDIR /home/gradle/src
COPY . .
RUN ./gradlew build