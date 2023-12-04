package org.example.SpecialHashMap

/**
 * Класс-расширение HashMap с сортировкой по ключам и условию
 *
 * @author Vladislav Novikov
 */
class SpecialHashMap<V> : HashMap<String, V>() {
    val iloc = Iloc(this)

    val ploc = Ploc(this)
}