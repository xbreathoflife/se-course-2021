package ru.hse.smartUniversity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FordFulkersonTest {
    @Test
    fun checkMaxFlow() {
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

        val m = FordFulkerson()
        assertEquals(6, m.fordFulkerson(map, "101", "106"))
    }

    @Test
    fun checkMaxMincost() {
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

        val m = MincostFlow()
        assertEquals(12, m.findMinCost(map, "101", "106"))
    }
}
