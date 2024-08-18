package com.sovdee.skriptbounds.bounds

import org.joml.Vector3d
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class AxisAlignedBoxTest {

    lateinit var box : AxisAlignedBox

    @BeforeEach
    fun setup() {
        box = AxisAlignedBox(Vector3d(0.0, 0.0, 0.0), Vector3d(1.0, 1.0, 1.0))
    }

    @Test
    fun dimensions() {
        assertTrue(box.width == 1.0, "wrong width")
        assertTrue(box.length == 1.0, "wrong length")
        assertTrue(box.height == 1.0, "wrong height")
        assertTrue(box.center.equals(0.5, 0.5, 0.5), "wrong center")
        box.width = 2.0
        box.length = 3.0
        box.height = 4.0
        assertTrue(box.width == 2.0, "wrong width")
        assertTrue(box.length == 3.0, "wrong length")
        assertTrue(box.height == 4.0, "wrong height")
        assertTrue(box.center.equals(0.5, 0.5, 0.5), "wrong center")
    }

    @Test
    fun center() {
        assertTrue(box.width == 1.0, "wrong width")
        assertTrue(box.length == 1.0, "wrong length")
        assertTrue(box.height == 1.0, "wrong height")
        assertTrue(box.center.equals(0.5, 0.5, 0.5), "wrong center")
        box.center = Vector3d(1.0, 1.0, 1.0)
        assertTrue(box.width == 1.0, "wrong width")
        assertTrue(box.length == 1.0, "wrong length")
        assertTrue(box.height == 1.0, "wrong height")
        assertTrue(box.center.equals(1.0, 1.0, 1.0), "wrong center")
    }

    @Test
    fun translate() {
        box.translate(Vector3d(2.0, 3.0, 4.0))
        assertTrue(box.min.equals(2.0, 3.0, 4.0), "Failed to shift min")
        assertTrue(box.max.equals(3.0, 4.0, 5.0), "Failed to shift max")
    }

    @Test
    fun rotateX() {
        box.min.set(-2.0, -1.0, 0.0)
        box.rotateX()
        assertTrue(box.min.equals(-2.0, -0.5, -0.5), "Failed to rotate min")
        assertTrue(box.max.equals(1.0, 0.5, 1.5), "Failed to rotate max")
    }

    @Test
    fun rotateY() {
        box.min.set(-2.0, -1.0, 0.0)
        box.rotateY()
        assertTrue(box.min.equals(-1.0, -1.0, -1.0), "Failed to rotate min")
        assertTrue(box.max.equals(0.0, 1.0, 2.0), "Failed to rotate max")
    }

    @Test
    fun rotateZ() {
        box.min.set(-2.0, -1.0, 0.0)
        box.rotateZ()
        assertTrue(box.min.equals(-1.5, -1.5, 0.0), "Failed to rotate min")
        assertTrue(box.max.equals(0.5, 1.5, 1.0), "Failed to rotate max")
    }

    @Test
    fun scale() {
        box.scale(2.0)
        assertTrue(box.min.equals(-0.5, -0.5, -0.5), "Failed to scale min")
        assertTrue(box.max.equals(1.5, 1.5, 1.5), "Failed to scale max")
    }

    @Test
    fun expandUniform() {
        box.expandUniform(2.0)
        assertTrue(box.min.equals(-2.0, -2.0, -2.0), "Failed to expand min")
        assertTrue(box.max.equals(3.0, 3.0, 3.0), "Failed to expand max")
    }

    @Test
    fun expandToContain() {
        box.expandToContain(Vector3d(-1.0, 3.0, 0.5))
        assertTrue(box.min.equals(-1.0, 0.0, 0.0), "Failed to expand min")
        assertTrue(box.max.equals(1.0, 3.0, 1.0), "Failed to expand max")
    }

    @Test
    fun contains() {
        assertTrue(Vector3d(0.0, 0.0, 0.0) in box, "Failed to contain zero vector")
        assertTrue(Vector3d(0.9999, 0.5, 0.3) in box, "Failed to contain vector")
        assertTrue(Vector3d(0.5, 0.0, 0.0) in box, "Failed to contain face vector")
        assertFalse(Vector3d(-0.01, 0.03, 0.02) in box, "Unexpectedly contained vector")
    }

    @Test
    fun copy() {
        val newBox = box.copy()
        assertTrue(newBox.min.equals(0.0, 0.0, 0.0), "Failed to copy min")
        assertTrue(newBox.max.equals(1.0, 1.0, 1.0), "Failed to copy max")
        // TODO: implement deep copying methods
//        box.shift(Vector3d(0.0, 1.0, 0.0))
//        assertTrue(newBox.min.equals(0.0, 0.0, 0.0), "Modifying original min modified copy")
//        assertTrue(newBox.max.equals(1.0, 1.0, 1.0), "Modifying original max modified copy")
    }

    @Test
    fun testEquals() {
        assertTrue(box == AxisAlignedBox(Vector3d(0.0, 0.0, 0.0), Vector3d(1.0, 1.0, 1.0)), "Not equal to identical box")
        assertFalse(box == AxisAlignedBox(Vector3d(0.0, 0.0, 0.0), Vector3d(1.0, 0.1, 1.0)), "Equal to different box")
    }

    @Nested
    inner class AABoxIntersectionTest : IntersectionTest() {
        @Test
        fun intersects() {
            for (testBox in testBoxes) {
                assertTrue(box.intersects(testBox), "Failed to intersect $testBox")
            }
            box.translate(Vector3d(5.0, 5.0, 5.0))
            for (testBox in testBoxes) {
                assertFalse(box.intersects(testBox), "Unexpectedly intersected $testBox")
            }
        }
    }
}
