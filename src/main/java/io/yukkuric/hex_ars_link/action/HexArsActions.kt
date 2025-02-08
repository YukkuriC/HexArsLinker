package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.hex.HexActions
import io.yukkuric.hex_ars_link.HexArsLink.halModLoc
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation

class HexArsActions {
    companion object {
        private val CACHED: MutableMap<ResourceLocation, ActionRegistryEntry> = HashMap()

        init {
            wrap("cast_spell", "ewddwqwddwawawwawwqwwaww", HexDir.EAST, OpCastMyself)
            wrap("cast_spell_as", "aqaeqwaawewaawdwawwawwqwwawwwded", HexDir.NORTH_WEST, OpCastMyself)
        }

        @JvmStatic
        fun registerActions() {
            val reg = HexActions.REGISTRY
            for ((key, value) in CACHED) Registry.register(reg, key, value)
        }

        private fun wrap(name: String, signature: String, dir: HexDir, action: Action?): ActionRegistryEntry {
            val pattern = HexPattern.fromAngles(signature, dir)
            val key = halModLoc(name)
            val entry = ActionRegistryEntry(pattern, action)
            CACHED[key] = entry
            return entry
        }
    }
}