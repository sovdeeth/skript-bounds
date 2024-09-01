package com.sovdee.skriptbounds.bounds

import com.google.common.base.Preconditions
import com.sovdee.skriptbounds.math.square
import org.joml.Intersectiond
import org.joml.Vector3d
import kotlin.math.sqrt

/**
 * Represents a 3D spherical bounding box with a center point and a radius squared value.
 *
 * @property center The center point of the sphere in 3-dimensional space.
 * @property radiusSquared The square of the radius of the sphere.
 */
data class Sphere(override var center: Vector3d, var radiusSquared: Double) : BoundingBox {

    /**
     * Represents the radius of the sphere.
     *
     * The `radius` property calculates the sphere's radius based on the `radiusSquared` value.
     * When setting a new radius, it ensures the radius is non-negative and updates the
     * `radiusSquared` value accordingly.
     *
     * @throws IllegalArgumentException if radius < 0.
     */
    var radius: Double
        get() = sqrt(radiusSquared)
        set(value) {
            Preconditions.checkState(value >= 0, "radius must be >= 0: $value")
            radiusSquared = value * value
        }

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
