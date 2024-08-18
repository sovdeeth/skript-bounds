package com.sovdee.skriptbounds.elements.constructors

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import com.sovdee.skriptbounds.bounds.Sphere
import com.sovdee.skriptbounds.bukkit.toPosition
import org.bukkit.event.Event

@Name("Spherical Bounding Box")
@Description(
    "Creates a spherical bounding box centered on the given position, with the given radius."
)
@Examples(
    "set {_box} to a spherical bounding box of radius 5 at player's location",
    "",
    "set {_box} to an sphere bounding box with radius 20 at vector(-10, -10, -10)",
    "shift bound {_box} by vector(5, 10, 5)"
)
@Since("1.0.0")
class ExprSphere : SimpleExpression<Sphere>() {

    companion object {
        init {
            Skript.registerExpression(ExprSphere::class.java, Sphere::class.java, ExpressionType.COMBINED,
                "[a] spher(e|ical) bounding box (with [a]|of) radius [of] %number% at %vector/location%"
            )
        }
    }

    private lateinit var center : Expression<*>
    private lateinit var radius : Expression<Number>

    override fun init(
        expressions: Array<out Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parseResult: SkriptParser.ParseResult
    ): Boolean {
        center = expressions[0]
        radius = expressions[1] as Expression<Number>
        return true
    }

    override fun get(event: Event): Array<Sphere>? {
        val center = toPosition(this.center.getSingle(event))
        val radius = this.radius.getSingle(event)
        if (center == null || radius == null)
            return null
        val r = radius.toDouble()
        if (r < 0)
            return null
        return arrayOf(Sphere(center, r * r))
    }

    override fun isSingle() = true

    override fun getReturnType() = Sphere::class.java

    override fun toString(event: Event?, debug: Boolean)
        = " a spherical bounding box at ${center.toString(event, debug)} with radius ${radius.toString(event, debug)}"

}
