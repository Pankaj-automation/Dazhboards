pipeline {
    agent any

    tools {
        maven 'mvn'
        jdk 'jdk'
        allure 'Allure'
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

        stage('Prepare Extent Report for Jenkins') {
            steps {
                echo "🛠️ Renaming timestamped Extent report to a fixed name..."
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

            script {
                def testResult = currentBuild.rawBuild.getAction(hudson.tasks.junit.TestResultAction)
                if (testResult) {
                    def failed = testResult.getFailCount()
                    def skipped = testResult.getSkipCount()
                    def passed = testResult.getTotalCount() - failed - skipped

                    echo "Test Results => Passed: ${passed}, Failed: ${failed}, Skipped: ${skipped}"

                    if (failed == 0 && skipped == 0) {
                        echo '✅ All tests passed. Marking build as SUCCESS.'
                        currentBuild.result = 'SUCCESS'
                    } else if (failed > 0) {
                        echo '❌ Some tests failed. Build marked as UNSTABLE or FAILED.'
                    } else if (skipped > 0) {
                        echo '⚠️ Some tests were skipped. Marking build as UNSTABLE.'
                    }
                } else {
                    echo 'ℹ️ No test results found.'
                }
            }
        }

        failure {
            echo '🚨 Build failed. Check logs and test results.'
        }
    }

