#!/usr/bin/env groovy
import DockerImage

def call(String imageName) {
    def completeImageVersion = imageName+env.IMAGE_TAG
    echo "Ready to build image with version --> $completeImageVersion"
    return new DockerImage(this).buildDockerImage(completeImageVersion)   
}