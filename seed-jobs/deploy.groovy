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
                        url('https://github.com/harik8/sg-kube-deployer.git')
                    }
                    branches('*/master')
                }
            }
            scriptPath('manifests/Jenkinsfile.Deploy')
        }
    }
}

pipelineJob('Apps/Todo-UI/deploy') {
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
                        url('https://github.com/harik8/sg-kube-deployer.git')
                    }
                    branches('*/master')
                }
            }
            scriptPath('manifests/Jenkinsfile.Deploy')
        }
    }
}
