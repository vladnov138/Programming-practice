# Pascal interpreter  
На основе кода с практических занятий разработать интерпретатор для упрощенной версии языка Pascal.

Интерпретатор должен выдавать значение всех переменных используемых в программе, например, в виде словаря.
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
## Покрытие
![alt coverage](https://github.com/vladnov138/Programming-practice/blob/main/pascalInterpreter/assets/coverage.jpg)
