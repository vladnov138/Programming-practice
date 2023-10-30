from .ast import Number, BinOp, UnOp
from interpreter.parser import Parser


class Interpreter:
    def __init__(self):
        self.parser = Parser()

    def visit(self, node):
        if isinstance(node, Number):
            return self.visit_number(node)
        elif isinstance(node, BinOp):
            return self.visit_binop(node)
        elif isinstance(node, UnOp):
            return self.visit_inop(node)

    def visit_number(self, node):
        return float(node.token.value)

    def visit_binop(self, node):
        match node.op.value:
            case "+":
                return self.visit(node.left) + self.visit(node.right)
            case "-":
                return self.visit(node.left) - self.visit(node.right)
            case "*":
                return self.visit(node.left) * self.visit(node.right)
            case "/":
                return self.visit(node.left) / self.visit(node.right)

    def visit_inop(self, node):
        match node.op.value:
            case "+":
                return +self.visit(node.value)
            case "-":
                return -self.visit(node.value)
            case _:
                raise ValueError("Invalid operator")

    def eval(self, code):
        tree = self.parser.parse(code)
        return self.visit(tree)
