# 📊 ECG Analyzer - Sistema Web de Análise Cardíaca

Um **sistema completo Java + Spring Boot** para análise, processamento e visualização de dados de **Eletrocardiograma (ECG)** com interface web moderna.

## ✨ Funcionalidades Principais

- ✅ **Backend REST API** em Spring Boot
- ✅ **Análise avançada de ECG** com 45+ diagnósticos
- ✅ **Detecção inteligente** de anomalias cardíacas
- ✅ **Interface Web Responsiva** com gráficos interativos
- ✅ **Upload de arquivos** de ECG
- ✅ **Dados Simulados** para teste
- ✅ **Relatórios detalhados** com interpretação clínica

## 🏥 Condições Clínicas Detectadas (45+ Diagnósticos)

### 1. Anomalias Básicas
- Anomalias gerais | BPM (Taquicardia, Bradicardia)

### 2. Infartos e Isquemias ❤️
- Infarto do Miocárdio | Isquemia Silenciosa | Angina de Prinzmetal
- Espasmo Coronariano | Síndrome de Takotsubo

### 3. Desordens de Condução ⚡
- Bloqueio de Ramo (RBBB, LBBB) | Bloqueio AV (1º, 2º, 3º grau)
- Intervalos PR prolongados | QRS alargado

### 4. Arritmias
- Fibrilação Atrial | Flutter Atrial | Taquicardia Supraventricular
- Taquicardia Ventricular | Fibrilação Ventricular | Batidas Ectópicas

### 5. Síndromes Congênitas
- Síndrome de Wolff-Parkinson-White (WPW) | Síndrome de Brugada
- QT Longo | QT Curto

### 6. Desordens de Eletrólitos ⚗️
- Hipercalemia (potássio alto) | Hipocalemia (potássio baixo)
- Hipercalcemia (cálcio alto) | Hipocalcemia (cálcio baixo)

### 7. Padrões Especiais
- Padrão de Wellens | Onda de Osborn | Repolarização Precoce | Onda Épsilon

### 8. Hipertrofias
- Hipertrofia Ventricular Esquerda (LVH) | Hipertrofia Ventricular Direita (RVH)
- Sobrecarga Atrial

### 9. Inflamação e Pericardite 🫀
- Miocardite | Pericardite

### 10. Efeitos de Drogas 💊
- Efeitos de medicamentos (Digoxina, etc) | Padrões de intoxicação

### 11. Lesões Pós-Infarto
- Cicatriz Miocárdica | Aneurisma Ventricular | Arritmias Pós-Infarto

### 12. Condições Pulmonares e Vasculares 🫁
- **Embolia Pulmonar** - Alterações súbitas no eixo elétrico do coração
- **Hipertensão Pulmonar** - Hipertrofia RV + Intervalo PR prolongado
- **DPOC/Enfisema** - Sinais de sobrecarga crônica devido à dificuldade de bombear sangue pelos pulmões

### 13. Outras Condições
- Hipotermia (com Onda de Osborn) | Apneia do Sono | Síncope Vasovagal

### 14. Alterações Metabólicas e Hormonais 🆕
- **Hipotireoidismo** - Bradicardia + Diminuição da voltagem das ondas + Ritmo muito lento
- **Hipertireoidismo** - Taquicardia + Taquicardias ou arritmias como fibrilação atrial

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+
- Git

### Instalação

```bash
# 1. Clone o repositório
git clone https://github.com/gasperpb/ECG-Analyzer.git
cd ECG-Analyzer

# 2. Compile o projeto
mvn clean compile

# 3. Execute a aplicação
mvn spring-boot:run
```

### Acesso
- **URL**: http://localhost:8080
- **API REST**: http://localhost:8080/api/ecg

## 📁 Estrutura do Projeto

```
ECG-Analyzer/
├── src/main/
│   ├── java/com/ecg/
│   │   ├── ECGApplication.java        # Aplicação principal Spring Boot
│   │   ├── controller/
│   │   │   ├── ECGController.java     # API REST endpoints
│   │   │   └── ViewController.java    # Views web
│   │   ├── service/
│   │   │   ├── ECGAnalyzer.java       # Motor de análise (45+ diagnósticos)
│   │   │   ├── ECGReader.java         # Leitura de ECG
│   │   │   └── ImageProcessor.java    # Processamento de imagens
│   │   ├── model/
│   │   │   └── ECGData.java           # Modelo de dados
│   │   └── config/
│   │       └── WebConfig.java         # Configurações web
│   └── resources/
│       ├── templates/index.html       # Interface web
│       └── application.properties     # Configurações
├── pom.xml                            # Dependências Maven
└── README.md                          # Este arquivo
```

## 🔌 API REST Endpoints

```
GET  /api/ecg/analyze          # Analisa dados de ECG
POST /api/ecg/upload           # Upload de arquivo ECG
GET  /api/ecg/status           # Status da análise
```

## 🛠️ Tecnologias

- **Backend**: Java 17, Spring Boot 3.x
- **Frontend**: HTML5, CSS3, JavaScript, Chart.js
- **Build**: Maven
- **Database**: Em memória (extensível)

## 📊 Dados de Entrada

### Formato CSV
```
timestamp(ms),voltagem(mV)
0,0.02
4,0.05
8,0.08
```

### Formato JSON
```json
{
  "samplingRate": 250,
  "duration": 10.5,
  "data": [0.02, 0.05, 0.08, ...]
}
```

## 📈 Saída da Análise

Cada análise retorna:
- ✅ BPM detectado
- ✅ Tipo de ritmo cardíaco
- ✅ Anomalias identificadas
- ✅ Descrição clínica
- ✅ Indicadores de severidade
- ✅ Recomendações

## 🔧 Configuração

Edite `application.properties`:

```properties
server.port=8080
spring.application.name=ECG-Analyzer
```

## 📚 Documentação Adicional

- [README_ECG.md](README_ECG.md) - Guia detalhado de uso
- [README_WEB.md](README_WEB.md) - Documentação da interface web

## 🤝 Contribuições

Contribuições são bem-vindas! Por favor:

1. Faça um Fork
2. Crie uma branch (`git checkout -b feature/sua-feature`)
3. Commit (`git commit -m 'Add: sua feature'`)
4. Push (`git push origin feature/sua-feature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT.

## 👨‍💼 Autor

**Gasper PB** - [GitHub](https://github.com/gasperpb)

## 🙏 Agradecimentos

- Comunidade de desenvolvimento Java
- Spring Boot team
- Chart.js para visualizações
