# Kubernetes for developers
This is an example salary application consisting of companies and employees.

## Preparations
- Install docker
- Install Java 17 (and maven)
- Install [kubectl](https://kubernetes.io/docs/tasks/tools/) 
- For IntelliJ IDEA: install the Kubernetes plugin

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
The first issue you will probably encounter is that no pod will be started: `kubectl get pods` will only show you the employee app. What's going on? `kubectl get deployments` will show you a 0/1. `kubectl get replicasets` will show you that 1 company pod is desired, but none are current or ready. `kubectl describe repicaset company-<tag>` will tell you the problem! 

Now fix this issue :-). 

If you fix this issue you will see the container appear when you type in `kubectl get pods`. But what's this? An ErrImagePull?

Seems there is something misconfigured with your image. Go fix it!

#### pod won't get healthy
So now the pod will start, but it will not get healthy. Something is up with the healthcheck. Are you able to find the issue and fix it? 

Hint: on what endpoint does the spring boot app run its healthcheck? And on what endpoint is the deployment checking? 

#### High availability service
Now that we have 1 pod up-and-running, let's make this service a highly available service, by running two instances of this pod. 

#### Ingress route does not work
You may already have noticed the company endpoints are not reachable through your external endpoint. What's going on? 

Let's first check if our app is running correctly, by routing the pod to your local machine: 

```
kubectl port-forward company-<id>
```

You should be able to reach the company endpoints locally now; for example: `GET http://localhost:8081/company/actuator/health`

So the pod is running; something is wrong with the routing. Can you fix it? 

#### (Re)configure the employee client

#### remote debuggen

