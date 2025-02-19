FROM openjdk:17
# 타임존 설정을 ENV 설정보다 먼저 수행
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo "Asia/Seoul" > /etc/timezone
ARG JAR_FILE=build/libs/ChungBazi_BE-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/serviceAccountKey.json /app/src/main/resources/serviceAccountKey.json
# ENV 설정으로 JVM의 타임존을 설정
ENV TZ=Asia/Seoul
ENTRYPOINT ["java","-jar","/app.jar"]