#   Sales system

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.1.8.RELEASE/gradle-plugin/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.1.8.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.1.8.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Microservices](https://microservices.io/)
* [Saga pattern](https://microservices.io/patterns/data/saga.html)
* [Eventuate framework](http://eventuate.io/)

### Application overview

This application  demonstrates how to maintain data consistency in an Java/JDBC/JPA-based  microservice architecture using http://microservices.io/patterns/data/saga.html[sagas].

The application consists of two services:

* `Order Service` - creates orders
* `Customer Service` - manages customers

Both services are implemented using Spring Boot, JPA and the https://github.com/eventuate-tram/eventuate-tram-sagas[Eventuate Tram Saga framework]

The `Order Service` uses a saga to enforce the customer's credit limit when creating orders.

### Architecture
// TODO: Add diagram here

### How to run
This sales system required following services:
* `Kafka server`
* `Zookeeper server`
* `MySQL server`
* `Eventuate CDC service`

To run those service go inside docker folder and execute following command 
``docker-compose up`` 