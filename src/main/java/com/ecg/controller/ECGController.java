package com.ecg.controller;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.ecg.model.ECGData;
import com.ecg.service.ECGAnalyzer;
import com.ecg.service.ECGReader;
import com.ecg.service.ImageProcessor;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Text;

/**
 * REST API Controller para ECG
 */
@RestController
@RequestMapping("/api/ecg")
@CrossOrigin(origins = "*")
public class ECGController {
    
    private void addAllDiagnostics(Map<String, Object> response, ECGAnalyzer analyzer) {
        // Diagnósticos básicos
        response.put("bpm", analyzer.getBPM());
        response.put("peakCount", analyzer.getPeakCount());
        response.put("isAnomalous", analyzer.isAnomalous());
        response.put("anomalyDescription", analyzer.getAnomalyDescription());
        
        // Infarto
        response.put("hasInfarction", analyzer.hasInfarction());
        response.put("infarctionDescription", analyzer.getInfarctionDescription());
        response.put("infarctionSeverity", analyzer.getInfarctionSeverity());
        response.put("stElevation", analyzer.getSTElevation());
        
        // Condução
        response.put("hasConductionDisorder", analyzer.hasConductionDisorder());
        response.put("conductionDescription", analyzer.getConductionDescription());
        response.put("branchBlockType", analyzer.getBranchBlockType());
        response.put("avBlockType", analyzer.getAVBlockType());
        response.put("prInterval", analyzer.getPRInterval());
        response.put("qrsWidth", analyzer.getQRSWidth());
        
        // Inflamação
        response.put("hasInflammation", analyzer.hasInflammation());
        response.put("inflammationDescription", analyzer.getInflammationDescription());
        response.put("inflammationType", analyzer.getInflammationType());
        response.put("diffuseSTElevation", analyzer.getDiffuseSTElevation());
        
        // Medicamentos
        response.put("hasDrugEffect", analyzer.hasDrugEffect());
        response.put("drugEffectDescription", analyzer.getDrugEffectDescription());
        response.put("drugType", analyzer.getDrugType());
        response.put("stDepression", analyzer.getSTDepression());
        response.put("qtInterval", analyzer.getQTInterval());
        
        // Cicatriz e Aneurisma
        response.put("hasMyocardialScar", analyzer.hasMyocardialScar());
        response.put("myocardialScarDescription", analyzer.getMyocardialScarDescription());
        response.put("hasVentricularAneurysm", analyzer.hasVentricularAneurysm());
        response.put("ventricularAneurysmDescription", analyzer.getVentricularAneurysmDescription());
        response.put("hasPostInfarctionArrhythmia", analyzer.hasPostInfarctionArrhythmia());
        response.put("postInfarctionArrhythmiaDescription", analyzer.getPostInfarctionArrhythmiaDescription());
        
        // 30+ Novos diagnósticos
        response.put("hasAtrialFibrillation", analyzer.hasAtrialFibrillation());
        response.put("atrialFibrillationDescription", analyzer.getAtrialFibrillationDescription());
        response.put("hasAtrialFlutter", analyzer.hasAtrialFlutter());
        response.put("atrialFlutterDescription", analyzer.getAtrialFlutterDescription());
        response.put("hasSuperventricularTachycardia", analyzer.hasSuperventricularTachycardia());
        response.put("superventricularTachycardiaDescription", analyzer.getSuperventricularTachycardiaDescription());
        response.put("hasVentricularTachycardia", analyzer.hasVentricularTachycardia());
        response.put("ventricularTachycardiaDescription", analyzer.getVentricularTachycardiaDescription());
        response.put("hasVentricularFibrillation", analyzer.hasVentricularFibrillation());
        response.put("ventricularFibrillationDescription", analyzer.getVentricularFibrillationDescription());
        response.put("hasEctopicBeats", analyzer.hasEctopicBeats());
        response.put("ectopicBeatsDescription", analyzer.getEctopicBeatsDescription());
        
        response.put("hasWolffParkinsonWhite", analyzer.hasWolffParkinsonWhite());
        response.put("wolffParkinsonWhiteDescription", analyzer.getWolffParkinsonWhiteDescription());
        response.put("hasBrugadaSyndrome", analyzer.hasBrugadaSyndrome());
        response.put("brugadaSyndromeDescription", analyzer.getBrugadaSyndromeDescription());
        response.put("hasLongQT", analyzer.hasLongQT());
        response.put("longQTDescription", analyzer.getLongQTDescription());
        response.put("hasShortQT", analyzer.hasShortQT());
        response.put("shortQTDescription", analyzer.getShortQTDescription());
        
        response.put("hasHyperkalemia", analyzer.hasHyperkalemia());
        response.put("hyperkalemiaDescription", analyzer.getHyperkalemiaDescription());
        response.put("hasHypokalemia", analyzer.hasHypokalemia());
        response.put("hypokalemiaDescription", analyzer.getHypokalemiaDescription());
        response.put("hasHypercalcemia", analyzer.hasHypercalcemia());
        response.put("hypercalcemiaDescription", analyzer.getHypercalcemiaDescription());
        response.put("hasHypocalcemia", analyzer.hasHypocalcemia());
        response.put("hypocalcemiaDescription", analyzer.getHypocalcemiaDescription());
        
        response.put("hasWellensPattern", analyzer.hasWellensPattern());
        response.put("wellensPatternDescription", analyzer.getWellensPatternDescription());
        response.put("hasOsbornWave", analyzer.hasOsbornWave());
        response.put("osbornWaveDescription", analyzer.getOsbornWaveDescription());
        response.put("hasEarlyRepolarization", analyzer.hasEarlyRepolarization());
        response.put("earlyRepolarizationDescription", analyzer.getEarlyRepolarizationDescription());
        response.put("hasEpsilonWave", analyzer.hasEpsilonWave());
        response.put("epsilonWaveDescription", analyzer.getEpsilonWaveDescription());
        
        response.put("hasLVH", analyzer.hasLVH());
        response.put("lvhDescription", analyzer.getLVHDescription());
        response.put("hasRVH", analyzer.hasRVH());
        response.put("rvhDescription", analyzer.getRVHDescription());
        response.put("hasAtrialOverload", analyzer.hasAtrialOverload());
        response.put("atrialOverloadDescription", analyzer.getAtrialOverloadDescription());
        
        response.put("hasSilentIschemia", analyzer.hasSilentIschemia());
        response.put("silentIschemiaDescription", analyzer.getSilentIschemiaDescription());
        response.put("hasPrinzmetalAngina", analyzer.hasPrinzmetalAngina());
        response.put("prinzmetalAnginaDescription", analyzer.getPrinzmetalAnginaDescription());
        response.put("hasCoronarySpasm", analyzer.hasCoronarySpasm());
        response.put("coronarySpasmDescription", analyzer.getCoronarySpasmDescription());
        response.put("hasTakotsuboSyndrome", analyzer.hasTakotsuboSyndrome());
        response.put("takotsuboSyndromeDescription", analyzer.getTakotsuboSyndromeDescription());
        
        response.put("hasPulmonaryEmbolism", analyzer.hasPulmonaryEmbolism());
        response.put("pulmonaryEmbolismDescription", analyzer.getPulmonaryEmbolismDescription());
        response.put("hasPulmonaryHypertension", analyzer.hasPulmonaryHypertension());
        response.put("pulmonaryHypertensionDescription", analyzer.getPulmonaryHypertensionDescription());
        response.put("hasChronicLungDisease", analyzer.hasChronicLungDisease());
        response.put("chronicLungDiseaseDescription", analyzer.getChronicLungDiseaseDescription());
        response.put("hasHypothermia", analyzer.hasHypothermia());
        response.put("hypothermiaDescription", analyzer.getHypothermiaDescription());
        response.put("hasSleepApnea", analyzer.hasSleepApnea());
        response.put("sleepApneaDescription", analyzer.getSleepApneaDescription());
        response.put("hasVasovagalSyncope", analyzer.hasVasovagalSyncope());
        response.put("vasovagalSyncopeDescription", analyzer.getVasovagalSyncopeDescription());
    }
    
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
            response.put("duration", data.getDurationSeconds());
            response.put("minVoltage", data.getMinVoltage());
            response.put("maxVoltage", data.getMaxVoltage());
            response.put("avgVoltage", data.getAverageVoltage());
            addAllDiagnostics(response, analyzer);
            response.put("voltages", data.getVoltages());
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
            response.put("duration", data.getDurationSeconds());
            response.put("minVoltage", data.getMinVoltage());
            response.put("maxVoltage", data.getMaxVoltage());
            response.put("avgVoltage", data.getAverageVoltage());
            addAllDiagnostics(response, analyzer);
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
            response.put("duration", data.getDurationSeconds());
            response.put("minVoltage", data.getMinVoltage());
            response.put("maxVoltage", data.getMaxVoltage());
            response.put("avgVoltage", data.getAverageVoltage());
            addAllDiagnostics(response, analyzer);
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
     * Gera PDF de diagnóstico com os dados da análise
     * POST /api/ecg/generate-pdf
     */
    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateDiagnosticPDF(@RequestBody Map<String, Object> analysisData) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            // Título
            Paragraph title = new Paragraph("RELATÓRIO DE DIAGNÓSTICO ECG")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(18);
            document.add(title);
            
