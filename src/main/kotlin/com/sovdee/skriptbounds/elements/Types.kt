package com.sovdee.skriptbounds.elements

import ch.njol.skript.classes.ClassInfo
import ch.njol.skript.registrations.Classes
import com.sovdee.skriptbounds.bounds.BoundingBox

class Types {
    companion object {
        init {
            Classes.registerClass(
                ClassInfo(BoundingBox::class.java, "boundingbox")
                    .user("bounding ?box(es)?")
                    .name("Bounding Box")
                    .description(
                        "Represents any sort of bounding box, be it align, orientable, or a sphere."
                    )
            )
        }
    }
}
