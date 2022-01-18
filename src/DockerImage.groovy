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

    def commitVersionNum(String branchName) {
        script.echo "Preparing to commit POM.xml version changes to SCM ..."
        // use docker hub credentials from jenkins credentials settings
        script.withCredentials([script.usernamePassword(credentialsId: 'jenkins_pat', passwordVariable: 'PSWD', usernameVariable: 'UID')]) {
            // set git config first for jenkins commit
            script.sh 'git config --global user.email "jenkins@example.com"'
            script.sh 'git config --global user.name "jenkins"'
            
            // print git info for informational purpose
            script.sh 'git branch'
            
            // authenticate to git repo
            script.sh "git remote set-url origin https://$script.PSWD@github.com/utkaln/basic-java-app.git"
            
            // commit pom.xml to git repo
            script.sh 'git add .'
            script.sh 'git commit -m "jenkins: version updated"'
            script.sh "git push origin HEAD:$branchName"
        }
    }


    def provisionEC2Terraform(){
        dir("terraform") {
            script.sh "terraform init"
            script.sh "terraform apply --auto-approve"
            // read ip address from terraform output and set to env var
            EC2_PUBLIC_IP = script.sh(
                script: "terraform output ec2_public_ip",
                returnStdout: true
            ).trim()
        }
    }

    def deployToEC2Terraform(String imageName, String imageTag) {
        // get IP of EC2 from Terraform output
        script.echo "IP addr of EC2 instance found as ${EC2_PUBLIC_IP}"
        // define docker compose command in a shell script
        def imageNameTag = imageName+imageTag 
        def dockerComposeCmd = "bash ./docker-shell.sh ${imageNameTag}"
        

        script.sshagent(['ec2-instance-ssh-key']) {
            // Copy the above shell script to EC2 first
            script.sh "scp -o StrictHostKeyChecking=no docker-shell.sh ec2-user@${EC2_PUBLIC_IP}:/home/ec2-user"

            // Copy docker-compose from git repo to EC2 instance
            script.sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ec2-user@${EC2_PUBLIC_IP}:/home/ec2-user"
            
            // Script to run docker command
            // IP subject to change with each restart of EC2
            // suppress confirmation questions with param -o
            script.sh "ssh -o StrictHostKeyChecking=no ec2-user@${EC2_PUBLIC_IP} $dockerComposeCmd"
        }
    }

}