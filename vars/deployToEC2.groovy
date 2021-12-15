#!/usr/bin/env groovy
import DockerImage

def call() {
    def ec2param = params.EC2_IP
    echo "Deploying to EC2 $ec2param"
    return new DockerImage(this).deployToEC2(ec2param)   
}