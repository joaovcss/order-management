# Order Management API

API REST em Spring Boot para gestão de pedidos, produtos e usuários, com autenticação e autorização baseada em JWT usando Spring Security.

## Sumário
- Visão geral
- Funcionalidades principais
- Stack de tecnologias
- Estrutura do projeto
- Autenticação e segurança (JWT)
- Endpoints principais
  - Autenticação (`/auth`)
  - Usuários (`/users`)
  - Produtos (`/products`)
  - Pedidos (`/orders`)
- Exemplos de requisição
- Execução do projeto

---

## Visão geral

Este projeto é uma API de gerenciamento de pedidos, permitindo:

- Cadastro e gestão de **usuários** (clientes e administradores).
- Cadastro e gestão de **produtos**.
- Criação e fluxo de **pedidos**, incluindo mudança de status (criado, pago, enviado, entregue, cancelado).
- Controle de acesso com **Spring Security** e **JWT**, com papéis de usuário (`ROLE_USER`) e administrador (`ROLE_ADMIN`).

Base URL padrão (ambiente local):

- `http://localhost:8080`

Classe principal: `OrderManagementApplication` em `com.yonix.order_management`.

---

## Funcionalidades principais

### Usuários
- Registro de novos usuários pela rota pública de autenticação (`/auth/register`), com role padrão `USER`.
- Listagem de usuários (apenas ADMIN).
- Remoção de usuários (apenas ADMIN).
- Criação de usuários ADMIN feita diretamente no banco de dados (não exposta via endpoint público).

### Produtos
- CRUD de produtos:
  - Listar todos os produtos.
  - Buscar produto por ID.
  - Criar, atualizar e deletar produtos (apenas ADMIN).

### Pedidos
- Criação de pedidos associados a um usuário, com itens de produto e quantidade.
- Listagem de todos os pedidos.
- Busca de pedido por ID.
- Mudança de status por rotas específicas:
  - Cancelar pedido.
  - Marcar como pago.
  - Marcar como enviado.
  - Marcar como entregue.
- Validações de fluxo de status (ex.: não é possível enviar um pedido cancelado), com exceções dedicadas (`OrderCancelledException`, `OrderStatusException`, etc.).

### Tratamento de erros
- Tratamento centralizado de exceções pelo `RestExceptionHandler`, retornando códigos de status HTTP adequados (400, 404, 409, etc.) e mensagens descritivas.

---

## Stack de tecnologias

- **Java** (versão definida no `pom.xml`).
- **Spring Boot** (Web, Validation, etc.).
- **Spring Security** (autenticação e autorização com JWT).
- **Spring Data JPA / Hibernate** para acesso a dados.
- **Banco de dados relacional** (configurado em `application.properties`).
- **Maven** para build e gerenciamento de dependências.
- **Flyway** (ou similar) para migrações de banco, com scripts em `src/main/resources/db/migration` (ex.: `V1__create-user-table.sql`).

---

## Estrutura do projeto

Pacotes principais:

- `com.yonix.order_management.controller`
  - `AuthController` – autenticação e registro (`/auth`).
  - `OrderController` – pedidos (`/orders`).
  - `ProductController` – produtos (`/products`).
  - `UserController` – usuários (`/users`).
- `com.yonix.order_management.service`
  - Regras de negócio: `OrderService`, `ProductService`, `UserService`, `AuthService`, `TokenService`, `UserDetailsServiceImpl`.
- `com.yonix.order_management.repository`
  - Repositórios JPA: `OrderRepository`, `ProductRepository`, `UserRepository`.
- `com.yonix.order_management.entity`
  - Entidades JPA: `Order`, `OrderItem`, `OrderStatus`, `Product`, `User`, `UserRole`.
- `com.yonix.order_management.dto`
  - Requests: `CreateOrderRequest`, `OrderItemRequest`, `CreateProductRequest`, `UpdateProductRequest`, `CreateUserRequest`, `LoginRequest`, `RegisterRequest`.
  - Responses: `OrderResponse`, `OrderItemResponse`, `ProductResponse`, `UserResponse`, `LoginResponse`.
  - Mappers: `OrderMapper`, `ProductMapper`, `UserMapper`.
- `com.yonix.order_management.infra.security`
  - Configurações de segurança: `SecurityConfigurations`, `SecurityFilter` (filtro JWT), etc.
- `com.yonix.order_management.infra`
  - `RestExceptionHandler` – tratamento global de exceções.

---

## Autenticação e segurança (JWT)

A autenticação é baseada em **JWT** com **Spring Security** em modo **stateless**:

- CSRF desabilitado.
- `SessionCreationPolicy.STATELESS`.
- Filtro customizado (`SecurityFilter`) adicionado antes de `UsernamePasswordAuthenticationFilter`.

### Fluxo básico

1. **Registro**: o cliente chama `POST /auth/register` com os dados do usuário, que será criado com role padrão `USER`.
2. **Login**: o cliente chama `POST /auth/login` com as credenciais.
3. A API retorna um **token JWT** (`LoginResponse`) em caso de sucesso.
4. Para acessar rotas protegidas, o cliente envia o cabeçalho:
   - `Authorization: Bearer <token>`

### Regras de acesso (SecurityConfigurations)

- Público:
  - `POST /auth/login`
  - `POST /auth/register`
- Apenas **ADMIN** (`ROLE_ADMIN`):
  - `POST /products`
  - `PATCH /products`
  - `DELETE /products`
  - `GET /users`
  - `DELETE /users`
  - `PATCH /orders/send/**`
  - `PATCH /orders/deliver/**`
