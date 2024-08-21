package com.sovdee.skriptbounds.elements.properties

import ch.njol.skript.Skript
import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.expressions.base.PropertyExpression
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.sovdee.skriptbounds.bounds.CuboidBoundingBox
import com.sovdee.skriptbounds.bukkit.toPosition
import org.bukkit.Location
import org.bukkit.event.Event
import org.bukkit.util.Vector
import org.joml.Vector3d

class ExprCuboidCorners : PropertyExpression<CuboidBoundingBox, Vector>() {

    companion object {
        init {
            Skript.registerExpression(ExprCuboidCorners::class.java, Vector::class.java, ExpressionType.PROPERTY,
                "[the] (min:(min[imum]|lesser)|(max[imum]|greater)) corner[s] of bound[s|ing box[es]] %cuboidboundingboxes%",
                "bound[s|ing box[es]] %cuboidboundingboxes%'[s] (min:(min[imum]|lesser)|(max[imum]|greater)) corner[s]"
                )
        }
    }

    private var min : Boolean = false
    private lateinit var getCorner : (CuboidBoundingBox) -> Vector3d

    override fun init(
        expressions: Array<out Expression<*>?>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parseResult: ParseResult
    ): Boolean {
        min = parseResult.hasTag("min")
        @Suppress("UNCHECKED_CAST")
        expr = expressions[0] as Expression<CuboidBoundingBox>

        getCorner = if (min) { box : CuboidBoundingBox -> box.min} else { box : CuboidBoundingBox -> box.max}

        return true
    }

    override fun get(event: Event, source: Array<out CuboidBoundingBox>): Array<Vector> {
        return source.map(getCorner).map{vec -> Vector.fromJOML(vec)}.toTypedArray()
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>>? {
        return when (mode) {
            ChangeMode.SET, ChangeMode.ADD, ChangeMode.REMOVE -> arrayOf(Vector::class.java, Location::class.java)
            else -> null
        }
    }

    override fun change(event: Event, delta: Array<out Any?>?, mode: ChangeMode) {
        val deltaV = toPosition(delta?.get(0)) ?: return
        val bounds = expr.getArray(event)
        when (mode) {
            ChangeMode.ADD -> bounds.forEach { getCorner(it).add(deltaV); it.fixMinMax() }
            ChangeMode.REMOVE -> bounds.forEach { getCorner(it).sub(deltaV); it.fixMinMax() }
            ChangeMode.SET -> bounds.forEach { getCorner(it).set(deltaV); it.fixMinMax() }
            else -> throw IllegalArgumentException("Change mode '${mode}' is not supported by ExprCuboidCorners.")
        }
    }

    override fun getReturnType() = Vector::class.java

    override fun toString(event: Event?, debug: Boolean) = "${if (min) "minimum" else "maximum"} corner of ${expr.toString(event, debug)}"

}
