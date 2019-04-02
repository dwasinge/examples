## Overview

Java JAR containing all required domain classes required by the scheduler application.

This JAR can be included as a dependency in the other micro service applications.

---

## How do I use it?

### Prerequisites

Java 8

Apache Maven

### Build the JAR using Maven

`mvn clean install`

### Include in Other Applications

`
<dependency>
  <groupId>examples.scheduler.common</groupId>
  <artifactId>delivery-domain</artifactId>
  <version>0.0.1</version>
</dependency>
`