@echo off
setlocal

REM Comprueba si .env.properties existe
if not exist ".env.properties" (
    if exist ".env.properties.example" (
        echo Creando .env.properties a partir de .env.properties.example...
        copy ".env.properties.example" ".env.properties"
        if %errorlevel% neq 0 (
            echo Error al copiar .env.properties.example a .env.properties.
            exit /b 1
        )
    ) else (
        echo .env.properties.example no existe. No se puede crear .env.properties.
        exit /b 1
    )
)

REM Comprueba si el comando commit-and-tag-version está instalado
where commit-and-tag-version >nul 2>&1
if %errorlevel% neq 0 (
    REM Comprueba si Node.js esta instalado
    where node >nul 2>&1
    if %errorlevel% neq 0 (
        echo Node.js no esta instalado. Por favor, instala Node.js primero.
        exit /b 1
    ) else (
        echo Instalando commit-and-tag-version...
        npm install -g commit-and-tag-version
        if %errorlevel% neq 0 (
            echo Error al instalar commit-and-tag-version.
            exit /b 1
        )
    )
)

REM Comprueba si el directorio actual es un repositorio de git
if not exist ".git" (
    echo Inicializando un nuevo repositorio Git...
    git init --initial-branch=desarrollo
    if %errorlevel% neq 0 (
        echo Error al inicializar el repositorio Git.
        exit /b 1
    )
    git add -A
    git commit -am "feat: Acaex API Initializr - Inicio de Proyecto"
    if %errorlevel% neq 0 (
        echo Error al hacer el primer commit.
        exit /b 1
    )
    
    REM Crear la primera versión
    echo Creando la primera version con commit-and-tag-version...
    commit-and-tag-version --release-as 0.1.0
    if %errorlevel% neq 0 (
        echo Error al crear la primera versión.
        exit /b 1
    )
)

echo Setup completado.
endlocal
exit /b 0