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

            return listOf(
                Arguments.of(mapSingle, "101", "101", emptyList<String>()),
                Arguments.of(mapTwoRooms, "101", "102", arrayListOf("102")),
                Arguments.of(mapLineRooms, "101", "104", arrayListOf("102", "103", "104")),
                Arguments.of(mapCycle, "101", "103", arrayListOf("103")),
                Arguments.of(mapCycle, "101", "102", arrayListOf("102"))
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
