package io.yukkuric.hex_ars_link.glyph;

import at.petrak.hexcasting.api.casting.iota.*;
import com.hollingsworth.arsnouveau.api.spell.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class HexCallbackSpellPart extends AbstractEffect {
    public static final String ID = "hex_callback";
    public static final String DESCRIP = "HexCallback";
    public HexCallbackSpellPart() {
        super(ID, DESCRIP);
    }

    @Override
    protected int getDefaultManaCost() {
        return 50;
    }
    @Override
    protected @NotNull Set<AbstractAugment> getCompatibleAugments() {
        return Set.of();
    }


    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        if (!(shooter instanceof ServerPlayer player)) return;
        this.onCastWithInitStack(new EntityIota(rayTraceResult.getEntity()), player);
    }
    @Override
    public void onResolveBlock(BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        if (!(shooter instanceof ServerPlayer player)) return;
        this.onCastWithInitStack(new Vec3Iota(rayTraceResult.getBlockPos().getCenter()), player);
    }
    public void onCastWithInitStack(Iota init, ServerPlayer player) {
        var env = new GlyphCallbackCastEnv(player, InteractionHand.MAIN_HAND);
    }
}
