package ru.hse.smartUniversity

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class RouteFindingTest {

    companion object {
        @JvmStatic
        fun routes(): List<Arguments> {
            val mapSingle = UniversityMap()
            mapSingle.addRoom("101")

            val mapTwoRooms = UniversityMap()
            mapTwoRooms.addRoom("101")
            mapTwoRooms.addRoom("102")
            mapTwoRooms.connect("101", "102")

            val mapLineRooms = UniversityMap()
            mapLineRooms.addRoom("101")
            mapLineRooms.addRoom("102")
            mapLineRooms.addRoom("103")
            mapLineRooms.addRoom("104")
            mapLineRooms.connect("101", "102")
            mapLineRooms.connect("102", "103")
            mapLineRooms.connect("103", "104")

            val mapCycle = UniversityMap()
            mapCycle.addRoom("101")
            mapCycle.addRoom("102")
            mapCycle.addRoom("103")
            mapCycle.connect("101", "102")
            mapCycle.connect("102", "103")
            mapCycle.connect("103", "101")

            val mapBig = UniversityMap()
            mapBig.addRoom("101")
            mapBig.addRoom("102")
            mapBig.addRoom("103")
            mapBig.addRoom("104")
            mapBig.addRoom("ROOT")
            mapBig.addRoom("106")
            mapBig.connect("101", "102")
            mapBig.connect("102", "103")
            mapBig.connect("103", "104")
            mapBig.connect("104", "ROOT")
            mapBig.connect("102", "ROOT")
            mapBig.connect("ROOT", "106")

            val mapWeighted = UniversityMap()
            mapWeighted.addRoom("101")
            mapWeighted.addRoom("102")
            mapWeighted.addRoom("103")
            mapWeighted.addRoom("104")
            mapWeighted.connect("101", "104", 5)
            mapWeighted.connect("101", "102", 1)
            mapWeighted.connect("102", "103", 1)
            mapWeighted.connect("103", "104", 1)

            val mapNoRoute = UniversityMap()
            mapNoRoute.addRoom("101")
            mapNoRoute.addRoom("102")

            val mapTwoRoutes = UniversityMap()
            mapTwoRoutes.addRoom("101")
            mapTwoRoutes.addRoom("102")
            mapTwoRoutes.addRoom("103")
            mapTwoRoutes.addRoom("104")
            mapTwoRoutes.connect("101", "102", 2)
            mapTwoRoutes.connect("102", "103", 4)
            mapTwoRoutes.connect("103", "104", 3)
            mapTwoRoutes.connect("101", "104", 3)

            return listOf(
                Arguments.of(mapSingle, "101", "101", emptyList<String>()),
                Arguments.of(mapTwoRooms, "101", "102", arrayListOf("102")),
                Arguments.of(mapLineRooms, "101", "104", arrayListOf("102", "103", "104")),
                Arguments.of(mapCycle, "101", "103", arrayListOf("103")),
                Arguments.of(mapCycle, "101", "102", arrayListOf("102")),
                Arguments.of(mapBig, "101", "106", arrayListOf("102", "ROOT", "106")),
                Arguments.of(mapWeighted, "101", "104", arrayListOf("102", "103", "104")),
                Arguments.of(mapNoRoute, "101", "102", listOf("Route not found")),
                Arguments.of(mapTwoRoutes, "101", "103",
                    arrayListOf(arrayListOf("102", "103"), arrayListOf("104", "103")))
            )
        }
    }

    @ParameterizedTest
    @MethodSource("routes")
    fun testRouteFinding(map: UniversityMap, start: String, finish: String, expectedRoute: List<String>) {
        val actualRoute = findRoute(map, start, finish)
        Assertions.assertEquals(
            expectedRoute,
            actualRoute,
            "Routes don't equal! Actual route is $actualRoute, but expected route is $expectedRoute"
        )
    }
}
