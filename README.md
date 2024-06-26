# Microsserviços

## Ordem de deploy das aplicações
- eureka server
- ms gateway
- ms clientes

## Modelo dos microserviços

![Aplicação](./imgs/app.png)

## Modelo de mensageria

![Mensageria](./imgs/mensageria.png)

## URLs
- [EurekaServer](http://localhost:8761/)

- Roda aplicação Spring via linha de comando
```
./nvmw spring-boot:run
```

## RabbitMQ
- http://localhost:15672/
```
docker run -it --name microservico-rabbitmq -p 5672:5672 -p 15672:15672 --network ms-curso rabbitmq:3.9-management

usuário: guest
senha: guest
```

## Keyclock

```
docker run --name mskeyclock -p 8081:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin --network ms-curso quay.io/keycloak/keycloak:18.0.0 start-dev
```

- http://localhost:8081/
- Administration Console 
- admin
- admin
- Criar controle de acesso
  - Criar Realm
    - Pode ser:
      - Conjunto de aplicações
      - Dominio
      - Aplicação
    - Criar um client
      - Uma aplicação que será cliente
- Agora habilitou algumas opções a mais
  - Em settings
    - Name: Cliente projeto avaliação de crédito
    - Access Type : confidencial
    - Standard Flow Enabled: ON
    - Direct Access Grants Enabled: ON
    - ervice Accounts Enabled : ON
    - Authorization Enabled: ON
    - * Valid Redirect URIs : http://localhost:8080
    - Save
  - Em Credentils terá um Secret
- Após Adicionar a dependência do oauth2 resource server
  - Vai no keyclock em:  Realm Settings > General
      - Endpoint
      - Ao clicar no link abrir um arquivo json
        - URL para validar o token
        - "issuer": "http://localhost:8081/realms/mscursorealm",
      - Para colocar no client http para fazer a requisição
        - "token_endpoint": "http://localhost:8081/realms/mscursorealm/protocol/openid-connect/token",
      - ClientId é o nome do client Real: mscredito
      - Client Secret
        - Em Credentils terá um Secret
- Tempo de expiração do token
  - Vá em Clients > Settings >  Advanced Settings 
    - Access Token Lifespan

## Actuator
- Dependência
```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
- Configuração no Application.yml
  - Exibe todos os endpoint `configuravel`
  - arquivo de logs
```
management:
  endpoints:
    web:
      exposure:
        include: '*'
logging:
  file:
    name: ./logs/log-file.log
```
- Ao clicar no microserviço no eureka server
> http://laptop-4nij11i6:51891/actuator/info

- Logs da aplicação
```
"logfile": {
  "href": "http://laptop-4nij11i6:51891/actuator/logfile",
  "templated": false
}
```

## Spring Doc
- https://springdoc.org/
- Swagger da aplicação
  - http://localhost:`port`/swagger-ui/index.html


## Maven
- ./mvnw clean package -DskipTests
- cd target
- java -jar eurekaserver-0.0.1-SNAPSHOT.jar
```
FROM openjdk:17
WORKDIR /app
COPY ./target/eurekaserver-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT java -jar app.jar
```

- docker build -t img-eureka-server .
- docker container run --name eureka-server-container -p 8761:8761 -d --network ms-curso img-eureka-server
  
- docker build -t img-ms-cartoes .
- docker container run --name ms-cartoes-container -d --network ms-curso -P -e RABBITMQ_SERVER=microservico-rabbitmq -e EUREKA_SERVER=eureka-server-container img-ms-cartoes

- docker build -t img-ms-cliente .
- docker container run --name ms-cliente-container -d --network ms-curso -P -e EUREKA_SERVER=eureka-server-container img-ms-cliente

- docker build -t img-ms-avaliador .
- docker container run --name ms-avaliador-container -d --network ms-curso -P -e RABBITMQ_SERVER=microservico-rabbitmq -e EUREKA_SERVER=eureka-server-container img-ms-avaliador

- docker build -t img-gateway .
- docker container run --name gateway-container -d --network ms-curso -p 8080:8080 -e RABBITMQ_SERVER=microservico-rabbitmq -e EUREKA_SERVER=eureka-server-container -e KEYCLOAK_SERVER=mskeyclock -e KEYCLOAK_PORT=8080 img-gateway

- docker inspect --format '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' eureka-server-container
- docker network create ms-curso
- docker network ls


## Build dentro do docker
```

```