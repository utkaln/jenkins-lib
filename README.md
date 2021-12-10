# jenkins-lib
* This repo contains groovy scripts at its most basic level to be used in Jenkins script.
* The files in this repo are used in the Jenkins file in the repo - [basic-java-app](https://github.com/utkaln/basic-java-app.git) 
* More about Jenkins file and using shared lib can be found at this [Gist](https://gist.github.com/utkaln/3f4c7439d4f95411d8db5d631a60901f#using-jenkins-shared-lib-for-scalability-and-reusuability)
* To use this shared lib follow the steps to configure in Jenkins 
    1. Jenkins > Manage Jenkins > Configure tools > Global shared pipeline
    2. Choose Git repo and enter the repo url - https://github.com/utkaln/jenkins-lib
    3. Provide a name for the shared library. For example: 'jenkins-shared-lib' . Please note this name that needs to confiured in the Jenkins file
    4. Choose version as branch - main
    5. Enter credentials for git that must be configured in Jenkins credential
