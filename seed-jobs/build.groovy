#!/usr/bin/env groovy

folder('Apps') {
    displayName('Apps')
    description('Docker Builds')
}

folder('Apps/Todo') {
    displayName('Todo')
    description('Todo Build')
}

folder('Apps/Todo-UI') {
    displayName('Todo-UI')
    description('Todo-UI Build')
}

multibranchPipelineJob('Apps/Todo/todo') {
    branchSources {
        git {
            id('00001')
            remote('https://gitlab.com/harik8/todo-list-service.git')
        }
    }
    factory {
        workflowBranchProjectFactory {
            scriptPath('Jenkinsfile')
        }
    }
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(50)
        }
    }
}

multibranchPipelineJob('Apps/Todo-UI/todo-ui') {
    branchSources {
        git {
            id('00002')
            remote('https://gitlab.com/harik8/sg-kube-deployer.git')
        }
    }
    factory {
        workflowBranchProjectFactory {
            scriptPath('docker/todo-ui/Jenkinsfile')
        }
    }
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(50)
        }
    }
}