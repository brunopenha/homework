FROM maven:3.5-jdk-8
ADD target/homework.jar homework.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "homework.jar"]
