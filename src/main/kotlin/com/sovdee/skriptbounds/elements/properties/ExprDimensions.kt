package com.sovdee.skriptbounds.elements.properties

import ch.njol.skript.expressions.base.SimplePropertyExpression
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.sovdee.skriptbounds.bounds.AxisAlignedBox
import com.sovdee.skriptbounds.bounds.BoundingBox
import java.util.*

class ExprDimensions : SimplePropertyExpression<BoundingBox, Double>() {

    companion object {
        init {
            register(ExprDimensions::class.java, Double::class.javaObjectType, "bound[ing box] (0:length|1:width|2:height)", "boundingboxes")
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
        if (from !is AxisAlignedBox)
            return null
        return when (dimension) {
            Dimension.LENGTH -> from.length
            Dimension.WIDTH -> from.width
            Dimension.HEIGHT -> from.height
        }
    }

    override fun getReturnType() = Double::class.javaObjectType

    override fun getPropertyName() = "bound " + dimension.toString().lowercase(Locale.getDefault())

    private enum class Dimension {
        LENGTH, WIDTH, HEIGHT
    }

}
