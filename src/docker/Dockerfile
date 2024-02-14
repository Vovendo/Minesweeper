FROM openjdk:17-oracle

ARG JAR_FILE=minesweeper-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]