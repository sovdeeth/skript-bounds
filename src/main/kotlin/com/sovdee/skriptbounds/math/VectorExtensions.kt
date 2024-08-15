package com.sovdee.skriptbounds.math

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
