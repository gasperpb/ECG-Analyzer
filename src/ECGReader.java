import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe para ler dados de ECG de um arquivo
 * Formato esperado: timestamp(ms),voltagem(mV)
 * Exemplo:
 * 0,0.5
 * 10,0.6
 * 20,0.7
 */
public class ECGReader {
    private double samplingRate; // Hz
    
    public ECGReader(double samplingRate) {
        this.samplingRate = samplingRate;
    }
    
    /**
     * Lê dados de ECG de um arquivo CSV
     */
    public ECGData readFromFile(String filePath) throws IOException {
        ECGData data = new ECGData(samplingRate);
        
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Arquivo não encontrado: " + filePath);
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineCount = 0;
            
            while ((line = reader.readLine()) != null) {
                lineCount++;
                
                // Ignora linhas vazias e comentários
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                
                try {
                    String[] parts = line.split(",");
                    if (parts.length < 2) {
                        System.err.println("Linha " + lineCount + " inválida: " + line);
                        continue;
                    }
                    
                    long timestamp = Long.parseLong(parts[0].trim());
                    double voltage = Double.parseDouble(parts[1].trim());
                    
                    data.addDataPoint(voltage, timestamp);
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao processar linha " + lineCount + ": " + line);
                    System.err.println("Erro: " + e.getMessage());
                }
            }
            
            System.out.println("✓ Leitura concluída: " + data.getSize() + " pontos de dados");
            System.out.println("  Duração: " + String.format("%.2f", data.getDurationSeconds()) + " segundos");
            System.out.println("  Voltagem min: " + String.format("%.2f", data.getMinVoltage()) + " mV");
            System.out.println("  Voltagem máx: " + String.format("%.2f", data.getMaxVoltage()) + " mV");
            
        }
        
        return data;
    }
    
    /**
     * Gera dados de ECG simulados (para teste)
     * Simula um sinal de ECG típico
     */
    public static ECGData generateSimulatedData(int durationSeconds, double samplingRate) {
        ECGData data = new ECGData(samplingRate);
        int totalPoints = (int) (durationSeconds * samplingRate);
        
        // Simula um sinal ECG com padrão P-QRS-T repetido
        double heartRate = 72; // BPM
        double cycleLength = (60.0 / heartRate) * samplingRate;
        
        for (int i = 0; i < totalPoints; i++) {
            double t = i / samplingRate;
            double cyclePosition = (i % (int) cycleLength) / cycleLength;
            
            // Componentes do ECG
            double voltage = 0;
            
            // Onda P (0-0.2)
            if (cyclePosition < 0.2) {
                voltage = 0.15 * Math.sin(Math.PI * cyclePosition / 0.2);
            }
            // Complexo QRS (0.3-0.45)
            else if (cyclePosition >= 0.3 && cyclePosition < 0.45) {
                double qrs = (cyclePosition - 0.3) / 0.15;
                voltage = -0.5 * Math.sin(Math.PI * qrs) + 0.3 * Math.sin(2 * Math.PI * qrs);
            }
            // Onda T (0.55-0.8)
            else if (cyclePosition >= 0.55 && cyclePosition < 0.8) {
                voltage = 0.2 * Math.sin(Math.PI * (cyclePosition - 0.55) / 0.25);
            }
            
            // Adiciona ruído
            voltage += (Math.random() - 0.5) * 0.05;
            
            long timestamp = (long) (i * 1000.0 / samplingRate);
            data.addDataPoint(voltage, timestamp);
        }
        
        return data;
    }
}
