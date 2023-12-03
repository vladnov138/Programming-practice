import Exceptions.InvalidOperatorException
import SpecialHashMap.SpecialHashMap
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Тесты для класса {@link SpecialHashMap}
 *
 * @author Vladislav Novikov
 */
class TestSpecialHashMap {
    protected lateinit var map: SpecialHashMap

    /**
     * Инициализация map перед каждым тестом
     */
    @BeforeEach
    fun initClass() {
        map = SpecialHashMap()
    }

    /**
     * Тест для поля iloc.
     * Проверяет правильность сортировки по индексу
     */
    @Test
    fun testIlocField() {
        // given
        map["value1"] = 1
        map["value2"] = 2
        map["value3"] = 3
        map["1"] = 10
        map["2"] = 20
        map["3"] = 30
        map["1, 5"] = 100
        map["5, 5"] = 200
        map["10, 5"] = 300

        val expected = listOf(10, 300, 200, 3)

        // when
        val result = listOf(map.iloc[0], map.iloc[2], map.iloc[5], map.iloc[8])

        // then
        Assertions.assertThat(result)
            .isEqualTo(expected)
    }

    /**
     * Тест для поля ploc.
     * Проверяет правильность фильтрации по заданному условию.
     */
    @Test
    fun testPlocField() {
        // given
        map["value1"] = 1
        map["value2"] = 2
        map["value3"] = 3
        map["1"] = 10
        map["2"] = 20
        map["3"] = 30
        map["(1, 5)"] = 100
        map["(5, 5)"] = 200
        map["(10, 5)"] = 300
        map["(1, 5, 3)"] = 400
        map["(5, 5, 4)"] = 500
        map["(10, 5, 5)"] = 600

        val expected = listOf(
            hashMapOf("1" to 10, "2" to 20, "3" to 30),
            hashMapOf("1" to 10, "2" to 20),
            hashMapOf("(1, 5)" to 100, "(5, 5)" to 200, "(10, 5)" to 300),
            hashMapOf("(10, 5)" to 300),
            hashMapOf("(1, 5, 3)" to 400),
            hashMapOf(),
            hashMapOf("1" to 10, "2" to 20)
        )
        // when
        val result = listOf(
            map.ploc[">=1"],
            map.ploc["<3"],
            map.ploc[">0, >0"],
            map.ploc[">=10, >0"],
            map.ploc["<5, >=5, >=3"],
            map.ploc[">3"],
            map.ploc["<=2"]
        )
        // then
        Assertions.assertThat(result)
            .isEqualTo(expected)
    }

    /**
     * Тест для поля ploc.
     * Проверяет исключение при некорректном операторе сравнения.
     * @throws InvalidOperatorException при ошибке теста
     */
    @Test
    fun testPlocFieldInvalidOperator() {
        // given
        map["value1"] = 1
        map["value2"] = 2
        map["value3"] = 3
        map["1"] = 10
        // when
        // then
        Assertions.assertThatThrownBy { map.ploc["+2"] }.isInstanceOf(InvalidOperatorException::class.java)
    }
}