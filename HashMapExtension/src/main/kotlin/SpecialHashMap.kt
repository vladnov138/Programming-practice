package org.example

class SpecialHashMap() {
    val hashMap: HashMap<String, Int> = HashMap()
    val iloc: List<Int?>
        get() {
            val sortedKeys = hashMap.keys.toSortedSet()
            return sortedKeys.map { hashMap[it] }
        }
    val ploc = Ploc(this)

    operator fun get(key: String) = hashMap[key]

    operator fun set(key: String, value: Int) {
        hashMap[key] = value
    }

    private fun handleCondition() {

    }
}