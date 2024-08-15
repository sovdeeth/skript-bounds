package com.sovdee.skriptbounds

import ch.njol.skript.Skript
import ch.njol.skript.SkriptAddon
import ch.njol.skript.bstats.bukkit.Metrics
import ch.njol.skript.util.Version
import org.bukkit.plugin.java.JavaPlugin
import java.io.IOException
import java.util.logging.Logger

class SkriptBounds : JavaPlugin() {

    override fun onEnable() {
        val manager = server.pluginManager
        val skript = manager.getPlugin("Skript")
        SkriptBounds.logger = logger
        if (skript == null || !skript.isEnabled) {
            error("Could not find Skript! Make sure you have it installed and that it properly loaded. Disabling...")
            manager.disablePlugin(this)
            return
        } else if (Skript.getVersion() < Version(2, 9, 0)) {
            error("You are running an unsupported version of Skript. Please update to at least Skript 2.9.0. Disabling...")
            manager.disablePlugin(this)
            return
        }
        addon = Skript.registerAddon(this)
        addon.languageFileDirectory = "lang"
        try {
            addon.loadClasses("com.sovdee.skriptbounds", "elements")
        } catch (error: IOException) {
            error.printStackTrace()
            manager.disablePlugin(this)
            return
        }
        Metrics(this, 18457)
        info("Successfully enabled skript-bounds.")
    }

    companion object {

        private lateinit var logger: Logger
        lateinit var addon: SkriptAddon; private set

        fun info(string: String) {
            logger.info(string)
        }

        fun warning(string: String) {
            logger.warning(string)
        }

        fun error(string: String) {
            logger.severe(string)
        }

        fun debug(string: String) {
            if (Skript.debug())
                logger.info(string)
        }
    }

}
