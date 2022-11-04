FROM openjdk:8-jre

WORKDIR /app

COPY build/libs/*.jar gift.jar

ENTRYPOINT ["java", "-jar", "gift.jar"]
