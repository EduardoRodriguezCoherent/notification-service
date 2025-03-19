# Notification Service

Microservice part of Gym Management System, this service is in charge of consuming a kafka topic (**membership-topic**),
it receives messages and processes the events using a reactive flux (**WebFlux**). This services sends emails to
customers whenever and has been and update to their memberships.

## Running Locally

### **Prerequisites**
To run the application locally, ensure you have the following:

- **MongoDB** – Installed and running.
- **Java 17+** – Ensure you have Java Development Kit (JDK) installed.
- **Maven** – To build and run the project.
- **Docker** – If you prefer running MongoDB in a container. But it is necessary for running Kafka.
- **Discovery Service** – This microservice relies on a service discovery component. Ensure it is running before starting this service.
- **Kafka** – This service runs on a docker container, so before calling this service, and instance of Kafka must be up
  and running. The logic for sending notifications is handled automatically, so there is no need for us to interact
  with this service directly, since it consumes the topics, saves the notification and send  and managing memberships is handled by the **orchestrator-service**, so you must not call directly
  this service or any other.

## Setup Instructions

### 1. Start the Discovery Service
Before running this service, you need to start the Discovery Service to enable service registration and discovery.

```sh
cd path/to/discovery-service
mvn spring-boot:run
```

### 2. Configure MongoDB Database
If Mongo is installed locally, create a database:

```nosql
use gym_notification;
```

The previous command will create a new mongo database, there is no such a command like "create".

### 3. Configure Application Properties
Make sure the application’s database connection is correctly configured. Open src/main/resources/application.properties (or application.yml) and ensure the following configuration:

```properties
# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/gym_notification
spring.data.mongodb.database=gym_notification
spring.data.mongodb.reactive.repositories.enabled=true
```

If you're using Docker for MongoDB, ensure the host is localhost or the IP address of your Docker container.

### 4. Configure Email Properties
```properties
# Email Configuration (Using Gmail SMTP)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${SMTP_USERNAME}
spring.mail.password=${SMTP_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smt.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
```
#### Important ####

The **SMTP_USERNAME** and **SMTP_PASSWORD** environment variables must be configured before running the application.
These values can be securely stored in:

- GitHub Secrets for secure CI/CD integration.
- Environment Variables in your development environment, such as IntelliJ IDEA or your operating system.

DO NOT use your real email password, instead, go to https://myaccount.google.com/security and create an application 
password.

### 5 . Configure Kafka Properties
Make sure kafka is correctly configured, change ports if necessary, also, since this is the consumer service that reads
messages from kafka topic, we must include serializer and deserializer in this configuration. As well as, indicate
the function that processes the messages (*processMembershipEvent*, in this case). 

```properties
# Kafka configuration
# Define the function (consumer)
spring.cloud.stream.function.definition=processMembershipEvent

# Connect to Kafka inside Docker
spring.cloud.stream.kafka.binder.brokers=localhost:9092
# Match producer topic
spring.cloud.stream.bindings.processMembershipEvent-in-0.destination=membership-topic
spring.cloud.stream.bindings.processMembershipEvent-in-0.group=membership-group
spring.cloud.stream.bindings.processMembershipEvent-in-0.content-type=application/json

# Ensure Kafka Consumer Uses JSON Deserializer
spring.cloud.stream.kafka.bindings.processMembershipEvent-in-0.consumer.configuration.value.deserializer=org.springframework.kafka.support.serializer.JsonDeserializer

# Java class to deserialize into
spring.cloud.stream.kafka.bindings.processMembershipEvent-in-0.consumer.configuration.spring.json.value.default.type=com.gym_management.notification_service.dto.MembershipEventDto

# Ensure the key is deserialized correctly
spring.cloud.stream.kafka.bindings.processMembershipEvent-in-0.consumer.configuration.key.deserializer=org.apache.kafka.common.serialization.StringDeserializer

spring.cloud.stream.kafka.bindings.processMembershipEvent-in-0.consumer.configuration.spring.json.trusted.packages=*
```

### 6. Start kafka with Docker
Before starting notification service, it is mandatory to have a kafka instance, to do that, a **docker-compose.yml**
file is included in this service. You need to follow the next steps:

1. **Open Docker desktop on your machine.**
2. **Using terminal, run the following command.**
  ```bash
  cd path/to/notification-service
  ```
  And then
  ```bash
  docker compose up -d
  ```
  This should set the container and start kafka.

### 7. Start the Notification Service
Once the Discovery Service is up and running, and also you have the kafka instance and topic configured; Navigate to 
the directory of the notification microservice:

```bash
cd path/to/notification-service
```
Run the following command to build and start the application:

```bash
mvn spring-boot:run
```
This will start the Notification Service, which should now be available on http://localhost:8085 (or any configured port).

### **Troubleshooting**
1. Ensure that the Discovery Service is running before starting the Notification service.
2. Check that MongoDB is correctly configured and the **gym_notification** database exists.
3. If running MongoDB via Docker, verify that the container is up and accessible.
4. Check that kafka container is running correctly.