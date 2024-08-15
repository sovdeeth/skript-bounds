package com.sovdee.skriptbounds.math

import org.joml.Vector3d

interface Expandable {

    fun expandUniform(amount: Double)

    fun expandToContain(point: Vector3d)

}
