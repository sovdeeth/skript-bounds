package com.sovdee.skriptbounds.elements.conditions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.sovdee.skriptbounds.bounds.BoundingBox
import org.bukkit.event.Event

class CondIntersects : Condition() {

    companion object {
        init {
            Skript.registerCondition(CondIntersects::class.java,
                "bound[ing box] %boundingbox% (intersects|collides with|touches) bound[s|ing box[es]] %boundingboxes%",
                "bound[ing box] %boundingbox% does(n't|not) (intersect|collide with|touch) bound[s|ing box[es]] %boundingboxes%"
            )
        }
    }

    private lateinit var mainBox: Expression<BoundingBox>
    private lateinit var otherBoxes: Expression<BoundingBox>

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<out Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parseResult: ParseResult
    ): Boolean {
        mainBox = expressions[0] as Expression<BoundingBox>
        otherBoxes = expressions[1] as Expression<BoundingBox>
        isNegated = matchedPattern == 1
        return true
    }

    override fun check(event: Event): Boolean {
        val mainBox = this.mainBox.getSingle(event) ?: return false
        return otherBoxes.check(event, { box -> mainBox.intersects(box)}, isNegated)
    }

    override fun toString(event: Event?, debug: Boolean)
        = "bound ${mainBox.toString(event, debug)} ${if (isNegated) "does not" else ""} intersects bound ${otherBoxes.toString(event, debug)}"
}
