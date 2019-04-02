## Overview

Spring Boot REST Application Delivery Schedule Solver Service.

This service uses JMS to enrich delivery schedules with the required information from the delivery-crew-service and delivery-schedule-service.  It then will use the Optaplanner engine to find a solution for the given scheduling problem.

It uses the delivery-scheduler-planner KJAR to perform the solving.

Unsolved schedules are consumed from the solver-queue.  Apache Camel is used to perform the enrichment by calling each of the REST services to gather required data for solving.  Once the enrichment is complete, it is submitted to the solving engine.  As new best solutions are found, the schedule is placed on the persist-schedule-queue.  This queue is being monitored by the delivery-schedule-service for updating the schedule in its data store.

This application requires an instance of ActiveMQ to be running in order to submit schedules for solving or to persist updated schedules.  This should be the same instance configured in the delivery-schedule-service.

The application runs on port 9098 by default and the actuator runs on port 9099.

---

## How do I use it?

### Prerequisites

Java 8

Apache Maven

ActiveMQ Running (should only be run once)

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

`java -jar target/delivery-schedule-solver-service-1.0.0-SNAPSHOT.jar`

### Alternative

`mvn clean spring-boot:run`

### Running the Tests

Unit tests will be executed during the `test` lifecycle phase and will run as part of any maven goal after `test`.

`mvn package`

### Access the application

To access the application, open the following link in your browser:

`http://localhost:9098`

Swagger UI can be accessed with the following link:

`http://localhost:9098/swagger-ui.html`

### Exposed Endpoints

All exposed endpoints can be found using the [swagger api documentation](http://localhost:9098/v2/api-docs) or the [swagger-ui page](http://localhost:9098/swagger-ui.html).
