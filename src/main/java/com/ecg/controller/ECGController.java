package com.ecg.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ecg.model.ECGData;
import com.ecg.service.ECGAnalyzer;
import com.ecg.service.ECGReader;
import com.ecg.service.ImageProcessor;

/**
 * REST API Controller para ECG
 */
@RestController
@RequestMapping("/api/ecg")
@CrossOrigin(origins = "*")
public class ECGController {
    
    /**
     * Analisa arquivo ECG enviado
     * POST /api/ecg/analyze
     */
    @PostMapping("/analyze")
    public Map<String, Object> analyzeFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Salva arquivo temporário
            String tempPath = System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename();
            file.transferTo(new java.io.File(tempPath));
            
            // Lê dados
            ECGReader reader = new ECGReader(250);
            ECGData data = reader.readFromFile(tempPath);
            
            if (data.getSize() == 0) {
                response.put("success", false);
                response.put("error", "Nenhum dado foi lido do arquivo");
                return response;
            }
            
            // Analisa
            ECGAnalyzer analyzer = new ECGAnalyzer(data);
            
            // Prepara resposta
            response.put("success", true);
            response.put("bpm", analyzer.getBPM());
            response.put("peakCount", analyzer.getPeakCount());
            response.put("duration", data.getDurationSeconds());
            response.put("minVoltage", data.getMinVoltage());
            response.put("maxVoltage", data.getMaxVoltage());
            response.put("avgVoltage", data.getAverageVoltage());
            response.put("isAnomalous", analyzer.isAnomalous());
            response.put("anomalyDescription", analyzer.getAnomalyDescription());
            response.put("hasInfarction", analyzer.hasInfarction());
            response.put("infarctionDescription", analyzer.getInfarctionDescription());
            response.put("infarctionSeverity", analyzer.getInfarctionSeverity());
            response.put("stElevation", analyzer.getSTElevation());
            response.put("hasConductionDisorder", analyzer.hasConductionDisorder());
            response.put("conductionDescription", analyzer.getConductionDescription());
            response.put("branchBlockType", analyzer.getBranchBlockType());
            response.put("avBlockType", analyzer.getAVBlockType());
            response.put("prInterval", analyzer.getPRInterval());
            response.put("qrsWidth", analyzer.getQRSWidth());
            response.put("hasInflammation", analyzer.hasInflammation());
            response.put("inflammationDescription", analyzer.getInflammationDescription());
            response.put("inflammationType", analyzer.getInflammationType());
            response.put("diffuseSTElevation", analyzer.getDiffuseSTElevation());            response.put("hasDrugEffect", analyzer.hasDrugEffect());
            response.put("drugEffectDescription", analyzer.getDrugEffectDescription());
            response.put("drugType", analyzer.getDrugType());
            response.put("stDepression", analyzer.getSTDepression());
            response.put("qtInterval", analyzer.getQTInterval());            response.put("voltages", data.getVoltages());
            response.put("timestamps", data.getTimestamps());
            response.put("peakIndices", analyzer.getPeakIndices());
            
