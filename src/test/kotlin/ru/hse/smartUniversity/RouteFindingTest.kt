package ru.hse.smartUniversity

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class SequenceMatchingTest {

    companion object {
        @JvmStatic
        fun routs(): List<Arguments> {

            val mapSingle = UniversityMap()
            mapSingle.addRoom("101")

            return listOf(
                Arguments.of(mapSingle, "101", "101", emptyArray<Int>()),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("routs")
    fun testRouteFinding(map: UniversityMap, start: String, finish: String, expectedRoute: Array<Int>) {
        val actualRoute = findRoute(map, start, finish)
        Assertions.assertArrayEquals(expectedRoute, actualRoute, "Routes don't equal! " +
            "Actual route is $actualRoute, but expected route is $expectedRoute")
    }
}
