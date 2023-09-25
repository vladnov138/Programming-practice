section .text
global main

extern printf
extern sscanf
extern strtok

main:
    ; open the file
    mov rax, 2
    mov rdi, filename
    mov rsi, 0 ; readonly
    syscall

    ; read the file data to the buffer
    mov rdi, rax
    mov rax, 0
    mov rsi, buffer
    mov rdx, 64
    syscall

    ; close the file
    mov rdi, rax
    mov rax, 3
    syscall

    xor rdx, rdx
    xor rcx, rcx
    mov [xlen], rdx
    mov [ylen], rcx

    ; 0 line
    push rbp
    mov rdi, buffer
    mov rsi, format_el
    call strtok
    pop rbp
    
    mov [line], rax

    mov rbx, x
    jmp fill_x

next_x:
    mov rax, [line]
    add rax, 2
    mov [line], rax
    jmp fill_x

fill_x:
    mov rax, [line]
    push rbp
    mov rdi, rax
    mov rsi, format_num
    mov rdx, rbx
    call sscanf
    pop rbp

    cmp eax, 0
    je next_x
    jl get_xlen

    add rbx, 4

    jmp next_x

get_xlen:
    sub rbx, x
    mov [xlen], rbx
    xor rbx, rbx
    jmp get_y_line

get_y_line:

    push rbp
    mov rdi, 0
    mov rsi, format_el
    call strtok
    pop rbp
    
    mov [line], rax

mov_y_arr:
    mov rbx, y

next_y:
    mov rax, [line]
    add rax, 2
    mov [line], rax
    jmp fill_y

fill_y:
    mov rax, [line]
    push rbp
    mov rdi, rax
    mov rsi, format_num
    mov rdx, rbx
    call sscanf
    pop rbp

    cmp eax, 0
    je next_y
    jl mov_ylen

    add rbx, 4

    jmp next_y

mov_ylen:
    sub rbx, y
    mov [ylen], rbx

logic:
    xor edx, edx
    mov ecx, [ylen]
    cmp [xlen], ecx
    je arr_size_is_ok

arr_size_not_ok:
    push rbp
    mov rdi, format_str
    mov rsi, err_msg
    call printf
    pop rbp
    mov rax, 60
    mov rdi, 1
    syscall

arr_size_is_ok:
    mov eax, [x + edx]
    mov ebx, [y + edx]
    add [dif], eax
    sub [dif], ebx
    add edx, [step]
    cmp [xlen], edx
    jg arr_size_is_ok

print_res:
    mov rcx, [dif]
    xor rdx, rdx
    mov eax, [xlen]
    mov ebx, [step]
    div ebx
    mov ebx, eax
    mov eax, [dif]
    cdq
    idiv ebx
    push rbp
    mov rdi, format
    mov rsi, rax
    call printf
    pop rbp

end:
    mov rax, 60
    xor rdi, rdi
    syscall

section   .data
    ; x dd 5, 3, 2, 6, 1, 7, 4 ; 28
    ; xlen dd $ - x
    ; y dd 0, 10, 1, 9, 2, 8, 5 ; 35
    ; ylen dd $ - y
    format db "Result: %d", 0xA, 0xD
    err_msg dd "Invalid arrays lens"
    format_str db "%s", 0xA, 0xD
    step dd 4
    filename db "data.txt", 0
    format_num db "%d"
    format_el db 0xA


section .bss
    dif resb 4
    line resb 32
    buffer resb 64
    x resb 64
    y resb 64
    xlen resb 4
    ylen resb 4