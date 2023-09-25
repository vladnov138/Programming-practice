section   .text
global    main

extern printf

main:
    finit
    fld dword [num]
    fsqrt
    fstp qword [res]

    push rbp
    mov rdi, format
    movq xmm0, qword [res]
    xor rdx, rdx
    mov rax, 1
    call printf
    pop rbp

    mov rax, 60
    xor rdi, rdi
    syscall

section   .data
    num dd 5.0
    format db "sqrt: %f", 0xA, 0xD

section .bss
    res resb 4