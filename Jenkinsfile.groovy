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
                sh 'mvn clean package'
            }
        }
        stage('sonar'){
            steps{
                withSonarQubeEnv('sonar'){
                    sh 'mvn sonar:sonar'
                }
            }
        }


    }
}