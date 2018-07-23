FROM openjdk:8-jdk-alpine
ADD build/libs/mutant-1.0.0.jar mutant.jar
RUN sh -c 'touch /mutant.jar'
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://mutant-mongo/mutant","-Djava.security.egd=file:/dev/./urandom","-jar","/mutant.jar"]