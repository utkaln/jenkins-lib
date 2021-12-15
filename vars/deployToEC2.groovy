#!/usr/bin/env groovy
import DockerImage

def call(String ipEC2) {
    echo "Deploying  to IP --> $ipEC2"
    return new DockerImage(this).deployToEC2(ipEC2)   
}