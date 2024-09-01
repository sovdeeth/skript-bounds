package com.sovdee.skriptbounds.bounds

import com.google.common.base.Preconditions
import com.sovdee.skriptbounds.math.isStrictlyGTE
import com.sovdee.skriptbounds.math.isStrictlyLTE
import com.sovdee.skriptbounds.math.midpoint
import org.joml.Intersectiond
import org.joml.Vector3d

/**
 * A class representing an axis-aligned bounding box (AABB).
 *
 * @property min The minimum coordinates of the bounding box.
 * @property max The maximum coordinates of the bounding box.
 */
data class AxisAlignedBox(override val min: Vector3d, override val max: Vector3d) : CuboidBoundingBox {


    /**
     * Represents the width of the AxisAlignedBox along the x-axis.
     * The length is computed as the difference between the maximum and minimum x-coordinates.
     *
     * Getting the width returns the current width based on the min and max x-coordinates.
     * Setting the width modifies the min and max x-coordinates such that the center of the box remains unchanged.
     *
     * @throws IllegalArgumentException if the provided width is negative.
     */
    override var width: Double
        get() = max.x - min.x
        set(value) {
            Preconditions.checkState(value >= 0, "width must be >= 0: $value")
            val mid = (max.x + min.x) / 2
            min.x = mid - value / 2
            max.x = mid + value / 2
        }

    /**
     * Represents the length of the AxisAlignedBox along the z-axis.
     * The length is computed as the difference between the maximum and minimum z-coordinates.
     *
     * Getting the length returns the current length based on the min and max z-coordinates.
     * Setting the length modifies the min and max z-coordinates such that the center of the box remains unchanged.
     *
     * @throws IllegalStateException if the provided length is negative.
     */
    override var length: Double
        get() = max.z - min.z
        set(value) {
            Preconditions.checkState(value >= 0, "length must be >= 0: $value")
            val mid = (max.z + min.z) / 2
            min.z = mid - value / 2
            max.z = mid + value / 2
        }

    /**
     * Represents the height of the AxisAlignedBox along the y-axis.
     * The height is computed as the difference between the maximum and minimum y-coordinates.
     *
     * Getting the height returns the current height based on the min and max y-coordinates.
     * Setting the height modifies the min and max y-coordinates such that the center of the box remains unchanged.
     *
     * @throws IllegalStateException if the provided height is negative.
     */
    override var height: Double
        get() = max.y - min.y
        set(value) {
            Preconditions.checkState(value >= 0, "height must be >= 0: $value")
            val mid = (max.y + min.y) / 2
            min.y = mid - value / 2
            max.y = mid + value / 2
        }

    /**
     * Represents the center point of the AxisAlignedBox.
     *
     * When getting this value, the midpoint between `min` and `max` is returned.
     * When setting this value, the bounding box is translated so that its new center point
     * aligns with the given value.
     */
    override var center: Vector3d
        get() {
            return min.midpoint(max, Vector3d())
        }
        set(value) {
            val currentCenter = min.midpoint(max, Vector3d())
            this.translate(value.sub(currentCenter, currentCenter))
        }

    /**
     * Rotates the axis-aligned bounding box around the X-axis by 90 degrees.
     * Effectively swaps the Y and Z components of the bounding box's dimensions.
     * Assumes the center of rotation is the geometric center of the bounding box.
     */
    @Suppress("DuplicatedCode")
    fun rotateX() {
        val between = max.sub(min, Vector3d()).div(2.0)
        val half = min.add(between, Vector3d())
        val temp = between.y
        between.y = between.z
        between.z = temp
        half.add(between, max)
        half.sub(between, min)
    }

    /**
     * Rotates the axis-aligned bounding box around the Y-axis by 90 degrees.
     * Effectively swaps the X and Z components of the bounding box's dimensions.
     * Assumes the center of rotation is the geometric center of the bounding box.
     */
    @Suppress("DuplicatedCode")
    fun rotateY() {
        val between = max.sub(min, Vector3d()).div(2.0)
        val half = min.add(between, Vector3d())
        val temp = between.x
        between.x = between.z
        between.z = temp
        half.add(between, max)
        half.sub(between, min)
    }

    /**
     * Rotates the axis-aligned bounding box around the Z-axis by 90 degrees.
     * Effectively swaps the X and Y components of the bounding box's dimensions.
     * Assumes the center of rotation is the geometric center of the bounding box.
     */
    @Suppress("DuplicatedCode")
    fun rotateZ() {
        val between = max.sub(min, Vector3d()).div(2.0)
        val half = min.add(between, Vector3d())
        val temp = between.x
        between.x = between.y
        between.y = temp
        half.add(between, max)
        half.sub(between, min)
    }

    override fun scale(scalingFactor: Double) {
        val between = max.sub(min, Vector3d()).div(2.0)
        val half = min.add(between, Vector3d())
        between.mul(scalingFactor)
        half.add(between, max)
        half.sub(between, min)
        fixMinMax()
    }

    override fun translate(direction: Vector3d) {
        min.add(direction)
        max.add(direction)
    }

    override fun expandUniform(amount: Double) {
        min.add(-amount, -amount, -amount)
        max.add(amount, amount, amount)
        fixMinMax()
    }

    override fun expandToContain(point: Vector3d) {
        min.min(point)
        max.max(point)
    }

    override fun fixMinMax() {
        val temp = min.min(max, Vector3d())
        max.max(min, max)
        min.set(temp)
    }

    override fun intersects(other: BoundingBox): Boolean {
        return when (other) {
            is AxisAlignedBox -> Intersectiond.testAabAab(min, max, other.min, other.max)
            is Sphere -> Intersectiond.testAabSphere(min, max, other.center, other.radiusSquared)
        }
    }

    /**
     * Computes the intersection of this axis-aligned box with another axis-aligned box.
     *
     * @param other The other axis-aligned box to intersect with.
     * @return A new axis-aligned box representing the intersection of this box with the other box.
     */
    fun intersection(other: AxisAlignedBox): AxisAlignedBox {
        return this.intersection(other, this)
    }

    /**
     * Computes the intersection of this axis-aligned box with another axis-aligned box.
     * Updates the destination box with the intersection result.
     *
     * @param other The other axis-aligned box to intersect with.
     * @param dest The destination axis-aligned box to store the intersection result.
     * @return The destination axis-aligned box containing the intersection.
     */
    fun intersection(other: AxisAlignedBox, dest: AxisAlignedBox): AxisAlignedBox {
        min.max(other.min, dest.min)
        max.min(other.max, dest.max)
        return dest
    }

    override fun contains(point: Vector3d): Boolean {
        return point.isStrictlyGTE(min) && point.isStrictlyLTE(max)
    }

}