            // Data e hora
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            Paragraph dateTime = new Paragraph("Data/Hora: " + now.format(formatter))
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(10);
            document.add(dateTime);
            document.add(new Paragraph("\n"));
            
            // Seção: Estatísticas Básicas
            Paragraph statsTitle = new Paragraph("1. ESTATÍSTICAS DO SINAL")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(12);
            document.add(statsTitle);
            
            Table statsTable = new Table(2);
            addTableRow(statsTable, "BPM (Batidas por Minuto)", String.format("%.1f", safeGetDouble(analysisData, "bpm", 0.0)));
            addTableRow(statsTable, "Picos Detectados", String.valueOf(safeGetInt(analysisData, "peakCount", 0)));
            addTableRow(statsTable, "Duração (segundos)", String.format("%.2f", safeGetDouble(analysisData, "duration", 0.0)));
            addTableRow(statsTable, "Voltagem Mínima (mV)", String.format("%.2f", safeGetDouble(analysisData, "minVoltage", 0.0)));
            addTableRow(statsTable, "Voltagem Máxima (mV)", String.format("%.2f", safeGetDouble(analysisData, "maxVoltage", 0.0)));
            addTableRow(statsTable, "Voltagem Média (mV)", String.format("%.3f", safeGetDouble(analysisData, "avgVoltage", 0.0)));
            document.add(statsTable);
            document.add(new Paragraph("\n"));
            
