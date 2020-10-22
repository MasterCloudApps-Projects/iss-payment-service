# iss-payment-service

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=iss-payment-service&metric=alert_status)](https://sonarcloud.io/dashboard?id=iss-payment-service)

We decided to implement it in an extremely simplified version of an insurance sales system to test the following aspects of microservice development:

* Project creation and development
* Access of both relational and NoSQL databases
* Blocking and non-blocking operations implementation
* Microservice to microservice communication (synchronous and asynchronous)
* Service discovery
* Running background jobs

The iss-payment-service microservice is responsible to created Policy Account, showed Policy Account list, and registered payments from the bank statement file.
This module managed policy accounts. Once the policy was created, an account was created in this service with the expected income. Payment-service also had an implementation of a scheduled process in which a CSV file with payments was imported, and payments were assigned to policy accounts. This component showed asynchronous communication between services using Kafka and had the ability to create background jobs using Spring boot. It also allowed for the database to be accessed using JPA.

This is a spring boot project that exposes different endpoints and data can be persisted in postgresql database.

In the Integration Tests, the Testcontainers Java library is used for starting postgresql in a docker container.

## How to run Application

#### Option 1:
Running application from command line using Docker, this is the cleanest way.
In order for this approach to work, of course, you need to have Docker installed in your local environment.

* From the root directory you can run the following command:<br/>
    ```docker-compose -f docker/docker-compose.yml up --build```
* Application will be running on: http://localhost:8084
* To stop it you can open other terminal in the same directory, and then run the following command:<br/>
    ```docker-compose -f docker/docker-compose.yml down```

#### Option 2:
In case you have postgresql installed locally, and you want to run from IDE

* Import the testcontainers-demo application as a Maven project to your IDE.
* Run maven clean install command to build the project.
* Then you can search for Application.java class and run it with profile 'dev'
* Application will be running on: http://localhost:8084

#### Option 3:
In case you don't have postgresql installed locally, and you want to run from IDE
   
* Import the testcontainers-demo application as a Maven project to your IDE.
* Run maven clean install command to build the project.
* From the command line run the following command to have postgresql running in a docker container:<br/>
    ```docker run -d -p 5432:5432 --name postgres_db postgres:9.6-alpine```
* Then you can search for Application.java class and run it with profile 'dev'
* Application will be running on: http://localhost:8084

## How to run the Unit Test

```mvn -B clean verify```

## How to run the Integration Test

#### Option 1:
* Import the testcontainers-demo application as a Maven project to your IDE.
* Run maven clean verify with profile 'integration-test' to build the project and start running the IT tests.<br/>
    ```mvn -B clean verify -Pintegration-test```

#### Option 2:
* Import the testcontainers-demo application as a Maven project to your IDE.
* Run maven clean install command to build the project.
* Then you can search for XXXIT.java class and run it as a junit test.

