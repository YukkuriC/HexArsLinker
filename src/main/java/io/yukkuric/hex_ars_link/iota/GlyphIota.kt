package io.yukkuric.hex_ars_link.iota

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.mishaps.MishapEvalTooMuch
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import com.hollingsworth.arsnouveau.api.registry.GlyphRegistry
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart
import com.hollingsworth.arsnouveau.api.spell.Spell
import io.yukkuric.hex_ars_link.HexArsLink
import io.yukkuric.hex_ars_link.HexArsLink.halModLoc
import io.yukkuric.hex_ars_link.config.LinkConfig
import io.yukkuric.hex_ars_link.env.hex.MishapDisallowedGlyph
import io.yukkuric.hex_ars_link.hexparse.Code2Glyph
import io.yukkuric.hex_ars_link.hexparse.Glyph2Code
import io.yukkuric.hex_ars_link.tag.HALTags
import io.yukkuric.hexparse.parsers.ParserMain
import io.yukkuric.hexparse.parsers.str2nbt.ToDialect
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceLocation
import java.util.function.BiConsumer

class GlyphIota(val key: ResourceLocation) : Iota({ TYPE }) {
    constructor(spell: AbstractSpellPart) : this(spell.registryName)

    object TYPE : IotaType<GlyphIota>() {
        val INVALID = Component.translatable("hexcasting.tooltip.null_iota").withStyle { s -> s.withColor(color()) }

        val CODEC = ResourceLocation.CODEC.xmap(::GlyphIota, GlyphIota::key).fieldOf("key")
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, GlyphIota> =
            ResourceLocation.STREAM_CODEC.map(::GlyphIota, GlyphIota::key).mapStream({ it })

        override fun codec() = CODEC
        override fun streamCodec() = STREAM_CODEC

        fun validateTag(tag: GlyphIota): ResourceLocation? {
            val loc = tag.key
            if (GlyphRegistry.getSpellPart(loc) == null) return null
            return loc
        }

        fun display(tag: GlyphIota): Component {
            val key = validateTag(tag) ?: return INVALID
            if (LinkConfig.useLegacyGlyphDisplay()) return Component.translatable(GlyphRegistry.getSpellPart(key)!!.localizationKey)
                .withStyle { s -> s.withColor(color()) }
            return Component.literal("[item:$key]")
        }

        override fun color() = 0x66ccff
    }

    fun getSpellPart() = GlyphRegistry.getSpellPart(key)
    override fun isTruthy() = true
    override fun toleratesOther(other: Iota?) = other is GlyphIota && key == other.key
    override fun display() = TYPE.display(this)
    override fun hashCode() = key.hashCode()

    companion object {
        @JvmStatic
        val ID = halModLoc("glyph")

        @JvmStatic
        fun registerSelf(regFunc: BiConsumer<ResourceLocation, IotaType<*>>) {
            regFunc.accept(ID, TYPE)
            // hexparse compat
            try {
                ParserMain.AddForthParser(Code2Glyph)
                ParserMain.AddBackParser(Glyph2Code)
                ToDialect.INSTANCE.mapper["glyph_callback"] = "glyph_hex_ars_link:hex_callback"
            } catch (e: NoClassDefFoundError) {
            } catch (e: Throwable) {
                HexArsLink.LOGGER.error(e.stackTraceToString())
            }
        }

        fun grabSpell(raw: SpellList, isDelegated: Boolean = false): Spell {
            val ret = ArrayList<AbstractSpellPart>()
            for (sub in raw) {
                if (sub !is GlyphIota) continue
                val part = sub.getSpellPart() ?: continue
                var tagChecker = part.glyphItem.defaultInstance
                if (tagChecker.`is`(HALTags.Item.DISALLOWED)
                    || (isDelegated && tagChecker.`is`(HALTags.Item.DISALLOWED_DELEGATED))
                ) {
                    throw MishapDisallowedGlyph(part.localizationKey.asTranslatedComponent)
                }
                ret.add(part)
            }
            if (ret.size > LinkConfig.maxGlyphLimitForPatterns()) throw MishapEvalTooMuch()
            return Spell(ret)
        }
    }
}