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
                    sh "pylint-fail-under --fail_under 5.0  ../**/*.py"
                }
            }
            stage('Unit Test') {
                steps {
                    files = findFiles glob:'test**manager.py'
                    for (int i=0; i< files.length; i++){
                        sh "python3 ${file.name}"
                        }
                    }
                post {
                    always {
                        junit 'test-reports/*.xml'
                    }
                }
            }
        }
    }
}
