#   Sales system functional tests

### Reference Documentation
For further reference, please consider the following sections:

* [Official Spock documentation](http://spockframework.org/spock/docs/1.3/all_in_one.html)
* [Spock reports documentation](https://github.com/renatoathaydes/spock-reports)

### Overview

Functional tests are written using Spock and [http-builder](https://mvnrepository.com/artifact/org.codehaus.groovy.modules.http-builder) is used for API requests. [spock-reports](https://github.com/renatoathaydes/spock-reports) is used for report creation

### How to run
This sales system required following services:
* `Kafka server`
* `Zookeeper server`
* `MySQL server`
* `Eventuate CDC service`

To run those service go inside docker folder and execute following command 
``docker-compose up``

To run the functional tests go inside sales-fta folder and execute following command
* `gradlew ordersTest` to run only Orders functional tests
* `gradlew customersTest` to run only Customers functional tests
* `gradlew test` to run all functional tests

Default report location is ``build/spock-reports/index.html``