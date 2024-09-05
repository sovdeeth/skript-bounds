package com.sovdee.skriptbounds.elements.conditions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.skript.lang.VerboseAssert
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import com.sovdee.skriptbounds.bounds.BoundingBox
import com.sovdee.skriptbounds.bukkit.toPosition
import org.bukkit.event.Event

class CondBoundContains : Condition(), VerboseAssert {

    companion object {
        init {
            Skript.registerCondition(CondBoundContains::class.java,
                "bound[s|ing box[es]] %boundingboxes% contain[s] %vectors/locations%",
                "bound[s|ing box[es]] %boundingboxes% do[es](n't| not) contain %vectors/locations%",
                "%vectors/locations% (is|are) [contained] (within|in|inside of) bound[s|ing box[es]] %boundingboxes%",
                "%vectors/locations% (is|are)(n't| not) [contained] (within|in|inside of) bound[s|ing box[es]] %boundingboxes%"
                )
        }
    }

    private lateinit var boundingBoxes : Expression<BoundingBox>
    private lateinit var positions : Expression<*>
    private var matchedPattern: Int = 0

    override fun init(
        expressions: Array<out Expression<*>?>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parseResult: ParseResult
    ): Boolean {
        @Suppress("UNCHECKED_CAST")
        boundingBoxes = expressions[if (matchedPattern > 1) 1 else 0] as Expression<BoundingBox>
        positions = expressions[if (matchedPattern > 1) 0 else 1]!!
        isNegated = matchedPattern % 2 == 1
        this.matchedPattern = matchedPattern
        return true
    }

    override fun check(event: Event): Boolean {
        val positions = this.positions.getAll(event).mapNotNull { toPosition(it) }.toTypedArray()
        if (positions.isEmpty())
            return isNegated

        val isAnd = this.positions.and
        return boundingBoxes.check(event) { box ->
            SimpleExpression.check(positions, box::contains, isNegated, isAnd)
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        val pluralBoxes = !boundingBoxes.isSingle
        val pluralPoints = !positions.isSingle
        return when (matchedPattern) {
            0, 1 -> "bounding box${if (pluralBoxes) "es" else ""} ${boundingBoxes.toString(event, debug)} contain${if (pluralBoxes) "" else "s"} ${positions.toString(event, debug)}"
            2, 3 -> "${positions.toString(event, debug)} ${if (pluralPoints) "are" else "is"} contained in bounding box$pluralBoxes ${boundingBoxes.toString(event, debug)}"
            else -> throw IllegalStateException("Invalid matched pattern '${matchedPattern}' for CondBoundContains.")
        }
    }

    override fun getExpectedMessage(event: Event) = "vectors/locations within the bounds of " + VerboseAssert.getExpressionValue(boundingBoxes, event)

    override fun getReceivedMessage(event: Event): String = VerboseAssert.getExpressionValue(positions, event)

}
