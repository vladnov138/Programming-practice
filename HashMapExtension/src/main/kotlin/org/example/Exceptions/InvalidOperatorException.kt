package org.example.Exceptions

/**
 * Класс-исключение на случай невалидного оператора сравнения
 *
 * @author Vladislav Novikov
 */
class InvalidOperatorException : Exception() {
    override val cause: Throwable?
        get() = super.cause
    override val message: String?
        get() = super.message
}