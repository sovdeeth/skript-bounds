package com.sovdee.skriptbounds.bounds

import org.joml.Vector3d
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach

abstract class IntersectionTest {

    lateinit var testBoxes : List<BoundingBox>

    @BeforeEach
    fun boxSetup() {
        testBoxes = listOf(AxisAlignedBox(Vector3d(0.0, 0.0, 0.0), Vector3d(1.0, 1.0, 1.0)),Sphere(Vector3d(0.0, 0.0, 0.0), 1.0))
    }

}
