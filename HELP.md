# Getting Started
This small project integrates GitHub, Gradle, SpringBoot, AWS an Jenkins. The goal is to have each branch compiled, tested, packaged as a docker container and then uploading the resulting image to ECR.

### Backend
The BE conists of one user service, with a few REST endpoints for managing users a mysql database. To the start the project locally you can start the mysql image from the docker-compose file and run `gradlew bootRun`

### Pipeline
There is a jenkins master and 2 jenkins slave instances.
see [Jenkins](http://ec2-18-197-152-13.eu-central-1.compute.amazonaws.com:8080/)

The pipeline is declarative with a Jenkinsfile added in the repo. It consists of the following steps

+ Checkout - each push to the git repository is picked up by a webhook and the commit is checkout from git
+ Compile - the source code is compiled
+ Unit tests - unit tests are executed
+ Deploy - the build is deployed so we can verify the application boots and functional testing can be done.
+ Api tests - integration and functional tests are executed
+ Build docker image - the now compiled and tested commit is packaged in a jar, and a docker container is build see [Dockerfile](https://github.com/iliyaYanev/Git-Teraform/blob/master/src/main/docker/Dockerfile)
+ Push to ECR - the resultting image is tagged and pushed to an [ECR repo](https://eu-central-1.console.aws.amazon.com/ecr/repositories/demo/?region=eu-central-1)

### Additional Links
These additional references should also help you:

 [Getting started with EC2](https://docs.aws.amazon.com/cli/latest/userguide/cli-services-ec2-instances.html)
 
 [Getting started with RDS](https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/CHAP_GettingStarted.html)
 
 [Swagger](http://ec2-18-197-152-13.eu-central-1.compute.amazonaws.com:9091/user/swagger-ui.html#/user-resource)

