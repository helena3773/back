pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = 'project-back'
        DOCKERFILE_PATH = 'Dockerfile'        // Jenkins workspace 기준
        PROJECT_PATH = "/home/ubuntu/devops-midterm/back"   // 빌드할 폴더
    }

    stages {
        stage('Checkout & Pull Latest') {
            steps {
                echo "Moving to project folder and updating code..."
                dir("${PROJECT_PATH}") {
                    // Git pull로 최신 코드 가져오기
                    sh '''
                        git reset --hard
                        git pull origin main
                        echo "Current commit: $(git rev-parse --short HEAD)"
                        echo "Branch: $(git rev-parse --abbrev-ref HEAD)"
                        echo "Commit message: $(git log -1 --pretty=%B)"
                    '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                dir("${PROJECT_PATH}") {
                    sh """
                        docker build \
                            -t ${DOCKER_IMAGE_NAME}:latest \
                            -t ${DOCKER_IMAGE_NAME}:${BUILD_NUMBER} \
                            -f ${DOCKERFILE_PATH} \
                            .
                    """
                    sh "docker images | grep ${DOCKER_IMAGE_NAME}"
                }
            }
        }

        stage('Docker Compose Up') {
            steps {
                echo 'Starting services with Docker Compose...'
                dir("${PROJECT_PATH}") {
                    sh """
                        docker-compose pull
                        docker-compose up -d
                    """
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
