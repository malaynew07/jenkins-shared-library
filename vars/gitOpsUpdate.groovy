def call(String infraRepoUrl, String githubCredsId, String buildId) {
    withCredentials([usernamePassword(credentialsId: githubCredsId, passwordVariable: 'GIT_PASS', usernameVariable: 'GIT_USER')]) {
        sh """
            git config --global user.email "jenkins@example.com"
            git config --global user.name "Jenkins Library"
            rm -rf infra-repo
            git clone https://${GIT_USER}:${GIT_PASS}@${infraRepoUrl} infra-repo
            cd infra-repo
            sed -i 's/tag: .*/tag: "${buildId}"/g' my-voting-app/values.yaml
            git add .
            git commit -m "GitOps: Update tag to ${buildId}"
            git push origin main
        """
    }
}
