# Comment Service

O **Comment Service** é o microsserviço responsável pela gestão de comentários. Ele atua como o ponto de entrada para os usuários e coordena a persistência de dados após a validação pelo serviço de moderação.

## ⚙️ Funcionalidades

- Criação de novos comentários com validação automática.
- Listagem paginada de comentários aprovados.
- Busca de comentários por ID.
- Tratamento de erros resiliente para falhas de comunicação com o serviço de moderação (Exception Handler).

## 🔌 Integração

Este serviço utiliza **Interface Clients** do Spring 6 para se comunicar de forma síncrona com o `moderation-service` na porta `8084`.

## 📂 Estrutura de Pastas

- `api`: Controladores REST, DTOs e configurações de cliente HTTP.
- `domain`: Entidades JPA e interfaces de repositório.

## 🛠️ Endpoints Principais

| Método | Endpoint             | Descrição                                  |
| :----- | :------------------- | :----------------------------------------- |
| `POST` | `/api/comments`      | Cria um comentário (passa pela moderação). |
| `GET`  | `/api/comments`      | Retorna lista paginada.                    |
| `GET`  | `/api/comments/{id}` | Retorna detalhes de um comentário.         |

## 💾 Banco de Dados

Utiliza **H2 Database** configurado para persistir em arquivo local (`~/algasensors-comment-service-db`).
Console do H2 acessível em `/h2-console` quando o serviço está rodando.