            // Seção: Diagnósticos
            Paragraph diagTitle = new Paragraph("2. DIAGNÓSTICOS")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(12);
            document.add(diagTitle);
            
            // Infarto
            boolean hasInfarction = (boolean) analysisData.getOrDefault("hasInfarction", false);
            if (hasInfarction) {
                Paragraph infarctionPara = new Paragraph("⚠️ INFARTO DETECTADO")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(11);
                document.add(infarctionPara);
                
                Table infarctionTable = new Table(2);
                addTableRow(infarctionTable, "Descrição", (String) analysisData.getOrDefault("infarctionDescription", "N/A"));
                addTableRow(infarctionTable, "Severidade", String.valueOf(safeGetInt(analysisData, "infarctionSeverity", 0)));
                addTableRow(infarctionTable, "Elevação ST (mV)", String.format("%.2f", safeGetDouble(analysisData, "stElevation", 0.0)));
                document.add(infarctionTable);
                document.add(new Paragraph("\n"));
            }
            
            // Transtornos de Condução
            boolean hasConductionDisorder = (boolean) analysisData.getOrDefault("hasConductionDisorder", false);
            if (hasConductionDisorder) {
                Paragraph conductionPara = new Paragraph("⚡ TRANSTORNO DE CONDUÇÃO DETECTADO")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(11);
                document.add(conductionPara);
                
                Table conductionTable = new Table(2);
                addTableRow(conductionTable, "Descrição", (String) analysisData.getOrDefault("conductionDescription", "N/A"));
                addTableRow(conductionTable, "Tipo de Bloqueio de Ramo", (String) analysisData.getOrDefault("branchBlockType", "Nenhum"));
                addTableRow(conductionTable, "Tipo de Bloqueio AV", (String) analysisData.getOrDefault("avBlockType", "Nenhum"));
                addTableRow(conductionTable, "Intervalo PR (ms)", String.valueOf(safeGetInt(analysisData, "prInterval", 0)));
                addTableRow(conductionTable, "Largura QRS (ms)", String.valueOf(safeGetInt(analysisData, "qrsWidth", 0)));
                document.add(conductionTable);
                document.add(new Paragraph("\n"));
            }
            
            // Inflamação
            boolean hasInflammation = (boolean) analysisData.getOrDefault("hasInflammation", false);
            if (hasInflammation) {
                Paragraph inflammationPara = new Paragraph("🔴 INFLAMAÇÃO CARDÍACA DETECTADA")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(11);
                document.add(inflammationPara);
                
                Table inflammationTable = new Table(2);
                addTableRow(inflammationTable, "Descrição", (String) analysisData.getOrDefault("inflammationDescription", "N/A"));
                addTableRow(inflammationTable, "Tipo", (String) analysisData.getOrDefault("inflammationType", "N/A"));
                addTableRow(inflammationTable, "Elevação ST Difusa (mV)", String.format("%.2f", safeGetDouble(analysisData, "diffuseSTElevation", 0.0)));
                document.add(inflammationTable);
                document.add(new Paragraph("\n"));
            }
            
