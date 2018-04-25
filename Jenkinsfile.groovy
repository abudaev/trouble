pipeline {

    agent {
        label 'docker'
    }
    tools {
        manve ''
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