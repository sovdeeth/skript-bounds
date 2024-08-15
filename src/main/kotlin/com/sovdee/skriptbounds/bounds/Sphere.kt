package com.sovdee.skriptbounds.bounds

import org.joml.Intersectiond
import org.joml.Vector3d

data class Sphere(val center: Vector3d, val radiusSquared: Double) : BoundingBox {
    override fun intersects(other: BoundingBox): Boolean {
        return when (other) {
            is Sphere -> Intersectiond.testSphereSphere(center, radiusSquared, other.center, other.radiusSquared)
            is AxisAlignedBox -> Intersectiond.testAabSphere(other.min, other.max, center, radiusSquared)
            else -> false
        }
    }

    override fun contains(point: Vector3d): Boolean {
        return center.distanceSquared(point) <= radiusSquared
    }
}
