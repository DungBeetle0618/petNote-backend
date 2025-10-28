# build stage
FROM gradle:8.10.2-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean bootJar -x test

# run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV TZ=Asia/Seoul
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/app/app.jar"]