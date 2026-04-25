import java.io.IOException;

/**
 * Programa principal para análise de ECG
 * Demonstra o uso do leitor, analisador e visualizador de ECG
 */
public class ECGApp {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║    LEITOR DE ECG - ANÁLISE CARDÍACA    ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        try {
            // ============================================
            // OPÇÃO 1: Usar dados de um arquivo CSV
            // ============================================
            analyzeFromFile();
            
            // ============================================
            // OPÇÃO 2: Usar dados simulados (descomente para usar)
            // ============================================
            // analyzeSimulatedData();
            
        } catch (Exception e) {
            System.err.println("Erro durante execução: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Análise de ECG a partir de arquivo CSV
     */
    public static void analyzeFromFile() throws IOException {
        System.out.println("1️⃣  Lendo arquivo de ECG...");
        
        // Define a taxa de amostragem (frequência de leitura em Hz)
        double samplingRate = 250; // 250 amostras por segundo
        
        // Cria o leitor
        ECGReader reader = new ECGReader(samplingRate);
        
        // Lê os dados do arquivo
        String filePath = "ecg_data.csv";
        ECGData data = reader.readFromFile(filePath);
        
        if (data.getSize() == 0) {
            System.err.println("❌ Nenhum dado foi lido do arquivo!");
            return;
        }
        
        // ============================================
        // Análise dos dados
        // ============================================
        System.out.println("\n2️⃣  Analisando dados de ECG...");
        ECGAnalyzer analyzer = new ECGAnalyzer(data);
        analyzer.printReport();
        
        // ============================================
        // Geração do gráfico
        // ============================================
        System.out.println("3️⃣  Gerando visualização...");
        ECGVisualizer visualizer = new ECGVisualizer(data, analyzer);
        visualizer.setSize(1400, 700);
        
        String outputPath = "ecg_output.png";
        visualizer.generateAndSaveChart(outputPath);
        
        System.out.println("\n✅ Análise concluída com sucesso!");
        System.out.println("📊 Abra '" + outputPath + "' para visualizar o gráfico");
    }
    
    /**
     * Análise de ECG com dados simulados
     */
    public static void analyzeSimulatedData() throws IOException {
        System.out.println("1️⃣  Gerando dados simulados de ECG...");
        
        // Cria dados simulados (10 segundos de duração, 250 Hz de amostragem)
        ECGData data = ECGReader.generateSimulatedData(10, 250);
        
        System.out.println("✓ Dados gerados: " + data.getSize() + " pontos");
        
        // ============================================
        // Análise dos dados
        // ============================================
        System.out.println("\n2️⃣  Analisando dados de ECG...");
        ECGAnalyzer analyzer = new ECGAnalyzer(data);
        analyzer.printReport();
        
        // ============================================
        // Geração do gráfico
        // ============================================
        System.out.println("3️⃣  Gerando visualização...");
        ECGVisualizer visualizer = new ECGVisualizer(data, analyzer);
        visualizer.setSize(1400, 700);
        
        String outputPath = "ecg_simulated_output.png";
        visualizer.generateAndSaveChart(outputPath);
        
        System.out.println("\n✅ Análise concluída com sucesso!");
        System.out.println("📊 Abra '" + outputPath + "' para visualizar o gráfico");
    }
}
