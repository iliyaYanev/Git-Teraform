try {
  stage('Checkout') {
    node {
      cleanWs()
      checkout scm
    }
  }
    stage("Build") {
        node {
            sh "chmod +x gradlew"
            sh "./gradlew clean build -x test --max-workers=3"
        }
    }
    stage("Unit tests") {
            node {
                sh "chmod +x gradlew"
                sh "./gradlew test --max-workers=3"
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
