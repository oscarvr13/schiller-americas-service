pipeline {
    agent any
    stages {
        stage('Init') {
            steps {
                echo 'Hi, this is Anshul from LevelUp360'
                echo 'We are Starting the Testing  just one test'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn clean verify'
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying in Staging Area"
            }
        }
        stage('Deploy Production') {
            steps {
                echo "Deploying in Production Area"
            }
        }
    }
}