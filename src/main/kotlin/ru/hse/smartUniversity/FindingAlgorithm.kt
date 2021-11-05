package ru.hse.smartUniversity

import java.util.*

class AlgorithmException: Exception()

class FindingAlgorithm {
    private var root = UUID.randomUUID().toString()
    private var parents = mutableMapOf<String, String>()

    private fun distInitialization(map: UniversityMap): MutableMap<String, Int> {
        val dist = mutableMapOf<String, Int>()
        val rooms = map.getAllRooms()
        for (room in rooms)
            dist[room] = Int.MAX_VALUE
        return dist
    }

    fun bfs(map: UniversityMap, start: String) {
        return
        val compareByWeight: Comparator<Pair<String, Int>> = compareBy { it.second }
        val roomsQueue = PriorityQueue(compareByWeight)
        roomsQueue.add(start to 0)
        parents[start] = root
        val dist = distInitialization(map)
        dist[start] = 0
        while (!roomsQueue.isEmpty()) {
            val curRoom = roomsQueue.remove()
            for (neigh in map.neighbors(curRoom.first)) {
                if (dist[neigh]!! > dist[curRoom.first]!! + map.getDist(curRoom.first, neigh)) {
                    dist[neigh] = dist[curRoom.first]!! + map.getDist(curRoom.first, neigh)
                    parents[neigh] = curRoom.first
                    roomsQueue.add(neigh to dist.getValue(neigh))
                }
            }
        }
    }

    fun returnRoute(finish: String): List<String> {
        val route = LinkedList<String>()
        var curRoom = finish
        while (parents[curRoom] != root) {
            route.add(curRoom)
            if (parents[curRoom] == null) {
                throw AlgorithmException()
            }
            curRoom = parents[curRoom]!!
        }
        route.reverse()
        return route
    }
}