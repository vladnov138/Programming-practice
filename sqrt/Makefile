CC = gcc
ARCH_TYPE = -felf64
MAIN_FILE = main.asm
CC_FLAGS = -no-pie -fno-pie
O_FILE = main.o
OUT_FILE = ./a.out
ASM = nasm

clean:
	rm $(O_FILE)
	rm $(OUT_FILE)

run:
	$(ASM) $(ARCH_TYPE) $(MAIN_FILE) && $(CC) $(CC_FLAGS) $(O_FILE) && $(OUT_FILE)