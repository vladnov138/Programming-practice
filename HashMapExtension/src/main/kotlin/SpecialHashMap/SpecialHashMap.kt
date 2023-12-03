package SpecialHashMap

/**
 * Класс-расширение HashMap с сортировкой по ключам и условию
 *
 * @author Vladislav Novikov
 */
class SpecialHashMap : HashMap<String, Int>() {
    val iloc: List<Int?>
        get() {
            val sortedKeys = this.keys.toSortedSet()
            return sortedKeys.map { this[it] }
        }

    val ploc = Ploc(this)
}