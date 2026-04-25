#!/bin/bash
# Script para testar o sistema ECG com dados simulados
# Use este script para testar rapidamente sem preparar arquivo CSV

echo "╔════════════════════════════════════════╗"
echo "║  TESTE ECG - DADOS SIMULADOS            ║"
echo "╚════════════════════════════════════════╝"
echo ""

cd "$(dirname "$0")"

echo "1️⃣  Compilando..."
javac -d bin src/*.java

if [ $? -ne 0 ]; then
    echo "❌ Erro na compilação"
    exit 1
fi

echo ""
echo "2️⃣  Executando teste com dados simulados..."
echo ""

# Cria um arquivo temporário que testa dados simulados
cat > bin/TestSimulated.java << 'EOF'
import java.io.IOException;

public class TestSimulated {
    public static void main(String[] args) throws IOException {
        // Gera 30 segundos de dados simulados a 250 Hz
        ECGData data = ECGReader.generateSimulatedData(30, 250);
        ECGAnalyzer analyzer = new ECGAnalyzer(data);
        analyzer.printReport();
        
        ECGVisualizer viz = new ECGVisualizer(data, analyzer);
        viz.setSize(1600, 800);
        viz.generateAndSaveChart("ecg_simulated.png");
        
        System.out.println("✅ Teste concluído!");
        System.out.println("📊 Gráfico salvo em: ecg_simulated.png");
    }
}
EOF

javac -d bin bin/TestSimulated.java 2>/dev/null
java -cp bin TestSimulated

echo ""
echo "✨ Teste finalizado!"
