package com.sovdee.skriptbounds.elements

import ch.njol.skript.classes.ClassInfo
import ch.njol.skript.classes.Serializer
import ch.njol.skript.registrations.Classes
import ch.njol.yggdrasil.Fields
import com.sovdee.skriptbounds.bounds.AxisAlignedBox
import com.sovdee.skriptbounds.bounds.BoundingBox
import com.sovdee.skriptbounds.bounds.CuboidBoundingBox
import com.sovdee.skriptbounds.bounds.Sphere
import org.joml.Vector3d

class Types {
    companion object {
        init {
            Classes.registerClass(
                ClassInfo(BoundingBox::class.java, "boundingbox")
                    .user("bounding ?box(e?s)?")
                    .name("Bounding Box")
                    .description(
                        "Represents any sort of bounding box, be it axis aligned, orientable, or a sphere."
                    )
            )

            Classes.registerClass(
                ClassInfo(CuboidBoundingBox::class.java, "cuboidboundingbox")
                    .user("cuboid ?bounding ?box(e?s)?")
                    .name("Cuboid Bounding Box")
                    .description(
                        "Represents a cuboid bounding box, axis aligned or orientable."
                    )
            )

            Classes.registerClass(
                ClassInfo(AxisAlignedBox::class.java, "alignedboundingbox")
                    .user("(axis)? ?aligned ?bounding ?box(e?s)?")
                    .name("Axis Aligned Bounding Box")
                    .description(
                        "Represents a bounding box that's aligned along the global x, y, and z axis."
                    )
                    .serializer(object : Serializer<AxisAlignedBox>() {
                        override fun serialize(o: AxisAlignedBox): Fields {
                            val fields = Fields()
                            fields.putPrimitive("minx", o.min.x)
                            fields.putPrimitive("miny", o.min.y)
                            fields.putPrimitive("minz", o.min.z)
                            fields.putPrimitive("maxx", o.max.x)
                            fields.putPrimitive("maxy", o.max.y)
                            fields.putPrimitive("maxz", o.max.z)
                            return fields
                        }

                        override fun canBeInstantiated() = false

                        override fun mustSyncDeserialization() = false

                        override fun deserialize(o: AxisAlignedBox?, f: Fields?) {
                            throw IllegalStateException("AABs have no nullary constructor so this shouldn't be called.")
                        }

                        override fun deserialize(fields: Fields): AxisAlignedBox {
                            val min = Vector3d(
                                fields.getAndRemovePrimitive("minx", Double::class.java),
                                fields.getAndRemovePrimitive("miny", Double::class.java),
                                fields.getAndRemovePrimitive("minz", Double::class.java)
                            )
                            val max = Vector3d(
                                fields.getAndRemovePrimitive("maxx", Double::class.java),
                                fields.getAndRemovePrimitive("maxy", Double::class.java),
                                fields.getAndRemovePrimitive("maxz", Double::class.java)
                            )
                            return AxisAlignedBox(min, max)
                        }
                    })
            )

            Classes.registerClass(
                ClassInfo(Sphere::class.java, "sphericalboundingbox")
                    .user("spher(e|ical) ?bounding ?box(e?s)?")
                    .name("Spherical Bounding Box")
                    .description(
                        "Represents a bounding box that's a sphere centered on a point."
                    )
                    .serializer(object : Serializer<Sphere>() {
                        override fun serialize(o: Sphere): Fields {
                            val fields = Fields()
                            fields.putPrimitive("centerx", o.center.x)
                            fields.putPrimitive("centery", o.center.y)
                            fields.putPrimitive("centerz", o.center.z)
                            fields.putPrimitive("rsquared", o.radiusSquared)
                            return fields
                        }

                        override fun canBeInstantiated() = false

                        override fun mustSyncDeserialization() = false

                        override fun deserialize(o: Sphere?, f: Fields?) {
                            throw IllegalStateException("Spheres have no nullary constructor so this shouldn't be called.")
                        }

                        override fun deserialize(fields: Fields): Sphere {
                            val center = Vector3d(
                                fields.getAndRemovePrimitive("centerx", Double::class.java),
                                fields.getAndRemovePrimitive("centery", Double::class.java),
                                fields.getAndRemovePrimitive("centerz", Double::class.java)
                            )
                            val rSquared = fields.getAndRemovePrimitive("rSquared", Double::class.java)
                            return Sphere(center, rSquared)
                        }
                    })
            )
        }
    }
}
