# Animals Service

Aplicação de acolhimento e acompanhamento de cães e gatos resgatados. O projeto oferece uma API REST com Spring Boot e uma interface web estática para consultar os animais, cadastrar novos resgates e visualizar o ranking de resgatadores.

## Requisitos

- Java 17 ou superior
- Maven Wrapper incluído no projeto

## Executar a aplicação

No Windows, abra o terminal nesta pasta e execute:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

Quando a aplicação estiver iniciada, abra **http://localhost:8081/** no navegador. A interface e a API são servidas pelo mesmo endereço.

> Não abra `src/main/resources/static/index.html` diretamente com o protocolo `file://`. A aplicação usa caminhos HTTP para carregar os arquivos de estilo e JavaScript e para acessar a API.

## Interface

A página inicial apresenta o resumo dos animais cadastrados e permite:

- Listar cães e gatos e filtrar entre todos, para adoção ou adotados.
- Buscar animais pelo nome provisório ou raça.
- Cadastrar cães ou gatos com informações de resgate e, opcionalmente, de saída, recebedor e óbito.
- Consultar o ranking de resgatadores por espécie e período.

O cadastro é uma operação de criação. A API atual não oferece endpoints para editar ou excluir animais.

## API

As datas devem ser enviadas no formato ISO `AAAA-MM-DD`.

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/cachorros/listar` | Lista todos os cães |
| `GET` | `/cachorros/para-adocao` | Lista cães sem data de saída |
| `GET` | `/cachorros/adotados` | Lista cães com data de saída |
| `POST` | `/cachorros` | Cadastra um cão |
| `GET` | `/gatos/listar` | Lista todos os gatos |
| `GET` | `/gatos/para-adocao` | Lista gatos sem data de saída |
| `GET` | `/gatos/adotados` | Lista gatos com data de saída |
| `POST` | `/gatos` | Cadastra um gato |
| `GET` | `/cachorros/ranking-resgatadores?dataInicio=AAAA-MM-DD&dataFim=AAAA-MM-DD` | Consulta o ranking de resgatadores de cães no período |
| `GET` | `/gatos/ranking-resgatadores?dataInicio=AAAA-MM-DD&dataFim=AAAA-MM-DD` | Consulta o ranking de resgatadores de gatos no período |

O ranking é retornado como uma lista de pares contendo o nome do resgatador e a quantidade de animais resgatados.

### Exemplo de cadastro

Envie uma requisição `POST` para `/cachorros` ou `/gatos`, com `Content-Type: application/json`:

```json
{
  "nomeProvisorio": "Pingo",
  "idadeEstimada": 2,
  "raca": "Vira-lata",
  "dataEntrada": "2026-11-27",
  "dataSaida": null,
  "condicaoChegada": "Resgatado em boas condições",
  "nomeRecebedor": null,
  "dataObito": null,
  "porte": "Pequeno",
  "resgatador": "Ana"
}
```

`dataSaida`, `nomeRecebedor` e `dataObito` são opcionais. O identificador é gerado pelo banco. Um animal sem `dataSaida` aparece na lista para adoção; com `dataSaida`, aparece na lista de adotados.

### Exemplo de consulta ao ranking

```text
GET http://localhost:8081/cachorros/ranking-resgatadores?dataInicio=2026-01-01&dataFim=2026-12-31
```

## Banco de dados

A aplicação usa H2 em memória. Os dados iniciais são carregados a partir de `src/main/resources/data.sql` e ficam disponíveis durante a execução atual; ao reiniciar a aplicação, o banco é recriado.

## Testes

Execute os testes com:

```powershell
.\mvnw.cmd test
```

Ou, no Linux/macOS:

```bash
./mvnw test
```
