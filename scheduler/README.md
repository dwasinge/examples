# Scheduling Application

## Overview

This application is a sample application that creates the back end services required to schedule people for delivery assignments.  The application uses Optaplanner as its planning engine to map people to delivery assignments.

## Domain Summary

Crew Members - Employees that can be assigned to a delivery
Crew Member Availability - Employees can specify times that they are unavailable for deliveries
Delivery Role - Required Role/Skill for a specific delivery
Delivery Assignment - Maps the a crew member with a given skillset to a delivery role for a specific delivery.
Delivery Schedule - Wrapper planning solution submitted to the planning engine for solving.

## Documentation

### Project Summary

- [Delivery Domain](common/delivery-domain/README.md)
  - Project contains all common domain classes
- [Delivery Schedule Planner](business-automation/planner/delivery-schedule-planner/README.md)
  - Project contains all business automation artifacts for the rules/planning engine
- [Delivery Crew Service](microservices/delivery-crew-service/README.md)
  - Project exposes a REST API to manage all crew and their availability
- [Delivery Schedule Service](microservices/delivery-schedule-service/README.md)
  - Project exposes a REST API to manage all deliveries and mappings
- [Delivery Schedule Solver Service](microservices/delivery-schedule-solver-service/README.md)
  - JMS application that asyncronously solves planning problems and places solution on queue for processing

### Installation

- [Install on OpenShift Cluster](docs/openshift-installation.md)
