package ru.hse.smartUniversity

class UniversityMap {
    private data class Room(val name: String) {
        val neighbors = mutableSetOf<Room>()
    }

    private val rooms = mutableMapOf<String, Room>()
    private fun connect(first: Room?, second: Room?) {
        if (second != null) first?.neighbors?.add(second)
        if (first != null) second?.neighbors?.add(first)
    }

    fun addRoom(name: String) {
        rooms[name] = Room(name)
    }

    fun connect(first: String, second: String) =
        connect(rooms[first], rooms[second])

    fun neighbors(name: String): List<String> =
        rooms[name]?.neighbors?.map { it.name } ?: listOf()
}
