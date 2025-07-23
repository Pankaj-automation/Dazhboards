pipeline {
    agent any

    tools {
        maven 'mvn'
        jdk 'jdk'
    }

    environment {
        MAVEN_OPTS = "-Dmaven.test.failure.ignore=true"
    }

    stages {

        stage('Checkout') {
            steps {
                echo "🔄 Checking out code"
                checkout scm
            }
        }

        stage('Build and Run Tests') {
            steps {
                echo "⚙️ Building project and running tests..."
                script {
                    def result = sh(script: 'mvn clean test -e', returnStatus: true)
                    if (result != 0) {
                        echo "❌ Some tests failed, continuing to generate reports."
                    } else {
                        echo "✅ All tests passed."
                    }
                }
            }
        }

        stage('Publish Surefire Reports') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }
 stage('Console Output Dump') {
        steps {
            echo "📜 Dumping Surefire output to Jenkins console log..."
            sh 'cat target/surefire-reports/*.txt || true'
        }}
        stage('Generate Allure Report') {
            when {
                expression { fileExists('target/allure-results') }
            }
            steps {
                echo "🧪 Generating Allure report..."
                allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            }
        }
        stage('Publish HTML Report') {
            steps {
                echo "🌐 Publishing Extent/HTML Report..."
                publishHTML([
                  reportDir: 'Dazhboards/test-output',
                  reportFiles: 'dazhboardsExtentReport.html',
                  reportName: 'Dazhboards HTML Report',
                  keepAll: true,
                  alwaysLinkToLastBuild: true,
                  allowMissing: false
                ])
            }
        }
    }

    post {
        always {
            echo '🧹 Cleaning workspace...'
            cleanWs()
        }

        failure {
            echo '🚨 Build failed. Check logs and test results.'
        }

        success {
            echo '✅ Build completed successfully.'
        }
    }
}
