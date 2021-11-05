package ru.hse.smartUniversity

fun findRoute(map: UniversityMap, start: String, finish: String): List<String> {
    val algorithm = FindingAlgorithm()
    algorithm.bfs(map, start)
    return algorithm.returnRoute(finish)
}

fun findAllRoutes(map: UniversityMap, start: String, finish: String): List<List<String>> {
    val algorithm = FindingAlgorithm()
    return algorithm.findAllRoutes(map, start, finish).map { it.drop(1) }
}
