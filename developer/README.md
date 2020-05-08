# demo project

This project uses Quarkus, the Supersonic Subatomic Java Framework. It requires Java JDK 11. 

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `demo-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/demo-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/demo-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.


mkdir quarkus-demo


cd quarkus-demo

#add mvn wrapper
mvn -N io.takari:maven:wrapper -Dmaven=3.6.3

#Create a project using mvn archetype
./mvnw io.quarkus:quarkus-maven-plugin:create

#dev mode
./mvnw quarkus:dev

#to list an extension
./mvnw quarkus:list-extensions | grep extensionname

#to add an extension
./mvnw quarkus:add-extension -Dextensions="extensionname1, extensionname2"

#to package as JVM app
./mvnw clean package

#to package as native
./mvnw clean package -Dquarkus.container-image.build=true

#to check openapi
curl http://localhost:8080/gbtdemo/openapi/



./mvnw quarkus:add-extension -Dextensions="quarkus-jsonb, quarkus-resteasy-jsonb"
./mvnw quarkus:add-extension -Dextensions="quarkus-hibernate-orm-panache, quarkus-jdbc-postgresql"
./mvnw quarkus:add-extension -Dextensions="quarkus-jdbc-h2" 
./mvnw quarkus:add-extension -Dextensions="quarkus-smallrye-openapi"
./mvnw quarkus:add-extension -Dextensions="quarkus-qute quarkus-resteasy-qute"
./mvnw quarkus:add-extension -Dextensions="quarkus-rest-client"
./mvnw quarkus:add-extension -Dextensions="quarkus-container-image-jib"


In the developer ms add rest client
./mvnw quarkus:add-extension -Dextensions="quarkus-rest-client, smallrye-fault-tolerance"

./mvnw quarkus:add-extension -Dextensions="quarkus-kubernetes"

check images published at https://hub.docker.com/repositories


kubectl apply -f target/kubernetes/kubernetes.json


# enable ingress in minikube. Check if ingress is running kubectl get pods -n kube-system
minikube addons enable ingress

# Check if ingress config for developer is running
kubectl get ingress

# access the application http://192.168.1.190/gbtdemo/developer/

# ConfigMaps to get configs from kubernetes
./mvnw quarkus:add-extension -Dextensions="kubernetes-config"

# Create a configmap from a file 

kubectl create configmap developer-cm --from-file=src/main/resources/application-k8s.properties

# to package and deploy to kubernetes automatically 
./mvnw clean package -Dquarkus.kubernetes.deploy=true

# websockets

./mvnw quarkus:add-extension -Dextensions="quarkus-undertow-websockets"


