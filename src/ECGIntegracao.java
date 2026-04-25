// Exemplo de integração com sensor real
// Este arquivo mostra como adaptar o sistema para ler de um dispositivo/sensor

import java.io.*;
import java.util.Scanner;

/**
 * Integração com Sensores e Dispositivos ECG
 * Exemplos de como conectar diferentes tipos de fontes de dados
 */
public class ECGIntegracao {
    
    /**
     * Alternativa 1: Ler de uma porta serial (Arduino, dispositivo médico)
     * Requer biblioteca: jSerialComm ou RXTX
     */
    public static ECGData lerDeSensorSerial(String portaSerial, int baudRate, double samplingRate) throws Exception {
        System.out.println("Conectando ao sensor serial: " + portaSerial + " @ " + baudRate + " bps");
        
        ECGData dados = new ECGData(samplingRate);
        
        // Pseudocódigo - você precisaria da biblioteca jSerialComm
        /*
        SerialPort puerto = SerialPort.getCommPort(portaSerial);
        puerto.setComPortParameters(baudRate, 8, 1, 0);
        puerto.openPort();
        
        InputStream entrada = puerto.getInputStream();
        BufferedReader leitor = new BufferedReader(new InputStreamReader(entrada));
        
        long inicioBit = System.currentTimeMillis();
        String linha;
        
        while ((linha = leitor.readLine()) != null) {
            String[] partes = linha.split(",");
            if (partes.length >= 1) {
                double voltagem = Double.parseDouble(partes[0]);
                long timestamp = System.currentTimeMillis() - inicioBit;
                dados.addDataPoint(voltagem, timestamp);
                
                // Mostra em tempo real (opcional)
                if (dados.getSize() % 100 == 0) {
                    System.out.println("Lidos: " + dados.getSize() + " pontos");
                }
            }
        }
        puerto.closePort();
        */
        
        return dados;
    }
    
    /**
     * Alternativa 2: Ler de uma API REST
     * Requer biblioteca: HttpClient ou OkHttp
     */
    public static ECGData lerDeAPIREST(String url, double samplingRate) throws Exception {
        System.out.println("Conectando à API: " + url);
        
        ECGData dados = new ECGData(samplingRate);
        
        // Pseudocódigo com HttpClient
        /*
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();
        
        HttpResponse<String> response = client.send(request, 
            HttpResponse.BodyHandlers.ofString());
        
        // Parse JSON (requer json-simple ou gson)
        JSONArray dados_json = new JSONArray(response.body());
        
        for (int i = 0; i < dados_json.length(); i++) {
            JSONObject ponto = dados_json.getJSONObject(i);
            double voltage = ponto.getDouble("voltage");
            long timestamp = ponto.getLong("timestamp");
            dados.addDataPoint(voltage, timestamp);
        }
        */
        
        return dados;
    }
    
    /**
     * Alternativa 3: Leitura em Tempo Real de um Arquivo (streaming)
     * Monitora arquivo que está sendo atualizado
     */
    public static void monitorarArquivoEmTempoReal(String caminhoArquivo, double samplingRate) throws Exception {
        System.out.println("Monitorando arquivo em tempo real: " + caminhoArquivo);
        
        File arquivo = new File(caminhoArquivo);
        long ultimaLeitura = 0;
        ECGData dados = new ECGData(samplingRate);
        int ultimosPontos = 0;
        
        while (true) {
            // Verifica se arquivo foi modificado
            long ultimaModificacao = arquivo.lastModified();
            
            if (ultimaModificacao > ultimaLeitura) {
                ultimaLeitura = ultimaModificacao;
                
                try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
                    String linha;
                    int linhaAtual = 0;
                    
                    while ((linha = leitor.readLine()) != null) {
                        linhaAtual++;
                        
                        // Ignora comentários
                        if (linha.startsWith("#") || linha.trim().isEmpty()) {
                            continue;
                        }
                        
                        try {
                            String[] partes = linha.split(",");
                            if (partes.length >= 2) {
                                long timestamp = Long.parseLong(partes[0].trim());
                                double voltage = Double.parseDouble(partes[1].trim());
                                dados.addDataPoint(voltage, timestamp);
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Erro na linha " + linhaAtual + ": " + linha);
                        }
                    }
                    
                    // Processa apenas novos dados
                    if (dados.getSize() > ultimosPontos) {
                        System.out.println("Novos pontos detectados: " + 
                            (dados.getSize() - ultimosPontos));
                        
                        // Análise incremental (últimos 5 segundos)
                        if (dados.getSize() > 100) {
                            ECGAnalyzer analisador = new ECGAnalyzer(dados);
                            System.out.println("  BPM atual: " + 
                                String.format("%.1f", analisador.getBPM()));
                            System.out.println("  Status: " + 
                                analisador.getAnomalyDescription());
                        }
                        
                        ultimosPontos = dados.getSize();
                    }
                }
            }
            
            // Aguarda antes de verificar novamente
            Thread.sleep(1000); // Verifica a cada 1 segundo
        }
    }
    
