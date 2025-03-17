package io.yukkuric.hex_ars_link.glyph;

import at.petrak.hexcasting.api.casting.eval.env.StaffCastEnv;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;

public class GlyphCallbackCastEnv extends StaffCastEnv {
    public GlyphCallbackCastEnv(ServerPlayer caster, InteractionHand castingHand) {
        super(caster, castingHand);
    }
}
