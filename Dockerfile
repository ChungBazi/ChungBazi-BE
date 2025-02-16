FROM openjdk:17
ARG JAR_FILE=build/libs/ChungBazi_BE-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/serviceAccountKey.json /app/src/main/resources/serviceAccountKey.json
ENTRYPOINT ["java","-jar","/app.jar"]