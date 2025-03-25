package io.yukkuric.hex_ars_link.iota

import at.petrak.hexcasting.api.spell.SpellList
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.iota.IotaType
import at.petrak.hexcasting.api.utils.downcast
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart
import com.hollingsworth.arsnouveau.api.spell.Spell
import io.yukkuric.hex_ars_link.HexArsLink
import io.yukkuric.hex_ars_link.HexArsLink.halModLoc
import io.yukkuric.hex_ars_link.action.spell.PatternCaster
import io.yukkuric.hex_ars_link.hexparse.Code2Glyph
import io.yukkuric.hex_ars_link.hexparse.Glyph2Code
import io.yukkuric.hexparse.parsers.ParserMain
import net.minecraft.core.Registry
import net.minecraft.nbt.StringTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel

class GlyphIota(val key: ResourceLocation) : Iota(TYPE, key) {
    constructor(spell: AbstractSpellPart) : this(spell.registryName)

    object TYPE : IotaType<GlyphIota>() {
        val INVALID = Component.translatable("hexcasting.tooltip.null_iota").withStyle { s -> s.withColor(color()) }

        fun validateTag(tag: Tag?): ResourceLocation? {
            val key = tag?.downcast(StringTag.TYPE)?.asString ?: return null
            val loc = ResourceLocation(key)
            if (ArsNouveauAPI.getInstance().getSpellPart(loc) == null) return null
            return loc
        }

        override fun deserialize(tag: Tag?, p1: ServerLevel?): GlyphIota? {
            val key = validateTag(tag) ?: return null
            return GlyphIota(key)
        }

        override fun display(tag: Tag?): Component {
            val key = validateTag(tag) ?: return INVALID
            return Component.translatable(ArsNouveauAPI.getInstance().getSpellPart(key)!!.localizationKey)
                .withStyle { s -> s.withColor(color()) }
//            return Component.literal("[item:$key]")
        }

        override fun color() = 0x66ccff
    }

    fun getSpellPart() = ArsNouveauAPI.getInstance().getSpellPart(key)
    override fun isTruthy() = true
    override fun toleratesOther(other: Iota?) = other is GlyphIota && key == other.key
    override fun serialize() = StringTag.valueOf(key.toString())

    companion object {
        @JvmStatic
        val ID = halModLoc("glyph")

        @JvmStatic
        fun registerSelf() {
            Registry.register(HexIotaTypes.REGISTRY, ID, TYPE)
            // hexparse compat
            try {
                ParserMain.AddForthParser(Code2Glyph)
                ParserMain.AddBackParser(Glyph2Code)
            } catch (e: NoClassDefFoundError) {
            } catch (e: Throwable) {
                HexArsLink.LOGGER.error(e.stackTraceToString())
            }
        }

        fun grabSpell(raw: SpellList): Spell {
            val ret = Spell()
            for (sub in raw) {
                if (sub !is GlyphIota) continue
                val part = sub.getSpellPart() ?: continue
                ret.add(part)
            }
            return ret
        }
    }
}