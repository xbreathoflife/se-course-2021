package ru.hse.smartUniversity

fun findRoute(map: UniversityMap, start: String, finish: String): List<String> {
    var algorithm = FindingAlgorithm()
    algorithm.bfs(map, start)
    return algorithm.returnRoute(finish)
}
