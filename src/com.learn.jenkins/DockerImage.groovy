#!/usr/bin/env groovy
package com.learn.jenkins

Class DockerImage implements Serializable {
// Serializable helps persists state of the script when jenkins job is paused and resumed

//pass parameters from the root level scripts under /var folder
def script

//constructor to use the script element that has params from outside
DockerImage(script) {
    this.script = script
}

def buildDockerImage(String imageName) {
    script.echo "Build Docker image for basic java app and push to docker hub ..."

    // use docker hub credentials from jenkins credentials settings
    script.withCredentials([script.usernamePassword(credentialsId: 'docker-hub-utkal', passwordVariable: 'PSWD', usernameVariable: 'UID')]) {
        // command to build an image with a tag (version)
        // image name must match with private repo name in docker hub
        script.sh "docker build -t $imageName ."

        // authenticate to docker hub
        script.sh "echo $script.PSWD | docker login -u $script.UID --password-stdin"

        // push image to docker hub
        script.sh "docker push $imageName"
    }
}

}