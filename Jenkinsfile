pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        bat(script: 'mvn clean test', returnStatus: true, returnStdout: true)
      }
    }

  }
}