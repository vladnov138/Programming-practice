package org.example.SpecialHashMap

import org.example.Exceptions.InvalidOperatorException

/**
 * Класс для фильтрации ключей класса {@link SpecialHashMap.SpecialHashMap} по заданному условию
 *
 * @author Vladislav Novikov
 */
class Ploc<V>(private val specHashMap: SpecialHashMap<V>) {

    /**
     * Перегрузка оператора-индексатора [].
     * Фильтрует HashMap по заданному условию
     * @param condition условие
     * @return результат фильтрации
     * @throws InvalidOperatorException при невалидном условии
     */
    operator fun get(condition: String): Map<String, V> {
        val splittedCondition = condition
            .replace(Regex("[()\\s]"), "")
            .split(Regex("[A-Za-z,]"))
        return specHashMap.filterKeys {
            val splittedKey = it
                .replace(Regex("[()\\s]"), "")
                .split(Regex("[A-Za-z,]"))
            if (splittedCondition.size != splittedKey.size) {
                return@filterKeys false
            }
            for (i in splittedCondition.indices) {
                val item = splittedCondition[i]
                val operator = parseOperator(item)
                val number = item.replace(operator, "").toDouble()
                val numberKey = splittedKey[i].toDouble()
                when (operator) {
                    ">" -> {
                        if (numberKey <= number) {
                            return@filterKeys false
                        }
                    }
                    "<" -> {
                        if (numberKey >= number) {
                            return@filterKeys false
                        }
                    }
                    ">=" -> {
                        if (numberKey < number) {
                            return@filterKeys false
                        }
                    }
                    "<=" -> {
                        if (numberKey > number) {
                            return@filterKeys false
                        }
                    }
                    else -> {
                        throw InvalidOperatorException()
                    }
                }
            }
            true
         }
    }

    /**
     * Метод для парсинга оператора сравнения из выражения
     * @param expression выражение
     * @return оператор сравнения
     */
    private fun parseOperator(expression: String): String {
        var operator = ""
        for (char in expression) {
            if (char in "<>=") {
                operator += char
            }
        }
        return operator
    }
}