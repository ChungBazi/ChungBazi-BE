FROM openjdk:17
ARG JAR_FILE=build/libs/ChungBazi_BE-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ARG SERVICE_ACCOUNT_KEY
RUN echo "$SERVICE_ACCOUNT_KEY" > /app/src/main/resources/serviceAccountKey.json
ENTRYPOINT ["java","-jar","/app.jar"]