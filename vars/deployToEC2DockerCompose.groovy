#!/usr/bin/env groovy
import DockerImage

def call(String ec2ip, String imageName) {
    return new DockerImage(this).deployToEC2DockerCompose(ec2ip, imageName, env.IMAGE_TAG)   
}