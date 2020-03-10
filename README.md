# SG Kube Deployer

SG Kube Deployer is an automated module to deploy applications.
Platform is running top of AWS EKS and it uses Jenkins for CI/CD.

# Prerequisite

- terraform
- awscli
- kubectl

# Execution

`export DOCKER_HOST=<DOCKER_HOST>`
`export DOCKER_USER=<DOCKER_USER>`
`export DOCKER_PASSWORD=<DOCKER_PASSWORD>`

`git clone https://github.com/harik8/sg-kube-deployer.git`

`make deploy`

It deploys cluster and cluster resources.

`make destroy`
 
It destroys cluster and cluster resources.

# Addition Modules

- [terraform-eks](https://github.com/harik8/terraform-eks.git)
- [todo-list-service](https://github.com/harik8/todo-list-service.git)

# Services

Cluster runs todo-list-service. But the API service is not exposed to outter world.
You can access the GUI from URL given below.

# Service URLs

- [Jenkins] (https://jenkins.hari-karthigasu.dev/)
- [Todo GUI] (https://todo.hari-karthigasu.dev/ui)

# Known Issues

- Jenkins jobs would fail at first run, saying "Authentication Error"
    - Work Around : Copy and paste relevant URL from git to job configuration and re-run the job.

# Recommadations

- Deploy [external-dns](https://github.com/kubernetes-sigs/external-dns.git) to handle R53 entries once an ingress created.
- Deploy [cluster-autoscaler](https://github.com/kubernetes/autoscaler.git) to handle cluster's scalability.