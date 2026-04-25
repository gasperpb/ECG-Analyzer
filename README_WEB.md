# 🌐 ECG Analyzer - Aplicação Web

Aplicação web moderna para análise de ECG com Spring Boot + Chart.js

## 🚀 Como Executar

### Opção 1: Maven (Recomendado)

```bash
cd firstprojet

# Limpar build anterior
mvn clean

# Baixar dependências e compilar
mvn install

# Executar a aplicação
mvn spring-boot:run
```

A aplicação estará disponível em: **http://localhost:8080**

### Opção 2: JAR Executável

```bash
# Gerar JAR
mvn package

# Executar
java -jar target/ecg-analyzer-1.0.0.jar
```

### Opção 3: IDE (Eclipse/IntelliJ/VS Code)

1. Importe o projeto como Maven Project
2. Clique com botão direito no projeto → Run As → Maven build
3. Goals: `spring-boot:run`
4. Acesse: http://localhost:8080

## 📋 Requisitos

- **Java 17+** (verificar: `java -version`)
- **Maven 3.8+** (verificar: `mvn -v`)
- Navegador web moderno (Chrome, Firefox, Safari, Edge)

## 📁 Estrutura do Projeto

```
firstprojet/
├── src/
│   ├── main/
│   │   ├── java/com/ecg/
│   │   │   ├── ECGApplication.java        # Classe principal
│   │   │   ├── controller/
│   │   │   │   ├── ECGController.java     # REST API
│   │   │   │   └── ViewController.java    # Views
│   │   │   ├── service/
│   │   │   │   ├── ECGAnalyzer.java       # Análise
│   │   │   │   └── ECGReader.java         # Leitura
│   │   │   └── model/
│   │   │       └── ECGData.java           # Modelo
│   │   └── resources/
│   │       ├── templates/
│   │       │   └── index.html             # Página principal
│   │       └── application.properties     # Configurações
│   └── test/                              # Testes
├── pom.xml                                # Dependências Maven
└── README_WEB.md                          # Este arquivo
```

## 🎯 Funcionalidades

✅ **Upload de arquivos** - Carregue seus dados de ECG em CSV
✅ **Dados simulados** - Gere dados fictícios para teste
✅ **Análise em tempo real** - BPM, picos, anomalias
✅ **Gráficos interativos** - Visualize com Chart.js
✅ **API REST** - Integre com outros sistemas
✅ **Interface responsiva** - Funciona em mobile e desktop

## 📊 API REST

### Endpoints

#### 1. Analisar Arquivo
```
POST /api/ecg/analyze
Content-Type: multipart/form-data

Parâmetro: file (arquivo CSV)

Resposta:
{
  "success": true,
  "bpm": 72.5,
  "peakCount": 5,
  "duration": 3.5,
  "minVoltage": -0.4,
  "maxVoltage": 0.18,
  "avgVoltage": 0.05,
  "isAnomalous": false,
  "anomalyDescription": "Ritmo cardíaco normal",
  "voltages": [0.02, 0.05, ...],
  "timestamps": [0, 4, ...],
  "peakIndices": [75, 150, ...]
}
```

#### 2. Gerar Simulado
```
GET /api/ecg/simulate?duration=10

Parâmetro: duration (segundos, 5-60)

Resposta: (mesma do endpoint 1)
```

#### 3. Health Check
```
GET /api/ecg/health

Resposta:
{
  "status": "OK",
  "service": "ECG Analyzer API",
  "version": "1.0.0"
}
```

## 📥 Formato do Arquivo CSV

```
# Comentário (opcional)
# timestamp(ms),voltagem(mV)
0,0.02
4,0.05
8,0.08
...
```

**Taxa de amostragem esperada:** 250 Hz (intervalo de 4ms)

## 🔧 Configurações

Edite `src/main/resources/application.properties`:

```properties
# Porta do servidor
server.port=8080

# Nome da aplicação
spring.application.name=ECG Analyzer

# Limite de upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Nível de log
logging.level.root=INFO
```

## 🐛 Troubleshooting

### Erro: "Java 17 not found"
```bash
# Instale Java 17+
# Windows: https://www.oracle.com/java/technologies/downloads/
# Linux: sudo apt install openjdk-17-jdk
# macOS: brew install openjdk@17
```

### Erro: "Maven command not found"
```bash
# Instale Maven
# Windows: https://maven.apache.org/download.cgi
# Linux: sudo apt install maven
# macOS: brew install maven
```

### Porta 8080 já em uso
```bash
# Use outra porta
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

### Erro: "ConnectionRefused" na API
- Certifique-se de que o servidor está rodando
- Verifique o console para erros de inicialização
- Confirme a URL: http://localhost:8080

## 📱 Interface Web

### Abas Disponíveis

1. **Upload** - Carregue seu arquivo ECG
2. **Simular** - Gere dados de teste

### Informações Exibidas

- **BPM** - Batidas por minuto
- **Batidas Detectadas** - Número de picos
- **Duração** - Tempo total em segundos
- **Voltagem** - Min, Máx e Média
- **Status** - Normal ou Anômalo
- **Gráfico** - Visualização interativa

## 🔒 CORS

A API está configurada para aceitar requisições de qualquer origem (`*`).
Para produção, altere em `ECGController.java`:

```java
@CrossOrigin(origins = "https://seu-dominio.com")
```

## 📝 Logs

Verifique erros no console:

```bash
# Linux/Mac
mvn spring-boot:run | tail -f

# Windows
mvn spring-boot:run 2>&1 | findstr ERROR
```

## 🚢 Deploy em Produção

### Heroku
```bash
git init
git add .
git commit -m "Initial commit"
heroku create seu-app-name
git push heroku main
```

### Docker
```dockerfile
FROM openjdk:17
COPY target/ecg-analyzer-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
```

## 📚 Documentação Adicional

- [Spring Boot Guide](https://spring.io/projects/spring-boot)
- [Chart.js Docs](https://www.chartjs.org/)
- [REST API Best Practices](https://restfulapi.net/)

## 🤝 Contribuindo

Para melhorias, crie uma issue ou pull request!

## 📄 Licença

Livre para uso comercial e educacional.

---

**Status:** ✅ Pronto para Produção  
**Versão:** 1.0.0  
**Última atualização:** Abril 2026
