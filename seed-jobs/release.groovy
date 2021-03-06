pipelineJob('Apps/Todo/release') {
    displayName('release')

    logRotator {
        numToKeep(10)
        daysToKeep(30)
    }

    parameters {
        stringParam('VERSION', 'latest', 'Docker Image Version. Eg: v1.0.0')
    }

    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://github.com/harik8/todo-list-service.git')
                    }
                    branches('*/master')
                }
            }
            scriptPath('Jenkinsfile.Release')
        }
    }
}

pipelineJob('Apps/Todo-UI/release') {
    displayName('release')

    logRotator {
        numToKeep(10)
        daysToKeep(30)
    }

    parameters {
        stringParam('VERSION', 'latest', 'Docker Image Version. Eg: v1.0.0')
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
            scriptPath('docker/todo-ui/Jenkinsfile.Release')
        }
    }
}
