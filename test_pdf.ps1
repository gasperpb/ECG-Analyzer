$json = Get-Content test_data.json -Raw
$response = Invoke-WebRequest -Uri 'http://localhost:8080/api/ecg/generate-pdf' -Method POST -Body $json -ContentType 'application/json' -OutFile 'test_pdf_fixed.pdf' -PassThru

Write-Host "Status: $($response.StatusCode)"
$file = Get-Item 'test_pdf_fixed.pdf'
Write-Host "Arquivo: $($file.Name)"
Write-Host "Tamanho: $($file.Length) bytes"
Write-Host "PDF gerado com sucesso!" -ForegroundColor Green
