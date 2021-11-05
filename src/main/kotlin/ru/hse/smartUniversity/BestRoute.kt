package ru.hse.smartUniversity

fun findRoute(map: UniversityMap, start: String, finish: String): List<String> {
    val algorithm = FindingAlgorithm()
    algorithm.bfs(map, start)
    val route = algorithm.returnRoute(finish)
    if (route != null) {
        return route
    }
    return listOf("Route not found")
}
