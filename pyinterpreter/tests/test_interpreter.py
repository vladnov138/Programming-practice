import pytest
from interpreter import Interpreter


@pytest.fixture(scope="function")
def interpreter():
    return Interpreter()


class TestInterpreter:
    interpreter = Interpreter()

    def test_add(self, interpreter):
        assert interpreter.eval("2+2") == 4
    
    def test_sub(self, interpreter):
        assert interpreter.eval("2-2") == 0

    def test_add_with_letter(self, interpreter):
        with pytest.raises(SyntaxError):
            interpreter.eval("2+a")

    def test_wrong_operator(self, interpreter):
        with pytest.raises(SyntaxError):
            interpreter.eval("2&3")

    @pytest.mark.parametrize(
            "interpreter, code", [(interpreter, "2 + 2"),
                                  (interpreter, "2 +2 "),
                                  (interpreter, " 2+2")]
    )
    def test_add_spaces(self, interpreter, code):
        assert interpreter.eval(code) == 4

    @pytest.mark.parametrize(
        "interpreter, code", [(interpreter, "+ 2"),
                              (interpreter, " +2 "),
                              (interpreter, "+++ ++ +2"),]
    )
    def test_unary_add_spaces(self, interpreter, code):
        assert interpreter.eval(code) == 2

    def test_unary_add(self, interpreter):
        assert interpreter.eval("+2") == 2
        assert interpreter.eval("++++++2") == 2

    def test_unary_sub(self, interpreter):
        assert interpreter.eval("-2") == -2

    def test_unary_sub_signs(self, interpreter):
        assert interpreter.eval("--2") == 2
        assert interpreter.eval("---2") == -2

    @pytest.mark.parametrize(
        "interpreter, code", [(interpreter, "+-2"),
                              (interpreter, " - + 2")]
    )
    def test_unary_operators(self, interpreter, code):
        assert interpreter.eval(code) == -2

    def test_mul(self, interpreter):
        assert interpreter.eval("2*3") == 6

    @pytest.mark.parametrize(
        "interpreter, code", [(interpreter, "2 * 3"),
                              (interpreter, "2*  3"),
                              (interpreter, "2   *3"),]
    )
    def test_mul_spaces(self, interpreter, code):
        assert interpreter.eval(code) == 6

    def test_div(self, interpreter):
        assert interpreter.eval("2/2") == 1

    @pytest.mark.parametrize(
        "interpreter, code", [(interpreter, "2 / 2"),
                              (interpreter, "2/  2"),
                              (interpreter, "2   /2"), ]
    )
    def test_div_spaces(self, interpreter, code):
        assert interpreter.eval(code) == 1

    def test_mul_un(self, interpreter):
        assert interpreter.eval("2*-2") == -4
        assert interpreter.eval("-2*2") == -4
        assert interpreter.eval("-2*-2") == 4

    def test_div_un(self, interpreter):
        assert interpreter.eval("2/-2") == -1
        assert interpreter.eval("-2/2") == -1
        assert interpreter.eval("-2/-2") == 1

    def test_invalid_unop(self, interpreter):
        with pytest.raises(ValueError):
            interpreter.eval("*3")

    def test_parens(self, interpreter):
        assert interpreter.eval("2*(2+2)") == 8

    @pytest.mark.parametrize(
        "interpreter, code", [(interpreter, "(2  +  2)  * 2 "),
                              (interpreter, " (2 + 2) * 2"),
                              (interpreter, "2* (2 + 2)"), ]
    )
    def test_parens_spaces(self, interpreter, code):
        assert interpreter.eval(code) == 8

    def test_invalid_order(self, interpreter):
        with pytest.raises(SyntaxError):
            interpreter.eval(")2")

    def test_invalid_factor(self, interpreter):
        with pytest.raises(SyntaxError):
            interpreter.eval("(2-)")
