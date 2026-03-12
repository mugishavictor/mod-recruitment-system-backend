FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/mod-recruitment-system-0.0.1-SNAPSHOT.jar mod-recruitment-system.jar
ENTRYPOINT ["java","-jar","/mod-recruitment-system.jar"]