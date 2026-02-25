# README.md

```markdown
# Order Management

Sistema de gerenciamento de pedidos desenvolvido com Spring Boot e Java.

## Descrição
Aplicação para gerenciar pedidos, produtos e usuários com APIs RESTful completas.

## Tecnologias
- **Java 11+**
- **Spring Boot 3.x**
- **Maven**
- **JPA/Hibernate**
- **H2/MySQL** (conforme configuração)

## Pré-requisitos
- JDK 11 ou superior
- Maven 3.6+

## Instalação

1. Clone o repositório:
```bash
git clone git@github.com:joaovcss/order-management.git
cd order-management
```

2. Compile o projeto:
```bash
mvn clean install
```

## Executando a Aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## Estrutura do Projeto

```
src/main/java/com/yonix/order_management/
├── controller/          # Controladores REST
│   ├── OrderController.java
│   ├── ProductController.java
│   └── UserController.java
├── service/             # Lógica de negócio
├── repository/          # Acesso a dados
├── entity/              # Entidades JPA
├── dto/                 # Data Transfer Objects
│   ├── mapper/          # Mapeadores DTO <-> Entity
│   ├── request/         # DTOs de entrada
│   └── response/        # DTOs de saída
├── exceptions/          # Exceções customizadas
├── infra/               # Configurações de infraestrutura
└── OrderManagementApplication.java
```

## Endpoints Principais

### Pedidos
- `GET /orders` - Listar todos os pedidos
- `GET /orders/{id}` - Obter pedido por ID
- `POST /orders` - Criar novo pedido
- `PUT /orders/{id}` - Atualizar pedido
- `DELETE /orders/{id}` - Deletar pedido

### Produtos
- `GET /products` - Listar produtos
- `POST /products` - Criar produto
- `PUT /products/{id}` - Atualizar produto
- `DELETE /products/{id}` - Deletar produto

### Usuários
- `GET /users` - Listar usuários
- `POST /users` - Criar usuário
- `PUT /users/{id}` - Atualizar usuário
- `DELETE /users/{id}` - Deletar usuário

## Configuração

Edite `src/main/resources/application.properties` para configurar banco de dados e outras propriedades.

## Licença
MIT
```
