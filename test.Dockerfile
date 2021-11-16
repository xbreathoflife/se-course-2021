FROM gradle:7.2-jdk11-alpine AS build
WORKDIR /home/gradle/src
RUN git clone -b task-docker https://github.com/xbreathoflife/se-course-2021.git .
RUN gradle build --no-daemon