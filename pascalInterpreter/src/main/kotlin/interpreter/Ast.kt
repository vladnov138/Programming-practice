package interpreter

abstract class Node

data class PascalNumber(val token: Token) : Node()

data class BinOp(val left: Node, val op: Token, val right: Node) : Node()

data class UnOp(val op: Token, val value: Node) : Node()

data class Empty(private val value_: String = "") : Node() {
    val value = ""
}

data class Variable(val token: Token, val value: Node) : Node()

data class Identifier(val token: Token) : Node()

data class Semi(val left: Node, val right: Node) : Node()