    /**
     * Alternativa 4: Simular entrada do usuário interativa
     */
    public static ECGData lerDiretamenteDoUsuario(double samplingRate) {
        System.out.println("Modo interativo: Digite valores de voltagem (ou 'fim' para terminar)");
        System.out.println("Formato: valor_voltagem");
        
        ECGData dados = new ECGData(samplingRate);
        Scanner scanner = new Scanner(System.in);
        long timestamp = 0;
        
        while (true) {
            System.out.print("Voltagem (mV): ");
            String entrada = scanner.nextLine();
            
            if ("fim".equalsIgnoreCase(entrada)) {
                break;
            }
            
            try {
                double voltagem = Double.parseDouble(entrada);
                dados.addDataPoint(voltagem, timestamp);
                timestamp += (1000.0 / samplingRate); // 4ms @ 250Hz
                
                System.out.println("✓ Ponto " + dados.getSize() + " registrado");
            } catch (NumberFormatException e) {
                System.out.println("❌ Valor inválido. Digite um número.");
            }
        }
        
        scanner.close();
        return dados;
    }
    
    /**
     * Alternativa 5: Conectar a Plataforma IoT
     * (AWS IoT, Google Cloud IoT, Azure IoT Hub, etc.)
     */
    public static ECGData lerDeIoTHub(String iotEndpoint, String deviceId, double samplingRate) throws Exception {
        System.out.println("Conectando ao hub IoT: " + iotEndpoint);
        
        ECGData dados = new ECGData(samplingRate);
        
        // Exemplo com MQTT (IoT padrão)
        /*
        MqttClient client = new MqttClient(iotEndpoint, deviceId);
        MqttConnectOptions opcoes = new MqttConnectOptions();
        opcoes.setCleanSession(true);
        client.connect(opcoes);
        
        client.subscribe("ecg/dados", (topic, message) -> {
            String payload = new String(message.getPayload());
            String[] partes = payload.split(",");
            
            long timestamp = Long.parseLong(partes[0]);
            double voltage = Double.parseDouble(partes[1]);
            dados.addDataPoint(voltage, timestamp);
            
            System.out.println("Dado recebido: " + voltage + " mV");
        });
        */
        
        return dados;
    }
    
    /**
     * Exemplo de uso - descomente para testar
     */
    public static void main(String[] args) {
        try {
            // Exemplo 1: Leitura interativa
            System.out.println("=== Exemplo: Entrada Manual do Usuário ===");
            ECGData dados = lerDiretamenteDoUsuario(250);
            
            System.out.println("\nDados coletados: " + dados.getSize() + " pontos");
            
            // Análise
            ECGAnalyzer analisador = new ECGAnalyzer(dados);
            analisador.printReport();
            
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

/*
 * CONFIGURAÇÕES PARA DIFERENTES SENSORES:
 * 
 * 1. ARDUINO ECG:
 *    - Porta: COM3, COM4, etc (Windows) ou /dev/ttyUSB0 (Linux)
 *    - Baud Rate: 9600 ou 115200
 *    - Formato: "voltagem\n"
 * 
 * 2. SMARTWATCH/WEARABLE:
 *    - Protocolo: Bluetooth (BLE)
 *    - Usar bibliotecas: bluecove ou TinyB
 * 
 * 3. HOLTER (gravador portátil):
 *    - Formato: arquivo binário ou CSV
 *    - Frequência: 200-1000 Hz
 * 
 * 4. MONITOR HOSPITALAR:
 *    - Protocolo: HL7 ou DICOM
 *    - Conexão: TCP/IP
 * 
 * 5. APLICATIVO MOBILE:
 *    - Protocolo: HTTP/WebSocket
 *    - Formato JSON
 * 
 * DEPENDÊNCIAS MAVEN:
 * <dependency>
 *     <groupId>com.fazecast</groupId>
 *     <artifactId>jSerialComm</artifactId>
 *     <version>2.9.1</version>
 * </dependency>
 * 
 * <dependency>
 *     <groupId>org.eclipse.paho</groupId>
 *     <artifactId>org.eclipse.paho.client.mqttv3</artifactId>
 *     <version>1.2.5</version>
 * </dependency>
 */
