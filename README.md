# sg-kube-deployer

export  MONGO_USERNAME="root"    
export  MONGO_DATABASE="todo"
export  MONGO_COLLECTION="tasks"
export  MONGO_HOST="127.0.0.1"
export  MONGO_PORT="27017"
export  TODO_SERVICE_IP="0.0.0.0"
export  TODO_SERVICE_PORT="8080"

d81217358f812d70384e81e76de882a3697f6d22

{
        "auths": {
                "https://index.docker.io/v1/": {
                        "auth": "aGFyaWs4OmhhcmlLQGRvYzg="
                }
        },
        "HttpHeaders": {
                "User-Agent": "Docker-Client/19.03.6 (linux)"
        }
}

export DOCKER_USERNAME=harik8
export DOCKER_PASSWORD=hariK@doc8
export GIT_USERNAME=harik8
export GIT_PASSWORD=d81217358f812d70384e81e76de882a3697f6d22
kubectl create secret docker-registry registry-cred --docker-server=https://index.docker.io/v1/ --docker-username=harik8 --docker-password=hariK@doc8 -n jenkins
kubectl --namespace jenkins get secret jenkins-operator-credentials-todo -o 'jsonpath={.data.password}' | base64 --decode


#!/usr/bin/env groovy

String image    = env.JOB_NAME.split('/')[1].toLowerCase()
String registry = "harik8/$image"
String tag      = env.VERSION

pipeline {
    agent {
    kubernetes {
      	cloud 'kubernetes'
      	label 'todo'
      	defaultContainer 'jnlp'
      	yamlFile 'pod-deploy.yaml'
      }
    }

    stages {
        stage("deploy") {
            steps {
                container('kubeclt') {
			sh "kubectl get po --all-namespaces"
                     }
                }
            }
        }
    }
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: todo
  name: todo
spec:
  replicas: 1
  selector:
    matchLabels:
      app: todo
  strategy:
    type: RollingUpdate
  template:
    metadata:
      labels:
        app: todo
    spec:
      containers:
      - image: harikarthi/todo:latest
        name: todo
        resources:
          requests:
            memory: 256Mi
            cpu: 100m
          limits:
            memory: 512Mi
            cpu: 200m
        envFrom:
        - configMapRef:
          name: todo-conf
        - secretRef:
          name: mongo-cred
        port:
        - containerPort: 8080
status: {}