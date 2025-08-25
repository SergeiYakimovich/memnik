#Build stage

FROM gradle:jdk19 AS BUILD
WORKDIR /usr/app/
COPY . .
RUN gradle clean bootJar -x test

# Package stage

FROM openjdk:19-jdk
ENV JAR_NAME=memnik-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME .
EXPOSE 8181
ENTRYPOINT exec java -jar $APP_HOME/build/libs/$JAR_NAME
