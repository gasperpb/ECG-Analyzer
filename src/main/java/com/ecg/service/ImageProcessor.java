package com.ecg.service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import com.ecg.model.ECGData;

/**
 * Processador de imagens de ECG para extrair dados
 * Usando Java puro sem dependências externas
 */
public class ImageProcessor {
    private double samplingRate;
    
    public ImageProcessor(double samplingRate) {
        this.samplingRate = samplingRate;
    }
    
    /**
     * Processa uma imagem de ECG e extrai dados
     */
    public ECGData processImage(String imagePath) throws Exception {
        File imageFile = new File(imagePath);
        
        if (!imageFile.exists()) {
            throw new Exception("Arquivo não encontrado: " + imagePath);
        }
        
        // Lê a imagem
        BufferedImage image = ImageIO.read(imageFile);
        
        if (image == null) {
            throw new Exception("Não foi possível ler a imagem");
        }
        
        ECGData data = extractECGData(image);
        
        if (data.getSize() == 0) {
            throw new Exception("Nenhuma onda detectada na imagem");
        }
        
        return data;
    }
    
    /**
     * Extrai dados ECG de uma imagem
     */
    private ECGData extractECGData(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        
        // Detecta pixels escuros (a onda do ECG)
        List<Integer[]> wavePixels = new ArrayList<>();
        
        // Threshold para detectar pixels da onda
        int threshold = 128;
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int gray = getRGBtoGray(rgb);
                
                // Se pixel é escuro, é parte da onda
                if (gray < threshold) {
                    wavePixels.add(new Integer[]{x, y});
                }
            }
        }
        
        if (wavePixels.isEmpty()) {
            return new ECGData(samplingRate);
        }
        
        // Calcula limites
        int minX = Integer.MAX_VALUE, maxX = -1;
        int minY = Integer.MAX_VALUE, maxY = -1;
        
        for (Integer[] pixel : wavePixels) {
            minX = Math.min(minX, pixel[0]);
            maxX = Math.max(maxX, pixel[0]);
            minY = Math.min(minY, pixel[1]);
            maxY = Math.max(maxY, pixel[1]);
        }
        
        // Agrupa pixels por X para suavizar
        Map<Integer, List<Integer>> xGroups = new TreeMap<>();
        for (Integer[] pixel : wavePixels) {
            xGroups.computeIfAbsent(pixel[0], k -> new ArrayList<>()).add(pixel[1]);
        }
        
        // Cria dados
        ECGData data = new ECGData(samplingRate);
        
        // Assume 10 segundos de duração
        double pixelPerSecond = (maxX - minX) / 10.0;
        if (pixelPerSecond <= 0) pixelPerSecond = 1;
        
        // Escala de voltagem: -2.5mV a 2.5mV
        double voltsRange = 5.0;
        double voltsPerPixel = voltsRange / (maxY - minY);
        if (voltsPerPixel <= 0) voltsPerPixel = 1;
        
        double centerVoltage = 0;
        
        long startTime = 0;
        
        for (Integer x : xGroups.keySet()) {
            List<Integer> yValues = xGroups.get(x);
            
            // Média dos Y para suavizar
            double avgY = yValues.stream().mapToDouble(Double::valueOf).average().orElse(minY);
            
            // Converte em timestamp (milisegundos)
            long timestamp = startTime + (long)((x - minX) * 1000 / pixelPerSecond);
            
            // Converte em voltagem
            // Inverte porque Y cresce para baixo na imagem
            double voltage = centerVoltage - (avgY - (minY + maxY) / 2.0) * voltsPerPixel;
            
            data.addDataPoint(voltage, timestamp);
        }
        
        return data;
    }
    
    /**
     * Processa imagem com deteção avançada
     */
    public ECGData processImageAdvanced(String imagePath) throws Exception {
        return processImage(imagePath);
    }
    
    /**
     * Converte RGB para escala de cinza
     */
    private int getRGBtoGray(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        
        // Fórmula padrão para conversão
        return (int)(0.299 * r + 0.587 * g + 0.114 * b);
    }
}
