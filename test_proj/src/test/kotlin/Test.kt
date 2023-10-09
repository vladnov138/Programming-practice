package test

import com.company.toInfix
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Test {
    @Test
    fun testCorrectVariants() {
        val variants = listOf("+ - 13 4 55", "+ 2 * 2 - 2 1", "+ + 10 20 30", "/ + 3 10 * + 2 3 - 3 5")
        val answers = listOf("((13 - 4) + 55)", "(2 + (2 * (2 - 1)))", "((10 + 20) + 30)",
            "((3 + 10) / ((2 + 3) * (3 - 5)))")
        for (i in variants.indices) {
            assertEquals(answers[i], toInfix(variants[i]))
        }
    }

    @Test
    fun testInvalidNumberOfOperands() {
        val variant = "- - 1 2"
        val exception = Assertions.assertThrows(ArithmeticException::class.java) { toInfix(variant) }
        assertEquals("Invalid number of operands", exception.message)
    }

    @Test
    fun testInvalidLine() {
        val variant = "- + a 1"
        val exception = Assertions.assertThrows(NumberFormatException::class.java) { toInfix(variant) }
        assertEquals("There is unknown symbol in line (not operator or operand)", exception.message)
    }
}