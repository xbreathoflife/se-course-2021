package ru.hse.smartUniversity

class UniversityMap {
    private data class Room(val name: String) {
        val neighbors = mutableSetOf<Room>()
        val distances = mutableMapOf<Room, Long>()
    }

    private val rooms = mutableMapOf<String, Room>()
    private fun connect(first: Room?, second: Room?, dist: Long) {
        if (second != null) first?.neighbors?.add(second)
        if (first != null) second?.neighbors?.add(first)
        if (second != null) first?.distances?.put(second, dist)
        if (first != null) second?.distances?.put(first, dist)
    }

    fun addRoom(name: String) {
        rooms[name] = Room(name)
    }

    fun connect(first: String, second: String, dist: Long = 1) =
        connect(rooms[first], rooms[second], dist)

    fun neighbors(name: String): List<String> =
        rooms[name]?.neighbors?.map { it.name } ?: listOf()

    fun getDist(from: String, to: String): Long =
        rooms[from]?.distances?.get(rooms[to]) ?: -1

    fun getAllRooms(): List<String> =
        rooms.keys.toList()
}
