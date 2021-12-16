#!/usr/bin/env groovy
import DockerImage

def call(String ec2ip, String imageTag) {
    return new DockerImage(this).deployToEC2(ec2ip, imageTag)   
}