package org.example

class Ploc(private val specHashMap: SpecialHashMap) {
    private val lexer = Lexer()
    operator fun get(condition: String): Map<String, Int> {
        val conditionTokens = parse(condition)
        return specHashMap.hashMap.filterKeys {
            val tokens = parse(it)
//            println(tokens)
//            println(countDim(tokens))
//            println(countDim(conditionTokens))
            if (countDim(tokens) != countDim(conditionTokens)) {
                return@filterKeys false
            }
            for (i in 0..<tokens.size - 1 step 2) {
                val operator = conditionTokens[i]
                val number = conditionTokens[i + 1]
                val keyToken = tokens[i / 2]
                println(tokens)
                println(keyToken)
                println()
                println(conditionTokens)
                println(number)
                println()
                if (keyToken.type != number.type) {
                    return@filterKeys false
                }
                when (operator.value) {
                    ">" -> {
                        if (keyToken.value.toDouble() <= number.value.toDouble()) {
                            return@filterKeys false
                        }
                    }

                    "<" -> {
                        if (keyToken.value.toDouble() >= number.value.toDouble()) {
                            return@filterKeys false
                        }
                    }

                    ">=" -> {
                        if (keyToken.value.toDouble() < number.value.toDouble()) {
                            return@filterKeys false
                        }
                    }

                    "<=" -> {
                        if (keyToken.value.toDouble() > number.value.toDouble()) {
                            return@filterKeys false
                        }
                    }

                    else -> {
                        throw Exception()
                    }
                }
            }
            return@filterKeys true
        }
    }

    private fun countDim(tokens: List<Token>): Int {
        var s = 0
        for (token in tokens) {
            if (token.type == TokenType.NUMBER) {
                s++
            }
        }
        return s
    }

    private fun parse(expression: String): List<Token> {
        lexer.init(expression)
        val tokens = mutableListOf<Token>()
        var currentToken = lexer.next()
        tokens.add(currentToken)
        while (currentToken.type != TokenType.ENDL) {
            currentToken = lexer.next()
            tokens.add(currentToken)
        }
        return tokens
    }
}