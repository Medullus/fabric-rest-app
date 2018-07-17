# medullus-rest-api

Note: localStoreFile.properties stores users information. When restarting the BC network, this file needs to be removed to sync with the network's reset.

# Swagger endpoints
`localhost:3000/v2/api-docs`
`localhost:3000/swagger-ui.html`

# to build docker image modify docker.image.prefix first in pom.xml
./mvnw install dockerfile:build -DskipTests
docker push <docker.image.prefix>/fabric-rest-api:latest
