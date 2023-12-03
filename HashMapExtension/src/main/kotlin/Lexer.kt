package org.example

class Lexer() {
    private var pos = -1
    private var text = ""
    private var currentChar: Char? = null

    fun init(expr: String) {
        pos = 0
        text = expr
        currentChar = expr[pos]
    }

    fun forward() {
        pos++;
        currentChar = if (pos > text.length - 1) null else text[pos]
    }

    fun skip() {
        while (currentChar != null && (currentChar!!.isWhitespace() ||
                    currentChar!! in "()" || currentChar!!.isLetter() || currentChar!! in ",.")) {
            forward()
        }
    }

    fun next(): Token {
        while (currentChar != null) {
            if (currentChar!!.isWhitespace() || currentChar!! in "()" || currentChar!!.isLetter() || currentChar!! in ",.") {
                skip()
                continue
            }
            if (currentChar!!.isDigit()) {
                return Token(TokenType.NUMBER, number())
            } else
            if (currentChar!! in "<>=") {
                return Token(TokenType.OPERATOR, operator())
            } else {
                throw Exception("Unknown token: $currentChar")
            }
        }
        return Token(TokenType.ENDL, "")
    }

    fun operator(): String {
        val result = mutableListOf<Char>()
        while (currentChar != null && currentChar!! in "<>=") {
            result.add(currentChar!!)
            forward()
        }
        return result.joinToString("")
    }

    fun number(): String {
        val result = mutableListOf<Char>()
        while (currentChar != null && (currentChar!!.isDigit() || currentChar == '.')) {
            result.add(currentChar!!)
            forward()
        }
        return result.joinToString("")
    }
}