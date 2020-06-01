from openjdk:15-alpine

copy build/libs/websocketchat-1.0.0.jar /

entrypoint ["java", "-jar", "/websocketchat-1.0.0.jar"]

