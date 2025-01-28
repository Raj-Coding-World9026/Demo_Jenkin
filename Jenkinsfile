pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'master', url: 'https://github.com/Raj-Coding-World9026/Demo_Jenkin.git'
            }
        }

        stage('Build') {
            steps {
                bat 'maven clean install'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deployment stage (to be implemented)...'
            }
        }
    }
}
