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
          stage('Clean Empty Allure Files') {
                    steps {
                        echo "🧼 Cleaning empty JSONs in Allure results..."
                        sh "find ${allure-results} -name '*.json' -size 0 -delete || true"
                    }
                }
     stage('Generate Allure Report') {
         when {
             expression { fileExists('allure-results') }
         }
         steps {
             echo "🧪 Generating Allure report..."
             allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
         }
     }

        stage('Publish HTML Report') {
         when {
                        expression { fileExists("${test-output}/${dazhboardsExtentReport}") }
                    }
            steps {
                echo "🌐 Publishing Extent/HTML Report..."
                publishHTML([
                    allowMissing: false,
                                        alwaysLinkToLastBuild: true,
                                        keepAll: true,
                                        reportDir: 'test-output',
                                        reportFiles: 'dazhboardsExtentReport',
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
