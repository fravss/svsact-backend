# heroisbackend

## Descrição
API backend para gerenciamento de heróis, utilizando Spring Boot, JPA e banco de dados relacional.

frontend: https://github.com/fravss/heroisfrontend

## Pré-requisitos
- Java JDK 17 ou superior
- Maven 3.x
- Banco de dados MySQL (ou outro configurado no application.properties)

## Configuração

1. Clone o repositório:
git clone https://github.com/fravss/herois-backend.git
cd herois-backend

2. Configure o arquivo src/main/resources/application.properties com as informações do seu banco:

spring.datasource.url=jdbc:mysql://localhost:3306/seubanco
spring.datasource.username=seuusuario
spring.datasource.password=suasenha

# Porta da aplicação (opcional, padrão 8080)
server.port=8080

## Como rodar

### Via IDE
- Importe o projeto como Maven.
- Rode a classe principal com @SpringBootApplication

### Via linha de comando
- Compile e rode com Maven:

mvn clean install
mvn spring-boot:run


## API Docs (Swagger)
Se o projeto tiver Swagger configurado, acesse a documentação via:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui/index.html)


