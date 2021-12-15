#!/usr/bin/env groovy
import DockerImage

def call() {
    echo "Deploying to EC2"
    return new DockerImage(this).deployToEC2($params.EC2_IP)   
}