# Sistema de Gestão Escolar

Este é um projeto de sistema de gestão escolar desenvolvido em **Java**, com integração ao banco de dados **PostgreSQL**. Ele permite gerenciar **estudantes**, **professores** e **cursos**. O projeto segue o padrão **MVC** (Model-View-Controller) para uma organização modular e escalável.

---

## Funcionalidades

- **Gerenciamento de Professores:**
  - Adicionar, editar e remover professores.
  - Listar todos os professores.
  - Pesquisar professores por nome ou matrícula.

- **Gerenciamento de Estudantes:**
  - Adicionar, editar e remover estudantes.
  - Listar todos os estudantes.
  - Pesquisar estudantes por nome ou matrícula.

- **Gerenciamento de Cursos:**
  - Adicionar novos cursos vinculando a professores e estudantes.
  - Editar e remover cursos existentes.
  - Filtrar cursos pelo nome.

---

## Requisitos

### Tecnologias Utilizadas

- **Java 17**
- **PostgreSQL 17** (rodando via Docker)
- **Swing** para interfaces gráficas.

### Dependências

#### Usando Maven:
Adicione a seguinte dependência ao arquivo `pom.xml`:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.5.0</version>
</dependency>
```

### Sem Maven:
Baixe o driver PostgreSQL em formato .jar do Maven Central Repository e adicione-o ao classpath do seu projeto.

### Configuração do Banco de Dados
Arquivo `docker-compose.yml`
```
version: "3.8"

services:
  db:
    image: postgres:17
    container_name: postgres_poo
    environment:
      POSTGRES_DB: school_management
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: ja_fui
    ports:
      - "5432:5432"
    volumes:
      - ./postgres/init.sql:/docker-entrypoint-initdb.d/init.sql
```
### Script de Inicialização init.sql
Crie o arquivo `./postgres/init.sql` com o seguinte conteúdo:

```CREATE TABLE IF NOT EXISTS professors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    specialty VARCHAR(100),
    registration VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS students (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    registration VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS courses (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    course_load INT NOT NULL,
    professor_id INT REFERENCES professors(id)
);
```
### Inicializando o Banco de Dados com Docker
Execute os seguintes comandos para configurar o banco de dados:

```
docker-compose up -d
```
Verifique se o banco está ativo:

```
docker exec -it postgres_poo psql -U admin -d school_management
```
Dentro do cliente PostgreSQL, execute:

```
\dt
```
## Configuração do Projeto
### Banco de Dados
No arquivo `src/data/DataBase.java:`

```
package src.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private static final String URL = "jdbc:postgresql://localhost:5432/school_management";
    private static final String USER = "admin";
    private static final String PASSWORD = "ja_fui";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }
}
```
## Como Rodar o Projeto
1. Compile o projeto Java:
- Certifique-se de que todas as dependências estão configuradas corretamente.

2. Execute a classe principal `Main`:
- Localização: src/Main.java.
- Esta classe inicializa a interface gráfica.

3. Interaja com o sistema de gestão escolar por meio das interfaces fornecidas.

---

## Estrutura do Projeto
O projeto está dividido em três principais camadas:

- Models: Representa as entidades principais (Student, Professor, Course).
- Views: Interfaces gráficas criadas com Swing.
- Controllers: Lógica para integrar as views com o banco de dados.

---

## Melhorias Futuras
- Implementar autenticação para diferentes usuários com permissões específicas.
- Adicionar relatórios exportáveis em PDF.
- Melhorar o design das interfaces gráficas.
- Implementar testes unitários para as classes do controller.
