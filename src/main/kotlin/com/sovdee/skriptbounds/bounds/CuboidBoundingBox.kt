package com.sovdee.skriptbounds.bounds

import org.joml.Vector3d

/**
 * Represents a bounding box with a specific length,
 * width, and height, and provides methods to ensure the correctness of
 * the bounding volume.
 */
sealed interface CuboidBoundingBox : BoundingBox {

    /**
     * Represents the length of the cuboid bounding box.
     * Can be set to modify the bounding box's dimensions.
     */
    var length: Double
    /**
     * Represents the width of the cuboid bounding box.
     * Can be set to modify the bounding box's dimensions.
     */
    var width: Double
    /**
     * Represents the height of the cuboid bounding box.
     * Can be set to modify the bounding box's dimensions.
     */
    var height: Double

    /**
     * Represents the minimum corner of a cuboid bounding box in 3-dimensional space.
     * This may be in a local or global frame of reference.
     */
    val min: Vector3d
    /**
     * Represents the maximum corner of a cuboid bounding box in 3-dimensional space.
     * This may be in a local or global frame of reference.
     */
    val max: Vector3d

    /**
     * Ensures that the minimum and maximum points of the cuboid bounding box are
     * correctly set after transformations such as addition, subtraction, or setting
     * of corners.
     *
     * The method validates and adjusts the `min` and `max` properties of the cuboid
     * bounding box to reflect the current state accurately, maintaining proper
     * bounds.
     *
     * This may act in a local or global frame of reference.
     */
    fun fixMinMax()
}
