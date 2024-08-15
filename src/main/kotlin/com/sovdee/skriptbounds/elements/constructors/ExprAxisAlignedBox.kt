package com.sovdee.skriptbounds.elements.constructors

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Name
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import com.sovdee.skriptbounds.bounds.AxisAlignedBox
import com.sovdee.skriptbounds.bounds.BoundingBox
import com.sovdee.skriptbounds.bukkit.toPosition
import org.bukkit.event.Event
import org.joml.Vector3d

@Name("Axis Aligned Bounding Box")
@Description(
    "Creates an axis aligned bounding box between the two given positions. " +
    "Axis aligned boxes are aligned to Minecraft's axis and therefore cannot rotate freely. " +
    "This is what Minecraft uses for hitboxes and block collisions."
)
class ExprAxisAlignedBox : SimpleExpression<BoundingBox>() {

    companion object {
        init {
            Skript.registerExpression(ExprAxisAlignedBox::class.java, BoundingBox::class.java, ExpressionType.COMBINED,
                "[a[n]] [axis(-| )aligned] bounding box between %vector/location% and %vector/location%",
                "[a[n]] [axis(-| )aligned] bounding box from %vector/location% to %vector/location%"
            )
        }
    }

    private lateinit var cornerA: Expression<*>
    private lateinit var cornerB: Expression<*>

    override fun init(
        expressions: Array<out Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parseResult: ParseResult
    ): Boolean {
        cornerA = expressions[0]
        cornerB = expressions[1]
        return true
    }

    override fun get(event: Event): Array<BoundingBox>? {
        val cornerA = toPosition(this.cornerA.getSingle(event))
        val cornerB = toPosition(this.cornerB.getSingle(event))
        if (cornerA == null || cornerB == null)
            return null
        val min = cornerA.min(cornerB, Vector3d())
        val max = cornerA.max(cornerB, Vector3d())
        return arrayOf(AxisAlignedBox(min, max))
    }

    override fun isSingle() = true

    override fun getReturnType() = BoundingBox::class.java

    override fun toString(event: Event?, debug: Boolean) = "an axis aligned bounding box between ${cornerA.toString(event, debug)} and ${cornerB.toString(event, debug)}"

}
