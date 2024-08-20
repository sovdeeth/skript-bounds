package com.sovdee.skriptbounds.math

import org.jetbrains.annotations.Contract
import org.joml.Vector3d

// Extensions to JOML's Vector3d class to provide simpler comparison

/**
 * @return whether this vector is less than or equal to the other, component-wise
 */
fun Vector3d.isStrictlyLTE(other: Vector3d): Boolean {
    return x <= other.x && y <= other.y && z <= other.z
}

/**
 * @return whether this vector is greater than or equal to the other, component-wise
 */
fun Vector3d.isStrictlyGTE(other: Vector3d): Boolean {
    return x >= other.x && y >= other.y && z >= other.z
}

/**
 * deep copies a vector
 */
fun Vector3d.copy(): Vector3d {
    return this.clone() as Vector3d
}

operator fun Vector3d.plus(other: Vector3d) : Vector3d {
    return this.add(other, Vector3d())
}

operator fun Vector3d.minus(other: Vector3d) : Vector3d {
    return this.sub(other, Vector3d())
}


/**
 * Sets this vector to the midpoint of the line between the two vectors
 * @param other The vector to use as the other end of the line segment
 * @return the modified vector
 */
@Contract("_ -> this")
fun Vector3d.midpoint(other: Vector3d) : Vector3d {
    return this.midpoint(other, this)
}

/**
 * Returns the midpoint of the line between the two vectors
 * @param other The vector to use as the other end of the line segment
 * @param dest The destination vector for the new components
 * @return the modified vector
 */
@Contract("_, _ -> param2")
fun Vector3d.midpoint(other: Vector3d, dest: Vector3d) : Vector3d {
    return this.add(other, dest).div(2.0)
}
