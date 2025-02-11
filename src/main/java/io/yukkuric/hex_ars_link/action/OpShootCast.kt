package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.spell.*
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import com.hollingsworth.arsnouveau.api.spell.Spell
import com.hollingsworth.arsnouveau.api.spell.SpellContext
import com.hollingsworth.arsnouveau.api.spell.SpellResolver
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile
import io.yukkuric.hex_ars_link.HexArsLink
import io.yukkuric.hex_ars_link.iota.GlyphIota
import net.minecraft.world.phys.Vec3
import kotlin.math.max

object OpShootCast : SpellAction {
    override val argc = 3

    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>> {
        val pos = args.getVec3(0)
        ctx.assertVecInRange(pos)
        val dir = args.getVec3(1)
        val raw = args.getList(2)
        val spell = GlyphIota.grabSpell(raw)
        return Triple(
            Action(pos, dir, spell),
            MediaConstants.DUST_UNIT * spell.spellSize + MediaConstants.SHARD_UNIT,
            listOf(),
        )
    }

    data class Action(
        val pos: Vec3, val dir: Vec3,
        val spell: Spell
    ) : RenderedSpell {
        override fun cast(ctx: CastingContext) {
            val owner = ctx.caster ?: return
            val world = ctx.world
            val projSpell = Spell(MethodProjectile.INSTANCE)
            projSpell.recipe.addAll(spell.recipe)
            val resolver = SpellResolver(SpellContext(world, projSpell, owner, PlayerCaster.from(owner)))
            if (!resolver.canCast(owner)) return
            resolver.expendMana()

            val proj = EntityProjectileSpell(world, resolver)
            proj.setPos(pos)
            val velocity =
                max(0.1f, 0.75f + HexArsLink.getStatsFromResolver(resolver).accMultiplier / 2.0f) // MethodProjectile
            proj.shoot(dir.x, dir.y, dir.z, velocity, 0f)
            world.addFreshEntity(proj)
        }
    }
}