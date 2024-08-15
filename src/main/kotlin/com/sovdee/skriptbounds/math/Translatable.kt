package com.sovdee.skriptbounds.math

import org.joml.Vector3d

interface Translatable {

    fun translate(direction: Vector3d)

    fun translateX(distance: Double) = translate(Vector3d(distance, 0.0, 0.0))
    fun translateY(distance: Double) = translate(Vector3d(0.0, distance, 0.0))
    fun translateZ(distance: Double) = translate(Vector3d(0.0, 0.0, distance))

}
