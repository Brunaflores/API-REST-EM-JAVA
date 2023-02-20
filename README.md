# API LabParadises

API LabParadises é uma aplicação Java que disponibiliza endpoints RESTful para gerenciar bairros e praias de um determinado litoral.

## Tecnologias

- Java
- Spring Boot
- Maven
- MySQL

## Configuração do ambiente

Para executar a API LabParadises, é necessário ter instalado em sua máquina o Java.

## Execução da API

Acesse a API através da URL 
<br> http://localhost:8080/

## **Endpoints**
<br>

### **`Endpoint Bairros:`**

| Endpoint | Método | Descrição |
| --- | --- | --- |
| /bairro/todos | GET | Retorna uma lista com todos os bairros cadastrados. |
| /bairro/{id} | GET | Retorna o bairro com o ID informado. |
| /bairro/cadastrar | POST | Cadastrar um novo bairro |
| /bairro/atualizar/{id} | PUT | Atualiza o bairro com o ID informado. |
| /bairro/excluir/{id} | DELETE | Exclui o bairro com o ID informado. |

Obs: Somente é possível excluir um bairro se este não estiver associado a uma praia;

<br>

### **`Endpoint Praias:`**

| Endpoint | Método | Descrição |
| --- | --- | --- |
| /praia/todas | GET | Retorna uma lista com todas as praias cadastradas. |
| /praia/buscar/{id} | GET | Retorna a praia com o ID informado. |
| /praia/acessibilidade/{acessibilidade} | GET | Retorna uma lista com todas as praias que possuem o acessibilidade. |
| /praia/populacao/{populacao} | GET | Retorna uma lista com todas as praias que possuem população menor ou igual ao valor informado. |
| /praia/status/{status} | GET | Retorna uma lista com todas as praias que possuem o status informado. |
| /praia/cadastrar | POST | Cadastra uma nova praia. |
| /praia/atualizar/{id} | PUT | Atualiza a praia com o ID informado. |
| /praia/excluir/{id} | DELETE | Exclui a praia com o ID informado. |

<br>

## **Modelos de dados utilizados na API:**
<br>

---
<br>

 ### **Endpoint Bairro - Cadastrar** 
`Host: localhost:8080/bairro/cadastrar `<br> 
*Content-Type: application/json*
```json
{
    "nome": "Coqueiros",
    "descricao": "Bairro com praia, parques e restaurantes",
    "populacao": 15000
}
```
---

<br>

### **Endpoint Praia - Cadastrar**
`Host: localhost:8080/praia/cadastrar `<br> 
*Content-Type: application/json* <br>
#### **Atenção:** _id do bairro obrigatório no cadastro da praia_
```json
{
    "bairro": {
        "id": 16
    },
    "nome": "Praia do Ribeirão",
    "acessibilidade": "sim",
    "status": "própria"
}
``` 
---
<br>

### **Endpoint Bairro - Atualizar**

`Host: localhost:8080/bairro/atualizar/{id} `<br> 
*Content-Type: application/json*

```json

{
    "nome": "Canto",
    "descricao": "Bairro residencial com vista para o mar e a lagoa",
    "populacao": 7000
}
``` 
---

<br>

### **Endpoint Praia - Atualizar**

`Host: localhost:8080/praia/atualizar/{id} `<br> 
*Content-Type: application/json*

```json
{
    "bairro": {
        "id": 12
    },
    "nome": "Praia da Agronômica",
    "acessibilidade": "sim",
    "status": "própria"
}
``` 

<br>

## Valores aceitos para Acessibilidade e Status:

| **Status** | **Acessibilidade** |
|---------|-------|
| ``própria``  | ``sim``   |
| ``imprópria``   | ``não``   |



