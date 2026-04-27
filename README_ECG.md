# 📊 Leitor de ECG - Sistema de Análise Cardíaca

Um sistema Java completo para leitura, análise e visualização de dados de **Eletrocardiograma (ECG)**.

## ✨ Funcionalidades

- ✅ **Leitura de ECG** a partir de arquivos CSV
- ✅ **Cálculo de BPM** (batidas por minuto)
- ✅ **Detecção de Anomalias**:
  - Bradicardia (BPM < 60)
  - Taquicardia (BPM > 100)
  - Arritmias (variação alta nos intervalos)
- ✅ **Geração de Gráficos** em PNG com visualização clara
- ✅ **Dados Simulados** para teste sem arquivo
- ✅ **Relatório Detalhado** com análise completa

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
- **DPOC/Enfisema** - Sinais de sobrecarga crônica devido à dificuldade de bombear sangue

### 13. Outras Condições
- Hipotermia (com Onda de Osborn) | Apneia do Sono | Síncope Vasovagal

### 14. Alterações Metabólicas e Hormonais 🆕
- **Hipotireoidismo** - Bradicardia + Diminuição da voltagem das ondas + Ritmo muito lento
- **Hipertireoidismo** - Taquicardia + Taquicardias ou arritmias como fibrilação atrial

## 📁 Estrutura do Projeto

```
firstprojet/
├── src/
│   ├── ECGApp.java           # Programa principal
│   ├── ECGData.java          # Armazenamento de dados
│   ├── ECGReader.java        # Leitura de arquivos e dados simulados
│   ├── ECGAnalyzer.java      # Análise e processamento
│   ├── ECGVisualizer.java    # Geração de gráficos
│   └── Carro.java            # Código anterior
├── bin/                       # Arquivos compilados
├── ecg_data.csv              # Arquivo de dados de exemplo
├── ecg_output.png            # Gráfico de saída
└── README.md                 # Este arquivo
```

## 🚀 Como Usar

### 1️⃣ Compilar

```bash
cd firstprojet
javac -d bin src/*.java
```

### 2️⃣ Executar

```bash
java -cp bin ECGApp
```

### 3️⃣ Resultado

O programa irá:
1. Ler dados do arquivo `ecg_data.csv`
2. Processar e analisar o ECG
3. Gerar um gráfico PNG: `ecg_output.png`
4. Exibir relatório no console

## 📋 Formato do Arquivo CSV

O arquivo deve conter dados no formato:
```
# Comentário (linhas com # são ignoradas)
timestamp(ms),voltagem(mV)
0,0.02
4,0.05
8,0.08
...
```

**Campos:**
- `timestamp`: Tempo em milissegundos (ms)
- `voltagem`: Valor de voltagem em milivolts (mV)

**Exemplo completo em `ecg_data.csv`**

## 📊 Saída do Programa

### Console
```
╔════════════════════════════════════════╗
║    LEITOR DE ECG - ANÁLISE CARDÍACA    ║
╚════════════════════════════════════════╝

1️⃣  Lendo arquivo de ECG...
✓ Leitura concluída: 189 pontos de dados
  Duração: 0,75 segundos
  Voltagem min: -0,40 mV
  Voltagem máx: 0,18 mV

2️⃣  Analisando dados de ECG...

==================================================
RELATÓRIO DE ANÁLISE DO ECG
==================================================
Batidas detectadas: 2
BPM: 117,2
Duração: 0,75 segundos

Status: ⚠ TAQUICARDIA: BPM alto (117,2)
==================================================

3️⃣  Gerando visualização...
✓ Gráfico salvo em: ecg_output.png
```

### Gráfico PNG
- Mostra o sinal de ECG em **azul**
- Marca os picos detectados em **vermelho**
- Exibe informações de análise no topo
- Grade de referência para leitura

## 🔧 Classes Principais

### `ECGData`
Armazena e gerencia dados de ECG:
- `addDataPoint(voltage, timestamp)` - Adiciona um ponto
- `getVoltages()` - Retorna todos os valores
- `getMaxVoltage()`, `getMinVoltage()` - Valores extremos
- `getDurationSeconds()` - Duração total

### `ECGReader`
Lê dados de arquivos:
- `readFromFile(filePath)` - Lê arquivo CSV
- `generateSimulatedData(duration, rate)` - Gera dados fictícios

### `ECGAnalyzer`
Análise de ECG:
- `getBPM()` - Retorna BPM calculado
- `getPeakCount()` - Número de batidas
- `isAnomalous()` - Verifica anomalias
- `getAnomalyDescription()` - Descrição da anomalia

### `ECGVisualizer`
Gera visualização:
- `setSize(width, height)` - Define dimensões
- `generateAndSaveChart(outputPath)` - Cria e salva PNG

### `ECGApp`
Programa orquestrador:
- `analyzeFromFile()` - Análise a partir de arquivo
- `analyzeSimulatedData()` - Análise com dados simulados

## 🎯 Parâmetros Ajustáveis

Você pode modificar em `ECGApp.java`:

```java
// Taxa de amostragem (Hz)
double samplingRate = 250;

// Dimensões do gráfico
visualizer.setSize(1400, 700);

// Limiares de detecção de anomalia em `ECGAnalyzer.java`
if (bpm < 60) { /* Bradicardia */ }
if (bpm > 100) { /* Taquicardia */ }
```

## 🧬 Algoritmo de Detecção de Picos

1. Define threshold = média dos voltages + 30% da amplitude
2. Identifica máximos locais acima do threshold
3. Garante distância mínima entre picos (400ms)
4. Calcula BPM a partir dos intervalos

## 🔍 Interpretação de Resultados

| Métrica | Normal | Alerta |
|---------|--------|--------|
| **BPM** | 60-100 | < 60 (Bradicardia) ou > 100 (Taquicardia) |
| **Variabilidade** | < 20% | > 20% (Possível Arritmia) |
| **Sinal** | Claro com picos | Ruidoso ou irregular |

## 🚨 Exemplos de Análise

### ✅ ECG Normal
- BPM: 72
- Batidas regulares
- Sem anomalias detectadas

### ⚠️ Taquicardia
- BPM: 120+
- Batidas rápidas demais
- Alerta em cor vermelha

### ⚠️ Arritmia
- Intervalos variáveis entre batidas
- Padrão irregular
- Possível fibrilação ou extra-sístole

## 📝 Casos de Uso

1. **Monitoramento Cardíaco**: Análise de pacientes
2. **Pesquisa Médica**: Processamento de datasets
3. **Educação**: Aprendizado em cardiologia
4. **Prototipagem**: Base para sistemas IoT

## 🛠️ Melhorias Futuras

- [ ] Interface gráfica (Swing/JavaFX)
- [ ] Leitura em tempo real de sensores
- [ ] Exportação de relatórios em PDF
- [ ] Detecção avançada de arritmias (ML)
- [ ] Banco de dados para histórico
- [ ] API REST para integração

## 📚 Referências

- **ECG Basics**: https://www.heart.org
- **Algoritmos de Detecção**: Pan-Tompkins Algorithm
- **Frequência Cardíaca**: OMS/WHO standards

## 📄 Licença

Livre para uso educacional e comercial.

## 👨‍💻 Autor

Desenvolvido em Java para fins educacionais e de pesquisa em cardiologia digital.

---

**Última atualização**: Abril 2026  
**Versão**: 1.0.0
