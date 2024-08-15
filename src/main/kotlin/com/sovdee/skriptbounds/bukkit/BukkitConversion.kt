package com.sovdee.skriptbounds.bukkit

import org.bukkit.Location
import org.bukkit.util.Vector
import org.joml.Vector3d

fun toPosition(any: Any?) : Vector3d? {
    return when (any) {
        is Vector -> toPosition(any)
        is Location -> toPosition(any)
        else -> null
    }
}

fun toPosition(vector: Vector?) : Vector3d? {
    return vector?.toVector3d()
}

fun toPosition(location: Location?) : Vector3d? {
    return location?.toVector()?.toVector3d()
}
