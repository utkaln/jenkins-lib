#!/usr/bin/env groovy
import DockerImage

def call(String ec2ip, String imageName) {
    return new DockerImage(this).deployToEKS(clusterName, imageName, env.IMAGE_TAG)   
}