package io.yukkuric.hex_ars_link.action

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.EntityIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.ktxt.UseOnContext
import com.hollingsworth.arsnouveau.api.spell.Spell
import com.hollingsworth.arsnouveau.api.spell.SpellContext
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch
import com.mojang.datafixers.util.Either
import io.yukkuric.hex_ars_link.env.ars.PatternCaster
import io.yukkuric.hex_ars_link.env.ars.PatternResolver
import io.yukkuric.hex_ars_link.iota.GlyphIota
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.Vec3

object OpTouchCast : SpellAction {
    override val argc = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val targetRaw = args.get(0)
        val pos: Vec3
        val target: Either<Entity, Vec3> = if (targetRaw is EntityIota) {
            val inner = targetRaw.entity
            pos = inner.position()
            Either.left(inner)
        } else if (targetRaw is Vec3Iota) {
            pos = targetRaw.vec3
            Either.right(pos)
        } else throw MishapInvalidIota.ofType(targetRaw, 0, "entity_or_vector")
        env.assertVecInRange(pos)

        val spell = GlyphIota.grabSpell(args.getList(1))
        val touchSpell = Spell(MethodTouch.INSTANCE)
        touchSpell.recipe.addAll(spell.recipe)
        val owner = env.caster
        val world = env.world
        val resolver = PatternResolver(
            SpellContext(world, touchSpell, owner, PlayerCaster.from(owner)),
            env, MethodTouch.INSTANCE.castingCost
        )
        return SpellAction.Result(
            Action(target, resolver),
            MediaConstants.DUST_UNIT * spell.spellSize + MediaConstants.SHARD_UNIT + resolver.mediaCost,
            listOf(),
            1 + spell.spellSize.toLong()
        )
    }

    data class Action(
        val target: Either<Entity, Vec3>,
        val resolver: PatternResolver,
    ) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            if (env.caster == null) return
            target.ifLeft { e -> resolver.onCastOnEntity(PatternCaster.CASTER_ITEM.value, e, env.castingHand) }
                .ifRight { p ->
                    resolver.onCastOnBlock(
                        UseOnContext(
                            env.world, env.caster, env.castingHand, PatternCaster.CASTER_ITEM.value,
                            BlockHitResult(p, Direction.UP, BlockPos.containing(p), false)
                        )
                    )
                }
        }
    }
}