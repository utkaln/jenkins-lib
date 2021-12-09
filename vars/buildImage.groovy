#!/usr/bin/env groovy

def call(imageName) {
    echo "Build Docker image for basic java app and push to docker hub ..."

    // use docker hub credentials from jenkins credentials settings
    withCredentials([usernamePassword(credentialsId: 'docker-hub-utkal', passwordVariable: 'PSWD', usernameVariable: 'UID')]) {
        // command to build an image with a tag (version)
        // image name must match with private repo name in docker hub
        sh "docker build -t $imageName ."

        // authenticate to docker hub
        sh "echo $PSWD | docker login -u $UID --password-stdin"

        // push image to docker hub
        sh 'docker push $imageName'
    }
}