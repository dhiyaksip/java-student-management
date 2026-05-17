#!/bin/bash

mkdir -p bin
javac -d bin src/model/*.java src/usecase/*.java src/Main.java

if [ $? -eq 0 ]; then
    java -cp bin Main
else
    echo "Compilation failed."
fi
