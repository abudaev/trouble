pipeline {

    agent {
        label 'Linux_Default'
    }
    tools {
        maven 'maven3.5.3'
    }

    stages {
        stage('scm') {
            steps {
                checkout scm
            }
        }
        stage('build') {
            steps {
                sh 'mvn -B clean package'
            }
        }
        stage('sonar') {
            steps {
                withSonarQubeEnv('sonar') {
                    sh 'mvn -B sonar:sonar'
                }
            }
        }
        stage('wait qg') {
            steps {
                timeout(1) {
                    def qg = waitForQualityGate()
                    if (qg.status != 'OK') {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                    }
                }
            }
        }


    }
}