@echo off
cls
if "%1" == "help" (
    echo usage
    echo run java 'module'
    echo run web
    echo run build 'module'
)
if "%1" == "java" (
    gradlew :%2:bootRun
)
if "%1" == "web" (
    cd web
    tyarn start
    cd ..
)
if "%1" == "build" (
    gradlew :generator:bootRun --args=%2
)