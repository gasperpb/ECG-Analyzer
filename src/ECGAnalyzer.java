import java.util.ArrayList;
import java.util.List;

/**
 * Classe para análise de dados de ECG
 * Calcula BPM, detecta anomalias e ritmo cardíaco
 */
public class ECGAnalyzer {
    private ECGData data;
    private List<Integer> peakIndices;
    private double bpm;
    private boolean isAnomalous;
    private String anomalyDescription;
    
    public ECGAnalyzer(ECGData data) {
        this.data = data;
        this.peakIndices = new ArrayList<>();
        this.isAnomalous = false;
        this.anomalyDescription = "";
        analyze();
    }
    
    /**
     * Realiza análise completa do ECG
     */
    private void analyze() {
        if (data.getSize() < 3) {
            System.err.println("Dados insuficientes para análise");
            return;
        }
        
        // Detecta picos (batidas do coração)
        detectPeaks();
        
        // Calcula BPM
        calculateBPM();
        
        // Detecta anomalias
        detectAnomalies();
    }
    
    /**
     * Detecta picos usando algoritmo de detecção de máximos locais
     */
    private void detectPeaks() {
        List<Double> voltages = data.getVoltages();
        double threshold = (data.getMaxVoltage() + data.getMinVoltage()) / 2.0 + 
                          (data.getMaxVoltage() - data.getMinVoltage()) * 0.3;
        
        int minDistanceBetweenPeaks = (int) (data.getSamplingRate() * 0.4); // Mínimo 400ms entre picos
        
        for (int i = 1; i < voltages.size() - 1; i++) {
            double current = voltages.get(i);
            double previous = voltages.get(i - 1);
            double next = voltages.get(i + 1);
            
            // Verifica se é um máximo local acima do threshold
            if (current > previous && current > next && current > threshold) {
                // Verifica distância mínima do último pico
                if (peakIndices.isEmpty() || (i - peakIndices.get(peakIndices.size() - 1)) >= minDistanceBetweenPeaks) {
                    peakIndices.add(i);
                }
            }
        }
    }
    
    /**
     * Calcula BPM (batidas por minuto)
     */
    private void calculateBPM() {
        if (peakIndices.size() < 2) {
            bpm = 0;
            return;
        }
        
        // Calcula intervalo médio entre picos
        double totalInterval = 0;
        for (int i = 1; i < peakIndices.size(); i++) {
            int samples = peakIndices.get(i) - peakIndices.get(i - 1);
            totalInterval += samples / data.getSamplingRate(); // Em segundos
        }
        
        double averageInterval = totalInterval / (peakIndices.size() - 1);
        bpm = 60.0 / averageInterval; // Converte para BPM
    }
    
    /**
     * Detecta anomalias no ritmo cardíaco
     */
    private void detectAnomalies() {
        StringBuilder report = new StringBuilder();
        
        if (peakIndices.size() < 2) {
            isAnomalous = true;
            anomalyDescription = "CRÍTICO: Menos de 2 batidas detectadas!";
            return;
        }
        
        // Verifica BPM
        if (bpm < 60) {
            isAnomalous = true;
            report.append("⚠ BRADICARDIA: BPM baixo (").append(String.format("%.1f", bpm)).append(")");
        } else if (bpm > 100) {
            isAnomalous = true;
            report.append("⚠ TAQUICARDIA: BPM alto (").append(String.format("%.1f", bpm)).append(")");
        }
        
        // Verifica variabilidade dos intervalos (detecção de arritmia)
        List<Double> intervals = new ArrayList<>();
        for (int i = 1; i < peakIndices.size(); i++) {
            int samples = peakIndices.get(i) - peakIndices.get(i - 1);
            intervals.add(samples / data.getSamplingRate() * 1000); // Em ms
        }
        
        if (intervals.size() > 1) {
            double meanInterval = intervals.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            double maxDeviation = 0;
            
            for (double interval : intervals) {
                double deviation = Math.abs(interval - meanInterval);
                maxDeviation = Math.max(maxDeviation, deviation);
            }
            
            double deviationPercent = (maxDeviation / meanInterval) * 100;
            
            if (deviationPercent > 20) {
                isAnomalous = true;
                if (report.length() > 0) report.append("\n");
                report.append("⚠ ARRITMIA: Variação alta nos intervalos (").append(String.format("%.1f", deviationPercent)).append("%)");
            }
        }
        
        anomalyDescription = report.length() > 0 ? report.toString() : "✓ Ritmo cardíaco normal";
    }
    
    /**
     * Retorna o BPM (batidas por minuto)
     */
    public double getBPM() {
        return bpm;
    }
    
    /**
     * Retorna o número de picos (batidas) detectados
     */
    public int getPeakCount() {
        return peakIndices.size();
    }
    
    /**
     * Retorna os índices dos picos
     */
    public List<Integer> getPeakIndices() {
        return new ArrayList<>(peakIndices);
    }
    
    /**
     * Verifica se há anomalias
     */
    public boolean isAnomalous() {
        return isAnomalous;
    }
    
    /**
     * Retorna descrição da anomalia
     */
    public String getAnomalyDescription() {
        return anomalyDescription;
    }
    
    /**
     * Imprime relatório de análise
     */
    public void printReport() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RELATÓRIO DE ANÁLISE DO ECG");
        System.out.println("=".repeat(50));
        System.out.println("Batidas detectadas: " + getPeakCount());
        System.out.println("BPM: " + String.format("%.1f", getBPM()));
        System.out.println("Duração: " + String.format("%.2f", data.getDurationSeconds()) + " segundos");
        System.out.println("\nStatus: " + getAnomalyDescription());
        System.out.println("=".repeat(50) + "\n");
    }
}
