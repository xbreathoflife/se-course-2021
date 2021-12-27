package ru.hse.smartUniversity

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test

internal class RouteFindingTest {

    @Test
    fun `test single`() {
        val map = UniversityMap()
        map.addRoom("101")
        val actualRoute = findRoute(map, "101", "101")
        val expectedRoute = emptyList<String>()

        Assertions.assertEquals(
            expectedRoute,
            actualRoute,
            "Routes don't equal! Actual route is $actualRoute, but expected route is $expectedRoute"
        )
    }

    @Test
    fun `test two rooms`() {
        val map = UniversityMap()
        map.addRoom("101")
        map.addRoom("102")
        map.connect("101", "102")
        val actualRoute = findRoute(map, "101", "102")
        val expectedRoute = arrayListOf("102")

        Assertions.assertEquals(
            expectedRoute,
            actualRoute,
            "Routes don't equal! Actual route is $actualRoute, but expected route is $expectedRoute"
        )
    }

    @Test
    fun `test line rooms`() {
        val map = UniversityMap()
        map.addRoom("101")
        map.addRoom("102")
        map.addRoom("103")
        map.addRoom("104")
        map.connect("101", "102")
        map.connect("102", "103")
        map.connect("103", "104")
        val actualRoute = findRoute(map, "101", "104")
        val expectedRoute = arrayListOf("102", "103", "104")

        Assertions.assertEquals(
            expectedRoute,
            actualRoute,
            "Routes don't equal! Actual route is $actualRoute, but expected route is $expectedRoute"
        )
    }

    @Test
    fun `test cycle`() {
        val map = UniversityMap()
        map.addRoom("101")
        map.addRoom("102")
        map.addRoom("103")
        map.connect("101", "102")
        map.connect("102", "103")
        map.connect("103", "101")
        val actualRoute1 = findRoute(map, "101", "103")
        val expectedRoute1 = arrayListOf("103")

        Assertions.assertEquals(
            expectedRoute1,
            actualRoute1,
            "Routes don't equal! Actual route is $actualRoute1, but expected route is $expectedRoute1"
        )
        val actualRoute2 = findRoute(map, "101", "102")
        val expectedRoute2 = arrayListOf("102")

        Assertions.assertEquals(
            expectedRoute2,
            actualRoute2,
            "Routes don't equal! Actual route is $actualRoute2, but expected route is $expectedRoute2"
        )
    }

    @Test
    fun `test big`() {
        val map = UniversityMap()
        map.addRoom("101")
        map.addRoom("102")
        map.addRoom("103")
        map.addRoom("104")
        map.addRoom("ROOT")
        map.addRoom("106")
        map.connect("101", "102")
        map.connect("102", "103")
        map.connect("103", "104")
        map.connect("104", "ROOT")
        map.connect("102", "ROOT")
        map.connect("ROOT", "106")
        val actualRoute = findRoute(map, "101", "106")
        val expectedRoute = arrayListOf("102", "ROOT", "106")

        Assertions.assertEquals(
            expectedRoute,
            actualRoute,
            "Routes don't equal! Actual route is $actualRoute, but expected route is $expectedRoute"
        )
    }

    @Test
    fun `test weighted`() {
        val map = UniversityMap()
        map.addRoom("101")
        map.addRoom("102")
        map.addRoom("103")
        map.addRoom("104")
        map.connect("101", "104", 5)
        map.connect("101", "102", 1)
        map.connect("102", "103", 1)
        map.connect("103", "104", 1)
        val actualRoute = findRoute(map, "101", "104")
        val expectedRoute = arrayListOf("102", "103", "104")

        Assertions.assertEquals(
            expectedRoute,
            actualRoute,
            "Routes don't equal! Actual route is $actualRoute, but expected route is $expectedRoute"
        )
    }

    @Test
    fun `test no route`() {
        val map = UniversityMap()
        map.addRoom("101")
        map.addRoom("102")

        assertThrows<AlgorithmException> { findRoute(map, "101", "102") }
    }

    @Test
    fun `test wrong weight`() {
        val map = UniversityMap()
        map.addRoom("101")
        map.addRoom("102")
        map.connect("101", "102", -1)

        assertThrows<AlgorithmException> { findRoute(map, "101", "102") }
    }

    @Test
    fun `test two rooms multiple`() {
        val map = UniversityMap()
        map.addRoom("101")
        map.addRoom("102")
        map.connect("101", "102")
        val actualRoutes = findAllRoutes(map, "101", "102")
        val expectedRoutes = listOf(arrayListOf("102"))

        Assertions.assertEquals(
            expectedRoutes.toSet(),
            actualRoutes,
            "Routes don't equal! Actual route is $actualRoutes, but expected route is $expectedRoutes"
        )
    }

