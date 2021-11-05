package ru.hse.smartUniversity

import java.util.*

class AlgorithmException: Exception()

class FindingAlgorithm {
    private var root = UUID.randomUUID().toString()
    private var parents = mutableMapOf<String, String>()

    private fun distInitialization(map: UniversityMap): MutableMap<String, Long> {
        val dist = mutableMapOf<String, Long>()
        val rooms = map.getAllRooms()
        for (room in rooms)
            dist[room] = Long.MAX_VALUE
        return dist
    }

    fun bfs(map: UniversityMap, start: String) {
        val compareByWeight: Comparator<Pair<String, Long>> = compareBy { it.second }
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

    fun findAllRoutes(map: UniversityMap, start: String, finish: String): Set<List<String>> {
        val route = listOf(start)
        val roomsQueue = LinkedList<List<String>>()
        val allRoutes = mutableSetOf<List<String>>()
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

    private fun calculateWeight(map: UniversityMap, route: List<String>, start: String): Long {
        var weight = 0L
        var prev = start
        for (room in route) {
            val cur_weight = map.getDist(room, prev)
            if (cur_weight < 0) {
                throw AlgorithmException()
            }
            weight += cur_weight
            prev = room
        }
        return weight
    }

    fun findMinRoutes(map: UniversityMap, start: String, routes: Set<List<String>>): Set<List<String>>{
        val dists = routes.map { route: List<String> -> calculateWeight(map, route, start) }
        val minDist = dists.minOrNull()
        val minRoutes = mutableSetOf<List<String>>()
        for (i in dists.indices) {
            if (dists[i] == minDist) {
                minRoutes.add(routes.elementAt(i))
            }
        }
        return minRoutes
    }
}