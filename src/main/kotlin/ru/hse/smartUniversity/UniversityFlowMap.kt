package ru.hse.smartUniversity

class UniversityFlowMap {
    private data class Room(val name: String) {
        val neighbors = mutableSetOf<Room>()
        val capacity = mutableMapOf<Room, Long>()
        val timings = mutableMapOf<Room, Long>()
    }

    data class Passage(val from: String?, val to: String?)

    private val rooms = mutableMapOf<String, Room>()
    private val edges = mutableListOf<Passage>()

    private fun addForRoom(room: Room?, sndRoom: Room?, capacity: Long, time: Long) {
        if (sndRoom != null) room?.neighbors?.add(sndRoom)
        if (sndRoom != null) room?.capacity?.put(sndRoom, capacity)
        if (sndRoom != null) room?.timings?.put(sndRoom, time)
        edges.add(Passage(room?.name, sndRoom?.name))
    }

    private fun connect(first: Room?, second: Room?, capacity: Long, time: Long) {
        addForRoom(first, second, capacity, time)
        addForRoom(second, first, capacity, time)
    }

    fun addRoom(name: String) {
        rooms[name] = Room(name)
    }

    fun connect(first: String, second: String, capacity: Long = 1, time: Long = 1) =
        connect(rooms[first], rooms[second], capacity, time)

    fun neighbors(name: String): List<String> =
        rooms[name]?.neighbors?.map { it.name } ?: listOf()

    fun getCapacity(from: String, to: String): Long =
        rooms[from]?.capacity?.get(rooms[to]) ?: -1

    fun changeCapacity(from: String, to: String, dist: Long) {
        val curDist = getCapacity(from, to)
        rooms[from]?.capacity?.replace(rooms.getValue(to), curDist + dist)
    }

    fun getTime(from: String, to: String): Long =
        rooms[from]?.timings?.get(rooms[to]) ?: 1

    fun getAllPassages(): List<Passage> =
        edges

    fun getAllRooms(): List<String> =
        rooms.keys.toList()
}
