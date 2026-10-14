# Uso:  .\Copiar.ps1 01     -> copia al portapapeles el texto 01-*.txt, listo para Ctrl+V en Word
param([Parameter(Mandatory = $true)][string]$Numero)
$f = Get-ChildItem -Path $PSScriptRoot -Filter "$Numero-*.txt" | Select-Object -First 1
if (-not $f) { Write-Host "No existe la evidencia $Numero" -ForegroundColor Red; exit 1 }
Get-Content -Path $f.FullName -Raw -Encoding UTF8 | Set-Clipboard
Write-Host "Copiado al portapapeles: $($f.Name)" -ForegroundColor Green