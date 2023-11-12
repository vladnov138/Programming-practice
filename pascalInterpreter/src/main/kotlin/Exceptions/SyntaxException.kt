package Exceptions

class SyntaxException(message: String) : Exception(message) {
    override val cause: Throwable?
        get() = super.cause
    override val message: String?
        get() = super.message
}