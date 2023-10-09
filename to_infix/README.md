# Converter from prefix to infix  
Конвертировать выражение из префиксной формы в инфиксную  
## Запуск тестов (необходим Maven):
```
mvn test
```
или через make:
```
make test
```
Отчет о покрытии искать в __target/site/jacoco/index.html__
## Запуск программы
Есть два варианта запуска программы:
1. IntelliJ Idea
2. Воспользоваться kotlinc и java:
```
kotlinc src/main/kotlin/Main.kt -include-runtime -d target/main.jar
java -jar ./target/main.jar
```
или через make:
```
make run
```
![alt coverage](https://github.com/vladnov138/Programming practice/to_infix/assets/coverage.jpg)