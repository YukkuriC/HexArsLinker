package io.yukkuric.hex_ars_link.hexparse

import at.petrak.hexcasting.api.casting.iota.Iota
import com.hollingsworth.arsnouveau.ArsNouveau
import com.hollingsworth.arsnouveau.api.registry.GlyphRegistry
import io.yukkuric.hex_ars_link.iota.GlyphIota
import io.yukkuric.hexparse.parsers.str2nbt.BaseConstParser.Prefix
import net.minecraft.resources.ResourceLocation

object Code2Glyph : Prefix("glyph_") {
    override fun parse(node: String): Iota {
        var path = node.substring(6)
        var namespace: String
        var colonIdx = path.indexOf(":")
        if (colonIdx >= 0) {
            namespace = path.substring(0, colonIdx)
            path = path.substring(colonIdx + 1)
        } else {
            namespace = ArsNouveau.MODID
        }

        // try match glyph
        var payload: ResourceLocation? = null
        if (!path.startsWith("glyph_")) {
            var tester = ResourceLocation.tryBuild(namespace, "glyph_$path")
            if (GlyphRegistry.getSpellPart(tester) != null) payload = tester
        }
        if (payload == null) payload = ResourceLocation.tryBuild(namespace, path)!!

        // direct iota
        return GlyphIota(payload)
    }
}