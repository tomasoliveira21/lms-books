pipeline {
    agent any

    environment {
        IMAGE_TAG = "latest"
        GITHUB_USERNAME = "tomasoliveira21"
        REPOSITORY_NAME = "lmsbooks"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out the repository...'
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                echo 'Building and testing the application with Maven...'
                sh 'mvn clean install'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                sh '''
                    docker build -t ghcr.io/${GITHUB_USERNAME}/${REPOSITORY_NAME}:${IMAGE_TAG} .
                '''
            }
        }

        stage('Push Docker Image') {
            steps {
                echo 'Pushing Docker image to GitHub Container Registry...'
                sh '''
                    echo "${GITHUB_TOKEN}" | docker login ghcr.io -u "${GITHUB_USERNAME}" --password-stdin
                    docker tag ${IMAGE_NAME}:${IMAGE_TAG} ghcr.io/${GITHUB_USERNAME}/${REPOSITORY_NAME}:${IMAGE_TAG}
                    docker push ghcr.io/${GITHUB_USERNAME}/${REPOSITORY_NAME}:${IMAGE_TAG}
                '''
            }
        }

        stage('Deploy') {
            when {
                expression { params.DEPLOY }
            }
            steps {
                echo 'Deploying the application using Docker Swarm...'
                sh '''
                    docker stack deploy --compose-file docker-compose-swarm.yml prod_stack
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
