package io.yukkuric.hex_ars_link.env.ars

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import com.hollingsworth.arsnouveau.api.spell.Spell
import com.hollingsworth.arsnouveau.api.spell.SpellContext
import com.hollingsworth.arsnouveau.api.spell.SpellResolver
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster
import io.yukkuric.hex_ars_link.config.LinkConfig

class PatternResolver(ctx: SpellContext?, val env: CastingEnvironment, val omitCost: Int = 0) :
    SpellResolver(ctx) {
    val resolveCostBase: Int
        get() {
            var res = super.getResolveCost()
            res -= omitCost
            return res
        }

    val mediaCost: Int
        get() {
            val ratio = LinkConfig.ratioExtraMediaCasting()
            val retRaw = ratio * resolveCostBase
            return retRaw.coerceAtMost(Int.MAX_VALUE.toDouble()).toInt()
        }

    override fun getResolveCost(): Int {
        val res = resolveCostBase
        val reducedRaw = LinkConfig.costRatePatternMana() * res
        return reducedRaw.coerceAtMost(Int.MAX_VALUE.toDouble()).toInt()
    }

    companion object {
        fun fromEnv(env: CastingEnvironment, spell: Spell): PatternResolver {
            val owner = env.caster
            val world = env.world
            return PatternResolver(SpellContext(world, spell, owner, PlayerCaster.from(owner)), env)
        }

        fun getMediaCost(env: CastingEnvironment, spell: Spell) = fromEnv(env, spell).mediaCost
    }
}