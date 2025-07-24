pipeline {
    agent any

    tools {
        maven 'mvn'
        jdk 'jdk'
        allure 'allure'
    }

    environment {
        MAVEN_OPTS = "-Dmaven.test.failure.ignore=true"
        ALLURE_RESULTS = "allure-results"
        EXTENT_REPORT_DIR = "test-output"
        EXTENT_REPORT_FILE = "dazhboardsExtentReport.html"
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
            }
        }

        stage('Clean Empty Allure Files') {
            steps {
                echo "🧼 Cleaning empty JSONs in Allure results..."
                sh "find ${ALLURE_RESULTS} -name '*.json' -size 0 -delete || true"
            }
        }

        stage('Generate Allure Report') {
            when {
                expression { fileExists("${ALLURE_RESULTS}") }
            }
            steps {
                echo "🧪 Generating Allure report..."
                allure includeProperties: false, jdk: '', results: [[path: "${ALLURE_RESULTS}"]]
            }
        }

        stage('Publish HTML Report') {
            when {
                expression { fileExists("${EXTENT_REPORT_DIR}/${EXTENT_REPORT_FILE}") }
            }
            steps {
                echo "🌐 Publishing Extent/HTML Report..."
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: "${EXTENT_REPORT_DIR}",
                    reportFiles: "${EXTENT_REPORT_FILE}",
                    reportName: 'Dazhboards Extent Report'
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
