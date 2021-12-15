#!/usr/bin/env groovy
import DockerImage

def call(String ec2ip) {
    echo "Deploying to EC2 $ec2ip"
    return new DockerImage(this).deployToEC2(ec2ip)   
}