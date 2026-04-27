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
    private boolean hasMyocardialScar;
    private String myocardialScarDescription;
    private boolean hasVentricularAneurysm;
    private String ventricularAneurysmDescription;
    private boolean hasPostInfarctionArrhythmia;
    private String postInfarctionArrhythmiaDescription;
    
    // Novos diagnósticos - Arritmias
    private boolean hasAtrialFibrillation;
    private String atrialFibrillationDescription;
    private boolean hasAtrialFlutter;
    private String atrialFlutterDescription;
    private boolean hasSuperventricularTachycardia;
    private String superventricularTachycardiaDescription;
    private boolean hasVentricularTachycardia;
    private String ventricularTachycardiaDescription;
    private boolean hasVentricularFibrillation;
    private String ventricularFibrillationDescription;
    private boolean hasEctopicBeats;
    private String ectopicBeatsDescription;
    
    // Síndromes Congênitas
    private boolean hasWolffParkinsonWhite;
    private String wolffParkinsonWhiteDescription;
    private boolean hasBrugadaSyndrome;
    private String brugadaSyndromeDescription;
    private boolean hasLongQT;
    private String longQTDescription;
    private boolean hasShortQT;
    private String shortQTDescription;
    
    // Desordens de Eletrólito
    private boolean hasHyperkalemia;
    private String hyperkalemiaDescription;
    private boolean hasHypokalemia;
    private String hypokalemiaDescription;
    private boolean hasHypercalcemia;
    private String hypercalcemiaDescription;
    private boolean hasHypocalcemia;
    private String hypocalcemiaDescription;
    
    // Padrões Especiais
    private boolean hasWellensPattern;
    private String wellensPatternDescription;
    private boolean hasOsbornWave;
    private String osbornWaveDescription;
    private boolean hasEarlyRepolarization;
    private String earlyRepolarizationDescription;
    private boolean hasEpsilonWave;
    private String epsilonWaveDescription;
    
    // Hipertrofias
    private boolean hasLVH;
    private String lvhDescription;
    private boolean hasRVH;
    private String rvhDescription;
    private boolean hasAtrialOverload;
    private String atrialOverloadDescription;
    
    // Isquemias
    private boolean hasSilentIschemia;
    private String silentIschemiaDescription;
    private boolean hasPrinzmetalAngina;
    private String prinzmetalAnginaDescription;
    private boolean hasCoronarySpasm;
    private String coronarySpasmDescription;
    private boolean hasTakotsuboSyndrome;
    private String takotsuboSyndromeDescription;
    
    // Outras Condições
    private boolean hasPulmonaryEmbolism;
    private String pulmonaryEmbolismDescription;
    private boolean hasPulmonaryHypertension;
    private String pulmonaryHypertensionDescription;
    private boolean hasChronicLungDisease;
    private String chronicLungDiseaseDescription;
    private boolean hasHypothermia;
    private String hypothermiaDescription;
    private boolean hasSleepApnea;
    private String sleepApneaDescription;
    private boolean hasVasovagalSyncope;
    private String vasovagalSyncopeDescription;
    
    // Alterações Metabólicas e Hormonais
    private boolean hasHypothyroidism;
    private String hypothyroidismDescription;
    private boolean hasHyperthyroidism;
    private String hyperthyroidismDescription;
    
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
        this.hasMyocardialScar = false;
        this.myocardialScarDescription = "";
        this.hasVentricularAneurysm = false;
        this.ventricularAneurysmDescription = "";
        this.hasPostInfarctionArrhythmia = false;
        this.postInfarctionArrhythmiaDescription = "";
        
        // Inicializar novos diagnósticos - Arritmias
        this.hasAtrialFibrillation = false;
        this.atrialFibrillationDescription = "";
        this.hasAtrialFlutter = false;
        this.atrialFlutterDescription = "";
        this.hasSuperventricularTachycardia = false;
        this.superventricularTachycardiaDescription = "";
        this.hasVentricularTachycardia = false;
        this.ventricularTachycardiaDescription = "";
        this.hasVentricularFibrillation = false;
        this.ventricularFibrillationDescription = "";
        this.hasEctopicBeats = false;
        this.ectopicBeatsDescription = "";
        
        // Síndromes Congênitas
        this.hasWolffParkinsonWhite = false;
        this.wolffParkinsonWhiteDescription = "";
        this.hasBrugadaSyndrome = false;
        this.brugadaSyndromeDescription = "";
        this.hasLongQT = false;
        this.longQTDescription = "";
        this.hasShortQT = false;
        this.shortQTDescription = "";
        
        // Desordens de Eletrólito
        this.hasHyperkalemia = false;
        this.hyperkalemiaDescription = "";
        this.hasHypokalemia = false;
        this.hypokalemiaDescription = "";
        this.hasHypercalcemia = false;
        this.hypercalcemiaDescription = "";
        this.hasHypocalcemia = false;
        this.hypocalcemiaDescription = "";
        
        // Padrões Especiais
        this.hasWellensPattern = false;
        this.wellensPatternDescription = "";
        this.hasOsbornWave = false;
        this.osbornWaveDescription = "";
        this.hasEarlyRepolarization = false;
        this.earlyRepolarizationDescription = "";
        this.hasEpsilonWave = false;
        this.epsilonWaveDescription = "";
        
        // Hipertrofias
        this.hasLVH = false;
        this.lvhDescription = "";
        this.hasRVH = false;
        this.rvhDescription = "";
        this.hasAtrialOverload = false;
        this.atrialOverloadDescription = "";
        
        // Isquemias
        this.hasSilentIschemia = false;
        this.silentIschemiaDescription = "";
        this.hasPrinzmetalAngina = false;
        this.prinzmetalAnginaDescription = "";
        this.hasCoronarySpasm = false;
        this.coronarySpasmDescription = "";
        this.hasTakotsuboSyndrome = false;
        this.takotsuboSyndromeDescription = "";
        
        // Outras Condições
        this.hasPulmonaryEmbolism = false;
        this.pulmonaryEmbolismDescription = "";
        this.hasPulmonaryHypertension = false;
        this.pulmonaryHypertensionDescription = "";
        this.hasChronicLungDisease = false;
        this.chronicLungDiseaseDescription = "";
        this.hasHypothermia = false;
        this.hypothermiaDescription = "";
        this.hasSleepApnea = false;
        this.sleepApneaDescription = "";
        this.hasVasovagalSyncope = false;
        this.vasovagalSyncopeDescription = "";
        
        // Alterações Metabólicas e Hormonais
        this.hasHypothyroidism = false;
        this.hypothyroidismDescription = "";
        this.hasHyperthyroidism = false;
        this.hyperthyroidismDescription = "";
        
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
        detectMyocardialScar();
        detectVentricularAneurysm();
        detectPostInfarctionArrhythmia();
        
        // Novos métodos de detecção
        detectAtrialFibrillation();
        detectAtrialFlutter();
        detectSuperventricularTachycardia();
        detectVentricularTachycardia();
        detectVentricularFibrillation();
        detectWolffParkinsonWhite();
        detectBrugadaSyndrome();
        detectQTAbnormalities();
        detectElectrolyteDisorders();
        detectWellensPattern();
        detectOsbornWave();
        detectEarlyRepolarization();
        detectEpsilonWave();
        detectVentricularHypertrophy();
        detectAtrialOverload();
        detectSilentIschemia();
        detectPrinzmetalAngina();
        detectCoronarySpasm();
        detectTakotsuboSyndrome();
        detectPulmonaryEmbolism();
        detectPulmonaryHypertension();
        detectChronicLungDisease();
        detectHypothermia();
        detectSleepApnea();
        detectVasovagalSyncope();
        detectHypothyroidism();
        detectHyperthyroidism();
        
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
    
    private void detectMyocardialScar() {
        List<Double> voltages = data.getVoltages();
        
        if (peakIndices.size() < 3 || voltages.size() < 20) {
            hasMyocardialScar = false;
            myocardialScarDescription = "";
            return;
        }
        
        StringBuilder report = new StringBuilder();
        int scarIndicators = 0;
        double avgVoltage = data.getAverageVoltage();
        double voltageRange = data.getMaxVoltage() - data.getMinVoltage();
        
        // Cicatriz miocárdica: padrão de ondas Q permanentes e duradouras
        // Q wave permanente > 0.04 segundos em amplitude > 25% do complexo QRS
        double permanentQWaveCount = 0;
        double qWaveDepthSum = 0;
        
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int qStart = Math.max(0, peakIndex - 12);
            int qEnd = peakIndex;
            
            double maxQDepth = 0;
            for (int j = qStart; j < qEnd; j++) {
                double qDepth = Math.min(voltages.get(j), 0);
                if (qDepth < maxQDepth) {
                    maxQDepth = qDepth;
                }
            }
            
            // Onda Q profunda (indicador de cicatriz)
            if (maxQDepth < -0.3 && Math.abs(maxQDepth) > voltageRange * 0.25) {
                permanentQWaveCount++;
                qWaveDepthSum += Math.abs(maxQDepth);
                scarIndicators++;
            }
        }
        
        // Cicatriz: redução de voltagem generalizada em região específica (voltagem residual baixa)
        double lowVoltageZones = 0;
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int afterPeakStart = peakIndex + 3;
            int afterPeakEnd = Math.min(afterPeakStart + 15, peakIndices.get(i + 1));
            
            double maxVoltageInZone = Double.MIN_VALUE;
            for (int j = afterPeakStart; j < afterPeakEnd && j < voltages.size(); j++) {
                maxVoltageInZone = Math.max(maxVoltageInZone, Math.abs(voltages.get(j)));
            }
            
            // Amplitude residual muito baixa (sinal de tecido cicatricial)
            if (maxVoltageInZone < voltageRange * 0.15) {
                lowVoltageZones++;
            }
        }
        
        if (lowVoltageZones > peakIndices.size() * 0.4) {
            scarIndicators++;
        }
        
        // Segmento ST anormal persistente (indicador de remodelação pós-infarto)
        double abnormalSTCount = 0;
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int stStart = peakIndex + 5;
            int stEnd = Math.min(stStart + 8, peakIndices.get(i + 1));
            
            if (stStart < voltages.size()) {
                double stVariation = 0;
                for (int j = stStart + 1; j < stEnd && j < voltages.size(); j++) {
                    stVariation += Math.abs(voltages.get(j) - voltages.get(j - 1));
                }
                
                // ST anormalmente irregular
                if (stVariation > voltageRange * 0.3) {
                    abnormalSTCount++;
                }
            }
        }
        
        if (abnormalSTCount > 2) {
            scarIndicators++;
        }
        
        // Determina se há cicatriz miocárdica
        if (scarIndicators >= 2 && permanentQWaveCount > 0) {
            hasMyocardialScar = true;
            report.append("🔸 Cicatriz Miocárdica Detectada | ");
            report.append("Ondas Q permanentes em ").append((int)permanentQWaveCount).append(" complexos | ");
            report.append("Padrão indicativo de infarto prévio com remodelação miocárdica");
            myocardialScarDescription = report.toString();
            isAnomalous = true;
        } else {
            hasMyocardialScar = false;
            myocardialScarDescription = "";
        }
    }
    
    private void detectVentricularAneurysm() {
        List<Double> voltages = data.getVoltages();
        
        if (peakIndices.size() < 3 || voltages.size() < 20) {
            hasVentricularAneurysm = false;
            ventricularAneurysmDescription = "";
            return;
        }
        
        StringBuilder report = new StringBuilder();
        int aneurysmIndicators = 0;
        double voltageRange = data.getMaxVoltage() - data.getMinVoltage();
        
        // Aneurisma ventricular: elevação ST persistente + depressão recíproca
        // Elevação ST localizada prolongada (não resolve rapidamente)
        double persistentSTElevation = 0;
        double maxPersistentST = 0;
        int persistentSTZones = 0;
        
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int stStart = peakIndex + 3;
            int stEnd = Math.min(stStart + 10, peakIndices.get(i + 1));
            
            if (stStart < voltages.size()) {
                double baselineVoltage = (voltages.get(peakIndex - 2) + voltages.get(peakIndex)) / 2.0;
                
                int stElevatedCount = 0;
                for (int j = stStart; j < stEnd && j < voltages.size(); j++) {
                    double elevation = voltages.get(j) - baselineVoltage;
                    if (elevation > 0.5) {
                        stElevatedCount++;
                        persistentSTElevation += elevation;
                        maxPersistentST = Math.max(maxPersistentST, elevation);
                    }
                }
                
                if (stElevatedCount > 5) {
                    persistentSTZones++;
                    aneurysmIndicators++;
                }
            }
        }
        
        // Ondas T invertidas em zona com elevação ST (padrão típico de aneurisma)
        double invertedTInAneurysm = 0;
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int stStart = peakIndex + 3;
            int stEnd = Math.min(stStart + 10, peakIndices.get(i + 1));
            int tWaveStart = stEnd;
            int tWaveEnd = Math.min(tWaveStart + 12, peakIndices.get(i + 1));
            
            // Verifica se há elevação ST
            double baselineVoltage = (voltages.get(peakIndex - 2) + voltages.get(peakIndex)) / 2.0;
            boolean hasSTElevation = false;
            for (int j = stStart; j < stEnd && j < voltages.size(); j++) {
                if ((voltages.get(j) - baselineVoltage) > 0.3) {
                    hasSTElevation = true;
                    break;
                }
            }
            
            // Se há elevação ST, verifica onda T invertida
            if (hasSTElevation && tWaveStart < voltages.size()) {
                for (int j = tWaveStart; j < tWaveEnd && j < voltages.size(); j++) {
                    if (voltages.get(j) < baselineVoltage - 0.4) {
                        invertedTInAneurysm++;
                    }
                }
            }
        }
        
        if (invertedTInAneurysm > 5) {
            aneurysmIndicators++;
        }
        
        // Depressão ST recíproca (oposto da elevação)
        double reciprocalDepressionCount = 0;
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIndex = peakIndices.get(i);
            int stStart = peakIndex + 3;
            int stEnd = Math.min(stStart + 8, peakIndices.get(i + 1));
            
            if (stStart < voltages.size()) {
                double baselineVoltage = (voltages.get(peakIndex - 2) + voltages.get(peakIndex)) / 2.0;
                
                for (int j = stStart; j < stEnd && j < voltages.size(); j++) {
                    double depression = baselineVoltage - voltages.get(j);
                    if (depression > 0.3) {
                        reciprocalDepressionCount++;
                    }
                }
            }
        }
        
        if (reciprocalDepressionCount > 8) {
            aneurysmIndicators++;
        }
        
        // Determina se há aneurisma ventricular
        if (aneurysmIndicators >= 2 && persistentSTZones > 0) {
            hasVentricularAneurysm = true;
            report.append("🔸 Aneurisma Ventricular Suspeito | ");
            report.append("Elevação ST persistente em ").append(persistentSTZones).append(" zonas (até ").append(String.format("%.2f", maxPersistentST)).append("mV) | ");
            report.append("Possível deformação da parede ventricular");
            ventricularAneurysmDescription = report.toString();
            isAnomalous = true;
        } else {
            hasVentricularAneurysm = false;
            ventricularAneurysmDescription = "";
        }
    }
    
    private void detectPostInfarctionArrhythmia() {
        List<Double> intervals = new ArrayList<>();
        
        if (peakIndices.size() < 5) {
            hasPostInfarctionArrhythmia = false;
            postInfarctionArrhythmiaDescription = "";
            return;
        }
        
        // Calcula intervalos entre picos
        for (int i = 1; i < peakIndices.size(); i++) {
            int samples = peakIndices.get(i) - peakIndices.get(i - 1);
            intervals.add(samples / data.getSamplingRate() * 1000);
        }
        
        StringBuilder report = new StringBuilder();
        int arrhythmiaIndicators = 0;
        
        // 1. Variabilidade anormal dos intervalos (indicador de arritmia pós-infarto)
        double meanInterval = intervals.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double variance = intervals.stream()
            .mapToDouble(i -> Math.pow(i - meanInterval, 2))
            .average().orElse(0);
        double stdDev = Math.sqrt(variance);
        double variationCoeff = (stdDev / meanInterval) * 100;
        
        if (variationCoeff > 25 && hasInfarction) {
            report.append("⚠️ Arritmia Pós-Infarto: Variabilidade alta nos intervalos (").append(String.format("%.1f", variationCoeff)).append("%)");
            arrhythmiaIndicators++;
        } else if (variationCoeff > 30) {
            report.append("⚠️ Arritmia: Variabilidade muito alta nos intervalos (").append(String.format("%.1f", variationCoeff)).append("%)");
            arrhythmiaIndicators++;
        }
        
        // 2. Pausa sinusal (intervalo muito longo entre dois picos)
        double maxInterval = intervals.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        double pauseThreshold = meanInterval * 1.6;
        
        if (maxInterval > pauseThreshold && hasInfarction) {
            report.append(" | Pausa sinusal detectada após infarto");
            arrhythmiaIndicators++;
        }
        
        // 3. Batidas ectópicas (intervalo muito curto seguido de pausa)
        int ectopicBeats = 0;
        for (int i = 1; i < intervals.size() - 1; i++) {
            double currentInterval = intervals.get(i);
            double nextInterval = intervals.get(i + 1);
            
            // Intervalo curto (prematura) seguido de pausa compensatória
            if (currentInterval < meanInterval * 0.6 && nextInterval > meanInterval * 1.2) {
                ectopicBeats++;
            }
        }
        
        if (ectopicBeats > 2) {
            if (report.length() > 0) report.append(" | ");
            report.append("Batidas ectópicas detectadas (").append(ectopicBeats).append(") - comum em pós-infarto");
            arrhythmiaIndicators++;
        }
        
        // 4. Fibrilação atrial pós-infarto (ausência de ritmo regular + frequência alta)
        double irregularityIndex = 0;
        for (int i = 1; i < intervals.size(); i++) {
            irregularityIndex += Math.abs(intervals.get(i) - intervals.get(i - 1));
        }
        irregularityIndex /= intervals.size();
        
        if (irregularityIndex > meanInterval * 0.4 && bpm > 100 && hasInfarction) {
            if (report.length() > 0) report.append(" | ");
            report.append("Padrão sugestivo de fibrilação atrial pós-infarto");
            arrhythmiaIndicators++;
        }
        
        // Determina se há arritmia pós-infarto
        if (arrhythmiaIndicators > 0 && (hasInfarction || hasMyocardialScar)) {
            hasPostInfarctionArrhythmia = true;
            postInfarctionArrhythmiaDescription = report.toString();
            isAnomalous = true;
        } else {
            hasPostInfarctionArrhythmia = false;
            postInfarctionArrhythmiaDescription = "";
        }
    }
    
    private void detectAtrialFibrillation() {
        if (peakIndices.size() < 10) {
            hasAtrialFibrillation = false;
            return;
        }
        List<Double> intervals = new ArrayList<>();
        for (int i = 1; i < peakIndices.size(); i++) {
            intervals.add((double)(peakIndices.get(i) - peakIndices.get(i - 1)));
        }
        double meanInterval = intervals.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double variance = intervals.stream().mapToDouble(i -> Math.pow(i - meanInterval, 2)).average().orElse(0);
        double cv = Math.sqrt(variance) / meanInterval;
        if (cv > 0.3 && bpm > 90) {
            hasAtrialFibrillation = true;
            atrialFibrillationDescription = "🔴 Fibrilação Atrial: Ritmo totalmente irregular (CV: " + String.format("%.2f", cv) + ") | Sem ondas P identificáveis | Risco de trombose";
            isAnomalous = true;
        } else {
            hasAtrialFibrillation = false;
            atrialFibrillationDescription = "";
        }
    }
    
    private void detectAtrialFlutter() {
        if (peakIndices.size() < 5) {
            hasAtrialFlutter = false;
            return;
        }
        double minInterval = peakIndices.stream().mapToInt(i -> i).max().orElse(0) / (double)peakIndices.size();
        if (bpm > 140 && bpm < 250 && !hasAtrialFibrillation) {
            hasAtrialFlutter = true;
            atrialFlutterDescription = "⚠️ Flutter Atrial: Frequência rápida regular (BPM: " + String.format("%.0f", bpm) + ") | Padrão em 'dente de serra' | Risco de comprometimento hemodinâmico";
            isAnomalous = true;
        } else {
            hasAtrialFlutter = false;
            atrialFlutterDescription = "";
        }
    }
    
    private void detectSuperventricularTachycardia() {
        if (bpm > 150 && bpm < 250 && !hasAtrialFlutter && !hasAtrialFibrillation) {
            hasSuperventricularTachycardia = true;
            superventricularTachycardiaDescription = "⚠️ Taquicardia Supraventricular: Frequência muito alta (BPM: " + String.format("%.0f", bpm) + ") | Ritmo regular | Pode resolver com manobra vagal";
            isAnomalous = true;
        } else {
            hasSuperventricularTachycardia = false;
            superventricularTachycardiaDescription = "";
        }
    }
    
    private void detectVentricularTachycardia() {
        if (peakIndices.size() < 5 || data.getVoltages().size() < 50) {
            hasVentricularTachycardia = false;
            return;
        }
        List<Double> intervals = new ArrayList<>();
        for (int i = 1; i < peakIndices.size(); i++) {
            intervals.add((double)(peakIndices.get(i) - peakIndices.get(i - 1)));
        }
        double meanInterval = intervals.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        if (bpm > 140 && qrsWidth > 140 && hasInfarction) {
            hasVentricularTachycardia = true;
            ventricularTachycardiaDescription = "🔴 TAQUICARDIA VENTRICULAR: Ritmo rápido perigoso (BPM: " + String.format("%.0f", bpm) + ") | QRS alargado (> 140ms) | EMERGÊNCIA MÉDICA";
            isAnomalous = true;
        } else {
            hasVentricularTachycardia = false;
            ventricularTachycardiaDescription = "";
        }
    }
    
    private void detectVentricularFibrillation() {
        List<Double> voltages = data.getVoltages();
        if (peakIndices.size() < 3 || voltages.size() < 100) {
            hasVentricularFibrillation = false;
            return;
        }
        double variation = 0;
        for (int i = 1; i < Math.min(100, voltages.size()); i++) {
            variation += Math.abs(voltages.get(i) - voltages.get(i - 1));
        }
        if (variation > data.getMaxVoltage() * 0.5 && peakIndices.size() < 5 && bpm > 300) {
            hasVentricularFibrillation = true;
            ventricularFibrillationDescription = "🔴 FIBRILAÇÃO VENTRICULAR: Ritmo TOTALMENTE CAÓTICO | Sem pulso | PARADA CARDÍACA | REANIMAÇÃO IMEDIATA!";
            isAnomalous = true;
        } else {
            hasVentricularFibrillation = false;
            ventricularFibrillationDescription = "";
        }
    }
    
    private void detectWolffParkinsonWhite() {
        if (prInterval < 100 && qrsWidth > 100) {
            hasWolffParkinsonWhite = true;
            wolffParkinsonWhiteDescription = "⚡ Síndrome de Wolff-Parkinson-White (WPW): Intervalo PR curto (" + String.format("%.0f", prInterval) + "ms) | Onda Delta visível | Via acessória extra | Risco de taquicardia";
            isAnomalous = true;
        } else {
            hasWolffParkinsonWhite = false;
            wolffParkinsonWhiteDescription = "";
        }
    }
    
    private void detectBrugadaSyndrome() {
        List<Double> voltages = data.getVoltages();
        if (peakIndices.size() < 3) {
            hasBrugadaSyndrome = false;
            return;
        }
        double stElevationV1V2 = 0;
        for (int i = 0; i < Math.min(50, voltages.size()); i++) {
            if (i > 5 && i < 15 && voltages.get(i) > data.getMaxVoltage() * 0.6) {
                stElevationV1V2 += voltages.get(i);
            }
        }
        if (stElevationV1V2 > 2 && qrsWidth < 120) {
            hasBrugadaSyndrome = true;
            brugadaSyndromeDescription = "⚠️ Síndrome de Brugada: Elevação ST tipo 1 em precordiais | Mutação genética | Risco de morte súbita | Requer monitoramento";
            isAnomalous = true;
        } else {
            hasBrugadaSyndrome = false;
            brugadaSyndromeDescription = "";
        }
    }
    
    private void detectQTAbnormalities() {
        if (qtInterval > 480) {
            hasLongQT = true;
            longQTDescription = "⚠️ Síndrome do QT Longo: Intervalo QT prolongado (" + String.format("%.0f", qtInterval) + "ms) | Risco de Torsades de Pointes | Evitar estimulantes";
            isAnomalous = true;
        } else if (qtInterval < 300) {
            hasShortQT = true;
            shortQTDescription = "⚠️ Síndrome do QT Curto: Intervalo QT muito curto (" + String.format("%.0f", qtInterval) + "ms) | Risco de arritmias ventriculares | Monitoramento recomendado";
            isAnomalous = true;
        } else {
            hasLongQT = false;
            hasShortQT = false;
            longQTDescription = "";
            shortQTDescription = "";
        }
    }
    
    private void detectElectrolyteDisorders() {
        List<Double> voltages = data.getVoltages();
        if (peakIndices.size() < 3) return;
        double voltageRange = data.getMaxVoltage() - data.getMinVoltage();
        
        // Hipercalemia: ondas T em tenda, QRS alargado
        if (qrsWidth > 130 && peakIndices.size() > 5) {
            boolean tWaveTent = false;
            for (int i = 10; i < Math.min(30, voltages.size()); i++) {
                if (voltages.get(i) > data.getMaxVoltage() * 0.7) tWaveTent = true;
            }
            if (tWaveTent) {
                hasHyperkalemia = true;
                hyperkalemiaDescription = "⚠️ Hipercalemia Suspeita: Ondas T em 'tenda' | QRS alargado (> 130ms) | Potássio elevado | URGÊNCIA";
                isAnomalous = true;
            }
        }
        
        // Hipocalemia: onda U proeminente, ST deprimido
        if (stDepression > 0.1 && qtInterval > 440) {
            hasHypokalemia = true;
            hypokalemiaDescription = "⚠️ Hipocalemia Suspeita: Onda U proeminente | ST deprimido | Intervalo QT prolongado | Potássio baixo";
            isAnomalous = true;
        }
        
        // Hipercalcemia: QT encurtado, ST elevado
        if (qtInterval < 350 && stElevation > 0.3) {
            hasHypercalcemia = true;
            hypercalcemiaDescription = "⚠️ Hipercalcemia: QT muito curto (" + String.format("%.0f", qtInterval) + "ms) | ST elevado | Risco de arritmia";
            isAnomalous = true;
        }
        
        // Hipocalcemia: QT prolongado, padrão especial
        if (qtInterval > 500) {
            hasHypocalcemia = true;
            hypocalcemiaDescription = "⚠️ Hipocalcemia: QT prolongado (" + String.format("%.0f", qtInterval) + "ms) | Risco de convulsões | Cálcio baixo";
            isAnomalous = true;
        }
    }
    
    private void detectWellensPattern() {
        List<Double> voltages = data.getVoltages();
        if (peakIndices.size() < 3 || voltages.size() < 50) {
            hasWellensPattern = false;
            return;
        }
        int invertedTCount = 0;
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIdx = peakIndices.get(i);
            int tStart = peakIdx + 15;
            if (tStart < voltages.size() && tStart + 10 < voltages.size()) {
                for (int j = tStart; j < tStart + 10; j++) {
                    if (voltages.get(j) < -0.2) invertedTCount++;
                }
            }
        }
        if (invertedTCount > 8 && !hasInfarction) {
            hasWellensPattern = true;
            wellensPatternDescription = "⚠️ Padrão de Wellens: Ondas T invertidas simétricas | Marca infarto anterior IMINENTE | Angiografia urgente recomendada";
            isAnomalous = true;
        } else {
            hasWellensPattern = false;
            wellensPatternDescription = "";
        }
    }
    
    private void detectOsbornWave() {
        List<Double> voltages = data.getVoltages();
        if (peakIndices.size() < 3 || voltages.size() < 30) {
            hasOsbornWave = false;
            return;
        }
        boolean osbornFound = false;
        for (int i = 5; i < Math.min(20, voltages.size()); i++) {
            double voltage = voltages.get(i);
            if (voltage > data.getMaxVoltage() * 0.5 && i > 0 && voltage > voltages.get(i - 1) && voltage > voltages.get(i + 1)) {
                osbornFound = true;
                break;
            }
        }
        if (osbornFound && bpm < 50) {
            hasOsbornWave = true;
            osbornWaveDescription = "🔵 Padrão de Osborn: Onda J visível | Bradicardia severa (BPM: " + String.format("%.0f", bpm) + ") | HIPOTERMIA";
            isAnomalous = true;
        } else {
            hasOsbornWave = false;
            osbornWaveDescription = "";
        }
    }
    
    private void detectEarlyRepolarization() {
        List<Double> voltages = data.getVoltages();
        if (peakIndices.size() < 3) return;
        boolean earlyRepol = false;
        double stElevCount = 0;
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIdx = peakIndices.get(i);
            if (peakIdx + 3 < voltages.size()) {
                double stValue = voltages.get(peakIdx + 3);
                if (stValue > 0.1 && stValue < 0.3) stElevCount++;
            }
        }
        if (stElevCount > 2 && stElevation < 0.5) {
            hasEarlyRepolarization = true;
            earlyRepolarizationDescription = "🟡 Repolarização Precoce: Elevação ST juvenil simétrica | ST < 0.5mV | Padrão variante normal";
            isAnomalous = false;
        } else {
            hasEarlyRepolarization = false;
            earlyRepolarizationDescription = "";
        }
    }
    
    private void detectEpsilonWave() {
        List<Double> voltages = data.getVoltages();
        if (peakIndices.size() < 3 || qrsWidth < 110) {
            hasEpsilonWave = false;
            return;
        }
        int epsilonCount = 0;
        for (int i = 1; i < peakIndices.size() - 1; i++) {
            int peakIdx = peakIndices.get(i);
            int afterPeak = peakIdx + 10;
            if (afterPeak + 3 < voltages.size()) {
                double baselineVariation = Math.abs(voltages.get(afterPeak) - voltages.get(afterPeak + 3));
                if (baselineVariation > 0.05 && baselineVariation < 0.15) epsilonCount++;
            }
        }
        if (epsilonCount > 3) {
            hasEpsilonWave = true;
            epsilonWaveDescription = "⚠️ Onda Epsilon: Pequenas deflexões após QRS | Arritmia ventricular de origem direita | Risco de morte súbita";
            isAnomalous = true;
        } else {
            hasEpsilonWave = false;
            epsilonWaveDescription = "";
        }
    }
    
    private void detectVentricularHypertrophy() {
        List<Double> voltages = data.getVoltages();
        double highVoltageCount = 0;
        for (double v : voltages) {
            if (Math.abs(v) > data.getMaxVoltage() * 0.8) highVoltageCount++;
        }
        if (qrsWidth > 100 && (highVoltageCount / voltages.size()) > 0.2) {
            if (qrsWidth > 120) {
                hasRVH = true;
                rvhDescription = "⚠️ Hipertrofia Ventricular Direita: Desvio de eixo para direita | QRS alargado | Pressão pulmonar elevada";
                isAnomalous = true;
            } else {
                hasLVH = true;
                lvhDescription = "⚠️ Hipertrofia Ventricular Esquerda: Voltagens elevadas | Strain pattern | Sobrecarga cardíaca crônica | Risco de morte súbita";
                isAnomalous = true;
            }
        } else {
            hasLVH = false;
            hasRVH = false;
            lvhDescription = "";
            rvhDescription = "";
        }
    }
    
    private void detectAtrialOverload() {
        List<Double> voltages = data.getVoltages();
        if (peakIndices.size() < 3 || voltages.size() < 50) {
            hasAtrialOverload = false;
            return;
        }
        boolean beforePeakVariation = false;
        for (int i = 2; i < peakIndices.size() - 1; i++) {
            int peakIdx = peakIndices.get(i);
            if (peakIdx > 15 && peakIdx - 10 >= 0) {
                double variation = 0;
                for (int j = peakIdx - 10; j < peakIdx - 5; j++) {
                    variation += Math.abs(voltages.get(j));
                }
                if (variation > data.getMaxVoltage() * 0.3) beforePeakVariation = true;
            }
        }
        if (beforePeakVariation) {
            hasAtrialOverload = true;
            atrialOverloadDescription = "⚠️ Sobrecarga Atrial: Ondas P alteradas | Aumento de duração | Pressão atrial elevada";
            isAnomalous = true;
        } else {
            hasAtrialOverload = false;
            atrialOverloadDescription = "";
        }
    }
    
    private void detectSilentIschemia() {
        if (hasInfarction || hasWellensPattern) return;
        if (stDepression > 0.1) {
            hasSilentIschemia = true;
            silentIschemiaDescription = "⚠️ Isquemia Silenciosa: Depressão ST detectada | Sem sintomas | Fluxo coronariano reduzido";
            isAnomalous = true;
        } else {
            hasSilentIschemia = false;
            silentIschemiaDescription = "";
        }
    }
    
    private void detectPrinzmetalAngina() {
        if (stElevation > 0.2 && stElevation < 0.5 && !hasInfarction) {
            hasPrinzmetalAngina = true;
            prinzmetalAnginaDescription = "⚠️ Angina de Prinzmetal: Elevação ST transitória (0.2-0.5mV) | Sem ondas Q | Espasmo coronariano | Tende a resolver";
            isAnomalous = true;
        } else {
            hasPrinzmetalAngina = false;
            prinzmetalAnginaDescription = "";
        }
    }
    
    private void detectCoronarySpasm() {
        List<Double> intervals = new ArrayList<>();
        if (peakIndices.size() < 5) return;
        for (int i = 1; i < peakIndices.size(); i++) {
            intervals.add((double)(peakIndices.get(i) - peakIndices.get(i - 1)));
        }
        double maxInterval = intervals.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        double minInterval = intervals.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        if ((maxInterval - minInterval) / minInterval > 0.4 && stElevation > 0.1) {
            hasCoronarySpasm = true;
            coronarySpasmDescription = "⚠️ Espasmo Coronariano: Variação irregular na frequência | Elevação ST variável | Episódico";
            isAnomalous = true;
        } else {
            hasCoronarySpasm = false;
            coronarySpasmDescription = "";
        }
    }
    
    private void detectTakotsuboSyndrome() {
        if (hasInfarction && qrsWidth < 120 && stElevation > 0.5 && infarctionSeverity < 4) {
            hasTakotsuboSyndrome = true;
            takotsuboSyndromeDescription = "⚠️ Síndrome de Takotsubo: Padrão pseudo-infarto | Apical ballooning | Gatilho emocional típico | Prognóstico melhor";
            isAnomalous = true;
        } else {
            hasTakotsuboSyndrome = false;
            takotsuboSyndromeDescription = "";
        }
    }
    
    private void detectPulmonaryEmbolism() {
        if (bpm > 120 && qrsWidth < 120) {
            int s1q3t3Count = 0;
            // Padrão S1Q3T3 é característico
            if (stElevation > 0.1 && stDepression > 0.1) s1q3t3Count++;
            if (s1q3t3Count > 0) {
                hasPulmonaryEmbolism = true;
                pulmonaryEmbolismDescription = "🔴 EMBOLIA PULMONAR SUSPEITA: Taquicardia (BPM: " + String.format("%.0f", bpm) + ") | Padrão S1Q3T3 | " +
                    "Alterações súbitas no eixo elétrico do coração | EMERGÊNCIA VASCULAR";
                isAnomalous = true;
            } else {
                hasPulmonaryEmbolism = false;
                pulmonaryEmbolismDescription = "";
            }
        } else {
            hasPulmonaryEmbolism = false;
            pulmonaryEmbolismDescription = "";
        }
    }
    
    private void detectPulmonaryHypertension() {
        if (hasRVH && prInterval > 150) {
            hasPulmonaryHypertension = true;
            pulmonaryHypertensionDescription = "⚠️ Hipertensão Pulmonar: Hipertrofia RV + Intervalo PR prolongado | Pressão pulmonar elevada | Risco progressivo";
            isAnomalous = true;
        } else {
            hasPulmonaryHypertension = false;
            pulmonaryHypertensionDescription = "";
        }
    }
    
    private void detectChronicLungDisease() {
        if (hasRVH && bpm > 85) {
            hasChronicLungDisease = true;
            chronicLungDiseaseDescription = "⚠️ DPOC ou Enfisema: Hipertrofia RV + Taquicardia basal | " +
                "Sinais de sobrecarga crônica devido à dificuldade de bombear sangue pelos pulmões | Cor pulmonale";
            isAnomalous = true;
        } else {
            hasChronicLungDisease = false;
            chronicLungDiseaseDescription = "";
        }
    }
    
    private void detectHypothermia() {
        if (bpm < 40 && hasOsbornWave) {
            hasHypothermia = true;
            hypothermiaDescription = "🔵 HIPOTERMIA: Bradicardia severa (BPM: " + String.format("%.0f", bpm) + ") | Onda de Osborn | Temperatura corporal crítica | Reaquecimento necessário";
            isAnomalous = true;
        } else {
            hasHypothermia = false;
            hypothermiaDescription = "";
        }
    }
    
    private void detectSleepApnea() {
        List<Double> intervals = new ArrayList<>();
        if (peakIndices.size() < 10) return;
        for (int i = 1; i < peakIndices.size(); i++) {
            intervals.add((double)(peakIndices.get(i) - peakIndices.get(i - 1)));
        }
        int pauseCount = 0;
        double meanInterval = intervals.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        for (double interval : intervals) {
            if (interval > meanInterval * 1.8) pauseCount++;
        }
        if (pauseCount > 3 && bpm < 60) {
            hasSleepApnea = true;
            sleepApneaDescription = "⚠️ Apneia do Sono Suspeita: Pausas sinusais múltiplas | Bradicardia | Monitoramento noturno recomendado";
            isAnomalous = true;
        } else {
            hasSleepApnea = false;
            sleepApneaDescription = "";
        }
    }
    
    private void detectVasovagalSyncope() {
        if (bpm < 50 && qrsWidth < 100 && !hasInfarction && !hasHypothermia) {
            hasVasovagalSyncope = true;
            vasovagalSyncopeDescription = "🟡 Síncope Vasovagal: Bradicardia abrupta | ECG normal | Mecanismo reflexo | Prognóstico bom";
            isAnomalous = true;
        } else {
            hasVasovagalSyncope = false;
            vasovagalSyncopeDescription = "";
        }
    }
    
    private void detectHypothyroidism() {
        if (bpm < 60 && stElevation < 0.05 && data.getMaxVoltage() < 1.0) {
            hasHypothyroidism = true;
            hypothyroidismDescription = "🔵 HIPOTIREOIDISMO SUSPEITO: Bradicardia (BPM: " + String.format("%.0f", bpm) + ") | " +
                "Diminuição da voltagem das ondas | Ritmo muito lento | Considerar testes de TSH";
            isAnomalous = true;
        } else {
            hasHypothyroidism = false;
            hypothyroidismDescription = "";
        }
    }
    
    private void detectHyperthyroidism() {
        if (bpm > 100 && (hasAtrialFibrillation || hasSuperventricularTachycardia)) {
            hasHyperthyroidism = true;
            hyperthyroidismDescription = "🔴 HIPERTIREOIDISMO SUSPEITO: Taquicardia (BPM: " + String.format("%.0f", bpm) + ") | " +
                "Frequentemente causa taquicardias ou arritmias como fibrilação atrial | Considerar testes de TSH";
            isAnomalous = true;
        } else {
            hasHyperthyroidism = false;
            hyperthyroidismDescription = "";
        }
    }
    
    public boolean hasMyocardialScar() {
        return hasMyocardialScar;
    }
    
    public String getMyocardialScarDescription() {
        return myocardialScarDescription;
    }
    
    public boolean hasVentricularAneurysm() {
        return hasVentricularAneurysm;
    }
    
    public String getVentricularAneurysmDescription() {
        return ventricularAneurysmDescription;
    }
    
    public boolean hasPostInfarctionArrhythmia() {
        return hasPostInfarctionArrhythmia;
    }
    
    public String getPostInfarctionArrhythmiaDescription() {
        return postInfarctionArrhythmiaDescription;
    }
    
    // Getters para Arritmias
    public boolean hasAtrialFibrillation() { return hasAtrialFibrillation; }
    public String getAtrialFibrillationDescription() { return atrialFibrillationDescription; }
    public boolean hasAtrialFlutter() { return hasAtrialFlutter; }
    public String getAtrialFlutterDescription() { return atrialFlutterDescription; }
    public boolean hasSuperventricularTachycardia() { return hasSuperventricularTachycardia; }
    public String getSuperventricularTachycardiaDescription() { return superventricularTachycardiaDescription; }
    public boolean hasVentricularTachycardia() { return hasVentricularTachycardia; }
    public String getVentricularTachycardiaDescription() { return ventricularTachycardiaDescription; }
    public boolean hasVentricularFibrillation() { return hasVentricularFibrillation; }
    public String getVentricularFibrillationDescription() { return ventricularFibrillationDescription; }
    public boolean hasEctopicBeats() { return hasEctopicBeats; }
    public String getEctopicBeatsDescription() { return ectopicBeatsDescription; }
    
    // Getters para Síndromes Congênitas
    public boolean hasWolffParkinsonWhite() { return hasWolffParkinsonWhite; }
    public String getWolffParkinsonWhiteDescription() { return wolffParkinsonWhiteDescription; }
    public boolean hasBrugadaSyndrome() { return hasBrugadaSyndrome; }
    public String getBrugadaSyndromeDescription() { return brugadaSyndromeDescription; }
    public boolean hasLongQT() { return hasLongQT; }
    public String getLongQTDescription() { return longQTDescription; }
    public boolean hasShortQT() { return hasShortQT; }
    public String getShortQTDescription() { return shortQTDescription; }
    
    // Getters para Desordens de Eletrólito
    public boolean hasHyperkalemia() { return hasHyperkalemia; }
    public String getHyperkalemiaDescription() { return hyperkalemiaDescription; }
    public boolean hasHypokalemia() { return hasHypokalemia; }
    public String getHypokalemiaDescription() { return hypokalemiaDescription; }
    public boolean hasHypercalcemia() { return hasHypercalcemia; }
    public String getHypercalcemiaDescription() { return hypercalcemiaDescription; }
    public boolean hasHypocalcemia() { return hasHypocalcemia; }
    public String getHypocalcemiaDescription() { return hypocalcemiaDescription; }
    
    // Getters para Padrões Especiais
    public boolean hasWellensPattern() { return hasWellensPattern; }
    public String getWellensPatternDescription() { return wellensPatternDescription; }
    public boolean hasOsbornWave() { return hasOsbornWave; }
    public String getOsbornWaveDescription() { return osbornWaveDescription; }
    public boolean hasEarlyRepolarization() { return hasEarlyRepolarization; }
    public String getEarlyRepolarizationDescription() { return earlyRepolarizationDescription; }
    public boolean hasEpsilonWave() { return hasEpsilonWave; }
    public String getEpsilonWaveDescription() { return epsilonWaveDescription; }
    
    // Getters para Hipertrofias
    public boolean hasLVH() { return hasLVH; }
    public String getLVHDescription() { return lvhDescription; }
    public boolean hasRVH() { return hasRVH; }
    public String getRVHDescription() { return rvhDescription; }
    public boolean hasAtrialOverload() { return hasAtrialOverload; }
    public String getAtrialOverloadDescription() { return atrialOverloadDescription; }
    
    // Getters para Isquemias
    public boolean hasSilentIschemia() { return hasSilentIschemia; }
    public String getSilentIschemiaDescription() { return silentIschemiaDescription; }
    public boolean hasPrinzmetalAngina() { return hasPrinzmetalAngina; }
    public String getPrinzmetalAnginaDescription() { return prinzmetalAnginaDescription; }
    public boolean hasCoronarySpasm() { return hasCoronarySpasm; }
    public String getCoronarySpasmDescription() { return coronarySpasmDescription; }
    public boolean hasTakotsuboSyndrome() { return hasTakotsuboSyndrome; }
    public String getTakotsuboSyndromeDescription() { return takotsuboSyndromeDescription; }
    
    // Getters para Outras Condições
    public boolean hasPulmonaryEmbolism() { return hasPulmonaryEmbolism; }
    public String getPulmonaryEmbolismDescription() { return pulmonaryEmbolismDescription; }
    public boolean hasPulmonaryHypertension() { return hasPulmonaryHypertension; }
    public String getPulmonaryHypertensionDescription() { return pulmonaryHypertensionDescription; }
    public boolean hasChronicLungDisease() { return hasChronicLungDisease; }
    public String getChronicLungDiseaseDescription() { return chronicLungDiseaseDescription; }
    public boolean hasHypothermia() { return hasHypothermia; }
    public String getHypothermiaDescription() { return hypothermiaDescription; }
    public boolean hasSleepApnea() { return hasSleepApnea; }
    public String getSleepApneaDescription() { return sleepApneaDescription; }
    public boolean hasVasovagalSyncope() { return hasVasovagalSyncope; }
    public String getVasovagalSyncopeDescription() { return vasovagalSyncopeDescription; }
    
    // Getters para Alterações Metabólicas e Hormonais
    public boolean hasHypothyroidism() { return hasHypothyroidism; }
    public String getHypothyroidismDescription() { return hypothyroidismDescription; }
    public boolean hasHyperthyroidism() { return hasHyperthyroidism; }
    public String getHyperthyroidismDescription() { return hyperthyroidismDescription; }
}
