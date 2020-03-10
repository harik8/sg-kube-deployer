#!/usr/bin/env groovy

folder('Docker') {
    displayName('Docker')
    description('Docker Builds')
}

folder('Docker/Todo') {
    displayName('Todo')
    description('Todo Build')
}

folder('Docker/Todo-UI') {
    displayName('Todo-UI')
    description('Todo-UI Build')
}

multibranchPipelineJob('Docker/Todo/todo') {
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

multibranchPipelineJob('Docker/Todo-UI/todo-ui') {
    branchSources {
        git {
            id('00002')
            remote('https://gitlab.com/harik8/sg-kube-deployer.git')
        }
    }
    factory {
        workflowBranchProjectFactory {
            scriptPath('todo-ui/Jenkinsfile')
        }
    }
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(50)
        }
    }
}