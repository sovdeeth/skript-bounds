package com.sovdee.skriptbounds.bukkit

import org.bukkit.Location
import org.bukkit.util.Vector
import org.joml.Vector3d

fun toPosition(any: Any?) : Vector3d? {
    return when (any) {
        is Vector -> any.toPosition()
        is Location -> any.toPosition()
        else -> null
    }
}

fun Vector?.toPosition() : Vector3d? {
    val v = this?.toVector3d()
    return if (v?.isFinite == true) v else null
}

fun Location?.toPosition() : Vector3d? {
    val v = this?.toVector()?.toVector3d()
    return if (v?.isFinite == true) v else null
}
