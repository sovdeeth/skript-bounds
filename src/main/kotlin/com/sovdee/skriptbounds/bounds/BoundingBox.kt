package com.sovdee.skriptbounds.bounds

import com.sovdee.skriptbounds.math.Expandable
import com.sovdee.skriptbounds.math.Scalable
import com.sovdee.skriptbounds.math.Translatable
import org.joml.Vector3d

sealed interface BoundingBox : Scalable, Translatable, Expandable {

    fun intersects(other: BoundingBox): Boolean

    operator fun contains(point: Vector3d): Boolean

}
