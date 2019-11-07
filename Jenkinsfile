try {
  stage('Checkout') {
    node {
      cleanWs()
      checkout scm
    }
  }
    stage("Compile") {
    timeout(time: 5, unit: 'MINUTES')
        node {
            sh "chmod +x gradlew"
            sh "./gradlew clean build -x test --max-workers=3"
        }
    }

    stage("Unit Tests") {
        timeout(time: 5, unit: 'MINUTES')
            node {
                sh "chmod +x gradlew"
                sh "./gradlew clean test --max-workers=3"
            }
        }

    stage("Build Docker Image") {
        node {
            
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
  if (currentBuild.result == 'SUCCESS') {
    currentBuild.result = 'SUCCESS'
  }
}
