package io.yukkuric.hex_ars_link.mixin;

import at.petrak.hexcasting.api.spell.casting.CastingContext;
import io.yukkuric.hex_ars_link.env.hex.GlyphCallbackCastEnv;
import io.yukkuric.hex_ars_link.env.hex.GlyphCallbackCastEnvContext;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CastingContext.class)
public class MixinCastingContext implements GlyphCallbackCastEnvContext {
    @Override
    public GlyphCallbackCastEnv getCallbackEnv() {
        return callbackEnv;
    }
    @Override
    public void setCallbackEnv(GlyphCallbackCastEnv env) {
        callbackEnv = env;
    }
    private GlyphCallbackCastEnv callbackEnv;

    @Inject(method = "isVecInRange", at = @At("HEAD"), remap = false, cancellable = true)
    void handleCallbackAmbit(Vec3 vec, CallbackInfoReturnable<Boolean> cir) {
        var env = GlyphCallbackCastEnvContext.fromContext(CastingContext.class.cast(this));
        if (env == null) return;
        var overrideRes = env.isVecInRangeEnvironment(vec);
        if (overrideRes == GlyphCallbackCastEnv.ResVecInRange.PASS) return;
        cir.setReturnValue(overrideRes == GlyphCallbackCastEnv.ResVecInRange.TRUE);
        cir.cancel();
    }
}
