#!/usr/bin/env groovy
import DockerImage

def call(String imageName) {
    echo "Ready to build image with version --> $env.IMAGE_TAG"
    def completeImageVersion = imageName+$env.IMAGE_TAG
    return new DockerImage(this).buildDockerImage(completeImageVersion)   
}