            // Efeito de Medicamentos
            boolean hasDrugEffect = (boolean) analysisData.getOrDefault("hasDrugEffect", false);
            if (hasDrugEffect) {
                Paragraph drugPara = new Paragraph("💊 EFEITO DE MEDICAMENTOS DETECTADO")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(11);
                document.add(drugPara);
                
                Table drugTable = new Table(2);
                addTableRow(drugTable, "Descrição", (String) analysisData.getOrDefault("drugEffectDescription", "N/A"));
                addTableRow(drugTable, "Medicamento", (String) analysisData.getOrDefault("drugType", "Desconhecido"));
                addTableRow(drugTable, "Depressão ST (mV)", String.format("%.2f", safeGetDouble(analysisData, "stDepression", 0.0)));
                addTableRow(drugTable, "Intervalo QT (ms)", String.valueOf(safeGetInt(analysisData, "qtInterval", 0)));
                document.add(drugTable);
                document.add(new Paragraph("\n"));
            }
            
            // Cicatriz Miocárdica
            boolean hasMyocardialScar = (boolean) analysisData.getOrDefault("hasMyocardialScar", false);
            if (hasMyocardialScar) {
                Paragraph scarPara = new Paragraph("🔴 CICATRIZ MIOCÁRDICA DETECTADA")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(11);
                document.add(scarPara);
                
                Paragraph scarText = new Paragraph((String) analysisData.getOrDefault("myocardialScarDescription", "N/A"));
                document.add(scarText);
                document.add(new Paragraph("\n"));
            }
            
            // Aneurisma Ventricular
            boolean hasVentricularAneurysm = (boolean) analysisData.getOrDefault("hasVentricularAneurysm", false);
            if (hasVentricularAneurysm) {
                Paragraph aneurysmPara = new Paragraph("⚠️ ANEURISMA VENTRICULAR DETECTADO")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(11);
                document.add(aneurysmPara);
                
                Paragraph aneurysmText = new Paragraph((String) analysisData.getOrDefault("ventricularAneurysmDescription", "N/A"));
                document.add(aneurysmText);
                document.add(new Paragraph("\n"));
            }
            
            // Status Geral
            document.add(new Paragraph("\n"));
            Paragraph statusTitle = new Paragraph("3. STATUS GERAL")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(12);
            document.add(statusTitle);
            
            boolean isAnomalous = (boolean) analysisData.getOrDefault("isAnomalous", false);
            String statusText = isAnomalous ? 
                "❌ ANOMALIA DETECTADA: " + analysisData.getOrDefault("anomalyDescription", "N/A") :
                "✅ ECG NORMAL - Sem anomalias detectadas";
            
            Paragraph statusPara = new Paragraph(statusText)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(11);
            document.add(statusPara);
            
            // Rodapé
            document.add(new Paragraph("\n\n"));
            Paragraph footer = new Paragraph("Este relatório foi gerado automaticamente pelo ECG Analyzer. " +
                "Consulte um médico cardiologista para interpretação e diagnóstico final.")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(9);
            document.add(footer);
            
            document.close();
            
            byte[] pdfBytes = baos.toByteArray();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentLength(pdfBytes.length);
            String filename = "diagnostico_ecg_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";
            headers.setContentDispositionFormData("attachment", filename);
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
                
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Método auxiliar para adicionar linhas na tabela do PDF
     */
    private void addTableRow(Table table, String label, String value) throws IOException {
        Cell labelCell = new Cell().add(new Paragraph(label)
            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
            .setFontSize(10));
        Cell valueCell = new Cell().add(new Paragraph(value)
            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
            .setFontSize(10));
        
        table.addCell(labelCell);
        table.addCell(valueCell);
    }
    
    /**
     * Método auxiliar para converter valores com segurança
     */
    private double safeGetDouble(Map<String, Object> map, String key, double defaultValue) {
        Object value = map.getOrDefault(key, defaultValue);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return defaultValue;
    }
    
    /**
     * Método auxiliar para converter valores inteiros com segurança
     */
    private int safeGetInt(Map<String, Object> map, String key, int defaultValue) {
        Object value = map.getOrDefault(key, defaultValue);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return defaultValue;
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
