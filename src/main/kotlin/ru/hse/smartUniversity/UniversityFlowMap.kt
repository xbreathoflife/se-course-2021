package ru.hse.smartUniversity

class UniversityFlowMap {
    private data class Room(val name: String) {
        val neighbors = mutableSetOf<Room>()
        val capacity = mutableMapOf<Room, Long>()
    }

    private val rooms = mutableMapOf<String, Room>()
    private fun connect(first: Room?, second: Room?, capacity: Long) {
        if (second != null) first?.neighbors?.add(second)
        if (second != null) first?.capacity?.put(second, capacity)
        if (first != null) second?.neighbors?.add(first)
        if (first != null) second?.capacity?.put(first, capacity)
    }

    fun addRoom(name: String) {
        rooms[name] = Room(name)
    }

    fun connect(first: String, second: String, dist: Long = 1) =
        connect(rooms[first], rooms[second], dist)

    fun neighbors(name: String): List<String> =
        rooms[name]?.neighbors?.map { it.name } ?: listOf()

    fun getCapacity(from: String, to: String): Long =
        rooms[from]?.capacity?.get(rooms[to]) ?: -1

    fun changeCapacity(from: String, to: String, dist: Long) {
        val curDist = getCapacity(from, to)
        rooms[from]?.capacity?.replace(rooms.getValue(to), curDist + dist)
    }

    fun getAllRooms(): List<String> =
        rooms.keys.toList()
}
