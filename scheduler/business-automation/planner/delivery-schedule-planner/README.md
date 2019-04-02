## Overview

Optaplanner knowledge JAR (KJAR) containing all artifacts required for solving the scheduler planning problem.

This KJAR contains the following:

* src/main/resources/META-INF/kmodule.xml - defines this as KJAR for Optaplanner
* src/main/resources/examples/scheduler/business/automation/delivery/planner
** deliveryScheduleSolver.xml - Optaplanner Solver Configuration
** deliverySchedule.drl - Contains the Rules used to determine a solution

This JAR can be loaded into a KIE server instance or used on the classpath to run in an embedded Optaplanner instance.

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
  <groupId>examples.scheduler.business.automation.delivery.planner</groupId>
  <artifactId>delivery-schedule-planner</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
`