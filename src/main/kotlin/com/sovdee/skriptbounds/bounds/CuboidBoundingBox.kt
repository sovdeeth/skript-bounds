package com.sovdee.skriptbounds.bounds

import org.joml.Vector3d

sealed interface CuboidBoundingBox : BoundingBox {

    var length: Double
    var width: Double
    var height: Double

    val min: Vector3d
    val max: Vector3d

    fun fixMinMax()
}