    @Test
    fun `test diamond`() {
        val map = UniversityMap()
        map.addRoom("101")
        map.addRoom("102")
        map.addRoom("103")
        map.addRoom("104")
        map.connect("101", "102")
        map.connect("101", "103")
        map.connect("102", "104")
        map.connect("103", "104")
        val actualRoutes = findAllRoutes(map, "101", "104")
        val expectedRoutes = listOf(arrayListOf("102", "104"), arrayListOf("103", "104"))

        Assertions.assertEquals(
            expectedRoutes.toSet(),
            actualRoutes,
            "Routes don't equal! Actual route is $actualRoutes, but expected route is $expectedRoutes"
        )
    }

    @Test
    fun `test crossbar`() {
        val map = UniversityMap()
        map.addRoom("101")
        map.addRoom("102")
        map.addRoom("103")
        map.addRoom("104")
        map.connect("101", "102", 2)
        map.connect("101", "103", 5)
        map.connect("102", "104", 5)
        map.connect("103", "104", 2)
        map.connect("102", "103", 2)
        map.connect("101", "104", 6)
        val actualRoutes = findAllRoutes(map, "101", "104")
        val expectedRoutes = listOf(arrayListOf("102", "103", "104"), arrayListOf("104"))

        Assertions.assertEquals(
            expectedRoutes.toSet(),
            actualRoutes,
            "Routes don't equal! Actual route is $actualRoutes, but expected route is $expectedRoutes"
        )
    }

    @Test
    fun `test weights multiple`() {
        val map = UniversityMap()
        map.addRoom("101")
        map.addRoom("102")
        map.addRoom("103")
        map.connect("101", "102")
        map.connect("102", "103")
        map.connect("103", "101", 5)
        val actualRoutes = findAllRoutes(map, "101", "102")
        val expectedRoutes = listOf(arrayListOf("102"))

        Assertions.assertEquals(
            expectedRoutes.toSet(),
            actualRoutes,
            "Routes don't equal! Actual route is $actualRoutes, but expected route is $expectedRoutes"
        )
    }

    @Test
    fun `test big weights`() {
        val map = UniversityMap()
        map.addRoom("101")
        map.addRoom("102")
        map.addRoom("103")
        map.connect("101", "102", 2000000000)
        map.connect("102", "103", 2000000000)
        map.connect("103", "101", 2000000000)
        val actualRoutes = findAllRoutes(map, "101", "102")
        val expectedRoutes = listOf(arrayListOf("102"))

        Assertions.assertEquals(
            expectedRoutes.toSet(),
            actualRoutes,
            "Routes don't equal! Actual route is $actualRoutes, but expected route is $expectedRoutes"
        )
    }

    @Test
    fun `test wrong weight multiple`() {
        val map = UniversityMap()
        map.addRoom("101")
        map.addRoom("102")
        map.addRoom("103")
        map.connect("101", "102", -1)
        map.connect("102", "103", 1)
        assertThrows<AlgorithmException> { findAllRoutes(map, "101", "103") }
    }

    @Test
    fun `test ford fulkerson`() {
        val ff = FordFulkerson()
        val map = UniversityFlowMap()
        map.addRoom("101")
        map.addRoom("102")
        map.addRoom("103")
        map.addRoom("104")
        map.addRoom("105")
        map.addRoom("106")
        map.connect("101", "102", 8)
        map.connect("102", "103", 9)
        map.connect("103", "106", 2)
        map.connect("101", "105", 3)
        map.connect("105", "104", 4)
        map.connect("104", "106", 5)
        map.connect("105", "103", 7)

        Assertions.assertEquals(6, ff.fordFulkerson(map, "101", "106"))
    }

    @Test
    fun `test mincost`() {
        val mf = MincostFlow()
        val map = UniversityFlowMap()
        map.addRoom("101")
        map.addRoom("102")
        map.addRoom("103")
        map.addRoom("104")
        map.addRoom("105")
        map.addRoom("106")
        map.connect("101", "102", 2, 2)
        map.connect("102", "103", 2, 1)
        map.connect("102", "104", 2, 10)
        map.connect("103", "105", 2, 2)
        map.connect("104", "105", 2, 20)
        map.connect("105", "106", 2, 1)

        Assertions.assertEquals(12, mf.findMinCost(map, "101", "106"))
    }
}
