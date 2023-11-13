package interpreter

import Exceptions.SyntaxException

class Parser {
    private var currentToken: Token? = null
    private var lexer = Lexer()

    private fun check_token(type: TokenType) {
        if (currentToken?.type == type) {
            currentToken = lexer.next()
        } else {
            throw SyntaxException("Invalid token order")
        }
    }

    private fun factor(): Node {
        val token = currentToken
        when (token!!.type) {
            TokenType.NUMBER -> {
                check_token(TokenType.NUMBER)
                return PascalNumber(token)
            }

            TokenType.L_PAREN -> {
                check_token(TokenType.L_PAREN)
                val result = expr()
                check_token(TokenType.R_PAREN)
                return result
            }

            TokenType.OPERATOR -> {
                check_token(TokenType.OPERATOR)
                return UnOp(token, factor())
            }

            TokenType.ID -> {
                check_token(TokenType.ID)
                return Identifier(token)
            }

            else -> {
                throw SyntaxException("Invalid factor")
            }
        }
    }

    private fun variable(): Token {
        val result = currentToken
        check_token(TokenType.ID)
        return result!!
    }

    private fun assignment(): Node {
        val idToken = variable()
        check_token(TokenType.ASSIGN)
        return Variable(idToken, expr())
    }

    private fun statement_list(): Node {
        var result = statement()
        if (currentToken!!.type == TokenType.SEMI) {
            check_token(TokenType.SEMI)
            result = Semi(result, statement_list())
        }
        return result
    }

    private fun complex_statement(): Node {
        check_token(TokenType.BEGIN)
        val result = statement_list()
        check_token(TokenType.END)
        return result
    }

    private fun empty(): Node {
        return Empty()
    }

    private fun program(): Node {
        val result = complex_statement()
        check_token(TokenType.DOT)
        return result
    }

    private fun statement(): Node {
        return when (currentToken?.type) {
            TokenType.BEGIN -> complex_statement()
            TokenType.ID -> assignment()
            TokenType.END -> empty()
            else -> throw SyntaxException("Invalid token")
        }
    }

    private fun term(): Node {
        val result = factor()
        while (currentToken!!.type == TokenType.OPERATOR) {
            if (currentToken!!.value !in "*/") {
                break
            }
            val token = currentToken
            check_token(TokenType.OPERATOR)
            return BinOp(result, token!!, term())
        }
        return result
    }

    private fun expr(): Node {
        var result = term()
        while (currentToken!!.type == TokenType.OPERATOR) {
            val token = currentToken
            check_token(TokenType.OPERATOR)
            result = BinOp(result, token!!, term())
        }
        return result
    }

    fun parse(code: String): Node {
        lexer.init(code)
        currentToken = lexer.next()
        return program()
    }
}