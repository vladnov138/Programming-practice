from interpreter.token import Token


class Node:
    pass


class Number(Node):
    def __init__(self, token: Token):
        self.token = token

    def __str__(self):
        return f"Number ({self.token})"


class BinOp(Node):
    def __init__(self, left: Node, op: Token, right: Node):
        self.left = left
        self.op = op
        self.right = right

    def __str__(self):
        return f"BinOp{self.op.value} ({self.left}, {self.right})"


class UnOp(Node):
    def __init__(self, value: Node, op: Token):
        self.value = value
        self.op = op

    def __str__(self):
        return f"UnOp{self.op.value} {self.value}"
