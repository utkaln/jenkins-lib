#!/usr/bin/env groovy
def call() {
    echo "Package stage of basic java app starting on branch $BRANCH_NAME ..."
    sh 'mvn package'
}