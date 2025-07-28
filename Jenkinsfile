pipeline {
    agent any

    tools {
        maven 'mvn'
        jdk 'jdk'
        allure 'Allure'
    }

    environment {
        MAVEN_OPTS = "-Dmaven.test.failure.ignore=false"
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
                sh 'mvn clean test -e'
            }
        }

        stage('Publish Surefire Reports') {
            steps {
                echo "📝 Publishing JUnit XML reports"
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

        stage('Prepare Extent Report for Jenkins') {
            steps {
                echo "🛠️ Renaming latest Extent report to a fixed name..."
                sh '''
                    mkdir -p test-output
                    latest_report=$(ls -t test-output/ExtentReport-*.html 2>/dev/null | head -n 1)
                    if [ -f "$latest_report" ]; then
                        cp "$latest_report" test-output/dazhboardsExtentReport.html
                        echo "✅ Copied $latest_report to test-output/dazhboardsExtentReport.html"
                    else
                        echo "⚠️ No Extent report found to copy."
                    fi
                '''
            }
        }

        stage('Publish HTML Report') {
            when {
                expression { fileExists("${EXTENT_REPORT_DIR}/${EXTENT_REPORT_FILE}") }
            }
            steps {
                echo "🌐 Publishing Extent HTML Report..."
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
        success {
            echo '✅ All tests passed. Build successful.'
        }

        failure {
            echo '🚨 Build failed due to test failures or errors.'
        }

        always {
            echo '🧹 Cleaning workspace...'
            cleanWs()
        }
    }
}
