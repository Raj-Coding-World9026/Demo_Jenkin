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
                sh 'maven clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deployment stage (to be implemented)...'
            }
        }
    }
}
