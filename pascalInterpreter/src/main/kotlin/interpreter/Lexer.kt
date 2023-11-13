package interpreter

import Exceptions.SyntaxException
import kotlin.collections.ArrayList

class Lexer {
    private var pos = -1
    private var text = ""
    private var currentChar: Char? = null

    fun init(text: String) {
        this.text = text
        pos = 0
        currentChar = text[pos]
    }

    fun forward() {
        pos++
        currentChar = if (pos > text.length - 1) {
            null
        } else {
            text[pos]
        }
    }

    fun skip() {
        while (currentChar != null && currentChar!!.isWhitespace()) {
            forward()
        }
    }

    fun number(): String {
        val result = ArrayList<Char>()
        while (currentChar!!.isDigit() || currentChar == '.') {
            result.add(currentChar!!)
            forward()
        }
        return result.joinToString("")
    }

    private fun variable(): String {
        val result = mutableListOf<Char>()
        while (currentChar!!.isLetter() || currentChar!!.isDigit()) {
            result.add(currentChar!!)
            forward()
        }
        return result.joinToString("")
    }

    fun next(): Token? {
        while (this.currentChar != null) {
            var currentChar = this.currentChar!!.let { this.currentChar!! }
            if (currentChar.isWhitespace()) {
                skip()
                continue
            }
            if (currentChar == 'B') {
                val begin = "EGIN"
                val result = mutableListOf<Char>()
                forward()
                for (i in begin.indices) {
                    currentChar = this.currentChar!!.let { this.currentChar!! }
                    result.add(currentChar)
                    forward()
                }
                if (result.joinToString("") == begin) {
                    return Token(TokenType.BEGIN, "BEGIN")
                }
                throw SyntaxException("Invalid variable")
            }
            if (currentChar.isDigit()) {
                return Token(TokenType.NUMBER, number())
            }
            if (currentChar in "+-*/") {
                val op = currentChar
                forward()
                return Token(TokenType.OPERATOR, op.toString())
            }
            if (currentChar in "()") {
                val paren = currentChar
                forward()
                return if (paren == '(') {
                    Token(TokenType.L_PAREN, paren.toString())
                } else {
                    Token(TokenType.R_PAREN, paren.toString())
                }
            }
            if (currentChar == ';') {
                forward()
                return Token(TokenType.SEMI, ";")
            }
            if (currentChar == '.') {
                forward()
                return Token(TokenType.DOT, ".")
            }
            if (currentChar == ':') {
                forward()
                while (this.currentChar!!.isWhitespace()) {
                    forward()
                }
                if (this.currentChar!! == '=') {
                    forward()
                    return Token(TokenType.ASSIGN, "=")
                }
                throw SyntaxException("Invalid syntax")
            }
            if (currentChar == 'E') {
                val end = "ND"
                val result = mutableListOf<Char>()
                forward()
                for (i in end.indices) {
                    currentChar = this.currentChar!!.let { this.currentChar!! }
                    result.add(currentChar)
                    forward()
                }
                if (result.joinToString("") == end) {
                    return Token(TokenType.END, "END")
                }
                throw SyntaxException("Invalid variable")
            }
            if (currentChar.isLetter()) {
                return Token(TokenType.ID, variable())
            }
            throw SyntaxException("Bad token")
        }
        return null
    }
}