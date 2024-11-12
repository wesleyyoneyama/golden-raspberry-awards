# golden-raspberry-awards

## Pré-requisitos

- JDK 21

## Executando o projeto

Executar o comando abaixo:
```shell script
./mvnw compile quarkus:dev
```

O projeto subirá na porta 8080.

Ao subir a aplicação será executado a migração do banco de dados via Flyway.
Nessa migração será criado as tabelas no banco H2 em memória e populado a base com os registros lidos do arquivo csv de entrada.

O arquivo csv lido está em: `src/main/resources/movielist.csv`

Para consultar o intevalo de prêmios deve-se executar o comando abaixo:
```shell script
curl --location 'localhost:8080/movie/min-max-winner-interval'
```

## Executando os testes

```shell script
./mvnw verify -DskipITs=false
```