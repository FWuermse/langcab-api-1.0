pipeline {
    agent any
    stages {
        stage('Undeploy API') {
            steps {
                sh 'sudo docker stop langcab_db && sudo docker rm langcab_db'
                sh 'sudo docker stop langcab_api && sudo docker rm langcab_api'
                sh 'sudo docker rmi langcab_api'
            }
        }
        stage('Build') {
            steps {
                sh 'sudo docker build -t langcab_api .'
            }
        }
        stage('Deploy') {
            steps {
               sh 'sudo docker-compose up -d'
            }
        }
    }
}