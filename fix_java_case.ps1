# Busca archivos que terminen en .Java
$files = git ls-files | Where-Object { $_ -match '\.Java$' }

if (-not $files) {
    Write-Output "No se encontraron archivos con extensión .Java"
    exit
}

Write-Output "Archivos a corregir:"
$files

foreach ($f in $files) {
    $newname = $f -replace '\.Java$', '.java'
    Write-Output "Renombrando $f -> $newname"

    # Paso intermedio para que Windows deje renombrar el case
    git mv $f "$newname.tmp"
    git mv "$newname.tmp" $newname
}

git commit -m "Fix: normalize .Java filenames to .java"
Write-Output "Cambios listos, haz 'git push' para subirlos"
