package com.sovdee.skriptbounds.bounds

import com.sovdee.skriptbounds.math.Expandable
import com.sovdee.skriptbounds.math.Scalable
import com.sovdee.skriptbounds.math.Translatable
import org.joml.Vector3d

/**
 * Represents a 3D bounding box with a center that can scale, translate, and expand.
 */
sealed interface BoundingBox : Scalable, Translatable, Expandable {

    /**
     * Represents the center of the BoundingBox.
     *
     * The `center` property defines the central point of a 3-dimensional bounding box.
     * It can be used in scaling, translating, and expanding operations to manipulate
     * the bounding box's position and size within a 3D space.
     */
    var center: Vector3d

    /**
     * Determines if this bounding box intersects with another bounding box.
     *
     * @param other The bounding box to test for intersection with.
     * @return true if the bounding boxes intersect, false otherwise.
     */
    fun intersects(other: BoundingBox): Boolean

    /**
     * Checks whether the given `point` is inside the bounding box.
     *
     * @param point The point in 3-dimensional space to be checked.
     * @return `true` if the point is inside the bounding box, `false` otherwise.
     */
    operator fun contains(point: Vector3d): Boolean

}
