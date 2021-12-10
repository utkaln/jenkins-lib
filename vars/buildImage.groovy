#!/usr/bin/env groovy
import com.learn.jenkins.DockerImage

def call(String imageName) {
    return new DockerImage(this).buildDockerImage(imageName)   
}