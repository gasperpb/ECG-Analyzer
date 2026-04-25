@echo off
REM Script para instalar Maven automaticamente no Windows

echo.
echo ============================================
echo  Verificando instalacao de Maven...
echo ============================================
echo.

REM Verificar se Maven já está instalado
where mvn >nul 2>nul
if %errorlevel% == 0 (
    echo [OK] Maven ja esta instalado
    mvn -v
    exit /b 0
)

echo [ERRO] Maven nao encontrado!
echo.
echo Para usar a aplicacao ECG Web, voce precisa instalar Maven.
echo.
echo ============================================
echo  OPCOES DE INSTALACAO
echo ============================================
echo.
echo 1. WINDOWS (com Chocolatey):
echo    choco install maven
echo.
echo 2. WINDOWS (Manual):
echo    a) Baixe: https://maven.apache.org/download.cgi
echo    b) Extraia em C:\Program Files\
echo    c) Adicione C:\Program Files\apache-maven-X.X.X\bin ao PATH
echo.
echo 3. VERIFICAR SE JA ESTA INSTALADO:
echo    mvn -v
echo.
echo ============================================
echo.

pause
