#!/usr/bin/env groovy
def call() {
    echo "Package stage of basic java app starting on branch $BRANCH_NAME ..."
    // clean package before 
    sh 'mvn clean package'
}