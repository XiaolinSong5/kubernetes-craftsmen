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

### healthcheck goed zetten

### endpoints tussen pods configureren

### image kan niet gevonden worden

### niet genoeg resources voor deployment

### remote debuggen

