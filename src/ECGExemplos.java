/**
 * EXEMPLOS DE USO - Sistema de Análise de ECG
 * 
 * Este arquivo demonstra como usar as classes ECG em seus próprios programas
 */

public class ECGExemplos {
    
    /**
     * Exemplo 1: Análise básica de um arquivo
     */
    public static void exemplo1_AnaliseBasica() throws Exception {
        System.out.println("\n=== EXEMPLO 1: Análise Básica ===\n");
        
        // Cria um leitor com 250 Hz de amostragem
        ECGReader reader = new ECGReader(250);
        
        // Lê os dados
        ECGData dados = reader.readFromFile("ecg_data.csv");
        
        // Cria um analisador
        ECGAnalyzer analisador = new ECGAnalyzer(dados);
        
        // Obtém resultados
        System.out.println("BPM: " + analisador.getBPM());
        System.out.println("Batidas: " + analisador.getPeakCount());
        System.out.println("Status: " + analisador.getAnomalyDescription());
    }
    
    /**
     * Exemplo 2: Análise com visualização
     */
    public static void exemplo2_ComVisualizacao() throws Exception {
        System.out.println("\n=== EXEMPLO 2: Com Visualização ===\n");
        
        ECGReader reader = new ECGReader(250);
        ECGData dados = reader.readFromFile("ecg_data.csv");
        ECGAnalyzer analisador = new ECGAnalyzer(dados);
        
        // Cria visualizador
        ECGVisualizer viz = new ECGVisualizer(dados, analisador);
        viz.setSize(1200, 600);  // Define tamanho
        viz.generateAndSaveChart("meu_ecg.png");
        
        System.out.println("✓ Gráfico salvo em: meu_ecg.png");
    }
    
    /**
     * Exemplo 3: Usando dados simulados
     */
    public static void exemplo3_DadosSimulados() throws Exception {
        System.out.println("\n=== EXEMPLO 3: Dados Simulados ===\n");
        
        // Gera 20 segundos de dados a 250 Hz
        ECGData dados = ECGReader.generateSimulatedData(20, 250);
        
        System.out.println("Pontos gerados: " + dados.getSize());
        System.out.println("Duração: " + dados.getDurationSeconds() + " segundos");
        
        // Analisa
        ECGAnalyzer analisador = new ECGAnalyzer(dados);
        System.out.println("BPM simulado: " + analisador.getBPM());
    }
    
    /**
     * Exemplo 4: Processamento customizado
     */
    public static void exemplo4_ProcessamentoCustomizado() throws Exception {
        System.out.println("\n=== EXEMPLO 4: Processamento Customizado ===\n");
        
        ECGReader reader = new ECGReader(250);
        ECGData dados = reader.readFromFile("ecg_data.csv");
        
        // Acessa dados brutos
        java.util.List<Double> voltagens = dados.getVoltages();
        java.util.List<Long> timestamps = dados.getTimestamps();
        
        System.out.println("Primeiro valor: " + voltagens.get(0) + " mV");
        System.out.println("Último valor: " + voltagens.get(voltagens.size()-1) + " mV");
        System.out.println("Amplitude: " + (dados.getMaxVoltage() - dados.getMinVoltage()) + " mV");
        
        // Análise ponto a ponto
        double somaVoltagens = 0;
        for (double v : voltagens) {
            somaVoltagens += Math.abs(v);
        }
        double energiaMedia = somaVoltagens / voltagens.size();
        System.out.println("Energia média: " + energiaMedia + " mV");
    }
    
