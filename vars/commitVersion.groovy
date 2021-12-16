#!/usr/bin/env groovy
import DockerImage

def call() {
    return new DockerImage(this).commitVersionNum(env.GIT_BRANCH)   
}