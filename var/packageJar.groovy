#!/usr/bin/env groovy
def call() {
    echo "Package stage of basic java app starting ..."
    sh 'mvn package'
}