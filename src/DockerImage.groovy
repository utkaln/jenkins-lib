#!/usr/bin/env groovy

class DockerImage implements Serializable {
    // Serializable helps persists state of the script when jenkins job is paused and resumed

    //pass parameters from the root level scripts under /var folder
    def script

    //constructor to use the script element that has params from outside
    DockerImage(script) {
        this.script = script
    }

    def buildDockerImage(String imageName) {
        script.echo "Build Docker image for basic java app and push to docker hub ..."

        // use docker hub credentials from jenkins credentials settings
        script.withCredentials([script.usernamePassword(credentialsId: 'docker-hub-utkal', passwordVariable: 'PSWD', usernameVariable: 'UID')]) {
            // command to build an image with a tag (version)
            // image name must match with private repo name in docker hub
            script.sh "docker build -t $imageName ."

            // authenticate to docker hub
            script.sh "echo $script.PSWD | docker login -u $script.UID --password-stdin"

            // push image to docker hub
            script.sh "docker push $imageName"
        }
    }

    def commitVersionNum(String versionTag) {
        script.echo "Committing version tag to Git --> $versionTag"

        // use docker hub credentials from jenkins credentials settings
        script.withCredentials([script.usernamePassword(credentialsId: 'github-credentials-utkaln', passwordVariable: 'PSWD', usernameVariable: 'UID')]) {
            // set git config first for jenkins commit
            script.sh 'git config --global user.email "jenkins@example.com"'
            script.sh 'git config --global user.name "jenkins"'
            
            // print git info for informational purpose
            script.sh 'git status'
            script.sh 'git branch'
            script.sh 'git config --list'
            
            // authenticate to git repo
            script.sh "git remote set-url origin https://${UID}:${PSWD}@github.com/utkaln/basic-java-app.git"

            // commit pom.xml to git repo
            script.sh 'git add .'
            script.sh 'git commit -m "jenkins: version updated"'
            script.sh 'git push'
        }
    }

}