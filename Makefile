SHELL := /bin/bash

.PHONY: help

help:
	@echo "============================================"
	@echo "# 	   	  HELP make <target>	          #"
	@echo "============================================"
	@echo "#  deploy       	 Create cluster   	   	   "
	@echo "#  destroy      	 Destroy cluster  	   	   "
	@echo "============================================"

deploy:
	@echo "==============================================================================================================================================================="
	@echo "# 			                                        CLUSTER IS INITIATING........					                             							 #"
	@echo "==============================================================================================================================================================="
	bash -c "git clone https://github.com/harik8/terraform-eks.git"
	bash -c "cd terraform-eks && terraform init && terraform apply --auto-approve=true"
	bash -c "aws eks update-kubeconfig --name dev-eks --region us-west-2"
	@echo "==============================================================================================================================================================="
	@echo "# 			                                    STARTED K8s OBJECT CREATION........					                             							 #"
	@echo "==============================================================================================================================================================="
	bash -c "kubectl create ns jenkins"
	bash -c "kubectl create ns nginx"
	bash -c "kubectl create ns mongodb"
	bash -c "kubectl create ns todo"
	bash -c "kubectl create secret docker-registry registry-cred --docker-server=$${DOKCER_HOST} --docker-username=$${DOCKER_USER} --docker-password=$${DOCKER_PASSWORD} -n jenkins"
	@echo "==============================================================================================================================================================="
	@echo "# 			                                    STARTED HELM OBJECT CREATION........					                             						  #"
	@echo "==============================================================================================================================================================="
	bash -c "helm install mongodb stable/mongodb -n mongodb"
	bash -c "helm install jenkins charts/jenkins-operator/ -n jenkins"
	bash -c "helm install nginx stable/nginx-ingress -f charts/nginx-ingress/values-nginx.yaml -n nginx"
	bash -c "export MONGODB_ROOT_PASSWORD=$$(kubectl get secret --namespace mongodb mongodb -o jsonpath="{.data.mongodb-root-password}" | base64 --decode) &&\
			kubectl create secret generic mongo-cred --from-literal=MONGO_PASSWORD=$${MONGODB_ROOT_PASSWORD} -n todo"
	@echo "==============================================================================================================================================================="
	@echo "# 			                                    CLUSTER HAS INITIATED SUCCESSFULLY		                                                     				 #"
	@echo "==============================================================================================================================================================="

destroy:
	@echo "==============================================================================================================================================================="
	@echo "# 			                                    STARTED DESTROYING CLUSTER........		                                                     				 #"
	@echo "==============================================================================================================================================================="
	bash -c "helm uninstall mongodb -n mongodb"
	bash -c "helm uninstall jenkins -n jenkins"
	bash -c "helm uninstall nginx -n nginx"
	bash -c "cd terraform-eks && terraform destroy --auto-approve"
	@echo "==============================================================================================================================================================="
	@echo "# 			                               CLUSTER HAS BEEN DESTROYED SUCCESSFULLY		                                                     				 #"
	@echo "==============================================================================================================================================================="
