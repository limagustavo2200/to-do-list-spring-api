# Etapa 1: Build
FROM ubuntu:latest AS build

# Atualiza e instala as dependências necessárias
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    maven

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto para o contêiner
COPY . .

# Realiza o build do projeto
RUN mvn clean install

# Etapa 2: Runtime
FROM openjdk:17-jdk-slim

# Define a porta exposta
EXPOSE 8080

# Copia o arquivo .jar da etapa de build
COPY --from=build /app/target/todo-list-0.0.1-SNAPSHOT.jar app.jar

# Define o comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
