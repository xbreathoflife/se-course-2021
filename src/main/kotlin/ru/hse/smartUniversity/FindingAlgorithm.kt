package ru.hse.smartUniversity

import java.util.*

class FindingAlgorithm {
    private var root = UUID.randomUUID().toString()
    private var parents = mutableMapOf<String, String>()

    private fun distInitialization(map: UniversityMap): MutableMap<String, Int> {
        val dist = mutableMapOf<String, Int>()
        val rooms = map.getAllRooms()
        for (room in rooms)
            dist[room] = -1
        return dist
    }

    fun bfs(map: UniversityMap, start: String) {
        val roomsQueue = LinkedList<String>()
        roomsQueue.add(start)
        parents[start] = root
        val dist = distInitialization(map)
        dist[start] = 0
        while (!roomsQueue.isEmpty()) {
            val curRoom = roomsQueue.remove()
            for (neigh in map.neighbors(curRoom)) {
                if (dist[neigh] == -1) {
                    dist[neigh] = dist[curRoom]!! + 1
                    parents[neigh] = curRoom
                    roomsQueue.add(neigh)
                }
            }
        }
    }

    fun returnRoute(finish: String): List<String> {
        val route = LinkedList<String>()
        var curRoom = finish
        while (parents[curRoom] != root) {
            route.add(curRoom)
            curRoom = parents[curRoom]!!
        }
        route.reverse()
        return route
    }
}