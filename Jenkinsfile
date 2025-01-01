pipeline {
    agent any

    environment {
        IMAGE_NAME = "odsoft/lmsbooks"
        IMAGE_TAG = "latest"
        DOCKER_REGISTRY = "your-docker-registry-url"
    }

    parameters {
        booleanParam(name: 'DEPLOY', defaultValue: false, description: 'Trigger deployment manually')
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
                    docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .
                '''
            }
        }

        stage('Push Docker Image') {
            steps {
                echo 'Pushing Docker image to repository...'
                sh '''
                    echo "${DOCKER_PASSWORD}" | docker login ${DOCKER_REGISTRY} -u "${DOCKER_USERNAME}" --password-stdin
                    docker push ${IMAGE_NAME}:${IMAGE_TAG}
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
