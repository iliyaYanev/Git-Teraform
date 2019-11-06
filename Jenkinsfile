try {
  stage('Checkout') {
    node {
      cleanWs()
      checkout scm
    }
  }
    stage("Build") {
        node {
            sh "./gradlew clean build -x test"
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
