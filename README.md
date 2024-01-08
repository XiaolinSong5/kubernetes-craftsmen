# Kubernetes voor ontwikkelaars
Dit is een voorbeeld salarisadministratie-appje blablabla

## Voorbereiding
- Installeer docker
- Installeer Java 17 (en maven)
- Installeer [kubectl](https://kubernetes.io/docs/tasks/tools/) 
- Voor intellij: Kubernetes plugin

## Opdrachten

### Deploy de Employee app in je cluster
1. Bouw en run de employee spring boot app
   
   ```bash
   ./employee/docker-build.sh
   ./employee/docker-run.sh
   ```

   Als het goed is start nu de spring boot applicatie en kun je de endpoints bereiken die gedefinieerd zijn in `employee/httpRequests.http` 

2. Push de gebouwde image naar de image registry.
   
   In order to be able to push your image you need to login into registry.gitlab.com. For that you need an access token with read and write privileges (read_registry, write_registry). You can create an access token [here](https://gitlab.com/-/user_settings/personal_access_tokens). 
   
   After that, you need to login into the gitlab registry:
   
   ```bash
   docker login registry.gitlab.com
   ```
   
   After a succesful login, you can push your image: 

   ```bash
   ./employee/docker-push
   ```

3. Run your image in your kubernetes cluster. 

4. Useful kubernetes commands to look around. 
   See [here](https://kubernetes.io/docs/reference/kubectl/quick-reference/) for the kubernetes quick reference guide. 

   ```
   kubectl get deployments
   kubectl get services
   kubectl get pods
   kubectl logs <podnaam>
   kubectl get replicasets
   kubectl describe deployment <deployment>
   ```

### Configure the healthchecks for the employee app
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
- sb health endpoint is raar

#### High availability service

#### Ingress route does not work
- port forwarding gebruiken

#### (Re)configure the employee client

#### remote debuggen

