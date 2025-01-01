pipeline {
    agent any
    parameters {
        booleanParam(name: 'DEPLOY', defaultValue: false, description: 'Trigger deployment manually')
    }
    stages {
        stage('Deploy') {
            when {
                expression { params.DEPLOY }
            }
            steps {
                sh 'docker stack deploy --compose-file docker-compose-swarm.yml prod_stack'
            }
        }
    }
}
