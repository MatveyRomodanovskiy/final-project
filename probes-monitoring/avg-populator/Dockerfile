FROM eclipse-temurin:17-jdk
COPY ./target/avg-populator-0.0.1.jar app.jar
ENV CONFIG_SERVER_HOST=config-server
ENTRYPOINT [ "java", "-jar", "app.jar" ]