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
        val compareByWeight: Comparator<Pair<String, Int>> = compareBy { it.second }
        val roomsQueue = PriorityQueue(compareByWeight)
        roomsQueue.add(start to 0)
        parents[start] = root
        val dist = distInitialization(map)
        dist[start] = 0
        while (!roomsQueue.isEmpty()) {
            val curRoom = roomsQueue.remove()
            for (neigh in map.neighbors(curRoom.first)) {
                val nextDist = map.getDist(curRoom.first, neigh)
                if (nextDist < 0) throw AlgorithmException()
                if (dist[neigh]!! > dist[curRoom.first]!! + nextDist) {
                    dist[neigh] = dist[curRoom.first]!! + nextDist
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

    fun findAllRoutes(map: UniversityMap, start: String, finish: String): MutableList<List<String>> {
        val route = listOf(start)
        val roomsQueue = LinkedList<List<String>>()
        val allRoutes = mutableListOf<List<String>>()
        roomsQueue.add(route)
        while (!roomsQueue.isEmpty()) {
            val tmpRoute = roomsQueue.remove()
            val lastRoom = tmpRoute[tmpRoute.lastIndex]
            if (lastRoom == finish) {
                allRoutes.add(tmpRoute)
            }

            for (neigh in map.neighbors(lastRoom)) {
                if (neigh !in tmpRoute) {
                    val newRoute = tmpRoute.toMutableList()
                    newRoute.add(neigh)
                    roomsQueue.add(newRoute)
                }
            }
        }
        return allRoutes
    }
}