            // Limpa arquivo
            new java.io.File(tempPath).delete();
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            e.printStackTrace();
        }
        
        return response;
    }
    
    /**
     * Analisa imagem de ECG
     * POST /api/ecg/analyze-image
     */
    @PostMapping("/analyze-image")
    public Map<String, Object> analyzeImage(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Valida tipo de arquivo
            String originalFilename = file.getOriginalFilename();
            if (!originalFilename.matches(".*\\.(png|jpg|jpeg|bmp|gif)$")) {
                response.put("success", false);
                response.put("error", "Formato de imagem inválido. Use PNG, JPG, JPEG, BMP ou GIF");
                return response;
            }
            
            // Salva arquivo temporário
            String tempPath = System.getProperty("java.io.tmpdir") + "/" + System.currentTimeMillis() + "_" + originalFilename;
            file.transferTo(new java.io.File(tempPath));
            
            // Processa imagem
            ImageProcessor processor = new ImageProcessor(250);
            ECGData data = processor.processImageAdvanced(tempPath);
            
            if (data.getSize() == 0) {
                response.put("success", false);
                response.put("error", "Nenhum dado foi extraído da imagem");
                new java.io.File(tempPath).delete();
                return response;
            }
            
            // Analisa
            ECGAnalyzer analyzer = new ECGAnalyzer(data);
            
            // Prepara resposta
            response.put("success", true);
            response.put("bpm", analyzer.getBPM());
            response.put("peakCount", analyzer.getPeakCount());
            response.put("duration", data.getDurationSeconds());
            response.put("minVoltage", data.getMinVoltage());
            response.put("maxVoltage", data.getMaxVoltage());
            response.put("avgVoltage", data.getAverageVoltage());
            response.put("isAnomalous", analyzer.isAnomalous());
            response.put("anomalyDescription", analyzer.getAnomalyDescription());
            response.put("hasInfarction", analyzer.hasInfarction());
            response.put("infarctionDescription", analyzer.getInfarctionDescription());
            response.put("infarctionSeverity", analyzer.getInfarctionSeverity());
            response.put("stElevation", analyzer.getSTElevation());
            response.put("hasConductionDisorder", analyzer.hasConductionDisorder());
            response.put("conductionDescription", analyzer.getConductionDescription());
            response.put("branchBlockType", analyzer.getBranchBlockType());
            response.put("avBlockType", analyzer.getAVBlockType());
            response.put("prInterval", analyzer.getPRInterval());
            response.put("qrsWidth", analyzer.getQRSWidth());
            response.put("hasInflammation", analyzer.hasInflammation());
            response.put("inflammationDescription", analyzer.getInflammationDescription());
            response.put("inflammationType", analyzer.getInflammationType());
            response.put("diffuseSTElevation", analyzer.getDiffuseSTElevation());
            response.put("hasDrugEffect", analyzer.hasDrugEffect());
            response.put("drugEffectDescription", analyzer.getDrugEffectDescription());
            response.put("drugType", analyzer.getDrugType());
            response.put("stDepression", analyzer.getSTDepression());
            response.put("qtInterval", analyzer.getQTInterval());
            response.put("voltages", data.getVoltages());
            response.put("timestamps", data.getTimestamps());
            response.put("peakIndices", analyzer.getPeakIndices());
            response.put("source", "image");
            response.put("message", "Imagem processada com sucesso! " + data.getSize() + " pontos extraídos");
            
            // Limpa arquivo
            new java.io.File(tempPath).delete();
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Erro ao processar imagem: " + e.getMessage());
            e.printStackTrace();
        }
        
        return response;
    }
    
    /**
     * Gera dados simulados de ECG
     * GET /api/ecg/simulate?duration=10
     */
    @GetMapping("/simulate")
    public Map<String, Object> simulateECG(
            @RequestParam(value = "duration", defaultValue = "10") int duration) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Gera dados simulados
            ECGData data = ECGReader.generateSimulatedData(duration, 250);
            
            // Analisa
            ECGAnalyzer analyzer = new ECGAnalyzer(data);
            
            // Prepara resposta
            response.put("success", true);
            response.put("bpm", analyzer.getBPM());
            response.put("peakCount", analyzer.getPeakCount());
            response.put("duration", data.getDurationSeconds());
            response.put("minVoltage", data.getMinVoltage());
            response.put("maxVoltage", data.getMaxVoltage());
            response.put("avgVoltage", data.getAverageVoltage());
            response.put("isAnomalous", analyzer.isAnomalous());
            response.put("anomalyDescription", analyzer.getAnomalyDescription());
            response.put("hasInfarction", analyzer.hasInfarction());
            response.put("infarctionDescription", analyzer.getInfarctionDescription());
            response.put("infarctionSeverity", analyzer.getInfarctionSeverity());
            response.put("stElevation", analyzer.getSTElevation());
            response.put("hasConductionDisorder", analyzer.hasConductionDisorder());
            response.put("conductionDescription", analyzer.getConductionDescription());
            response.put("branchBlockType", analyzer.getBranchBlockType());
            response.put("avBlockType", analyzer.getAVBlockType());
            response.put("prInterval", analyzer.getPRInterval());
            response.put("qrsWidth", analyzer.getQRSWidth());
            response.put("hasInflammation", analyzer.hasInflammation());
            response.put("inflammationDescription", analyzer.getInflammationDescription());
            response.put("inflammationType", analyzer.getInflammationType());
            response.put("diffuseSTElevation", analyzer.getDiffuseSTElevation());
            response.put("hasDrugEffect", analyzer.hasDrugEffect());
            response.put("drugEffectDescription", analyzer.getDrugEffectDescription());
            response.put("drugType", analyzer.getDrugType());
            response.put("stDepression", analyzer.getSTDepression());
            response.put("qtInterval", analyzer.getQTInterval());
            response.put("voltages", data.getVoltages());
            response.put("timestamps", data.getTimestamps());
            response.put("peakIndices", analyzer.getPeakIndices());
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Health check
     * GET /api/ecg/health
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("service", "ECG Analyzer API");
        response.put("version", "1.0.0");
        return response;
    }
}
