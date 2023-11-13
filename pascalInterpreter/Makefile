KOTLINC = kotlinc
JAVA = java
MAIN_FILE = src/main/kotlin/Main.kt
FLAGS = -include-runtime -d
OUT_FILE = ./target/main.jar

ifeq ($(OS), Windows_NT)
	RM = del
else
	RM = rm
endif

test:
	mvn test

clean:
	mvn clean

kotlin:
	$(KOTLINC) $(MAIN_FILE) $(FLAGS) $(OUT_FILE)

java:
	$(JAVA) -jar $(OUT_FILE)

run: kotlin java clean