import pytest

from interpreter.parser import Parser
from interpreter.token import Token, TokenType


class TestParser:
    def test_check_token(self):
        parser = Parser()
        parser._current_token = Token(TokenType.NUMBER, "2")
        with pytest.raises(SyntaxError):
            parser.check_token(TokenType.OPERATOR)
