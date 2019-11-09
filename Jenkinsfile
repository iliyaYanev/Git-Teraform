import groovy.json.JsonSlurperClassic
import groovy.json.JsonOutput


try {
    timeout(time: 10, unit: 'MINUTES') {
      stage('Checkout') {
        node {
          cleanWs()
          checkout scm
        }
      }
        stage("Compile") {
            node {
                sh "chmod +x gradlew"
                sh "./gradlew clean build -x test --no-daemon --max-workers=3"
            }
        }

        stage("Unit Tests") {
            node {
                sh "./gradlew clean test --no-daemon --max-workers=3"
            }
        }

        stage("Deploy") {
            node {
                sh(script: "echo \"./gradlew bootRun --args='--spring.profiles.active=test' --no-daemon --max-workers=3\" | at now", returnStdout: true)
                //Allow service to come up
                sleep(time: 30, unit: 'SECONDS')
                healthCheck(2)
            }
        }

        stage("Api Tests") {
            node {

            }
        }

        stage("Build Docker Image") {
            node {
                sh "./gradlew buildDocker --no-daemon --max-workers=3"
            }
        }

        stage("Push to ECR") {
            node {
                script
                {
                    // Push the Docker image to ECR
                    docker.withRegistry('https://445669340969.dkr.ecr.eu-central-1.amazonaws.com', 'ecr:eu-central-1:awsCredentials')
                    {
                        docker.image('demo').push('latest')
                    }
                }
            }
         }
}

  currentBuild.result = 'SUCCESS'
}

catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException flowError) {
  currentBuild.result = 'ABORTED'
}
catch (err) {
  currentBuild.result = 'FAILURE'
  throw err
}
finally {
    stage("Cleanup") {
          node {
             always {
                 sh(script: "docker rmi demo:latest | true", returnStdout: true)
                 cleanWs()
             }
          }
     }
  if (currentBuild.result == 'SUCCESS') {
    currentBuild.result = 'SUCCESS'
  }
}

@NonCPS
def jsonParse(def json) {
    return new groovy.json.JsonSlurperClassic().parseText(json)
}

def healthCheck(int interval) {
    String status = healthResult()
    while(status != "UP") {
        sleep(time: interval, unit: 'SECONDS')
        status = healthResult()
    }
    println "Project Deployed."
}

def healthResult() {
    def response = sh(script:"curl -s -X GET -H 'Accept: application/json' -H 'Content-Type: application/json' http://ec2-18-195-101-109.eu-central-1.compute.amazonaws.com:9090/user/actuator/health", returnStdout: true)
    println response
    return jsonParse(response).status
}
