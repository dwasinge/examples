## Overview

Spring Boot REST Application Delivery Schedule Service API.

The API is used to manage delivery schedules and their roles and assignments.  Delivery Schedule resources can be create, retrieved, updated, and deleted.  Delivery Role resources can also be created, retrieved, updated, and deleted.  Delivery Assignment resources can also be created, retrieved, updated, and deleted.

This application requires a MongoDB to be running if you want to run it locally.  It also requires an instance of ActiveMQ to be running in order to submit schedules for solving or to persist updated schedules.

The application runs on port 9092 by default and the actuator runs on port 9093.

---

## How do I use it?

### Prerequisites

Java 8

Apache Maven

Mongo DB Running

If using Docker:

`
$ docker run --name local-mongo -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=mongouser -e MONGO_INITDB_ROOT_PASSWORD=mongopass -e MONGO_INITDB_DATABASE=localMongoDb -d mongo
`

ActiveMQ Running

If using Docker and have a Red Hat Subscription:

`
$ docker login registry.redhat.io
Username: ${REGISTRY-SERVICE-ACCOUNT-USERNAME}
Password: ${REGISTRY-SERVICE-ACCOUNT-PASSWORD}
Login Succeeded!

$ docker pull registry.redhat.io/amq-broker-7/amq-broker-72-openshift

$ docker run --name local-amq -p 61616:61616 -e AMQ_USER=amquser -e AMQ_PASSWORD=amqpass -d  registry.redhat.io/amq-broker-7/amq-broker-72-openshift

`


### Build the application using Maven

`mvn clean install`

### Run the application

`java -jar target/delivery-schedule-service-1.0.0-SNAPSHOT.jar`

### Alternative

`mvn clean spring-boot:run`

### Running the Tests

Unit tests will be executed during the `test` lifecycle phase and will run as part of any maven goal after `test`.

`mvn package`

### Access the application

To access the application, open the following link in your browser:

`http://localhost:9092`

Swagger UI can be accessed with the following link:

`http://localhost:9092/swagger-ui.html`

### Exposed Endpoints

All exposed endpoints can be found using the [swagger api documentation](http://localhost:9092/v2/api-docs) or the [swagger-ui page](http://localhost:9092/swagger-ui.html).
