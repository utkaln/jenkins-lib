#!/usr/bin/env groovy
import DockerImage

def call(String versionName) {
    return new DockerImage(this).commitVersionNum(versionName)   
}