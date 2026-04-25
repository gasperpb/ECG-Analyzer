package com.ecg.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import com.ecg.model.ECGData;

/**
 * Classe para ler dados de ECG
 */
public class ECGReader {
    private double samplingRate;
    
    public ECGReader(double samplingRate) {
        this.samplingRate = samplingRate;
    }
    
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
                
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                
                try {
                    String[] parts = line.split(",");
                    if (parts.length < 2) {
                        continue;
                    }
                    
                    long timestamp = Long.parseLong(parts[0].trim());
                    double voltage = Double.parseDouble(parts[1].trim());
                    data.addDataPoint(voltage, timestamp);
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao processar linha " + lineCount + ": " + line);
                }
            }
        }
        
        return data;
    }
    
    public static ECGData generateSimulatedData(int durationSeconds, double samplingRate) {
        ECGData data = new ECGData(samplingRate);
        int totalPoints = (int) (durationSeconds * samplingRate);
        
        double heartRate = 72;
        double cycleLength = (60.0 / heartRate) * samplingRate;
        
        for (int i = 0; i < totalPoints; i++) {
            double t = i / samplingRate;
            double cyclePosition = (i % (int) cycleLength) / cycleLength;
            
            double voltage = 0;
            
            if (cyclePosition < 0.2) {
                voltage = 0.15 * Math.sin(Math.PI * cyclePosition / 0.2);
            }
            else if (cyclePosition >= 0.3 && cyclePosition < 0.45) {
                double qrs = (cyclePosition - 0.3) / 0.15;
                voltage = -0.5 * Math.sin(Math.PI * qrs) + 0.3 * Math.sin(2 * Math.PI * qrs);
            }
            else if (cyclePosition >= 0.55 && cyclePosition < 0.8) {
                voltage = 0.2 * Math.sin(Math.PI * (cyclePosition - 0.55) / 0.25);
            }
            
            voltage += (Math.random() - 0.5) * 0.05;
            
            long timestamp = (long) (i * 1000.0 / samplingRate);
            data.addDataPoint(voltage, timestamp);
        }
        
        return data;
    }
}
