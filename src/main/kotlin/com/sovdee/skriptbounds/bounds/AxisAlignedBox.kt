package com.sovdee.skriptbounds.bounds

import com.google.common.base.Preconditions
import com.sovdee.skriptbounds.math.isStrictlyGTE
import com.sovdee.skriptbounds.math.isStrictlyLTE
import com.sovdee.skriptbounds.math.midpoint
import org.joml.Intersectiond
import org.joml.Vector3d

data class AxisAlignedBox(override val min: Vector3d, override val max: Vector3d) : CuboidBoundingBox {

    /**
     * The computed width (x) of the box
     */
    override var width: Double
        get() = max.x - min.x
        set(value) {
            Preconditions.checkState(value >= 0, "width must be >= 0: $value")
            val mid = (max.x + min.x) / 2
            min.x = mid - value / 2
            max.x = mid + value / 2
        }

    override var length: Double
        get() = max.z - min.z
        set(value) {
            Preconditions.checkState(value >= 0, "length must be >= 0: $value")
            val mid = (max.z + min.z) / 2
            min.z = mid - value / 2
            max.z = mid + value / 2
        }

    override var height: Double
        get() = max.y - min.y
        set(value) {
            Preconditions.checkState(value >= 0, "height must be >= 0: $value")
            val mid = (max.y + min.y) / 2
            min.y = mid - value / 2
            max.y = mid + value / 2
        }

    override var center: Vector3d
        get() {
            return min.midpoint(max, Vector3d())
        }
        set(value) {
            val currentCenter = min.midpoint(max, Vector3d())
            this.translate(value.sub(currentCenter, currentCenter))
        }

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
        min.min(max, min)
        max.max(min, max)
    }

    override fun intersects(other: BoundingBox): Boolean {
        return when (other) {
            is AxisAlignedBox -> Intersectiond.testAabAab(min, max, other.min, other.max)
            is Sphere -> Intersectiond.testAabSphere(min, max, other.center, other.radiusSquared)
        }
    }

    fun intersection(other: AxisAlignedBox): AxisAlignedBox {
        return this.intersection(other, this)
    }

    fun intersection(other: AxisAlignedBox, dest: AxisAlignedBox): AxisAlignedBox {
        min.max(other.min, dest.min)
        max.min(other.max, dest.max)
        return dest
    }

    override fun contains(point: Vector3d): Boolean {
        return point.isStrictlyGTE(min) && point.isStrictlyLTE(max)
    }

}

