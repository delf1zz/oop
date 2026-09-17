#!/bin/bash
set -e

echo "Очистка старой сборки"
rm -rf out doc app.jar

echo "Компиляция исходников (javac)"
mkdir -p out
javac -d out src/main/java/ru/nsu/egorov/*.java

echo "Генерация документации"
javadoc -d doc -sourcepath src/main/java ru.nsu.egorov

echo "Сборка JAR-архива и запуск"
jar cfe app.jar ru.nsu.egorov.Main -C out .
java -jar app.jar

echo "Запуск Тестов из файла HeapSortTest"
./gradlew test --rerun