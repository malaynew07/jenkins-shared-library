def call(String imageName, String dockerHubUser, String credentialsId) {
    sh "docker build -t ${dockerHubUser}/${imageName}:${env.BUILD_ID} ."
    withCredentials([usernamePassword(credentialsId: credentialsId, passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "echo \$PASS | docker login -u \$USER --password-stdin"
        sh "docker push ${dockerHubUser}/${imageName}:${env.BUILD_ID}"
    }
}
