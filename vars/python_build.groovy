def call(){
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    sh 'pip install -r requirements.txt'
                }
            }
            stage('Lint test') {
                steps {
                    pylint-fail-under --fail_under 5.0  ../**/*.py
                }
            }
            stage('Unit Test') {
                steps {
                    sh 'python3 test_point_manager.py'
                }
                post {
                    always {
                        junit 'test-reports/*.xml'
                    }
                }
            }
            stage('Integration Test') {
                steps {
                    sh 'python3 test_points_api.py'
                }
                post {
                    always {
                        junit 'api-test-reports/*.xml'
                    }
                }
            }
        }
    }
}