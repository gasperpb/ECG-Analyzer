package com.ecg.model;

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
    
    public void addDataPoint(double voltage, long timestamp) {
        voltages.add(voltage);
        timestamps.add(timestamp);
    }
    
    public List<Double> getVoltages() {
        return new ArrayList<>(voltages);
    }
    
    public List<Long> getTimestamps() {
        return new ArrayList<>(timestamps);
    }
    
    public int getSize() {
        return voltages.size();
    }
    
    public double getSamplingRate() {
        return samplingRate;
    }
    
    public double getDurationSeconds() {
        if (timestamps.isEmpty()) return 0;
        return (timestamps.get(timestamps.size() - 1) - timestamps.get(0)) / 1000.0;
    }
    
    public double getMaxVoltage() {
        return voltages.stream().mapToDouble(Double::doubleValue).max().orElse(0);
    }
    
    public double getMinVoltage() {
        return voltages.stream().mapToDouble(Double::doubleValue).min().orElse(0);
    }
    
    public double getAverageVoltage() {
        return voltages.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }
}
