import java.util.ArrayList;
import java.util.List;

/**
 * Classe para armazenar dados de ECG
 */
public class ECGData {
    private List<Double> voltages;      // Valores de voltagem em mV
    private List<Long> timestamps;      // Timestamps em ms
    private double samplingRate;        // Taxa de amostragem em Hz
    
    public ECGData(double samplingRate) {
        this.voltages = new ArrayList<>();
        this.timestamps = new ArrayList<>();
        this.samplingRate = samplingRate;
    }
    
    /**
     * Adiciona um ponto de dados de ECG
     */
    public void addDataPoint(double voltage, long timestamp) {
        voltages.add(voltage);
        timestamps.add(timestamp);
    }
    
    /**
     * Retorna todos os valores de voltagem
     */
    public List<Double> getVoltages() {
        return new ArrayList<>(voltages);
    }
    
    /**
     * Retorna todos os timestamps
     */
    public List<Long> getTimestamps() {
        return new ArrayList<>(timestamps);
    }
    
    /**
     * Retorna o número de pontos de dados
     */
    public int getSize() {
        return voltages.size();
    }
    
    /**
     * Retorna a taxa de amostragem
     */
    public double getSamplingRate() {
        return samplingRate;
    }
    
    /**
     * Retorna a duração total em segundos
     */
    public double getDurationSeconds() {
        if (timestamps.isEmpty()) return 0;
        return (timestamps.get(timestamps.size() - 1) - timestamps.get(0)) / 1000.0;
    }
    
    /**
     * Retorna a voltagem máxima
     */
    public double getMaxVoltage() {
        return voltages.stream().mapToDouble(Double::doubleValue).max().orElse(0);
    }
    
    /**
     * Retorna a voltagem mínima
     */
    public double getMinVoltage() {
        return voltages.stream().mapToDouble(Double::doubleValue).min().orElse(0);
    }
    
    /**
     * Retorna a voltagem média
     */
    public double getAverageVoltage() {
        return voltages.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }
}
