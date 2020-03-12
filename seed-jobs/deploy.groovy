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
                        url('https://gitlab.com/harik8/todo-list-service.git')
                    }
                    branches('*/master')
                }
            }
            scriptPath('Jenkinsfile.Deploy')
        }
    }
}