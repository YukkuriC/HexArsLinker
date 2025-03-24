package io.yukkuric.hex_ars_link.glyph;

import at.petrak.hexcasting.api.casting.iota.*;
import com.hollingsworth.arsnouveau.api.spell.*;
import io.yukkuric.hex_ars_link.HexArsLink;
import io.yukkuric.hex_ars_link.env.CallbackStorage;
import io.yukkuric.hex_ars_link.env.GlyphCallbackCastEnv;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class HexCallbackSpellPart extends AbstractEffect {
    public static final ResourceLocation ID = new ResourceLocation(HexArsLink.MODID, "hex_callback");
    public static final String DESCRIP = "HexCallback";
    public static final HexCallbackSpellPart INSTANCE = new HexCallbackSpellPart();

    private HexCallbackSpellPart() {
        super(ID, DESCRIP);
    }

    @Override
    public String getName() {
        return getLocaleName();
    }

    @Override
    protected int getDefaultManaCost() {
        return 15;
    }
    @Override
    protected @NotNull Set<AbstractAugment> getCompatibleAugments() {
        return Set.of();
    }


    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        if (!(shooter instanceof ServerPlayer player)) return;
        var target = rayTraceResult.getEntity();
        this.onCastWithInitStack(resolver, new EntityIota(target), player, target.position());
    }
    @Override
    public void onResolveBlock(BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        if (!(shooter instanceof ServerPlayer player)) return;
        var pos = rayTraceResult.getBlockPos().getCenter();
        this.onCastWithInitStack(resolver, new Vec3Iota(pos), player, pos);
    }
    public void onCastWithInitStack(SpellResolver resolver, Iota init, ServerPlayer player, Vec3 pos) {
        var spell = CallbackStorage.Get(player);
        if (spell == null) return;
        var env = new GlyphCallbackCastEnv(player, pos, resolver);
        var vm = env.getVM(init);
        vm.queueExecuteAndWrapIotas(spell, env.getWorld());
    }
}
