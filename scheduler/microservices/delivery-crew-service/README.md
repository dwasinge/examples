## Overview

Spring Boot REST Application Delivery Crew Service API.

The API is used to manage delivery crew members and their availability.  Crew members can be create, retrieved, updated, and deleted.  Crew member availability resources can also be created, retrieved, updated, and deleted.

This application requires a MongoDB to be running if you want to run it locally.

The application runs on port 9090 by default and the actuator runs on port 9091.

---

## How do I use it?

### Prerequisites

Java 8

Apache Maven

Mongo DB Running

If using Docker:

```
docker run --name local-mongo -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=mongouser -e MONGO_INITDB_ROOT_PASSWORD=mongopass -e MONGO_INITDB_DATABASE=localMongoDb -d mongo
```

### Build the application using Maven

`mvn clean install`

### Run the application

`java -jar target/delivery-crew-service-1.0.0-SNAPSHOT.jar`

### Alternative

`mvn clean spring-boot:run`

### Running the Tests

Unit tests will be executed during the `test` lifecycle phase and will run as part of any maven goal after `test`.

`mvn package`

### Access the application

To access the application, open the following link in your browser:

`http://localhost:9090`

Swagger UI can be accessed with the following link:

`http://localhost:9090/swagger-ui.html`

### Exposed Endpoints

All exposed endpoints can be found using the [swagger api documentation](http://localhost:9090/v2/api-docs) or the [swagger-ui page](http://localhost:9090/swagger-ui.html).
