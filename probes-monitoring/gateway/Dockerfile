FROM eclipse-temurin:17-jdk
COPY ./target/gateway-0.0.1.jar app.jar
ENV CONFIG_SERVER_HOST=config-server
ENV ACCOUNT_PROVIDER_HOST=account-provider
ENV RANGE_PROVIDER_HOST=sensor-range-provider
ENV EMAILS_PROVIDER_HOST=sensor-emails-provider
ENTRYPOINT [ "java", "-jar", "app.jar" ]