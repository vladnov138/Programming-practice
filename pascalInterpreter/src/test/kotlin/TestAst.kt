import interpreter.Empty
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class TestAst {
    @Test
    fun testEmpty() {
        // given:
        // when:
        val result = Empty("")
        // then:
        Assertions.assertThat(result)
            .isNotNull()
            .isEqualTo(Empty())
        Assertions.assertThat(result.value)
            .isEqualTo("")
    }
}