    /**
     * Exemplo 5: Detecção de anomalias com filtro customizado
     */
    public static void exemplo5_DeteccaoAnomalia() throws Exception {
        System.out.println("\n=== EXEMPLO 5: Detecção de Anomalias ===\n");
        
        ECGReader reader = new ECGReader(250);
        ECGData dados = reader.readFromFile("ecg_data.csv");
        ECGAnalyzer analisador = new ECGAnalyzer(dados);
        
        double bpm = analisador.getBPM();
        
        // Sua lógica customizada
        if (bpm < 50) {
            System.out.println("🚨 ALERTA: Bradicardia severa!");
        } else if (bpm > 130) {
            System.out.println("⚠️ ALERTA: Taquicardia severa!");
        } else if (analisador.isAnomalous()) {
            System.out.println("⚠️ AVISO: " + analisador.getAnomalyDescription());
        } else {
            System.out.println("✅ ECG Normal");
        }
    }
    
    /**
     * Exemplo 6: Monitoramento contínuo (arquivo de lote)
     */
    public static void exemplo6_MonitoramentoEmLote() throws Exception {
        System.out.println("\n=== EXEMPLO 6: Lote de Monitoramento ===\n");
        
        String[] arquivos = {"ecg_data.csv"}; // Adicione mais arquivos aqui
        ECGReader reader = new ECGReader(250);
        
        for (String arquivo : arquivos) {
            try {
                System.out.println("\nProcessando: " + arquivo);
                ECGData dados = reader.readFromFile(arquivo);
                ECGAnalyzer analisador = new ECGAnalyzer(dados);
                
                // Registra resultado
                String status = analisador.isAnomalous() ? "ANÔMALO" : "NORMAL";
                System.out.println(String.format("  → BPM: %.1f | Status: %s", 
                    analisador.getBPM(), status));
                
            } catch (Exception e) {
                System.err.println("  ❌ Erro: " + e.getMessage());
            }
        }
    }
    
    /**
     * Exemplo 7: Criando dados programaticamente
     */
    public static void exemplo7_CriarDadosProgramaticamente() {
        System.out.println("\n=== EXEMPLO 7: Criar Dados Programaticamente ===\n");
        
        ECGData dados = new ECGData(250); // 250 Hz
        
        // Simula 1 segundo de dados
        for (int i = 0; i < 250; i++) {
            double voltagem = 0.1 * Math.sin(2 * Math.PI * i / 100);
            long timestamp = i * 4; // 4ms entre pontos
            dados.addDataPoint(voltagem, timestamp);
        }
        
        System.out.println("Dados criados: " + dados.getSize() + " pontos");
        System.out.println("Duração: " + dados.getDurationSeconds() + " segundos");
        System.out.println("Min/Max: " + dados.getMinVoltage() + " / " + dados.getMaxVoltage());
    }
    
    /**
     * Exemplo 8: Comparação entre pacientes
     */
    public static void exemplo8_ComparacaoPacientes() throws Exception {
        System.out.println("\n=== EXEMPLO 8: Comparação Entre Pacientes ===\n");
        
        ECGReader reader = new ECGReader(250);
        
        System.out.println("PACIENTE A:");
        ECGData dadosA = reader.readFromFile("ecg_data.csv");
        ECGAnalyzer analisadorA = new ECGAnalyzer(dadosA);
        System.out.println("  BPM: " + String.format("%.1f", analisadorA.getBPM()));
        
        // Você poderia ter outro arquivo para paciente B
        System.out.println("\nPACIENTE B:");
        System.out.println("  (Adicione arquivo paciente_b.csv)");
        
        // Análise comparativa
        System.out.println("\nCOMPARAÇÃO:");
        System.out.println("  Paciente A está dentro do normal: " + !analisadorA.isAnomalous());
    }
    
    // ====================================
    // MAIN - Execute os exemplos
    // ====================================
    public static void main(String[] args) {
        try {
            // Descomente os exemplos que quer rodar
            exemplo1_AnaliseBasica();
            exemplo2_ComVisualizacao();
            exemplo3_DadosSimulados();
            exemplo4_ProcessamentoCustomizado();
            exemplo5_DeteccaoAnomalia();
            exemplo6_MonitoramentoEmLote();
            exemplo7_CriarDadosProgramaticamente();
            exemplo8_ComparacaoPacientes();
            
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
