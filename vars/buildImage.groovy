#!/usr/bin/env groovy
import DockerImage

def call(String imageName) {
    return new DockerImage(this).buildDockerImage(imageName)   
}