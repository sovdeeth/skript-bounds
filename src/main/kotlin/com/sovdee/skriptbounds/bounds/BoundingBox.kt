package com.sovdee.skriptbounds.bounds

import org.joml.Vector3d

interface BoundingBox {

    fun intersects(other: BoundingBox): Boolean

    operator fun contains(point: Vector3d): Boolean

}
