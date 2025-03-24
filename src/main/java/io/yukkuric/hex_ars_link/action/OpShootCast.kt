package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import com.hollingsworth.arsnouveau.api.spell.Spell
import com.hollingsworth.arsnouveau.api.spell.SpellContext
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile
import io.yukkuric.hex_ars_link.env.ars.PatternResolver
import io.yukkuric.hex_ars_link.iota.GlyphIota
import net.minecraft.world.phys.Vec3
import kotlin.math.max

object OpShootCast : SpellAction {
    override val argc = 3

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val pos = args.getVec3(0)
        env.assertVecInRange(pos)
        val dir = args.getVec3(1)
        val raw = args.getList(2)
        val spell = GlyphIota.grabSpell(raw)
        val owner = env.caster
        val world = env.world
        val projSpell = Spell(MethodProjectile.INSTANCE)
        projSpell.recipe.addAll(spell.recipe)
        val resolver = PatternResolver(
            SpellContext(world, projSpell, owner, PlayerCaster.from(owner)),
            env, MethodProjectile.INSTANCE.castingCost
        )
        return SpellAction.Result(
            Action(pos, dir, resolver),
            MediaConstants.DUST_UNIT * spell.spellSize + MediaConstants.SHARD_UNIT + resolver.mediaCost,
            listOf(),
            1 + spell.spellSize.toLong()
        )
    }

    data class Action(
        val pos: Vec3, val dir: Vec3,
        val resolver: PatternResolver,
    ) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            val owner = env.caster ?: return
            val world = env.world
            if (!resolver.canCast(owner)) return
            resolver.expendMana()

            val proj = EntityProjectileSpell(world, resolver)
            proj.setPos(pos)
            val velocity = max(0.1f, 0.75f + resolver.castStats.accMultiplier / 2.0f) // MethodProjectile
            proj.shoot(dir.x, dir.y, dir.z, velocity, 0f)
            world.addFreshEntity(proj)
        }
    }
}