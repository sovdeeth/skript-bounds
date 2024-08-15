package com.sovdee.skriptbounds.elements.properties

import ch.njol.skript.expressions.base.SimplePropertyExpression
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.sovdee.skriptbounds.bounds.AxisAlignedBox
import java.util.*

class ExprDimensions : SimplePropertyExpression<AxisAlignedBox, Double>() {

    companion object {
        init {
            register(ExprDimensions::class.java, Double::class.java, "bound[ing box] (length|width|height)", "%boundingboxes%")
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

    override fun convert(from: AxisAlignedBox): Double {
        return when (dimension) {
            Dimension.LENGTH -> from.length
            Dimension.WIDTH -> from.width
            Dimension.HEIGHT -> from.height
        }
    }

    override fun getReturnType() = Double::class.java

    override fun getPropertyName() = dimension.toString().lowercase(Locale.getDefault())

    private enum class Dimension {
        LENGTH, WIDTH, HEIGHT
    }

}
