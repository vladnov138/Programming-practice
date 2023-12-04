package org.example.SpecialHashMap

class Iloc<V>(private val specHashMap: SpecialHashMap<V>) {
    operator fun get(index: Int): V? {
        val sortedKeys = specHashMap.keys.toSortedSet()
        return specHashMap[sortedKeys.elementAt(index)]
    }

}