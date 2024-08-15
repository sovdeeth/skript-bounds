package com.sovdee.skriptbounds.bounds

import com.sovdee.skriptbounds.math.isStrictlyGTE
import com.sovdee.skriptbounds.math.isStrictlyLTE
import org.joml.Intersectiond
import org.joml.Vector3d

data class AxisAlignedBox(val min: Vector3d, val max: Vector3d) : BoundingBox {

    fun shift(shiftBy: Vector3d) {
        min.add(shiftBy)
        max.add(shiftBy)
    }

    override fun intersects(other: BoundingBox): Boolean {
        return when (other) {
            is AxisAlignedBox -> Intersectiond.testAabAab(min, max, other.min, other.max)
            is Sphere -> Intersectiond.testAabSphere(min, max, other.center, other.radiusSquared)
            else -> false
        }
    }

    override fun contains(point: Vector3d): Boolean {
        return point.isStrictlyGTE(min) && point.isStrictlyLTE(max)
    }

}

