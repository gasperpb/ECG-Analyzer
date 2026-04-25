# 🌐 ECG Analyzer - Guia de Início Rápido (Web)

## 📋 O que foi criado?

Uma **aplicação web completa** para analisar ECG com:
- ✅ Interface bonita e moderna (HTML/CSS/JavaScript)
- ✅ Backend em Java (Spring Boot)
- ✅ Gráficos interativos (Chart.js)
- ✅ API REST para comunicação

## 🚀 PASSO A PASSO - Como Colocar em Funcionamento

### PASSO 1: Instalar o Maven (se não tiver)

**Windows:**
1. Abra: https://maven.apache.org/download.cgi
2. Baixe a versão "Binary zip archive"
3. Descompacte em `C:\Program Files\`
4. Adicione ao PATH do Windows:
   - Painel de Controle → Variáveis de Ambiente
   - Adicione: `C:\Program Files\apache-maven-3.9.x\bin`
5. Reinicie o Terminal

**Verificar:**
```bash
mvn -v
```

**Linux/Mac (mais fácil):**
```bash
# Ubuntu
sudo apt install maven

# Mac
brew install maven
```

### PASSO 2: Navegar até o Projeto

```bash
cd "c:\Users\Achilles\Documents\Projetos\java\firstprojet\firstprojet"
```

### PASSO 3: Compilar o Projeto

```bash
mvn clean install -DskipTests
```

Isto vai:
- Baixar todas as dependências (~200MB)
- Compilar o código
- Criar um JAR executável

⏱️ Primeira vez pode levar 5-10 minutos

### PASSO 4: Executar a Aplicação

```bash
mvn spring-boot:run
```

Você verá mensagens como:
```
Started ECGApplication in 3.456 seconds
Tomcat started on port(s): 8080
```

### PASSO 5: Abrir no Navegador

Copie e cole na barra de endereços:
```
http://localhost:8080
```

Você verá a página web do ECG Analyzer! 🎉

## 🎯 Como Usar a Página Web

### 1️⃣ **Aba: Upload**
- Clique em "Arraste aqui ou clique"
- Selecione um arquivo `.csv` com dados ECG
- Clique em "Analisar Arquivo"
- Veja os resultados!

### 2️⃣ **Aba: Simular**
- Escolha duração (ex: 10 segundos)
- Clique "Gerar Dados Simulados"
- Veja gráfico com dados fictícios

### 📊 Resultados Mostrados

- **BPM**: Batidas por minuto
- **Batidas Detectadas**: Número de picos
- **Duração**: Tempo total
- **Voltagem**: Min, Máx, Média
- **Status**: Normal ou Anômalo ⚠️
- **Gráfico Interativo**: Clique, zoom, arraste

## 📁 Estrutura de Arquivos

```
firstprojet/
├── pom.xml                              ← Dependências Maven
├── src/
│   └── main/
│       ├── java/com/ecg/               ← Código Java
│       │   ├── ECGApplication.java
│       │   ├── controller/
│       │   ├── service/
│       │   └── model/
│       └── resources/
│           ├── templates/
│           │   └── index.html           ← Página web
│           └── application.properties   ← Configurações
├── target/                              ← Arquivos compilados
├── README_WEB.md                        ← Documentação completa
└── GUIA_RAPIDO.md                       ← Este arquivo
```

## 🔧 Resolvendo Problemas

### ❌ Erro: "mvn: command not found"
**Solução:** Maven não está no PATH
```bash
# Adicione ao arquivo ~/.bashrc (Linux/Mac)
export PATH="$PATH:/caminho/para/maven/bin"
source ~/.bashrc

# Ou reinstale Maven
```

### ❌ Erro: "Porta 8080 já em uso"
**Solução:** Outra aplicação está usando a porta
```bash
# Opção A: Matar processo
# Linux/Mac:
lsof -i :8080 | grep LISTEN | awk '{print $2}' | xargs kill

# Windows:
netstat -ano | findstr :8080
taskkill /PID [PID] /F

