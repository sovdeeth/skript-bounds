package com.sovdee.skriptbounds.bounds

import com.sovdee.skriptbounds.math.square
import org.joml.Intersectiond
import org.joml.Vector3d
import kotlin.math.sqrt

data class Sphere(val center: Vector3d, var radiusSquared: Double) : BoundingBox {

    override fun scale(scalingFactor: Double) {
        radiusSquared *= scalingFactor * scalingFactor
    }

    override fun translate(direction: Vector3d) {
        center.add(direction)
    }

    override fun expandUniform(amount: Double) {
        radiusSquared = square(sqrt(radiusSquared) + amount)
    }

    override fun expandToContain(point: Vector3d) {
        radiusSquared = center.distanceSquared(point)
    }

    override fun intersects(other: BoundingBox): Boolean {
        return when (other) {
            is Sphere -> (center == other.center && radiusSquared == other.radiusSquared) || Intersectiond.testSphereSphere(center, radiusSquared, other.center, other.radiusSquared)
            is AxisAlignedBox -> Intersectiond.testAabSphere(other.min, other.max, center, radiusSquared)
        }
    }

    override fun contains(point: Vector3d): Boolean {
        return center.distanceSquared(point) <= radiusSquared
    }

}
