pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Build Docker images for the modules
                script {
                    docker.build('api_gateway_server', './api-gateway')
                    docker.build('auth_server', './auth-service')
                    docker.build('core_service_module', './core-service')
                }
            }
        }

        stage('Test') {
            steps {
                // Run your tests here
                script {
                    sh 'echo "Running tests..."'
                }
            }
        }

        stage('Compose Build') {
            steps {
                // Check if Docker Compose is running
                script {
                    if (sh(script: 'docker compose ps -q', returnStatus: true) == 0) {
                        // Docker Compose is running, so down it
                        sh 'docker compose down'
                    }
                }

                // Build Docker Compose
                script {
                    sh 'docker compose build'
                }
            }
        }

        stage('Deploy') {
            steps {
                // Deploy Docker Compose
                script {
                    sh 'docker compose up -d'
                }
            }
        }
    }
}