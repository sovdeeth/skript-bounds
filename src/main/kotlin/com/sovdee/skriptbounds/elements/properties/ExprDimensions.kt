package com.sovdee.skriptbounds.elements.properties

import ch.njol.skript.Skript
import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.expressions.base.SimplePropertyExpression
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.sovdee.skriptbounds.bounds.BoundingBox
import com.sovdee.skriptbounds.bounds.CuboidBoundingBox
import com.sovdee.skriptbounds.bounds.Sphere
import org.bukkit.event.Event
import java.util.*

class ExprDimensions : SimplePropertyExpression<BoundingBox, Double>() {

    companion object {
        init {
            Skript.registerExpression(ExprDimensions::class.java, Double::class.javaObjectType, ExpressionType.PROPERTY,
                "[the] bound[ing box] (0:length|1:width|2:height) of %cuboidboundingboxes%",
                "[the] bound[ing box] (3:radius) of %sphericalboundingboxes%"
                )
        }
    }

    private lateinit var dimension: Dimension

    override fun init(
        expressions: Array<out Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parseResult: ParseResult
    ): Boolean {
        dimension = Dimension.entries[parseResult.mark]
        return super.init(expressions, matchedPattern, isDelayed, parseResult)
    }

    override fun convert(from: BoundingBox): Double? {
        if (from is CuboidBoundingBox)
            return when (dimension) {
                Dimension.LENGTH -> from.length
                Dimension.WIDTH -> from.width
                Dimension.HEIGHT -> from.height
                Dimension.RADIUS -> null
            }

        if (from is Sphere)
            return when (dimension) {
                Dimension.RADIUS -> from.radius
                else -> null
            }

        throw IllegalArgumentException("Bounding box type '${from::class.simpleName}' is not supported by ExprDimensions.")
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>>? {
        return when (mode) {
            ChangeMode.SET, ChangeMode.ADD, ChangeMode.REMOVE -> arrayOf(Number::class.java)
            else -> null
        }
    }

    override fun change(event: Event, delta: Array<out Any?>?, mode: ChangeMode) {
        val amount = (delta?.get(0) as Number?)?.toDouble() ?: 0.0
        if (!amount.isFinite())
            return
        for (box in expr.getArray(event)) {
            val value = when (mode) {
                ChangeMode.SET ->  amount
                ChangeMode.ADD -> getValue(box) + amount
                ChangeMode.REMOVE -> getValue(box) - amount
                else -> 0.0
            }
            setValue(box, value.coerceIn(0.0, Double.MAX_VALUE))
        }
    }

    private fun getValue(box: BoundingBox): Double {
        return when (box) {
            is CuboidBoundingBox -> when (dimension) {
                Dimension.LENGTH -> box.length
                Dimension.WIDTH -> box.width
                Dimension.HEIGHT -> box.height
                else -> 0.0
            }

            is Sphere -> when (dimension) {
                Dimension.RADIUS -> box.radius
                else -> 0.0
            }
        }
    }

    private fun setValue(box: BoundingBox, value: Double) {
        when (box) {
            is CuboidBoundingBox -> when (dimension) {
                Dimension.LENGTH -> box.length = value
                Dimension.WIDTH -> box.width = value
                Dimension.HEIGHT -> box.height = value
                else -> return
            }

            is Sphere -> when (dimension) {
                Dimension.RADIUS -> box.radius = value
                else -> return
            }
        }
    }

    override fun getReturnType() = Double::class.javaObjectType

    override fun getPropertyName() = "bound " + dimension.toString().lowercase(Locale.getDefault())

    private enum class Dimension {
        LENGTH, WIDTH, HEIGHT, RADIUS
    }

}
