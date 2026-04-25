package com.ecg.service;

import java.util.ArrayList;
import java.util.List;
import com.ecg.model.ECGData;

/**
 * Classe para análise de ECG
 */
public class ECGAnalyzer {
    private ECGData data;
    private List<Integer> peakIndices;
    private double bpm;
    private boolean isAnomalous;
    private String anomalyDescription;
    private boolean hasInfarction;
    private String infarctionDescription;
    private double stElevation;
    private int infarctionSeverity;
    private boolean hasConductionDisorder;
    private String conductionDescription;
    private String branchBlockType;
    private String avBlockType;
    private double prInterval;
    private double qrsWidth;
    private boolean hasInflammation;
    private String inflammationDescription;
    private String inflammationType;
    private double diffuseSTElevation;
    private boolean hasDrugEffect;
    private String drugEffectDescription;
    private String drugType;
    private double stDepression;
    private double qtInterval;
    
    public ECGAnalyzer(ECGData data) {
        this.data = data;
        this.peakIndices = new ArrayList<>();
        this.isAnomalous = false;
        this.anomalyDescription = "";
        this.hasInfarction = false;
        this.infarctionDescription = "";
        this.stElevation = 0;
        this.infarctionSeverity = 0;
        this.hasConductionDisorder = false;
        this.conductionDescription = "";
        this.branchBlockType = "";
        this.avBlockType = "";
        this.prInterval = 0;
        this.qrsWidth = 0;
        this.hasInflammation = false;
        this.inflammationDescription = "";
        this.inflammationType = "";
        this.diffuseSTElevation = 0;
        this.hasDrugEffect = false;
        this.drugEffectDescription = "";
        this.drugType = "";
        this.stDepression = 0;
        this.qtInterval = 0;
        analyze();
    }
    
    private void analyze() {
        if (data.getSize() < 3) {
            return;
        }
        
        detectPeaks();
        calculateBPM();
        detectConductionDisorders();
        detectInflammation();
        detectDrugEffects();
        detectInfarction();
        detectAnomalies();
    }
    
    private void detectPeaks() {
        List<Double> voltages = data.getVoltages();
        double threshold = (data.getMaxVoltage() + data.getMinVoltage()) / 2.0 + 
                          (data.getMaxVoltage() - data.getMinVoltage()) * 0.3;
        
        int minDistanceBetweenPeaks = (int) (data.getSamplingRate() * 0.4);
        
        for (int i = 1; i < voltages.size() - 1; i++) {
            double current = voltages.get(i);
            double previous = voltages.get(i - 1);
            double next = voltages.get(i + 1);
            
            if (current > previous && current > next && current > threshold) {
                if (peakIndices.isEmpty() || (i - peakIndices.get(peakIndices.size() - 1)) >= minDistanceBetweenPeaks) {
                    peakIndices.add(i);
                }
            }
        }
    }
    
    private void calculateBPM() {
        if (peakIndices.size() < 2) {
            bpm = 0;
            return;
        }
        
        double totalInterval = 0;
        for (int i = 1; i < peakIndices.size(); i++) {
            int samples = peakIndices.get(i) - peakIndices.get(i - 1);
            totalInterval += samples / data.getSamplingRate();
        }
        
        double averageInterval = totalInterval / (peakIndices.size() - 1);
        bpm = 60.0 / averageInterval;
    }
    
    private void detectInflammation() {
        List<Double> voltages = data.getVoltages();
        
        if (peakIndices.size() < 2 || voltages.size() < 20) {
            hasInflammation = false;
            inflammationDescription = "";
            return;
        }
        
        StringBuilder report = new StringBuilder();
        int inflammationIndicators = 0;
        double avgVoltage = data.getAverageVoltage();
        double voltageRange = data.getMaxVoltage() - data.getMinVoltage();
        
        // Calcula elevação de ST difusa (em múltiplos pontos, não localizada)
        double diffuseSTCount = 0;
        double totalSTElevation = 0;
        double maxDiffuseST = 0;
        
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int stStart = peakIndex + 3;
            int stEnd = Math.min(stStart + 8, peakIndices.get(i + 1));
            
            if (stStart < voltages.size()) {
                double baselineVoltage = (voltages.get(peakIndex - 2) + voltages.get(peakIndex)) / 2.0;
                
                for (int j = stStart; j < stEnd && j < voltages.size(); j++) {
                    double elevation = voltages.get(j) - baselineVoltage;
                    
                    // Elevação ST difusa e uniforme (< 2mV)
                    if (elevation > 0.2 && elevation < 2.0) {
                        diffuseSTCount++;
                        totalSTElevation += elevation;
                        maxDiffuseST = Math.max(maxDiffuseST, elevation);
                    }
                }
            }
        }
        
