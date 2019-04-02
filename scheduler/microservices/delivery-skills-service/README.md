## Overview

Spring Boot REST Application Delivery Skills Service API.

The API is used to manage delivery skills.  Delivery skills can be create, retrieved, updated, and deleted.

This application requires a PostgreSQL instance to be running if you want to run it locally.

The application runs on port 9094 by default and the actuator runs on port 9095.

---

## How do I use it?

### Prerequisites

Java 8

Apache Maven

PostgreSQL DB Running

If using Docker:

```
docker run --name local-postgres -p 5432:5432 -e POSTGRES_USER=pguser -e POSTGRES_PASSWORD=pgpass -e POSTGRES_DB=localPostgresDb -d postgres
```

### Build the application using Maven

`mvn clean install`

### Run the application

`java -jar target/delivery-skills-service-1.0.0-SNAPSHOT.jar`

### Alternative

`mvn clean spring-boot:run`

### Running the Tests

Unit tests will be executed during the `test` lifecycle phase and will run as part of any maven goal after `test`.

`mvn package`

### Access the application

To access the application, open the following link in your browser:

`http://localhost:9094`

Swagger UI can be accessed with the following link:

`http://localhost:9094/swagger-ui.html`

### Exposed Endpoints

All exposed endpoints can be found using the [swagger api documentation](http://localhost:9094/v2/api-docs) or the [swagger-ui page](http://localhost:9094/swagger-ui.html).
