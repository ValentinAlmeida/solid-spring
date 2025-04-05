# 🌟 Projeto Demo: Configuração Simplificada 🚀

Bem-vindo ao guia de configuração do Projeto Demo! Este README foi aprimorado para garantir uma configuração perfeita do seu ambiente. Siga cuidadosamente os passos abaixo:

## ⚠️ Pré-requisitos IMPORTANTES

**Antes de criar o servidor do backend**, você DEVE:
1. Criar primeiro o container do banco de dados
2. Obter as configurações de conexão do banco (host, porta)
3. Configurar corretamente o arquivo `application.properties`

---

## 🗄️ 1. Primeiro Passo: Banco de Dados

### 🐘 Container PostgreSQL
Inicie o banco de dados primeiro para obter as configurações de conexão:

```bash
docker run -d --name db-demo -p 5432:5432 -e POSTGRES_PASSWORD=root postgres
```

**Anote estas informações:**
- Host: `172.17.0.2` (ou o IP do container - use `docker inspect db-demo`)
- Porta: `5432`
- Database: `postgres`
- Usuário: `postgres`
- Senha: `root`

---

## 📝 2. Configure o application.properties

Crie um arquivo `application.properties` na pasta `src/main/resources` com base no [application.properties.example](application.properties.example) e insira as configurações reais do seu banco:

```properties
# Database Configuration (substitua com seus valores reais)
spring.datasource.url=jdbc:postgresql://172.17.0.2:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=root
```

---

## 📦 3. Crie a Imagem do Backend

Agora sim, construa a imagem Docker para o backend:

```bash
docker build -t demo-backend -f Dockerfile.dev .
```

---

## 🛳️ 4. Inicie o Container do Backend

```bash
docker run --name demo-backend -d -it -p 8000:8080 -v "$(pwd):/app" -v "$HOME/.ssh:/root/.ssh" -w /app demo-backend
```

---

## 🔗 5. Crie e Conecte à Rede Docker

```bash
# Crie a rede
docker network create demo-network

# Conecte ambos os containers
docker network connect demo-network demo-backend
docker network connect demo-network db-demo
```

---

## 🌟 Dicas Importantes

1. **Verifique a conexão** entre containers usando:
   ```bash
   docker exec -it demo-backend ping db-demo
   ```

2. **Para problemas de conexão**, verifique:
   - Se o IP do banco está correto no application.properties
   - Se ambos containers estão na mesma rede
   - Se as portas estão expostas corretamente

3. **Variáveis de ambiente** podem substituir as configurações do properties:
   ```bash
   docker run -e SPRING_DATASOURCE_URL=jdbc:postgresql://db-demo:5432/postgres ...
   ```

---

🎉 **Pronto!** Seu ambiente está configurado e pronto para uso. 

💡 **Lembre-se sempre:** Banco de dados primeiro, backend depois! Esta ordem é crucial para evitar erros de conexão.

🌟 As tarefas estão ordenadas na pasta docs de acordo com o teste enviado! 🌟