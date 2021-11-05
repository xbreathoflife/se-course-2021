package ru.hse.smartUniversity

fun findRoute(map: UniversityMap, start: String, finish: String): List<String> {
    val algorithm = FindingAlgorithm()
    algorithm.bfs(map, start)
    return algorithm.returnRoute(finish)
}

fun findAllRoutes(map: UniversityMap, start: String, finish: String): Set<List<String>> {
    val algorithm = FindingAlgorithm()
    val routes = algorithm.findAllRoutes(map, start, finish).map { it.drop(1) }.toSet()
    return algorithm.findMinRoutes(map, start, routes)
}
