package ru.hse.smartUniversity

import java.util.LinkedList

class FordFulkerson {
    private fun bfs(graph: UniversityFlowMap, s: String, t: String, p: MutableMap<String, String?>): Boolean {
        val visited = mutableMapOf<String, Boolean>()
        val queue = LinkedList<String>()
        queue.add(s)
        visited[s] = true
        p[s] = null
        while (queue.isNotEmpty()) {
            val u = queue.remove()
            for (v in graph.neighbors(u)) {
                if (!visited.getOrDefault(v, false) && graph.getDist(u, v) > 0) {
                    queue.add(v)
                    p[v] = u
                    visited[v] = true
                }
            }
        }
        return visited[t] == true
    }

    fun fordFulkerson(map: UniversityFlowMap, s: String, t: String): Long {
        var v: String?
        var u: String?

        val p = mutableMapOf<String, String?>()
        var maxFlow: Long = 0
        while (bfs(map, s, t, p)) {
            var pathFlow = Long.MAX_VALUE
            v = t
            while (v != s) {
                checkNotNull(v)
                u = p.getValue(v)
                checkNotNull(u)
                pathFlow = pathFlow.coerceAtMost(map.getDist(u, v))
                v = u
            }
            v = t
            while (v != s) {
                checkNotNull(v)
                u = p.getValue(v)
                checkNotNull(u)
                map.changeDist(u, v, -pathFlow)
                map.changeDist(v, u, pathFlow)
                v = u
            }

            maxFlow += pathFlow
        }
        return maxFlow
    }
}
