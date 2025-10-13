pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = 'project-back'
        DOCKERFILE_PATH = 'Dockerfile'
        PROJECT_PATH = "back"
        REMOTE_USER = 'ubuntu'
        REMOTE_HOST = '13.124.109.82'
        REMOTE_PATH = '/home/ubuntu/devops-midterm'
    }

    stages {
         stage('Checkout & Build on Remote') {
            steps {
                sshagent(credentials: ['admin']) {
                    sh """
                    ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} << EOF
                            cd ${REMOTE_PATH}
                            cd ${PROJECT_PATH}
                            git reset --hard
                            git pull origin main
                            echo "Current commit: \$(git rev-parse --short HEAD)"
                            echo "Branch: \$(git rev-parse --abbrev-ref HEAD)"
                            echo "Commit message: \$(git log -1 --pretty=%B)"

                            docker build \
                                -t ${DOCKER_IMAGE_NAME}:latest \
                                -t ${DOCKER_IMAGE_NAME}:${BUILD_NUMBER} \
                                -f ${DOCKERFILE_PATH} \
                                .
                            EOF
                    """
                }
            }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                dir("${REMOTE_PATH}") {
                    sh """
                    ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} << EOF
                            -t ${DOCKER_IMAGE_NAME}:latest \
                            -t ${DOCKER_IMAGE_NAME}:${BUILD_NUMBER} \
                            -f ${DOCKERFILE_PATH} \
                            .
                            EOF
                    """
                    sh "docker images | grep ${DOCKER_IMAGE_NAME}"
                }
            }
        }
    }

    post {
        success {
            echo "Deployment completed successfully for build #${BUILD_NUMBER}"
        }
        failure {
            echo "Pipeline failed. Check logs."
        }
    }
}
