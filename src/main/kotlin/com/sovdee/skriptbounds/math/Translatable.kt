package com.sovdee.skriptbounds.math

import org.joml.Vector3d

/**
 * Represents an entity that can be translated in 3-dimensional space.
 */
interface Translatable {

    /**
     * Translates the object by a given vector.
     *
     * @param direction The vector by which to translate the object.
     */
    fun translate(direction: Vector3d)

    /**
     * Translates the object along the X-axis by the given distance.
     *
     * @param distance The distance to move along the X-axis.
     */
    fun translateX(distance: Double) = translate(Vector3d(distance, 0.0, 0.0))

    /**
     * Translates the object along the Y-axis by the given distance.
     *
     * @param distance The distance to move along the Y-axis.
     */
    fun translateY(distance: Double) = translate(Vector3d(0.0, distance, 0.0))

    /**
     * Translates the object along the Z-axis by the given distance.
     *
     * @param distance The distance to move along the Z-axis.
     */
    fun translateZ(distance: Double) = translate(Vector3d(0.0, 0.0, distance))

}
