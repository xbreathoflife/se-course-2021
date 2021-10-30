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
            mapTwoRooms.connect("101", "102")
            mapTwoRooms.connect("102", "103")
            mapTwoRooms.connect("103", "104")

            return listOf(
                Arguments.of(mapSingle, "101", "101", emptyArray<Int>()),
                Arguments.of(mapTwoRooms, "101", "102", arrayOf(102)),
                Arguments.of(mapTwoRooms, "101", "104", arrayOf(102, 103, 104)),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("routes")
    fun testRouteFinding(map: UniversityMap, start: String, finish: String, expectedRoute: Array<Int>) {
        val actualRoute = findRoute(map, start, finish)
        Assertions.assertArrayEquals(
            expectedRoute,
            actualRoute,
            "Routes don't equal! Actual route is $actualRoute, but expected route is $expectedRoute"
        )
    }
}
