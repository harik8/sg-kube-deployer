pipelineJob('Apps/Todo/deploy') {
    displayName('deploy')

    logRotator {
        numToKeep(10)
        daysToKeep(30)
    }

    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://gitlab.com/harik8/sg-kube-deployer.git')
                    }
                    branches('*/master')
                }
            }
            scriptPath('manifests/Jenkinsfile.Deploy')
        }
    }
}