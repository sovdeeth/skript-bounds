package com.sovdee.skriptbounds.elements.properties

import ch.njol.skript.Skript
import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.expressions.base.SimplePropertyExpression
import ch.njol.skript.lang.ExpressionType
import com.sovdee.skriptbounds.bounds.BoundingBox
import com.sovdee.skriptbounds.bukkit.toPosition
import com.sovdee.skriptbounds.math.minus
import com.sovdee.skriptbounds.math.plus
import org.bukkit.Location
import org.bukkit.event.Event
import org.bukkit.util.Vector

class ExprCenter : SimplePropertyExpression<BoundingBox, Vector>() {

    companion object {
        init {
            Skript.registerExpression(ExprCenter::class.java, Vector::class.java, ExpressionType.PROPERTY,
                "center[s] of bound[s|ing box[es]] %boundingboxes%",
                "bound[s|ing box[es]] %boundingboxes%'[s] center[s]"
            )
        }
    }

    override fun convert(from: BoundingBox?) = from?.let { Vector.fromJOML(it.center) }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>>? {
        return when (mode) {
            ChangeMode.ADD, ChangeMode.REMOVE, ChangeMode.SET -> arrayOf(Vector::class.java, Location::class.java)
            else -> null
        }
    }

    override fun change(event: Event, delta: Array<out Any?>?, mode: ChangeMode) {
        val deltaV = toPosition(delta?.get(0)) ?: return
        val bounds = expr.getArray(event)
        when (mode) {
            ChangeMode.ADD -> bounds.forEach { it.center += deltaV }
            ChangeMode.REMOVE -> bounds.forEach { it.center -= deltaV }
            ChangeMode.SET -> bounds.forEach { it.center = deltaV }
            else -> throw IllegalArgumentException("Change mode '${mode}' is not supported by ExprCenter.")
        }
    }

    override fun getReturnType() = Vector::class.java

    override fun getPropertyName() = "bound center"

}
