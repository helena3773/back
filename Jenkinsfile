pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = 'project-back'
        DOCKERFILE_PATH = './Dockerfile'
        BUILD_CONTEXT = '.'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from GitHub...'
                checkout scm

                sh '''
                    echo "Current commit: $(git rev-parse --short HEAD)"
                    echo "Branch: $(git rev-parse --abbrev-ref HEAD)"
                    echo "Commit message: $(git log -1 --pretty=%B)"
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                script {
                    dir("${BUILD_CONTEXT}") {
                        sh """
                            docker build \
                                -t ${DOCKER_IMAGE_NAME}:latest \
                                -t ${DOCKER_IMAGE_NAME}:${BUILD_NUMBER} \
                                -t ${DOCKER_IMAGE_NAME}:$(git rev-parse --short HEAD) \
                                -f ${DOCKERFILE_PATH} \
                                .
                        """
                    }
                }
                sh "docker images | grep ${DOCKER_IMAGE_NAME}"
            }
        }

    }

    post {
        success {
            echo "Docker image build & push completed for build #${BUILD_NUMBER}"
        }
        failure {
            echo 'Pipeline failed. Check logs.'
        }
    }
}
