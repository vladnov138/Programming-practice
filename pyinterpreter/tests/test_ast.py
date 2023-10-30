from interpreter.ast import BinOp, Number, UnOp
from interpreter.token import Token, TokenType


class TestAst:
    def test_str_number(self):
        assert str(Number(Token(TokenType.NUMBER, "2"))) == "Number (Token(TokenType.NUMBER, 2))"

    def test_str_binop(self):
        val = Number(Token(TokenType.NUMBER, "2"))
        operator = Token(TokenType.OPERATOR, "+")
        assert str(BinOp(val, operator, val)) == ("BinOp+ (Number (Token(TokenType.NUMBER, 2)), "
                                                  "Number (Token(TokenType.NUMBER, 2)))")

    def test_str_unop(self):
        val = Number(Token(TokenType.NUMBER, "2"))
        operator = Token(TokenType.OPERATOR, "+")
        assert str(UnOp(val, operator)) == "UnOp+ Number (Token(TokenType.NUMBER, 2))"
