package io.yukkuric.hex_ars_link.env.hex;

import at.petrak.hexcasting.api.spell.casting.CastingContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import net.minecraft.world.phys.Vec3;

public interface GlyphCallbackCastEnvContext {
    GlyphCallbackCastEnv getCallbackEnv();
    void setCallbackEnv(GlyphCallbackCastEnv env);

    static GlyphCallbackCastEnv fromContext(CastingContext ctx) {
        return GlyphCallbackCastEnvContext.class.cast(ctx).getCallbackEnv();
    }

    static GlyphCallbackCastEnv create(CastingContext ctx, Vec3 hitPos, SpellResolver resolver) {
        var res = new GlyphCallbackCastEnv(hitPos, resolver);
        GlyphCallbackCastEnvContext.class.cast(ctx).setCallbackEnv(res);
        return res;
    }
}
