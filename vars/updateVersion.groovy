#!/usr/bin/env groovy
def call() {
    echo "Updating version of the build under - $BRANCH_NAME ..."
    sh 'mvn build-helper:parse-version versions:set \
    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
    versions:commit'
    def version_line_pom = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version_tag = version_line_pom[1][1]
    env.IMAGE_TAG = "$version_tag-$BUILD_NUMBER"
    echo "The new version of the Docker Image is going to be $env.IMAGE_TAG"
}