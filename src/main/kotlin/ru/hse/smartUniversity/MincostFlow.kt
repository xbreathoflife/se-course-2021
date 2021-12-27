package ru.hse.smartUniversity

import java.lang.Long.min

class MincostFlow {

    private val distances = mutableMapOf<String, Long>()
    private val parents = mutableMapOf<String, String>()
    private val funcs = mutableMapOf<UniversityFlowMap.Passage, Long>()
    private val INFINITY = 1_000_000_000_000L

    private fun InitDistances(map: UniversityFlowMap) {
        distances.clear()
        parents.clear()
        val rooms = map.getAllRooms()
        for (room in rooms) {
            distances[room] = INFINITY
        }
    }

    private fun FordBellman(map: UniversityFlowMap, start: String) {
        InitDistances(map)
        distances[start] = 0
        val n = map.getAllRooms().size
        val passages = map.getAllPassages()
        for (k in 1 until n) {
            for (passage in passages) {
                val cur = distances[passage.from]!! + map.getTime(passage.from!!, passage.to!!)
                if (cur < distances[passage.to]!! && map.getCapacity(passage.from, passage.to) > funcs[passage]!!) {
                    distances.put(passage.to, cur)
                    parents.put(passage.to, passage.from)
                }
            }
        }
    }

    private fun findPath(map: UniversityFlowMap, start: String, finish: String): Long? {
        FordBellman(map, start)
        if (!parents.containsKey(finish)) {
            return null
        }
        var cur = finish
        var flow = INFINITY
        while (cur != start) {
            flow = min(flow, map.getCapacity(cur, parents[cur]!!))
            cur = parents[cur]!!
        }
        return flow
    }

    fun findMinCost(map: UniversityFlowMap, start: String, finish: String): Long {
        val passages = map.getAllPassages()
        for (passage in passages) {
            funcs[passage] = 0
        }
        var flow = findPath(map, start, finish)
        var weight = 0L
        while (flow != null) {
            var cur = finish
            while (cur != start) {
                funcs.put(
                    UniversityFlowMap.Passage(parents[cur], cur),
                    funcs[UniversityFlowMap.Passage(parents[cur], cur)]!! + flow
                )
                funcs.put(
                    UniversityFlowMap.Passage(cur, parents[cur]),
                    funcs[UniversityFlowMap.Passage(cur, parents[cur])]!! - flow
                )
                weight += map.getTime(parents[cur]!!, cur) * flow
                cur = parents[cur]!!
            }
            flow = findPath(map, start, finish)
        }
        return weight
    }
}
