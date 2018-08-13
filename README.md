# medullus-rest-api

#Quick start
Configure application properties and ensure `mhc.fabric.networkConfig` points to the correct network config.
pom.xml properties.docker.image.prefix requires the docker id of the docker image remote repo

1. `./mvnw package` will build jar at /target
2.  `java -jar /target/fabric-rest-api-0.0.1-SNAPSHOT.jar` will start the app
3.  `./mvnw install dockerfile:build -DskipTests` will build a docker image of app.

Note: app creates a temp file to store user data such as registered keys/certs. When app is rebooted, another temp file
is then created. This will cause inconsistent behavior as users that's already registered will be lost because of temp file 
and re-registering will result in error from fabric-ca as user is already registered.

# Swagger endpoints
`localhost:3000/v2/api-docs`
`localhost:3000/swagger-ui.html`

# to build docker image modify docker.image.prefix first in pom.xml
./mvnw install dockerfile:build -DskipTests
docker push <docker.image.prefix>/fabric-rest-api:latest
