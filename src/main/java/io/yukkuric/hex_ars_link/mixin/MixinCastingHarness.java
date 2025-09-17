package io.yukkuric.hex_ars_link.mixin;


import at.petrak.hexcasting.api.addldata.ADHexHolder;
import at.petrak.hexcasting.api.spell.casting.CastingHarness;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import shadowed.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.yukkuric.hex_ars_link.mixin_interface.HarnessCallbackMarker;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import shadowed.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

@Mixin(CastingHarness.class)
public class MixinCastingHarness implements HarnessCallbackMarker {
    private boolean forceDrainInventory = false;

    @Override
    public void setForceDrainInventory() {
        forceDrainInventory = true;
    }

    @WrapOperation(method = "withdrawMedia", at = @At(value = "INVOKE", target = "Lat/petrak/hexcasting/xplat/IXplatAbstractions;findHexHolder(Lnet/minecraft/world/item/ItemStack;)Lat/petrak/hexcasting/api/addldata/ADHexHolder;"))
    ADHexHolder replaceWithFakeArtifact(IXplatAbstractions inst, ItemStack stack, Operation<ADHexHolder> original) {
        if (!forceDrainInventory) return original.call(inst, stack);
        return NoobHolder.INSTANCE;
    }
}
