package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.PatternRegistry
import at.petrak.hexcasting.api.spell.Action
import at.petrak.hexcasting.api.spell.math.HexDir
import at.petrak.hexcasting.api.spell.math.HexPattern
import io.yukkuric.hex_ars_link.HexArsLink.halModLoc
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation

class HexArsActions {
    companion object {
        init {
            wrap("cast_spell", "qwaawewaawdwawwawwqwwaww", HexDir.EAST, OpCastMyself)
            wrap("cast_spell_as", "aqaeqwaawewaawdwawwawwqwwawwwded", HexDir.NORTH_WEST, OpCastFromPlayer)
            wrap("cast_spell_shoot", "qaeaqewqwaawewaawdwawwawwqwwaww", HexDir.NORTH_WEST, OpShootCast, true)
            wrap("read_glyphs", "qwaawewaawdwaqwqqqwq", HexDir.EAST, OpReadGlyphs)
        }

        @JvmStatic
        fun registerActions() {
            // keep ref
        }

        private fun wrap(name: String, signature: String, dir: HexDir, action: Action, isGreat: Boolean = false) {
            val pattern = HexPattern.fromAngles(signature, dir)
            val key = halModLoc(name)
            PatternRegistry.mapPattern(pattern, key, action, isGreat)
        }
    }
}