# Kubernetes for developers
This is an example salary application consisting of companies and employees.

## Preparations
- Install docker
- Install Java 17 (and maven)
- Install [kubectl](https://kubernetes.io/docs/tasks/tools/) 
- For IntelliJ IDEA: install the Kubernetes plugin

### Setup Kubernetes connection

#### Generate a kubeconfig (password is on the slides)
```shell
wget --user workshop --ask-password --content-disposition https://workshop.lion7.dev/kubeconfig/$USER
```

#### Make a copy of your existing config
```shell
cp ~/.kube/config ~/.kube/config.bak 
```
#### Then use our downloaded config
```shell
cp $USER.kubeconfig ~/.kube/config
```

Alternatively, you can set KUBECONFIG instead of copying:
```shell
export KUBECONFIG=$PWD/$USER.kubeconfig
```

## Tasks

### Getting the Employee app on your cluster
1. Build the Employee Spring Boot app
   
   ```bash
   ./employee/docker-build.sh
   ```
   
2. Run the Employee app we just build

   ```bash
   ./employee/docker-run.sh
   ```
   
   If everything went alright, you should now have a running Spring Boot application.
   You can reach its endpoints using the requests defined in `employee/httpRequests.http`.

3. Push the build image to the image registry.
   
   In order to be able to push your image you need to login into the registry.
   Run the command below and use the username and password found in the slides: 
   
   ```bash
   docker login registry.lion7.dev
   ```
   
   Push your image: 

   ```bash
   ./employee/docker-push.sh
   ```

4. Deploy your image in your kubernetes cluster by applying the Kustomization:
   
   1. Make sure in `employee/k8s/deployment.yaml` the right image is set at the 'image' tag. 
   2. Make sure the right namespace is set in `employee/k8s/kustomization.yaml` (find your namespace by typing: `kubectl config get-contexts`). 
   3. Finally, set your username in the ingress yaml. 
   4. Now you should be ready to apply your first deployment!
      ```bash
      kubectl apply -k ./employee/k8s
      ```

5. Useful kubernetes commands to look around. 
   See [here](https://kubernetes.io/docs/reference/kubectl/quick-reference/) for the kubernetes quick reference guide. 

   ```
   kubectl get events
   kubectl get all 
   kubectl get deployments
   kubectl get services
   kubectl get pods
   kubectl logs <pod-name>
   kubectl get replicasets
   kubectl describe deployment <name>
   ```

### Configure the health checks for the employee app
As you probably know, the Spring boot actuator library provides endpoints to allow Kubernetes to check on the health of the pod (see [here](https://www.baeldung.com/spring-liveness-readiness-probes) for some more background). There are currently two endpoints available: `/actuator/health/liveness` and `/actuator/health/readiness`. 

The assignment is to configure the health and readiness probe for the employee app (see [here](https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/#define-a-liveness-http-request) for more info).
