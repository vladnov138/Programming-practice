package interpreter

import Exceptions.SyntaxException

class Interpreter {
    private val parser = Parser()
    val variables = HashMap<String, Float>()

    fun visit(node: Node): Float {
        when (node) {
            is PascalNumber -> return visitNumber(node)
            is BinOp -> return visitBinOp(node)
            is Variable -> visitVariable(node)
            is Semi -> visitSemi(node)
            is Empty -> {}
            is Identifier -> return visitIdentifier(node)
            else -> return visitUnOp(node as UnOp)
        }
        return 0.0f
    }

    fun visitIdentifier(node: Identifier): Float {
        if (node.token.value in variables.keys) {
            return variables[node.token.value]!!
        }
        throw SyntaxException("Unknown variable: ${node.token.value}")
    }

    fun visitSemi(node: Semi) {
        visit(node.left)
        visit(node.right)
    }

    fun visitVariable(node: Variable) {
        if (node.token.value !in variables.keys) {
            variables[node.token.value] = 0.0f
        }
        variables[node.token.value] = visit(node.value)
    }

    fun visitNumber(number: PascalNumber): Float = number.token.value.toFloat()

    fun visitBinOp(node: BinOp): Float {
        return if (node.op.value == "+") {
            visit(node.left) + visit(node.right)
        } else if (node.op.value == "-") {
            visit(node.left) - visit(node.right)
        } else if (node.op.value == "*") {
            visit(node.left) * visit(node.right)
        } else {
            visit(node.left) / visit(node.right)
        }

//        when (node.op.value) {
//            "+" -> return visit(node.left) + visit(node.right)
//            "-" -> return visit(node.left) - visit(node.right)
//            "*" -> return visit(node.left) * visit(node.right)
//            else -> return visit(node.left) / visit(node.right)
//        }
    }

    fun visitUnOp(node: UnOp): Float {
        return when (node.op.value) {
            "+" -> +visit(node.value)
            "-" -> -visit(node.value)
            else -> throw SyntaxException("Invalid operator")
        }
    }

    fun eval(code: String): Float {
        val tree = parser.parse(code)
        return visit(tree)
    }
}