        this.diffuseSTElevation = maxDiffuseST;
        
        // Pericardite: elevação ST difusa e generalizada
        if (diffuseSTCount > 10 && maxDiffuseST > 0.3 && maxDiffuseST < 2.0) {
            report.append("🔴 Pericardite Suspeita: Elevação ST difusa e generalizada (").append(String.format("%.2f", maxDiffuseST)).append("mV)");
            inflammationIndicators++;
            inflammationType = "Pericardite";
        }
        
        // Detecta PR deprimido (sinal de pericardite)
        double prDepressionCount = 0;
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int prStart = Math.max(0, peakIndex - 15);
            int prEnd = peakIndex;
            
            double baselineVoltage = voltages.get(prStart);
            
            for (int j = prStart + 2; j < prEnd; j++) {
                if (voltages.get(j) < baselineVoltage - 0.3) {
                    prDepressionCount++;
                }
            }
        }
        
        if (prDepressionCount > 5 && inflammationIndicators == 1) {
            report.append(" | Segmento PR deprimido (sinal de pericardite aguda)");
        }
        
        // Miocardite: alterações difusas com ondas T invertidas
        double tWaveInversionCount = 0;
        double tWaveAbnormalities = 0;
        
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int nextPeakIndex = peakIndices.get(i + 1);
            int tWaveStart = peakIndex + 10;
            int tWaveEnd = Math.min(tWaveStart + 15, nextPeakIndex);
            
            if (tWaveStart < voltages.size()) {
                for (int j = tWaveStart; j < tWaveEnd && j < voltages.size(); j++) {
                    // Onda T invertida (muito negativa)
                    if (voltages.get(j) < avgVoltage - voltageRange * 0.3) {
                        tWaveInversionCount++;
                    }
                    // Onda T bifásica (anormal)
                    if (j > tWaveStart && j < tWaveEnd - 1) {
                        if ((voltages.get(j - 1) < voltages.get(j)) && (voltages.get(j + 1) < voltages.get(j))) {
                            tWaveAbnormalities++;
                        }
                    }
                }
            }
        }
        
        // Miocardite: inversão de T difusa + alterações ST
        if ((tWaveInversionCount > 8 || tWaveAbnormalities > 5) && diffuseSTCount > 5) {
            if (report.length() > 0) report.append(" | ");
            report.append("🔴 Miocardite Suspeita: Alterações difusas com inversão de onda T e mudanças de ST");
            inflammationIndicators++;
            inflammationType = "Miocardite";
        }
        
        // Detecta padrão de inflamação: voltagem baixa generalizada
        double lowAmplitudeCount = 0;
        for (double voltage : voltages) {
            if (Math.abs(voltage) < voltageRange * 0.2) {
                lowAmplitudeCount++;
            }
        }
        
        if (lowAmplitudeCount > voltages.size() * 0.4 && inflammationIndicators > 0) {
            report.append(" | Voltagem generalizada reduzida");
        }
        
        // Determina se há inflamação
        if (inflammationIndicators > 0) {
            hasInflammation = true;
            inflammationDescription = report.toString();
            isAnomalous = true;
        } else {
            hasInflammation = false;
            inflammationDescription = "";
            inflammationType = "";
        }
    }
    
    private void detectConductionDisorders() {
        List<Double> voltages = data.getVoltages();
        
        if (peakIndices.size() < 2) {
            hasConductionDisorder = false;
            conductionDescription = "";
            return;
        }
        
        StringBuilder report = new StringBuilder();
        int conductionIssues = 0;
        
        // Calcula largura do complexo QRS (duração entre picos)
        double avgQRSWidth = 0;
        for (int i = 0; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int nextPeakIndex = peakIndices.get(i + 1);
            double qrsWidth = (nextPeakIndex - peakIndex) / data.getSamplingRate() * 1000; // em ms
            avgQRSWidth += qrsWidth;
        }
        avgQRSWidth /= Math.max(1, peakIndices.size() - 1);
        this.qrsWidth = avgQRSWidth;
        
        // Detecta Bloqueio de Ramo (QRS alargado > 120ms)
        if (avgQRSWidth > 120) {
            report.append("⚡ Bloqueio de Ramo: Complexo QRS alargado (").append(String.format("%.0f", avgQRSWidth)).append("ms)");
            conductionIssues++;
            
            // Tenta identificar tipo de bloqueio
            double leftDeviations = 0;
            double rightDeviations = 0;
            
            for (int i = 1; i < peakIndices.size() - 1; i++) {
                int peakIndex = peakIndices.get(i);
                int qStart = Math.max(0, peakIndex - 10);
                int qEnd = peakIndex;
                
                // Analisa a forma da onda para determinar tipo
                double qWaveDepth = 0;
                for (int j = qStart; j < qEnd; j++) {
                    qWaveDepth += Math.min(voltages.get(j), 0);
                }
                
                if (qWaveDepth < -0.5) {
                    leftDeviations++;
                } else {
                    rightDeviations++;
                }
            }
            
            if (leftDeviations > rightDeviations) {
                branchBlockType = "Bloqueio de Ramo Esquerdo (BRE)";
            } else {
                branchBlockType = "Bloqueio de Ramo Direito (BRD)";
            }
        } else {
            branchBlockType = "";
        }
        
        // Detecta Bloqueia Atrioventricular (intervalo PR anormal)
        // PR normal: 120-200ms
        double avgPRInterval = 0;
        int prCount = 0;
        
        for (int i = 0; i < peakIndices.size() - 1; i++) {
            int currentPeak = peakIndices.get(i);
            int nextPeak = peakIndices.get(i + 1);
            
            // Estima intervalo PR (distância entre picos)
            double prInterval = (nextPeak - currentPeak) / data.getSamplingRate() * 1000; // em ms
            avgPRInterval += prInterval;
            prCount++;
        }
        
        if (prCount > 0) {
            avgPRInterval /= prCount;
            this.prInterval = avgPRInterval;
        }
        
        // Bloqueia AV de 1º grau: PR > 200ms
        if (this.prInterval > 200) {
            if (report.length() > 0) report.append(" | ");
            report.append("⚡ Bloqueio AV 1º Grau: Intervalo PR prolongado (").append(String.format("%.0f", this.prInterval)).append("ms)");
            conductionIssues++;
            avBlockType = "Bloqueio AV 1º Grau";
        }
        // Bloqueia AV de 2º grau: intervalos PR variáveis ou alguns pulsos ausentes
        else if (this.prInterval < 120) {
            if (report.length() > 0) report.append(" | ");
            report.append("⚡ Condução Acelerada: Intervalo PR muito curto (").append(String.format("%.0f", this.prInterval)).append("ms)");
            conductionIssues++;
            avBlockType = "Condução Acelerada";
        }
        
        // Detecção de irregularidade de ritmo (variabilidade anormal nos intervalos)
        if (prCount > 2) {
            List<Double> intervals = new ArrayList<>();
            for (int i = 0; i < peakIndices.size() - 1; i++) {
                int current = peakIndices.get(i);
                int next = peakIndices.get(i + 1);
                intervals.add((double)(next - current));
            }
            
            double meanInterval = intervals.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            double maxDeviation = 0;
            
            for (double interval : intervals) {
                maxDeviation = Math.max(maxDeviation, Math.abs(interval - meanInterval));
            }
            
            double deviationPercent = (maxDeviation / meanInterval) * 100;
            
            // Bloqueio AV 2º grau: variação > 30% nos intervalos
            if (deviationPercent > 30 && this.prInterval > 150) {
                if (report.length() > 0) report.append(" | ");
                report.append("⚡ Bloqueio AV 2º Grau: Variação irregular nos intervalos (").append(String.format("%.1f", deviationPercent)).append("%)");
                conductionIssues++;
                if (avBlockType.isEmpty()) {
                    avBlockType = "Bloqueio AV 2º Grau";
                }
            }
        }
        
        // Determina se há distúrbio de condução
        if (conductionIssues > 0) {
            hasConductionDisorder = true;
            conductionDescription = report.toString();
            isAnomalous = true;
        } else {
            hasConductionDisorder = false;
            conductionDescription = "✓ Condução elétrica normal";
            branchBlockType = "";
            avBlockType = "";
        }
    }
    
    private void detectInfarction() {
        List<Double> voltages = data.getVoltages();
        
        if (peakIndices.size() < 2 || voltages.size() < 10) {
            hasInfarction = false;
            infarctionDescription = "";
            return;
        }
        
        int infarctionIndicators = 0;
        double maxSTElevation = 0;
        double avgVoltage = data.getAverageVoltage();
        double voltageRange = data.getMaxVoltage() - data.getMinVoltage();
        
        // Detecta elevação do segmento ST
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int beforePeak = peakIndices.get(i) - 5;
            int afterPeak = peakIndices.get(i) + 5;
            
            if (beforePeak >= 0 && afterPeak < voltages.size()) {
                // Segmento ST é a região após o pico (onda S) até o próximo pico (onda T)
                double baselineVoltage = (voltages.get(beforePeak) + voltages.get(afterPeak)) / 2.0;
                
                // Coleta valores no segmento ST
                double maxSTVoltage = baselineVoltage;
                for (int j = afterPeak; j < Math.min(afterPeak + 10, voltages.size()); j++) {
                    maxSTVoltage = Math.max(maxSTVoltage, voltages.get(j));
                }
                
                double elevation = maxSTVoltage - baselineVoltage;
                maxSTElevation = Math.max(maxSTElevation, elevation);
                
                // Elevação ST anormal (> 10% do range de voltagem)
                if (elevation > voltageRange * 0.1) {
                    infarctionIndicators++;
                }
            }
        }
        
        stElevation = maxSTElevation;
        
        // Detecta ondas Q anormais (depressões profundas)
        double deepQCount = 0;
        for (int i = 0; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int nextPeakIndex = peakIndices.get(i + 1);
            int midpoint = (peakIndex + nextPeakIndex) / 2;
            
            // Verifica depressões entre picos
            for (int j = peakIndex + 3; j < midpoint; j++) {
                if (voltages.get(j) < avgVoltage - voltageRange * 0.2) {
                    deepQCount++;
                }
            }
        }
        
        if (deepQCount > 3) {
            infarctionIndicators++;
        }
        
        // Detecta inversão de onda T (voltagem muito baixa após o segmento ST)
        int lowAmpCount = 0;
        for (int i = 0; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int tWaveStart = peakIndex + 15;
            int tWaveEnd = Math.min(tWaveStart + 10, peakIndices.get(i + 1));
            
            if (tWaveStart < voltages.size()) {
                for (int j = tWaveStart; j < tWaveEnd && j < voltages.size(); j++) {
                    if (Math.abs(voltages.get(j)) < avgVoltage * 0.3) {
                        lowAmpCount++;
                    }
                }
            }
        }
        
        if (lowAmpCount > 5) {
            infarctionIndicators++;
        }
        
        // Detecta irregularidade no complexo QRS
        double qrsIrregularity = 0;
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakBefore = peakIndices.get(i - 1);
            int peak = peakIndices.get(i);
            int peakAfter = peakIndices.get(i + 1);
            
            double width1 = peak - peakBefore;
            double width2 = peakAfter - peak;
            
            if (Math.abs(width1 - width2) > width1 * 0.3) {
                qrsIrregularity++;
            }
        }
        
        if (qrsIrregularity > 2) {
            infarctionIndicators++;
        }
        
        // Determina se há infarto baseado nos indicadores
        if (infarctionIndicators >= 2) {
            hasInfarction = true;
            infarctionSeverity = Math.min(infarctionIndicators, 4);
            
            StringBuilder description = new StringBuilder();
            description.append("⚠️ POSSÍVEL INFARTO DO MIOCÁRDIO DETECTADO | ");
            
            if (maxSTElevation > voltageRange * 0.1) {
                description.append("Elevação ST significativa | ");
            }
            if (deepQCount > 3) {
                description.append("Ondas Q profundas detectadas | ");
            }
            if (lowAmpCount > 5) {
                description.append("Inversão de onda T | ");
            }
            if (qrsIrregularity > 2) {
                description.append("Complexo QRS irregular | ");
            }
            
            description.append("Severidade: ");
            if (infarctionSeverity == 4) {
                description.append("CRÍTICA - PROCURE EMERGÊNCIA IMEDIATAMENTE!");
            } else if (infarctionSeverity == 3) {
                description.append("ALTA - Necessário atendimento médico urgente");
            } else {
                description.append("MODERADA - Recomendado avaliação médica");
            }
            
            infarctionDescription = description.toString();
            isAnomalous = true;
        } else {
            hasInfarction = false;
            infarctionDescription = "";
            infarctionSeverity = 0;
        }
    }
    
    private void detectDrugEffects() {
        List<Double> voltages = data.getVoltages();
        
        if (peakIndices.size() < 2 || voltages.size() < 20) {
            hasDrugEffect = false;
            drugEffectDescription = "";
            return;
        }
        
        StringBuilder report = new StringBuilder();
        int drugIndicators = 0;
        double avgVoltage = data.getAverageVoltage();
        double voltageRange = data.getMaxVoltage() - data.getMinVoltage();
        
        // Calcula depressão de ST (padrão típico de Digoxina)
        double stDepressionCount = 0;
        double maxSTDepression = 0;
        double avgSTDepression = 0;
        
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int stStart = peakIndex + 3;
            int stEnd = Math.min(stStart + 8, peakIndices.get(i + 1));
            
            if (stStart < voltages.size()) {
                double baselineVoltage = (voltages.get(peakIndex - 2) + voltages.get(peakIndex)) / 2.0;
                
                for (int j = stStart; j < stEnd && j < voltages.size(); j++) {
                    double depression = baselineVoltage - voltages.get(j);
                    
                    // Depressão de ST (padrão em "colher" da Digoxina)
                    if (depression > 0.1 && depression < 0.8) {
                        stDepressionCount++;
                        avgSTDepression += depression;
                        maxSTDepression = Math.max(maxSTDepression, depression);
                    }
                }
            }
        }
        
        if (stDepressionCount > 0) {
            avgSTDepression /= stDepressionCount;
        }
        
        this.stDepression = maxSTDepression;
        
        // Digoxina: depressão de ST em "colher" + PR prolongado
        if (stDepressionCount > 8 && maxSTDepression > 0.2 && maxSTDepression < 0.8 && prInterval > 180) {
            report.append("💊 Efeito de Digoxina Suspeito: Depressão ST em 'colher' (").append(String.format("%.2f", maxSTDepression)).append("mV)");
            drugIndicators++;
            drugType = "Digoxina";
        }
        
        // Calcula intervalo QT (duração entre pico Q e final de T)
        double avgQTInterval = 0;
        int qtCount = 0;
        
        for (int i = 0; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int nextPeakIndex = peakIndices.get(i + 1);
            
            // QT é medido desde o início do QRS até o fim da onda T
            // Estimamos como aproximadamente 60% do intervalo RR
            double rrInterval = (nextPeakIndex - peakIndex) / data.getSamplingRate() * 1000; // em ms
            double estimatedQT = rrInterval * 0.6; // Aproximação
            
            avgQTInterval += estimatedQT;
            qtCount++;
        }
        
        if (qtCount > 0) {
            avgQTInterval /= qtCount;
        }
        
        this.qtInterval = avgQTInterval;
        
        // QT encurtado (Digoxina): QT < 320ms
        if (avgQTInterval < 320 && stDepressionCount > 5) {
            if (report.length() > 0) report.append(" | ");
            report.append("💊 Intervalo QT Encurtado (").append(String.format("%.0f", avgQTInterval)).append("ms) - Característica de Digoxina");
            drugIndicators++;
            if (!drugType.equals("Digoxina")) {
                drugType = "Digoxina";
            }
        }
        
        // Antiarrítmicos: QRS alargado + QT prolongado
        if (qrsWidth > 120 && avgQTInterval > 440) {
            if (report.length() > 0) report.append(" | ");
            report.append("💊 Padrão de Antiarrítmico: QRS alargado (").append(String.format("%.0f", qrsWidth)).append("ms) + QT prolongado (").append(String.format("%.0f", avgQTInterval)).append("ms)");
            drugIndicators++;
            drugType = "Antiarrítmico";
        }
        
        // Detecta padrão de ST elevado com antiarrítmicos
        double stElevationCount = 0;
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int stStart = peakIndex + 3;
            int stEnd = Math.min(stStart + 8, peakIndices.get(i + 1));
            
            if (stStart < voltages.size()) {
                double baselineVoltage = (voltages.get(peakIndex - 2) + voltages.get(peakIndex)) / 2.0;
                
                for (int j = stStart; j < stEnd && j < voltages.size(); j++) {
                    double elevation = voltages.get(j) - baselineVoltage;
                    if (elevation > 0.3) {
                        stElevationCount++;
                    }
                }
            }
        }
        
        // Antiarrítmicos podem causar ST elevado
        if (stElevationCount > 10 && qrsWidth > 120 && !drugType.equals("Digoxina")) {
            if (report.length() > 0) report.append(" | ");
            report.append("💊 Efeito de Antiarrítmico: Elevação ST com QRS alargado");
            drugIndicators++;
            drugType = "Antiarrítmico";
        }
        
        // Detecta ondas T bifásicas ou invertidas (Digoxina)
        double bifasicTCount = 0;
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int tWaveStart = peakIndex + 10;
            int tWaveEnd = Math.min(tWaveStart + 15, peakIndices.get(i + 1));
            
            if (tWaveStart < voltages.size()) {
                double minT = Double.MAX_VALUE;
                double maxT = Double.MIN_VALUE;
                
                for (int j = tWaveStart; j < tWaveEnd && j < voltages.size(); j++) {
                    minT = Math.min(minT, voltages.get(j));
                    maxT = Math.max(maxT, voltages.get(j));
                }
                
                // Onda T bifásica: tanto positiva quanto negativa
                if ((maxT > avgVoltage * 0.2) && (minT < -avgVoltage * 0.2)) {
                    bifasicTCount++;
                }
            }
        }
        
        if (bifasicTCount > 2 && stDepressionCount > 5 && !drugType.equals("Antiarrítmico")) {
            if (report.length() > 0) report.append(" | ");
            report.append("💊 Ondas T Bifásicas - Padrão característico de Digoxina");
            drugIndicators++;
            drugType = "Digoxina";
        }
        
        // Determina se há efeito de medicamento
        if (drugIndicators > 0) {
            hasDrugEffect = true;
            drugEffectDescription = report.toString();
            isAnomalous = true;
        } else {
            hasDrugEffect = false;
            drugEffectDescription = "";
            drugType = "";
        }
    }
    
    private void detectAnomalies() {
        StringBuilder report = new StringBuilder();
        
        if (peakIndices.size() < 2) {
            isAnomalous = true;
            anomalyDescription = "Menos de 2 batidas detectadas!";
            return;
        }
        
        if (bpm < 60) {
            isAnomalous = true;
            report.append("Bradicardia: BPM baixo (").append(String.format("%.1f", bpm)).append(")");
        } else if (bpm > 100) {
            isAnomalous = true;
            report.append("Taquicardia: BPM alto (").append(String.format("%.1f", bpm)).append(")");
        }
        
        List<Double> intervals = new ArrayList<>();
        for (int i = 1; i < peakIndices.size(); i++) {
            int samples = peakIndices.get(i) - peakIndices.get(i - 1);
            intervals.add(samples / data.getSamplingRate() * 1000);
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
                if (report.length() > 0) report.append(" | ");
                report.append("Arritmia: Variação alta (").append(String.format("%.1f", deviationPercent)).append("%)");
            }
        }
        
        anomalyDescription = report.length() > 0 ? report.toString() : "Ritmo cardíaco normal";
    }
    
    public double getBPM() {
        return bpm;
    }
    
    public int getPeakCount() {
        return peakIndices.size();
    }
    
    public List<Integer> getPeakIndices() {
        return new ArrayList<>(peakIndices);
    }
    
    public boolean isAnomalous() {
        return isAnomalous;
    }
    
    public String getAnomalyDescription() {
        return anomalyDescription;
    }
    
    public boolean hasInfarction() {
        return hasInfarction;
    }
    
    public String getInfarctionDescription() {
        return infarctionDescription;
    }
    
    public double getSTElevation() {
        return stElevation;
    }
    
    public int getInfarctionSeverity() {
        return infarctionSeverity;
    }
    
    public boolean hasConductionDisorder() {
        return hasConductionDisorder;
    }
    
    public String getConductionDescription() {
        return conductionDescription;
    }
    
    public String getBranchBlockType() {
        return branchBlockType;
    }
    
    public String getAVBlockType() {
        return avBlockType;
    }
    
    public double getPRInterval() {
        return prInterval;
    }
    
    public double getQRSWidth() {
        return qrsWidth;
    }
    
    public boolean hasInflammation() {
        return hasInflammation;
    }
    
    public String getInflammationDescription() {
        return inflammationDescription;
    }
    
    public String getInflammationType() {
        return inflammationType;
    }
    
    public double getDiffuseSTElevation() {
        return diffuseSTElevation;
    }
    
    public boolean hasDrugEffect() {
        return hasDrugEffect;
    }
    
    public String getDrugEffectDescription() {
        return drugEffectDescription;
    }
    
    public String getDrugType() {
        return drugType;
    }
    
    public double getSTDepression() {
        return stDepression;
    }
    
    public double getQTInterval() {
        return qtInterval;
    }
}
