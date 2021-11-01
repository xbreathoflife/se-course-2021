package ru.hse.smartUniversity

fun findRoute(map: UniversityMap, start: String, finish: String): List<String> {
    val route = ArrayList<String>()
    val visited = HashSet<String>()
    var curRoom = start
    while (curRoom != finish) {
        visited.add(curRoom)
        for (room in map.neighbors(curRoom).reversed()) {
            if (!visited.contains(room)) {
                curRoom = room
                break
            }
        }
        route.add(curRoom)
    }
    return route
}
