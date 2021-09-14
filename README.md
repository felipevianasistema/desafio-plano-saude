# Desafio Plano de Saúde

O projeto possui exemplos de como criar uma API utilizando Clojure, Pedestal e banco de dados Postgresql.

## Pré-requisitos para executar

- Ambiente clojure
- Banco de dados Postgres
- Postman para chamada dos endpoints (opcional)
- Comando para executar o projeto: lein run

## Endpoints

- Url base: http://localhost:3000
- Collection do Postman: https://drive.google.com/drive/folders/1_FrCPWo9Qej6U_ThDZpTqvDVor2AvrCT?usp=sharing

###### Plano

-	.../plano/cadastrar
-	.../plano/obter-todos
-	.../plano/obter-ativos
-	.../plano/obter/:id
-	.../plano/atualizar-status/:id
-	.../plano/atualizar/:id

###### Ficha de Inclusão

-	.../ficha-inclusao/cadastrar
-	.../ficha-inclusao/obter-todos
-	.../ficha-inclusao/obter-ativos
-	.../ficha-inclusao/obter/:id
-	.../ficha-inclusao/atualizar-status/:id
-	.../ficha-inclusao/atualizar/:id

## Banco de dados

O script para criação das tabelas está no diretório: /src/main/resources/sql/script_banco.sql

- Ao iniciar a aplicação utilizando o comando 'lein run' o projeto utiliza o banco de dados da máquina local
- Ao executar os testes, a aplicação cria o banco de dados em memória para não afetar o banco local

![alt text](https://i.ibb.co/gWL0dkX/sssss.png)
