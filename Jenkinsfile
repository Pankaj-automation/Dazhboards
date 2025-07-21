pipeline {
    agent any

    tools {
        maven 'mvn'     // Make sure this matches what's configured in Jenkins
        jdk 'jdk'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/Pankaj-automation/Dazhboards', branch: 'main'
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean test'
            }

        }

        stage('Generate Allure Report') {
            steps {
                sh 'allure generate allure-results --clean -o allure-report'
                allure includeProperties: false, results: [[path: 'allure-results']]
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}
