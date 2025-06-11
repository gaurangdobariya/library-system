FROM openjdk:21-slim

WORKDIR /app

COPY target/library-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS -jar library-0.0.1-SNAPSHOT.jar
