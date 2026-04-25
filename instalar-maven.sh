#!/bin/bash
# Script para instalar Maven no Linux/Mac

echo "=========================================="
echo " Verificando instalação de Maven..."
echo "=========================================="
echo ""

if command -v mvn &> /dev/null; then
    echo "[OK] Maven já está instalado"
    mvn -v
    exit 0
fi

echo "[ERRO] Maven não encontrado!"
echo ""
echo "=========================================="
echo " OPÇÕES DE INSTALAÇÃO"
echo "=========================================="
echo ""
echo "1. UBUNTU/DEBIAN:"
echo "   sudo apt update"
echo "   sudo apt install maven"
echo ""
echo "2. FEDORA/RHEL/CentOS:"
echo "   sudo dnf install maven"
echo ""
echo "3. macOS (Homebrew):"
echo "   brew install maven"
echo ""
echo "4. Manual (qualquer sistema):"
echo "   a) Baixe: https://maven.apache.org/download.cgi"
echo "   b) Extraia em ~/opt/"
echo "   c) Adicione ao PATH: export PATH=~/opt/apache-maven-X.X.X/bin:$PATH"
echo ""
echo "=========================================="
echo ""
read -p "Pressione Enter para sair..."
