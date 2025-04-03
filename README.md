# 🌟 Projeto demo: Configuração Simplificada 🚀

Bem-vindo ao guia de configuração do Projeto demo! Aqui você encontrará as etapas necessárias para configurar e iniciar rapidamente o ambiente Docker. Vamos nessa! 💪

---

## 📦 1. Crie a Imagem do Backend

Vamos começar construindo a imagem Docker para o backend! 🛠️e

```bash
docker build -t demo-backend -f Dockerfile.dev .
```

---

## 🛳️ 2. Inicie o Container do Backend

Hora de iniciar o backend! Ele será acessível na porta **8000**. 🌐

```bash
docker run --name demo-backend -d -it -p 8000:8080 -v "$(pwd):/app" -v "$HOME/.ssh:/root/.ssh" -w /app demo-backend
```

---

## 🔗 3. Crie uma Rede Docker

Vamos criar uma rede dedicada para nossos containers se comunicarem. 📡

```bash
docker network create demo-network
```

---

## 🗄️ 4. Inicie os Containers do Banco de Dados

### 🐘 Container PostgreSQL:
Configure e inicie o banco de dados com as credenciais necessárias. 🔒

```bash
docker run -d --name db-demo --net demo-network -e POSTGRES_PASSWORD=root postgres
```

---

## 🌐 5. Conecte o Backend à Rede

Agora, conecte o backend à rede criada no passo 3. 🚦

```bash
docker network connect demo-network demo-backend
```

---

🎉 **Pronto!** Seu ambiente Docker está configurado e funcionando. Agora é só codar e brilhar! 💻✨

🌟 As tarefas estão ordenadas na pasta docs de acordo com o teste enviado! 🌟
