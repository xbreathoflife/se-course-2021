package ru.hse.smartUniversity

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.lang.Exception

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

            return listOf(
                Arguments.of(mapSingle, "101", "101", emptyList<String>()),
                Arguments.of(mapTwoRooms, "101", "102", arrayListOf("102")),
                Arguments.of(mapLineRooms, "101", "104", arrayListOf("102", "103", "104")),
                Arguments.of(mapCycle, "101", "103", arrayListOf("103")),
                Arguments.of(mapCycle, "101", "102", arrayListOf("102")),
                Arguments.of(mapBig, "101", "106", arrayListOf("102", "ROOT", "106")),
                Arguments.of(mapWeighted, "101", "104", arrayListOf("102", "103", "104"))
            )
        }

        @JvmStatic
        fun routes_exceptions(): List<Arguments> {
            val mapNoRoute = UniversityMap()
            mapNoRoute.addRoom("101")
            mapNoRoute.addRoom("102")

            val mapWrongWeightedRoute = UniversityMap()
            mapWrongWeightedRoute.addRoom("101")
            mapWrongWeightedRoute.addRoom("102")
            mapWrongWeightedRoute.connect("101", "102", -1)

            return listOf(
                Arguments.of(mapNoRoute, "101", "102"),
                Arguments.of(mapWrongWeightedRoute, "101", "102")
            )
        }

        @JvmStatic
        fun multiple_routes(): List<Arguments> {
            val mapTwoRooms = UniversityMap()
            mapTwoRooms.addRoom("101")
            mapTwoRooms.addRoom("102")
            mapTwoRooms.connect("101", "102")

            val mapDiamond = UniversityMap()
            mapDiamond.addRoom("101")
            mapDiamond.addRoom("102")
            mapDiamond.addRoom("103")
            mapDiamond.addRoom("104")
            mapDiamond.connect("101", "102")
            mapDiamond.connect("101", "103")
            mapDiamond.connect("102", "104")
            mapDiamond.connect("103", "104")

            val mapWithWeight = UniversityMap()
            mapWithWeight.addRoom("101")
            mapWithWeight.addRoom("102")
            mapWithWeight.addRoom("103")
            mapWithWeight.connect("101", "102")
            mapWithWeight.connect("102", "103")
            mapWithWeight.connect("103", "101", 5)

            val mapCrossbar = UniversityMap()
            mapCrossbar.addRoom("101")
            mapCrossbar.addRoom("102")
            mapCrossbar.addRoom("103")
            mapCrossbar.addRoom("104")
            mapCrossbar.connect("101", "102", 2)
            mapCrossbar.connect("101", "103", 5)
            mapCrossbar.connect("102", "104", 5)
            mapCrossbar.connect("103", "104", 2)
            mapCrossbar.connect("102", "103", 2)
            mapCrossbar.connect("101", "104", 6)

            val mapWithBigWeights = UniversityMap()
            mapWithBigWeights.addRoom("101")
            mapWithBigWeights.addRoom("102")
            mapWithBigWeights.addRoom("103")
            mapWithBigWeights.connect("101", "102", 2000000000)
            mapWithBigWeights.connect("102", "103", 2000000000)
            mapWithBigWeights.connect("103", "101", 2000000000)

            return listOf(
                Arguments.of(mapTwoRooms, "101", "102", listOf(arrayListOf("102"))),
                Arguments.of(mapDiamond, "101", "104", listOf(arrayListOf("102", "104"), arrayListOf("103", "104"))),
                Arguments.of(mapWithWeight, "101", "102", listOf(arrayListOf("102"))),
                Arguments.of(mapWithBigWeights, "101", "102", listOf(arrayListOf("102"))),
                Arguments.of(mapCrossbar, "101", "104", listOf(arrayListOf("102", "103", "104"), arrayListOf("104"))),
            )
        }

        @JvmStatic
        fun multiple_routes_exceptions(): List<Arguments> {
            val mapWrongWeightedRoute = UniversityMap()
            mapWrongWeightedRoute.addRoom("101")
            mapWrongWeightedRoute.addRoom("102")
            mapWrongWeightedRoute.addRoom("103")
            mapWrongWeightedRoute.connect("101", "102", -1)
            mapWrongWeightedRoute.connect("102", "103", 1)

            return listOf(
                Arguments.of(mapWrongWeightedRoute, "101", "103")
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

    @ParameterizedTest
    @MethodSource("routes_exceptions")
    fun testRouteFindingException(map: UniversityMap, start: String, finish: String) {
        assertThrows<AlgorithmException> { findRoute(map, start, finish) }
    }

    @ParameterizedTest
    @MethodSource("multiple_routes")
    fun testMultipleRouteFinding(map: UniversityMap, start: String, finish: String, expectedRoutes: List<List<String>>) {
        val actualRoutes = findAllRoutes(map, start, finish)
        Assertions.assertEquals(
            expectedRoutes.toSet(),
            actualRoutes,
            "Routes don't equal! Actual route is $actualRoutes, but expected route is $expectedRoutes"
        )
    }

    @ParameterizedTest
    @MethodSource("multiple_routes_exceptions")
    fun testMultipleRouteFindingException(map: UniversityMap, start: String, finish: String) {
        assertThrows<AlgorithmException> { findAllRoutes(map, start, finish) }
    }
}
