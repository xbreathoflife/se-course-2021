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

            return listOf(
                Arguments.of(mapSingle, "101", "101", emptyArray<Int>()),
                Arguments.of(mapTwoRooms, "101", "102", arrayOf(102)),
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
