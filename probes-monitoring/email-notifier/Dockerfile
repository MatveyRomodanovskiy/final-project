FROM eclipse-temurin:17-jdk
COPY ./target/email-notifier-0.0.1.jar app.jar
ENV CONFIG_SERVER_HOST=config-server
ENV EMAILS_PROVIDER_HOST=emails-provider
ENTRYPOINT [ "java", "-jar", "app.jar" ]