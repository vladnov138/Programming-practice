package com.company

fun toInfix(prefix: String): String {
    val line = prefix.split(" ").reversed()
    val operators = "+-/*"
    val stack = ArrayDeque<String>()
    for (item in line) {
        if (item in operators) {
            if (stack.size > 1) {
                stack.addLast("(${stack.removeLast()} ${item} ${stack.removeLast()})")
            } else {
                throw ArithmeticException("Invalid number of operands");
            }
        } else if (item.toIntOrNull() != null){
            stack.addLast(item)
        } else {
            throw NumberFormatException("There is unknown symbol in line (not operator or operand)")
        }
    }
    return stack.removeLast()
}

fun main() {
    println("Введите префиксное выражение")
    val line = readLine() ?: ""
    try {
        println(toInfix(line))
    } catch (msg: Exception) {
        println(msg.message)
    }
}