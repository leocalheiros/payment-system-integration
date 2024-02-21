FROM maven:3.8.5-openjdk-17 as builder
LABEL authors="leonardo.calheiros"
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
COPY --from=builder ./app/target/*.jar ./application.jar
COPY --from=builder ./app/certs ./certs
EXPOSE 8080

ENV CLIENT_ID="your_client_id_here"
ENV CLIENT_SECRET="your_client_secret_here"
ENV JWT_SECRET=12345678
ENV MAIL_USER="your_mail_user_here"
ENV MAIL_PASSWORD="your_mail_password_here"
ENV MYSQL_HOST=db

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "application.jar"]