- Apenas **USER** (`ROLE_USER`):
  - `PATCH /orders/cancel/**`
- Demais rotas: requerem autenticação com JWT válido.

---

## Endpoints principais

### 1. Autenticação (`/auth`)

**Controller:** `AuthController`

- `POST /auth/register`
  - Descrição: registra um novo usuário com role padrão `USER`.
  - Acesso: público.
  - Body (JSON): `RegisterRequest`.
  - Resposta: `201 Created` com `UserResponse`.

- `POST /auth/login`
  - Descrição: autentica um usuário e retorna um JWT.
  - Acesso: público.
  - Body (JSON): `LoginRequest`.
  - Resposta: `200 OK` com `LoginResponse` (contém campo `token`).

### 2. Usuários (`/users`)

**Controller:** `UserController`

- `GET /users`
  - Descrição: lista todos os usuários.
  - Acesso: apenas ADMIN.
  - Resposta: lista de `UserResponse`.

- `DELETE /users/{id}`
  - Descrição: exclui um usuário pelo seu UUID.
  - Acesso: apenas ADMIN.
  - Resposta: `204 No Content`.

### 3. Produtos (`/products`)

**Controller:** `ProductController`

- `GET /products`
  - Descrição: lista todos os produtos.
  - Acesso: autenticado.
  - Resposta: lista de `ProductResponse`.

- `GET /products/{id}`
  - Descrição: busca produto por UUID.
  - Acesso: autenticado.
  - Resposta: `ProductResponse`.

- `POST /products`
  - Descrição: cria um novo produto.
  - Acesso: apenas ADMIN.
  - Body (JSON): `CreateProductRequest`.
  - Resposta: `201 Created` com `ProductResponse`.

- `PATCH /products/{id}`
  - Descrição: atualiza um produto existente.
  - Acesso: apenas ADMIN.
  - Body (JSON): `UpdateProductRequest`.
  - Resposta: `200 OK` com `ProductResponse`.

- `DELETE /products/{id}`
  - Descrição: exclui um produto.
  - Acesso: apenas ADMIN.
  - Resposta: `204 No Content`.

### 4. Pedidos (`/orders`)

**Controller:** `OrderController`

- `GET /orders`
  - Descrição: lista todos os pedidos.
  - Acesso: autenticado.
  - Resposta: lista de `OrderResponse`.

- `GET /orders/{id}`
  - Descrição: busca pedido por UUID.
  - Acesso: autenticado.
  - Resposta: `Order` (entidade) ou DTO, conforme implementação.

- `POST /orders`
  - Descrição: cria um novo pedido para um usuário.
  - Acesso: autenticado (ao menos `ROLE_USER`).
  - Body (JSON): `CreateOrderRequest`, contendo `userId` (UUID) e lista de itens (`OrderItemRequest` com `productId` e `quantity`).
  - Resposta: `201 Created` com `OrderResponse`.

- `PATCH /orders/cancel/{orderId}`
  - Descrição: cancela um pedido.
  - Acesso: `ROLE_USER`.
  - Resposta: `200 OK` com `OrderResponse` atualizado.

- `PATCH /orders/pay/{orderId}`
  - Descrição: marca um pedido como pago.
  - Acesso: autenticado (verifique suas regras de negócio/papel esperado).
  - Resposta: `200 OK` com `OrderResponse` atualizado.

- `PATCH /orders/send/{orderId}`
  - Descrição: marca um pedido como enviado.
  - Acesso: `ROLE_ADMIN`.
  - Resposta: `200 OK` com `OrderResponse` atualizado.

- `PATCH /orders/deliver/{orderId}`
  - Descrição: marca um pedido como entregue.
  - Acesso: `ROLE_ADMIN`.
  - Resposta: `200 OK` com `OrderResponse` atualizado.

---

## Exemplos de requisição

### Registro de usuário

`POST /auth/register`

Body (exemplo):
```json
{
  "name": "Cliente Teste",
  "email": "cliente@teste.com",
  "password": "senha123"
}
```

### Login

`POST /auth/login`

Body (exemplo):
```json
{
  "email": "cliente@teste.com",
  "password": "senha123"
}
```

Resposta (exemplo):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Uso do token em chamadas protegidas:
- Header: `Authorization: Bearer eyJhbGciOi...`

### Criação de produto (ADMIN)

`POST /products`

Body (exemplo):
```json
{
  "name": "Produto X",
  "price": 100.0,
  "stock": 10
}
```

### Criação de pedido

`POST /orders`

Body (exemplo):
```json
{
  "userId": "<uuid-do-usuario>",
  "items": [
    {
      "productId": "<uuid-do-produto>",
      "quantity": 2
    }
  ]
}
```

---

## Execução do projeto

### Pré-requisitos

- Java (versão definida no `pom.xml`).
- Maven instalado **ou** uso do wrapper (`mvnw`/`mvnw.cmd`).
- Banco de dados configurado e acessível com as credenciais definidas em `src/main/resources/application.properties`.

### Build e execução (Windows / PowerShell)

- Rodar testes e gerar build:

```powershell
./mvnw.cmd clean install
```

- Executar a aplicação:

```powershell
./mvnw.cmd spring-boot:run
```

Ou rodar o JAR gerado em `target`:

```powershell
java -jar target/order-management-<versao>.jar
```

A API ficará disponível em:

- `http://localhost:8080`