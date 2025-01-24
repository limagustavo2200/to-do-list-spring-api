# 📝 Todo List API

## Descrição
API de gerenciamento de tarefas (Todo List) que permite aos usuários criar e gerenciar suas tarefas pessoais de forma segura. O sistema implementa autenticação de usuários e validações de dados para garantir a segurança e integridade das informações.

### Tecnologias utilizadas:
- Spring Boot
- 🗄️ H2 Database (Banco de dados em memória)
- ⚙️ Lombok
- 📚 JPA/Hibernate
- 🔐 BCrypt (para criptografia de senhas)
- 🧩 Base64 (para codificação)
- 🐳 Docker (para containerização)

O projeto foi desenvolvido para demonstrar a implementação de uma API RESTful com autenticação e persistência de dados, utilizando boas práticas de desenvolvimento e segurança.

## 🗂️ Estrutura do Projeto
```
todo-list-spring-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/gustavo/todo_list/
│   │   │       ├── user/           
│   │   │       ├── task/           
|   |   |       ├── filter/ 
|   |   |       ├── errors/
|   |   |       ├── utils/   
│   │   └── resources/
│   │       └── application.properties
│   └── test/                                         
├── .gitattributes 
├── Dockerfile                  
├── .gitignore                      
├── mvnw                          
├── mvnw.cmd                       
└── pom.xml                        
```

## 📊 Modelos de Dados

### Usuário (UserModel)
Responsável pelo gerenciamento dos usuários do sistema. Possui sistema de autenticação com username único e senha criptografada usando BCrypt. Cada usuário pode ter múltiplas tarefas associadas e tem seu momento de criação registrado automaticamente.

Validações:
- Username deve ser único no sistema
- Senha é automaticamente criptografada antes do armazenamento
- Campos obrigatórios: username, name, password

### ✅  Tarefa (TaskModel)
Gerencia as tarefas dos usuários. Cada tarefa está vinculada a um usuário específico e possui controle de datas de início e término, prioridade e descrição. O título tem validação de tamanho máximo de 50 caracteres e o sistema garante que as datas sejam coerentes (início anterior ao término).

Validações:
- Título limitado a 50 caracteres
- Data de início deve ser anterior à data de término
- Datas devem ser posteriores à data atual
- Campos obrigatórios: title, startAt, endAt, priority
- Prioridade deve ser uma das opções: ALTA, MEDIA, BAIXA

## 🔐 Segurança

### 🛡️ Autenticação
- Utiliza Basic Auth para autenticação
- Senhas são criptografadas usando BCrypt
- Codificação Base64 para transmissão segura de credenciais

### 🚫 Autorização
- Usuários só podem acessar e modificar suas próprias tarefas
- Validação de proprietário em todas as operações de tarefas

## ⚙️ Instrução de instalação

### Pré-requisitos
- ☕ Java JDK 17 ou superior
- 🐳 Docker
- 🛠️ Maven
- 💻 IDE de sua preferência (recomendado: IntelliJ IDEA ou VS Code)

### Etapas (sem Docker)

1. Clone o repositório
```bash
git clone https://github.com/limagustavo2200/to-do-list-spring-api
```

2. Entre na pasta do projeto
```bash
cd to-do-list-spring-api
```

3. Execute o projeto
```bash
mvn spring-boot:run
```

### Etapas (com Docker)

1. Construa a imagem Docker
```bash
docker build -t todo-list-api
```

2. Execute o container:
```bash
docker run -p 8080:8080 todo-list-api
```
O projeto estará disponível em http://localhost:8080

## Instrução de uso

1. Primeiro, crie um usuário através do endpoint de registro
2. Use as credenciais para autenticar nas requisições (Basic Auth)
3. Comece a criar e gerenciar suas tarefas
4. Todas as requisições para tarefas devem incluir o cabeçalho de autenticação

## 🌐 API Endpoints

### Usuários
```bash
POST /users/
# Cria um novo usuário
# Body: {
#   "username": "seu_usuario",
#   "name": "Seu Nome",
#   "password": "sua_senha"
# }
```

### Tarefas
```bash
GET /tasks/
# Lista todas as tarefas do usuário autenticado

POST /tasks/
# Cria uma nova tarefa
# Body: {
#   "description": "Descrição da tarefa",
#   "title": "Título da tarefa", # Máximo 50 caracteres
#   "priority": "ALTA/MEDIA/BAIXA",
#   "startAt": "2023-10-20T12:00:00",
#   "endAt": "2023-10-20T14:00:00"
# }

PUT /tasks/{idTask}
# Atualiza uma tarefa existente
# Body: {
#   "description": "Nova descrição",
#   "title": "Novo título", # Máximo 50 caracteres
#   ...
# }
```

### Códigos de Resposta
- ✅ 200: Sucesso
- ✨ 201: Criado com sucesso
- ⚠️ 400: Erro de validação
- 🔒 401: Não autorizado
- 🚫 403: Proibido
- ❓ 404: Recurso não encontrado
- 💥 500: Erro interno do servidor


### Observações importantes:
- Todas as requisições para `/tasks` necessitam de autenticação
- As datas de início e término devem ser posteriores à data atual
- A data de início deve ser anterior à data de término
- Apenas o proprietário da tarefa pode modificá-la
- O título da tarefa tem um limite de 50 caracteres
- O username do usuário deve ser único no sistema
- Todas as datas devem ser enviadas no formato ISO 8601 (YYYY-MM-DDTHH:mm:ss)
- As respostas são retornadas no formato JSON

## Contribuição
Para contribuir com o projeto:
1. Faça um fork do repositório
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request


