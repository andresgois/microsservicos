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
```
docker run -it --name microservico-rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management

usuário: guest
senha: guest
```

## Keyclock

```
docker run --name mskeyclock -p 8081:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:18.0.0 start-dev
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
  - Vai no keyclock em:
    - Realm Settings
    - General
      - Endpoint
      - Ao clicar no link abrir um arquivo json
        - URL para validar o token
        - "issuer": "http://localhost:8081/realms/mscursorealm",
      - Para colocar no client http para fazer a requisição
        - "token_endpoint": "http://localhost:8081/realms/mscursorealm/protocol/openid-connect/token",
      - ClientId é o nome do client Real: mscredito
      - Client Secret
        - Em Credentils terá um Secret