# Opção B: Usar outra porta
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
# Então acesse: http://localhost:9090
```

### ❌ Erro: "Java version not supported"
**Solução:** Precisa de Java 17+
```bash
java -version

# Se for versão antiga, instale Java 17:
# https://www.oracle.com/java/technologies/downloads/
```

### ❌ Erro: "ConnectionRefused na API"
**Solução:** Servidor não iniciou
- Verifique console para erros
- Aguarde mensagem "Started ECGApplication"
- Refresque a página (F5)

## 📡 API REST (Para Programadores)

A aplicação expõe 3 endpoints:

### 1. Analisar Arquivo
```bash
curl -X POST -F "file=@ecg_data.csv" http://localhost:8080/api/ecg/analyze
```

**Resposta:**
```json
{
  "success": true,
  "bpm": 72.5,
  "peakCount": 5,
  "duration": 3.5,
  "isAnomalous": false,
  "anomalyDescription": "Ritmo cardíaco normal"
}
```

### 2. Simular Dados
```bash
curl http://localhost:8080/api/ecg/simulate?duration=10
```

### 3. Health Check
```bash
curl http://localhost:8080/api/ecg/health
```

## 🌐 Acessar de Outros Computadores

Se quiser acessar de outro PC na mesma rede:

1. Descubra o IP do computador:
```bash
# Windows
ipconfig
# Procure por: "IPv4 Address" (ex: 192.168.1.100)

# Linux/Mac
ifconfig
```

2. No outro PC, acesse:
```
http://192.168.1.100:8080
```

## 🔒 Firewall

Se a página não carregar de outro PC:
- Abra a porta 8080 no firewall
- Windows: Settings → Firewall → Advanced → Inbound Rules

## 📊 Arquivo CSV - Formato Esperado

Crie um arquivo `meus_dados.csv`:

```csv
# Comentário (opcional)
# Formato: timestamp(ms),voltagem(mV)
0,0.02
4,0.05
8,0.08
12,0.10
16,0.12
20,0.13
...
```

**Taxa de amostragem:** 250 Hz (4ms entre pontos)

Use o arquivo `ecg_data.csv` como exemplo!

## 🎨 Customizar a Página

Edite `src/main/resources/templates/index.html`:

```html
<!-- Mudar cores -->
background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);

<!-- Mudar título -->
<h1>❤️ ECG Analyzer</h1>

<!-- Mudar porta -->
server.port=9090 (em application.properties)
```

## 📦 Parar a Aplicação

No terminal onde rodando `mvn spring-boot:run`:
```bash
Pressione: CTRL + C
```

## ⭐ Dicas Extras

### 1. Modo Desenvolvimento (reload automático)
```bash
mvn spring-boot:run
# Edite arquivo → salve → página recarrega automaticamente
```

### 2. Gerar JAR e Rodar Offline
```bash
mvn package
java -jar target/ecg-analyzer-1.0.0.jar
```

### 3. Ver Logs Detalhados
```bash
mvn spring-boot:run -X
```

### 4. Integrar com Banco de Dados
- Adicione banco H2 em `pom.xml`
- Crie entity para armazenar histórico
- Faça query em ECGController

## 🎯 Próximas Melhorias

- [ ] Adicionar Dashboard com histórico
- [ ] Exportar relatórios em PDF
- [ ] Integrar com sensores reais (Bluetooth)
- [ ] Autenticação de usuários
- [ ] Gráficos em tempo real
- [ ] Deploy em Heroku/AWS

## 📞 Suporte

Problemas?
1. Verifique erros no console
2. Leia `README_WEB.md` com mais detalhes
3. Procure solução em: https://stackoverflow.com

## ✅ Resumo Final

| Item | Status |
|------|--------|
| Backend Java | ✅ Pronto |
| Frontend Web | ✅ Pronto |
| API REST | ✅ Pronto |
| Gráficos | ✅ Pronto |
| Upload Arquivos | ✅ Pronto |
| Dados Simulados | ✅ Pronto |

**Tudo está pronto para usar!** 🚀

---

**Versão:** 1.0.0  
**Data:** Abril 2026  
**Status:** ✅ Funcionando
