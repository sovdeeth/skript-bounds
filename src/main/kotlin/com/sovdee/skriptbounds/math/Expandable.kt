package com.sovdee.skriptbounds.math

import org.joml.Vector3d

/**
 * Represents an object that can be expanded in various ways.
 */
interface Expandable {

    /**
     * Expands an object uniformly by a given amount.
     *
     * @param amount The amount by which to expand the object uniformly.
     */
    fun expandUniform(amount: Double)

    /**
     * Expands the current dimensions to ensure that the specified point is contained within.
     *
     * @param point The point to be contained.
     */
    fun expandToContain(point: Vector3d)

}
