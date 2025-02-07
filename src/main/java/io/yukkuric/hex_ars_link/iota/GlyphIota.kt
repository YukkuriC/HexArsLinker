package io.yukkuric.hex_ars_link.iota

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.utils.downcast
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import com.hollingsworth.arsnouveau.api.registry.GlyphRegistry
import com.hollingsworth.arsnouveau.api.spell.Spell
import com.hollingsworth.arsnouveau.client.particle.ParticleColor
import com.hollingsworth.arsnouveau.common.items.SpellBook
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry
import io.yukkuric.hex_ars_link.HexArsLink.halModLoc
import net.minecraft.core.Registry
import net.minecraft.nbt.StringTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.ItemStack

class GlyphIota(val key: ResourceLocation) : Iota(TYPE, key) {
    object TYPE : IotaType<GlyphIota>() {
        fun validateTag(tag: Tag?): ResourceLocation? {
            val key = tag?.downcast(StringTag.TYPE)?.asString ?: return null
            val loc = ResourceLocation(key)
            if (GlyphRegistry.getSpellPart(loc) == null) return null
            return loc
        }

        override fun deserialize(tag: Tag?, p1: ServerLevel?): GlyphIota? {
            val key = validateTag(tag) ?: return null
            return GlyphIota(key)
        }

        override fun display(tag: Tag?): Component {
            val key = validateTag(tag) ?: return NullIota.DISPLAY
            return Component.translatable(GlyphRegistry.getSpellPart(key)!!.localizationKey)
                .withStyle({ s -> s.withColor(color()) })
        }

        override fun color() = 0x66ccff
    }

    fun getSpellPart() = GlyphRegistry.getSpellPart(key)
    override fun isTruthy() = true
    override fun toleratesOther(other: Iota?) = other is GlyphIota && key == other.key
    override fun serialize() = StringTag.valueOf(key.toString())

    companion object {
        @JvmStatic
        fun registerSelf() {
            Registry.register(HexIotaTypes.REGISTRY, halModLoc("glyph"), TYPE)
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

        val CASTER = lazy {
            val stack = ItemStack(ItemsRegistry.ARCHMAGE_SPELLBOOK)
            val book = stack.item as SpellBook
            val caster = book.getSpellCaster(stack)
            caster.color = ParticleColor.fromInt(TYPE.color())
            return@lazy caster
        }
    }
}