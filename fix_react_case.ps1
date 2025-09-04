# Busca archivos que terminen en .Ts o .Tsx
$files = git ls-files | Where-Object { $_ -match '\.Ts$' -or $_ -match '\.Tsx$' }

if (-not $files) {
    Write-Output "No se encontraron archivos con extensión mal capitalizada (.Ts o .Tsx)"
    exit
}

Write-Output "Archivos a corregir:"
$files

foreach ($f in $files) {
    if ($f -match '\.Tsx$') {
        $newname = $f -replace '\.Tsx$', '.tsx'
    } elseif ($f -match '\.Ts$') {
        $newname = $f -replace '\.Ts$', '.ts'
    }

    Write-Output "Renombrando $f -> $newname"

    # Paso intermedio para que Windows deje renombrar el case
    git mv $f "$newname.tmp"
    git mv "$newname.tmp" $newname
}

git commit -m "Fix: normalize .Ts/.Tsx filenames to .ts/.tsx"
Write-Output "Cambios listos, haz 'git push' para subirlos"
