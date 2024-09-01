package com.sovdee.skriptbounds.math

import org.jetbrains.annotations.Contract
import org.joml.Vector3d

// Extensions to JOML's Vector3d class to provide simpler comparison

/**
 * Checks if all components of this vector are less than or equal to the corresponding components of another vector.
 *
 * @param other The vector to compare with.
 * @return `true` if this vector is less than or equal to the other vector component-wise, `false` otherwise.
 */
fun Vector3d.isStrictlyLTE(other: Vector3d): Boolean {
    return x <= other.x && y <= other.y && z <= other.z
}

/**
 * Checks if this vector is strictly greater than or equal to another vector component-wise.
 *
 * @param other The vector to compare this vector with.
 * @return `true` if this vector is greater than or equal to the other vector component-wise, `false` otherwise.
 */
fun Vector3d.isStrictlyGTE(other: Vector3d): Boolean {
    return x >= other.x && y >= other.y && z >= other.z
}

/**
 * Adds the components of the given `other` vector to this vector and returns the result
 * as a new `Vector3d` instance.
 *
 * @param other The vector to be added to this vector.
 * @return A new `Vector3d` that is the result of adding the given `other` vector to this vector.
 */
@Contract("_ -> new", pure = true)
operator fun Vector3d.plus(other: Vector3d) : Vector3d {
    return this.add(other, Vector3d())
}

/**
 * Subtracts the components of the given `other` vector from this vector and returns the result
 * as a new `Vector3d` instance.
 *
 * @param other The vector to be subtracted from this vector.
 * @return A new `Vector3d` that is the result of subtracting the given `other` vector from this vector.
 */
@Contract("_ -> new", pure = true)
operator fun Vector3d.minus(other: Vector3d) : Vector3d {
    return this.sub(other, Vector3d())
}

/**
 * Sets this vector to the midpoint of the line between the two vectors.
 *
 * @param other The vector to use as the other end of the line segment.
 * @return the original vector modified to point to the midpoint.
 */
@Contract("_ -> this")
fun Vector3d.midpoint(other: Vector3d) : Vector3d {
    return this.midpoint(other, this)
}

/**
 * Returns the midpoint of the line between the two vectors
 * @param other The vector to use as the other end of the line segment
 * @param dest The destination vector for the new components
 * @return A vector pointing to the midpoint.
 */
@Contract("_, _ -> param2")
fun Vector3d.midpoint(other: Vector3d, dest: Vector3d) : Vector3d {
    return this.add(other, dest).div(2.0)
}
