import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Classe para visualizar ECG em gráfico
 * Usa XChart para gerar gráficos sem dependências externas complexas
 */
public class ECGVisualizer {
    private ECGData data;
    private ECGAnalyzer analyzer;
    private int width;
    private int height;
    
    public ECGVisualizer(ECGData data, ECGAnalyzer analyzer) {
        this.data = data;
        this.analyzer = analyzer;
        this.width = 1200;
        this.height = 600;
    }
    
    /**
     * Define o tamanho da imagem
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    /**
     * Gera e salva o gráfico como PNG usando drawing simples
     */
    public void generateAndSaveChart(String outputPath) throws IOException {
        System.out.println("Gerando gráfico...");
        
        // Cria um painel de desenho
        int padding = 80;
        int graphWidth = width - 2 * padding;
        int graphHeight = height - 2 * padding;
        
        // Cria uma imagem
        java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(
            width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        
        java.awt.Graphics2D g2d = image.createGraphics();
        
        // Fundo branco
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        
        // Desenha grade
        drawGrid(g2d, padding, graphWidth, graphHeight);
        
        // Desenha os eixos
        drawAxes(g2d, padding, graphWidth, graphHeight);
        
        // Desenha o sinal de ECG
        drawSignal(g2d, padding, graphWidth, graphHeight);
        
        // Desenha os picos detectados
        drawPeaks(g2d, padding, graphWidth, graphHeight);
        
        // Desenha informações
        drawInfo(g2d, padding);
        
        g2d.dispose();
        
        // Salva a imagem
        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();
        if (parentDir != null) {
            parentDir.mkdirs();
        }
        javax.imageio.ImageIO.write(image, "png", outputFile);
        
        System.out.println("✓ Gráfico salvo em: " + outputPath);
    }
    
    /**
     * Desenha a grade de fundo
     */
    private void drawGrid(java.awt.Graphics2D g2d, int padding, int graphWidth, int graphHeight) {
        g2d.setColor(new Color(230, 230, 230));
        g2d.setStroke(new BasicStroke(0.5f));
        
        // Linhas verticais
        int gridSpacingX = graphWidth / 10;
        for (int i = 0; i <= 10; i++) {
            int x = padding + i * gridSpacingX;
            g2d.drawLine(x, padding, x, padding + graphHeight);
        }
        
        // Linhas horizontais
        int gridSpacingY = graphHeight / 8;
        for (int i = 0; i <= 8; i++) {
            int y = padding + i * gridSpacingY;
            g2d.drawLine(padding, y, padding + graphWidth, y);
        }
    }
    
    /**
     * Desenha os eixos (X e Y)
     */
    private void drawAxes(java.awt.Graphics2D g2d, int padding, int graphWidth, int graphHeight) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        
        // Eixo X (Tempo)
        g2d.drawLine(padding, padding + graphHeight, padding + graphWidth, padding + graphHeight);
        
        // Eixo Y (Voltagem)
        g2d.drawLine(padding, padding, padding, padding + graphHeight);
        
        // Labels dos eixos
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("Tempo (s)", padding + graphWidth - 100, padding + graphHeight + 30);
        g2d.rotate(-Math.PI / 2);
        g2d.drawString("Voltagem (mV)", -padding - graphHeight / 2, padding - 50);
        g2d.rotate(Math.PI / 2);
        
        // Escala dos eixos
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        
        // Escala X
        for (int i = 0; i <= 10; i++) {
            double timeValue = (i / 10.0) * data.getDurationSeconds();
            int x = padding + (i * graphWidth / 10);
            g2d.drawString(String.format("%.1f", timeValue), x - 15, padding + graphHeight + 20);
        }
        
        // Escala Y
        double minV = data.getMinVoltage();
        double maxV = data.getMaxVoltage();
        for (int i = 0; i <= 8; i++) {
            double voltageValue = minV + (i / 8.0) * (maxV - minV);
            int y = padding + graphHeight - (i * graphHeight / 8);
            g2d.drawString(String.format("%.2f", voltageValue), padding - 50, y + 5);
        }
    }
    
    /**
     * Desenha o sinal de ECG
     */
    private void drawSignal(java.awt.Graphics2D g2d, int padding, int graphWidth, int graphHeight) {
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(1.5f));
        
        List<Double> voltages = data.getVoltages();
        double minV = data.getMinVoltage();
        double maxV = data.getMaxVoltage();
        double rangeV = maxV - minV;
        if (rangeV == 0) rangeV = 1;
        
        double timeRange = data.getDurationSeconds();
        if (timeRange == 0) timeRange = 1;
        
        for (int i = 0; i < voltages.size() - 1; i++) {
            // Converte dados para coordenadas de tela
            double x1 = padding + (i / (double) voltages.size()) * graphWidth;
            double y1 = padding + graphHeight - ((voltages.get(i) - minV) / rangeV) * graphHeight;
            
            double x2 = padding + ((i + 1) / (double) voltages.size()) * graphWidth;
            double y2 = padding + graphHeight - ((voltages.get(i + 1) - minV) / rangeV) * graphHeight;
            
            g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        }
    }
    
    /**
     * Desenha os picos detectados
     */
    private void drawPeaks(java.awt.Graphics2D g2d, int padding, int graphWidth, int graphHeight) {
        g2d.setColor(Color.RED);
        
        List<Double> voltages = data.getVoltages();
        List<Integer> peakIndices = analyzer.getPeakIndices();
        
        double minV = data.getMinVoltage();
        double maxV = data.getMaxVoltage();
        double rangeV = maxV - minV;
        if (rangeV == 0) rangeV = 1;
        
        int peakRadius = 5;
        
        for (int peakIdx : peakIndices) {
            if (peakIdx < voltages.size()) {
                double x = padding + (peakIdx / (double) voltages.size()) * graphWidth;
                double y = padding + graphHeight - ((voltages.get(peakIdx) - minV) / rangeV) * graphHeight;
                
                // Desenha círculo no pico
                g2d.fillOval((int) (x - peakRadius), (int) (y - peakRadius), 
                             peakRadius * 2, peakRadius * 2);
            }
        }
    }
    
    /**
     * Desenha informações no gráfico
     */
    private void drawInfo(java.awt.Graphics2D g2d, int padding) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        
        int yOffset = 30;
        g2d.drawString("ECG - Eletrocardiograma", 20, yOffset);
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        yOffset += 30;
        g2d.drawString("BPM: " + String.format("%.1f", analyzer.getBPM()), 20, yOffset);
        
        yOffset += 25;
        g2d.drawString("Batidas detectadas: " + analyzer.getPeakCount(), 20, yOffset);
        
        yOffset += 25;
        String status = analyzer.isAnomalous() ? "ANÔMALO" : "NORMAL";
        Color statusColor = analyzer.isAnomalous() ? Color.RED : new Color(0, 150, 0);
        g2d.setColor(statusColor);
        g2d.drawString("Status: " + status, 20, yOffset);
        
        g2d.setColor(Color.BLACK);
        yOffset += 25;
        String[] anomalies = analyzer.getAnomalyDescription().split("\n");
        for (String anomaly : anomalies) {
            g2d.drawString(anomaly, 20, yOffset);
            yOffset += 20;
        }
    }
}
