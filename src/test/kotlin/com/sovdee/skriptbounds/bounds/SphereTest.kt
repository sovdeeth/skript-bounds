package com.sovdee.skriptbounds.bounds

import org.joml.Vector3d
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested

class SphereTest {

    lateinit var sphere: Sphere

    @BeforeEach
    fun setup() {
        sphere = Sphere(Vector3d(0.0, 0.0, 0.0), 1.0)
    }

    @Test
    fun scale() {
        sphere.scale(2.0)
        assertTrue(sphere.center.equals(0.0, 0.0, 0.0), "Unexpectedly changed center")
        assertTrue(sphere.radiusSquared.equals(4.0), "Failed to scale radius")
    }

    @Test
    fun translate() {
        sphere.translate(Vector3d(1.0, 2.0, 3.0))
        assertTrue(sphere.center.equals(1.0, 2.0, 3.0), "Failed to translate center")
        assertTrue(sphere.radiusSquared.equals(1.0), "Unexpectedly changed radius")
    }

    @Test
    fun expandUniform() {
        sphere.expandUniform(2.0)
        assertTrue(sphere.center.equals(0.0, 0.0, 0.0), "Unexpectedly changed center")
        assertTrue(sphere.radiusSquared.equals(9.0), "Failed to expand radius")
    }

    @Test
    fun expandToContain() {
        sphere.expandToContain(Vector3d(2.0, 3.0, -6.0))
        assertTrue(sphere.center.equals(0.0, 0.0, 0.0), "Unexpectedly changed center")
        assertTrue(sphere.radiusSquared.equals(49.0), "Failed to expand radius")
    }

    @Nested
    inner class SphereIntersectionTest : IntersectionTest() {
        @Test
        fun intersects() {
            for (testBox in testBoxes) {
                assertTrue(sphere.intersects(testBox), "Failed to intersect $testBox")
            }
            sphere.translate(Vector3d(5.0, 5.0, 5.0))
            for (testBox in testBoxes) {
                assertFalse(sphere.intersects(testBox), "Unexpectedly intersected $testBox")
            }
        }
    }

    @Test
    fun contains() {
        assertTrue(Vector3d(0.0, 0.0, 0.0) in sphere, "Failed to contain zero vector")
        assertTrue(Vector3d(0.2, 0.5, 0.3) in sphere, "Failed to contain vector")
        assertTrue(Vector3d(1.0, 0.0, 0.0) in sphere, "Failed to contain face vector")
        assertFalse(Vector3d(-1.01, 0.03, 0.02) in sphere, "Unexpectedly contained vector")
    }
}
