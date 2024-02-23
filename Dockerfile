FROM openjdk:21
CMD ["./gradlew", "clean", "build"]
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]

