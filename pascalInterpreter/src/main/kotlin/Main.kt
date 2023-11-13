package org.example

import Exceptions.SyntaxException
import interpreter.Interpreter

fun main() {
    val interpreter = Interpreter()
    var text = ""
    val code = mutableListOf<String>()
    while ("END." !in text) {
        print("in> ")
        text = readln()
        code.add(text)
    }
    try {
        val result = interpreter.eval(code.joinToString(""))
        print("out>${interpreter.variables}")
    } catch (e: SyntaxException) {
        print("$e")
    }
}
