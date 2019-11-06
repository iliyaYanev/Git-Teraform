try {
  stage('Checkout') {
    node {
      cleanWs()
      checkout scm
    }
  }
  currentBuild.result = 'SUCCESS'
}
stage("Build") {
    node {
        sh "gradle build -x test"
    }
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
