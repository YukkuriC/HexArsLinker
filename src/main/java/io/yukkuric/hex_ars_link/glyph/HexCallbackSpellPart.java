package io.yukkuric.hex_ars_link.glyph;

import at.petrak.hexcasting.api.spell.casting.CastingContext;
import at.petrak.hexcasting.api.spell.casting.CastingHarness;
import at.petrak.hexcasting.api.spell.iota.*;
import com.hollingsworth.arsnouveau.api.spell.*;
import io.yukkuric.hex_ars_link.HexArsLink;
import io.yukkuric.hex_ars_link.env.hex.CallbackStorage;
import io.yukkuric.hex_ars_link.env.hex.GlyphCallbackCastEnvContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

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
    public int getDefaultManaCost() {
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
        var pos = Vec3.atCenterOf(rayTraceResult.getBlockPos());
        this.onCastWithInitStack(resolver, new Vec3Iota(pos), player, pos);
    }
    public void onCastWithInitStack(SpellResolver resolver, Iota init, ServerPlayer player, Vec3 pos) {
        var spell = CallbackStorage.Get(player);
        if (spell == null) return;
        var ctxRaw = new CastingContext(player, InteractionHand.MAIN_HAND, CastingContext.CastSource.STAFF);
        GlyphCallbackCastEnvContext.create(ctxRaw, pos, resolver);
        var vm = new CastingHarness(ctxRaw);
        vm.setStack(new ArrayList<>() {{
            add(init);
        }});
        vm.executeIotas(spell, ctxRaw.getWorld());
    }
}
