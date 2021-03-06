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
                    sh 'mvn -B sonar:sonar -Dsonar.login=d40041535f4bdbb44bace3803171331b06d78315'
                }
            }
        }
        stage('wait qg') {
            steps {
                waitForQualityGate abortPipeline: true,  authToken: 'd40041535f4bdbb44bace3803171331b06d78315'
            }
        }
    }
}