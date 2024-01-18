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
   2. Make sure the right namespace is set in `employee/k8s/kustomization.yaml` (find your namespace by typing: `kubectl config get contexts`). 
   3. Finally, set your username in the ingress yaml. 
   4. Set the secret in the `employee/k8s/secret.yaml`. 
      ```bash
      kubectl apply -k ./employee/k8s
      ```

5. Useful kubernetes commands to look around. 
   See [here](https://kubernetes.io/docs/reference/kubectl/quick-reference/) for the kubernetes quick reference guide. 

   ```
   kubectl get deployments
   kubectl get services
   kubectl get pods
   kubectl logs <pod-name>
   kubectl get replicasets
   kubectl describe deployment <name>
   ```

### Configure the health checks for the employee app
As you probably know, the Spring boot actuator library provides endpoints to allow Kubernetes to check on the health of the pod (see [here](https://www.baeldung.com/spring-liveness-readiness-probes) for some more background). There are currently two endpoints available: `/acutator/health/liveness` and `/actuator/health/readiness`. 

The assignment is to configure the health and readiness probe for the employee app (see [here](https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-startup-probes/#define-a-liveness-http-request) for more info). 

### Deploy the company app
We're now going to deploy the company app. Don't worry, this will _not_ work out of the box, we left you some issues to solve!

First off, build and push the company app in the same way as you did for the salary app. Subsequently, you can deploy your app on your cluster. After that, there are several issues to solve. 

#### Getting the pod to start
The first issue you will probably encounter is that no pod will be started: `kubectl get pods` will only show you the employee pod. What's going on? `kubectl get deployments` will show you a 0/1. `kubectl get replicasets` will show you that 1 company pod is desired, but none are current or ready. `kubectl describe repicaset company-<tag>` will tell you the problem! 

Now fix this issue :-). 

If you fix this issue you will see the container appear when you type in `kubectl get pods`. But what's this? An `ErrImagePull`?

Seems there is something misconfigured with your image. Go fix it!

#### pod won't stay healthy
So now the pod will start, but it will not stay healthy. Something is up with the healthcheck. Are you able to find the issue and fix it? 

Hint: on what endpoint does the spring boot app run its healthcheck? And on what endpoint is the deployment checking? 

#### High availability service
Now that we have 1 company pod up-and-running, let's make this service a highly available service, by running two instances of company. Take note of your resource limits: the goal of this assignment is to run 1 employee pod as well as 2 instances of the company pod within these limits.  

#### Ingress route does not work
You may already have noticed the company endpoints are not reachable through your external endpoint (for example `GET https://$user.lion7.dev/company/health`). What's going on? 

Let's first check if our app is running correctly, by routing the pod to your local machine: 

```
kubectl port-forward company-<id>
```

You should be able to reach the company endpoints locally now; for example: `GET http://localhost:8081/company/health`

So the pod is running; something is wrong with the routing. Can you fix it? 

#### (Re)configure the employee client
When you try the `/company/average-monthly-salary/Bitter%20bier` endpoint, the company app wil call the employee app through the `EmployeeClient.java`. 

Unfortunately, this doesn't work. Let's first check whether our company app can actually access employee, by executing a curl command from the company pod: 

To start an interactive bash shell: 

```bash
kubectl exec -it deployment/company -- /bin/bash
```

Now inside the pod, type: `curl http://employee:8080/employee/actuator/health` 

You should get an 'up' statement back. Note that when you go 'inside' a pod, you're limited to what the pod has available (in this case, at least bash and curl). 

Type `exit` to leave the interactive session. 

You could also directly execute the curl command without an interactive shell: 

```bash
kubectl exec deployment/company -- curl http://employee:8080/employee/actuator/health
```

So we've now figured out the company app can reach the employee app. So there's probably something wrong with the endpoint configuration of company somewhere. Can you figure it out? 
