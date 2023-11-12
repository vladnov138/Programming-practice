import interpreter.Interpreter
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestMain {
    private lateinit var interpreter: Interpreter

    @BeforeEach
    fun setInterpreter() {
        interpreter = Interpreter()
    }

    // given:
    // when:
    // then:

    @Test
    fun testBeginEnd() {
        // given:
        val code = """
            BEGIN
            END.
        """.trimIndent()
        val answer = "{}"
        // when:
        interpreter.eval(code)
        // then:
        val result = interpreter.variables.toString()
        Assertions.assertThat(result)
            .isEqualTo(answer)
    }

    @Test
    fun testSimpleCode() {
        // given:
        val code = """
            BEGIN
            	x:= 2 + 3 * (2 + 3);
                y:= 2 / 2 - 2 + 3 * ((1 + 1) + (1 + 1));
            END.
        """.trimIndent()
        val answer = "{x=17.0, y=11.0}"

        // when:
        interpreter.eval(code)

        // then:
        val result = interpreter.variables.toString()
        Assertions.assertThat(result)
            .isEqualTo(answer)
    }

    @Test
    fun testInnerBeginEnd() {
        // given:
        val code = """
            BEGIN
                y: = 2;
                BEGIN
                    a := 3;
                    a := a;
                    b := 10 + a + 10 * y / 4;
                    c := a - b
                END;
                x := 11;
            END.
        """.trimIndent()
        val answer = "{a=3.0, b=18.0, c=-15.0, x=11.0, y=2.0}"

        // when:
        interpreter.eval(code)

        // then:
        val result = interpreter.variables.toString()
        Assertions.assertThat(result)
            .isEqualTo(answer)
    }

    @Test
    fun testUnaryOperator() {
        // given:
        val code = """
            BEGIN
                y: = --2.3;
            END.
        """.trimIndent()
        val answer = "{y=2.3}"

        // when:
        interpreter.eval(code)

        // then:
        val result = interpreter.variables.toString()
        Assertions.assertThat(result)
            .isEqualTo(answer)
    }

    @Test
    fun testInvalidAssign() {
        // given:
        val code = """
            BEGIN
                y:: 2;
            END.
        """.trimIndent()
        val expectedExceptionClassName = "SyntaxException"
        // when:

        // then:

        // when:
        Assertions.assertThatThrownBy { interpreter.eval(code) }.satisfies(
            { exception: Throwable? ->
                Assertions.assertThat(exception!!.javaClass.getSimpleName()).isEqualTo(expectedExceptionClassName)
            })
    }

    @Test
    fun testBadLexerToken() {
        // given:
        val code = """
            BEGIN
                y ` 2;
            END.
        """.trimIndent()
        val expectedExceptionClassName = "SyntaxException"
        // when:

        // then:

        // when:
        Assertions.assertThatThrownBy { interpreter.eval(code) }.satisfies(
            { exception: Throwable? ->
                Assertions.assertThat(exception!!.javaClass.getSimpleName()).isEqualTo(expectedExceptionClassName)
            })
    }

    @Test
    fun testInvalidOrder() {
        // given:
        val code = """
            BEGIN
                y := 2 2;
            END.
        """.trimIndent()
        val expectedExceptionClassName = "SyntaxException"
        // when:

        // then:

        // when:
        Assertions.assertThatThrownBy { interpreter.eval(code) }.satisfies(
            { exception: Throwable? ->
                Assertions.assertThat(exception!!.javaClass.getSimpleName()).isEqualTo(expectedExceptionClassName)
            })
    }

    @Test
    fun testInvalidFactor() {
        // given:
        val code = """
            BEGIN
                :=;
            END.
        """.trimIndent()
        val expectedExceptionClassName = "SyntaxException"
        // when:

        // then:

        // when:
        Assertions.assertThatThrownBy { interpreter.eval(code) }.satisfies(
            { exception: Throwable? ->
                Assertions.assertThat(exception!!.javaClass.getSimpleName()).isEqualTo(expectedExceptionClassName)
            })
    }

    @Test
    fun testInvalidProgram() {
        // given:
        val code = """
            END.
        """.trimIndent()
        val expectedExceptionClassName = "SyntaxException"
        // when:

        // then:

        // when:
        Assertions.assertThatThrownBy { interpreter.eval(code) }.satisfies(
            { exception: Throwable? ->
                Assertions.assertThat(exception!!.javaClass.getSimpleName()).isEqualTo(expectedExceptionClassName)
            })
    }

    @Test
    fun testUnknownVariable() {
        // given:
        val code = """
            BEGIN
                a := b + 42;
            END.
        """.trimIndent()
        val expectedExceptionClassName = "SyntaxException"
        // when:

        // then:

        // when:
        Assertions.assertThatThrownBy { interpreter.eval(code) }.satisfies(
            { exception: Throwable? ->
                Assertions.assertThat(exception!!.javaClass.getSimpleName()).isEqualTo(expectedExceptionClassName)
            })
    }

    @Test
    fun testInvalidUnaryOperator() {
        // given:
        val code = """
            BEGIN
                a := *42;
            END.
        """.trimIndent()
        val expectedExceptionClassName = "SyntaxException"
        // when:

        // then:

        // when:
        Assertions.assertThatThrownBy { interpreter.eval(code) }.satisfies(
            { exception: Throwable? ->
                Assertions.assertThat(exception!!.javaClass.getSimpleName()).isEqualTo(expectedExceptionClassName)
            })
    }
}