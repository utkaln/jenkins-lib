#!/usr/bin/env groovy
import DockerImage

def call(String imageName) {
    return new DockerImage(this).deployToEC2Terraform(imageName, env.IMAGE_TAG)   
}