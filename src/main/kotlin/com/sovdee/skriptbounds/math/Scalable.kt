package com.sovdee.skriptbounds.math

/**
 * Represents something that can be scaled by a specified factor.
 */
interface Scalable {

    /**
     * Scales the object by a given scaling factor.
     *
     * @param scalingFactor the factor by which to scale the object. A value greater than 1 increases the size of the object,
     * while a value between 0 and 1 decreases the size. A value of 1 leaves the object unchanged.
     * Negative values will typically invert the shape, though exact behavior in this case is left up to the implementation.
     */
    fun scale(scalingFactor: Double)

}
