package com.sovdee.skriptbounds.bukkit

import org.bukkit.Location
import org.bukkit.util.Vector
import org.jetbrains.annotations.Contract
import org.joml.Vector3d

/**
 * Converts an object of type `Any?` to a `Vector3d?`.
 * If the object is an instance of `Vector` or `Location`, it converts to `Vector3d`
 * using respective extension functions. Otherwise, returns null. Additionally, if
 * the `Vector` or `Location` contain non-finite values, null is returned.
 *
 * @param any The object to be converted, which should be of type `Vector?` or `Location?`.
 * @return A `Vector3d` object if the input is a valid `Vector` or `Location`, otherwise null.
 */
@Contract("null -> null")
fun toPosition(any: Any?) : Vector3d? {
    return when (any) {
        is Vector -> any.toPosition()
        is Location -> any.toPosition()
        else -> null
    }
}

/**
 * Converts a `Vector` to a `Vector3d` if the vector has finite components.
 *
 * @return A `Vector3d` object if the vector is non-null and has finite components, otherwise null.
 */
@Contract("null -> null")
fun Vector?.toPosition() : Vector3d? {
    val v = this?.toVector3d()
    return if (v?.isFinite == true) v else null
}

/**
 * Converts a `Location` to a `Vector3d`.
 *
 * If the `Location` is null or its corresponding `Vector3d` contains non-finite values,
 * this function returns null.
 *
 * @return a `Vector3d` if the Location and its vector representation are finite, otherwise null.
 */
@Contract("null -> null")
fun Location?.toPosition() : Vector3d? {
    val v = this?.toVector()?.toVector3d()
    return if (v?.isFinite == true) v else null
}
