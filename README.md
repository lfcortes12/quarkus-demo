# Quarkus Demo

## Decription

Demo is composed for the following services:

- /developer runs at <http://localhost:8080/gbtdemo/gbtdemo/>
- /project runs at <http://localhost:8081/>
- /demo-web-socket <http://localhost:8082/home>

## How to create a project with Maven

```bash
# In case you do not have Maven installed in your console path add the maven wrapper
mvn -N io.takari:maven:wrapper -Dmaven=3.6.3

# Create a project using mvn archetype
./mvnw io.quarkus:quarkus-maven-plugin:create
```

## How to run it in dev mode

```bash
./mvnw quarkus:dev
```

## Working with Quarkus extensions

```bash

# lists all available extensions
./mvnw quarkus:list-extensions

# lists an specific extension
./mvnw quarkus:list-extensions | grep extensionname

# Adds an extension
./mvnw quarkus:add-extension -Dextensions="extensionname1, extensionname2"

```

## Packaging the application

```bash

# Package to run it in JVM mode
./mvnw clean package

# Package as a native application.
# -Dquarkus.container-image.build parameter uses a docker container with GraalVM installed to generate the native installer.
./mvnw clean package -Dquarkus.container-image.build=true

```

## Running the application

Once you have packaged the application there are two options to run it:

Option 1: If you packaged in JVM mode

```bash

java -jar target/developer-1.0-SNAPSHOT-runner.jar

```

Option 2: If you packaged as a native application

```bash

target/project-1.0-SNAPSHOT-runner

```

## Swagger and Open API

To check the exposed API

```bash
curl http://localhost:8080/gbtdemo/openapi/

```

To access swagger <http://localhost:8080/gbtdemo/swagger-ui/>

## Extensions used in this demo

```bash

# Adds rest services
./mvnw quarkus:add-extension -Dextensions="quarkus-jsonb, quarkus-resteasy-jsonb"
# Adds database access
./mvnw quarkus:add-extension -Dextensions="quarkus-hibernate-orm-panache, quarkus-jdbc-h2"
# Adds openapi and swagger support
./mvnw quarkus:add-extension -Dextensions="quarkus-smallrye-openapi"
# HTML templates using qute engine
./mvnw quarkus:add-extension -Dextensions="quarkus-qute quarkus-resteasy-qute"
# Creates rest clients. Used in developer service to get projects
./mvnw quarkus:add-extension -Dextensions="quarkus-rest-client"
# Adds fault tolerance (Retry policy, fallbacks and circuit breaker)
./mvnw quarkus:add-extension -Dextensions="smallrye-fault-tolerance"
# Creates docker images
./mvnw quarkus:add-extension -Dextensions="quarkus-container-image-jib"
# Kubernetes integration. Allows to deploy to Kubernates automatically running ./mvnw package -Dquarkus.kubernetes.deploy=true
./mvnw quarkus:add-extension -Dextensions="quarkus-kubernetes"

```

## Docker images

Docker images are published in your local registry and also in docker hub at <https://hub.docker.com/repositories> or locally with:

```bash

docker images

```

Once you have the image, you can create a container to run the application.

## Kubernetes

Install any Kubernetes distribution like minikube.

In case you want to add kubernetes support add the extension to the project *quarkus-kubernetes* it automatically generates the
deployment files in */target/kubernetes* . For this demo, *developer* and *project* services have Kubernetes support.

### Deploy the service in Kubernetes

Option 1: Manually
Requires packaging the application with `./mvnw package`

```bash

kubectl apply -f target/kubernetes/kubernetes.json

```

Option 2: Automatically

```bash

./mvnw package -Dquarkus.kubernetes.deploy=true

```

### Expose a service with Kubernetes Ingress

First of all, enable Ingress:

```bash

# Enables ingress in minikube. Check if ingress is running kubectl get pods -n kube-system
minikube addons enable ingress

# Checks if ingress is running
kubectl get pods -n kube-system

# Check if ingress config for developer is running
kubectl get ingress

```

Add `quarkus.kubernetes.expose=true` In the application.properties file to generate the Ingress configuration. Then, package and deploy it. It will be available at <http://[clusterip]/gbtdemo/developer/>

### Configure the service using Kubernetes ConfigMap

To externalize the service configuration, it's possible with ConfigMap so add the extension

```bash

# ConfigMaps to get configs from kubernetes
./mvnw quarkus:add-extension -Dextensions="kubernetes-config"


```

To enable ConfigMap in the service, add the following properties in `application.properties` file

```properties
%prod.quarkus.kubernetes-config.enabled=true
# quarkus.kubernetes-config.config-maps corresponds to the name of ConfigMap you have created.
quarkus.kubernetes-config.config-maps=developer-cm
quarkus.kubernetes-config.fail-on-missing-config=true

```

Now, create a ConfigMap from a file. For doing that, you can create a copy of any of the application file located at */src/main/resources* and then customize the values. Finally you can create the ConfigMap executing the following command:

```bash

kubectl create configmap developer-cm --from-file=application.properties

```

## Quarkus Websockets support

In *demo-web-socket* project you can find an implementation using websockets. To use websockets, use the proper extension

```bash

./mvnw quarkus:add-extension -Dextensions="quarkus-undertow-websockets"


```

Go to <http://localhost:8082/home> to access to the demo.
