package io.yukkuric.hex_ars_link.items;

import at.petrak.hexcasting.common.items.magic.ItemMediaHolder;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ItemLinker extends ItemMediaHolder implements OwnerBinder {
    static final String KEY_MAXMEDIA = "maxMedia";

    public ItemLinker(Supplier<Double> convertRatioGetter, Properties pProperties) {
        super(pProperties);
        _convertRatioGetter = convertRatioGetter;
    }

    double _convertRatio = -1;
    Supplier<Double> _convertRatioGetter;

    public double getConvertRatio() {
        if (_convertRatio < 0) _convertRatio = _convertRatioGetter.get();
        return _convertRatio;
    }

    // media props
    @Override
    public boolean canProvideMedia(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean canRecharge(ItemStack itemStack) {
        return false;
    }

    @Override
    public int getMedia(ItemStack stack) {
        var player = getOwner(stack);
        if (player == null) return 0;
        return (int) (ManaUtil.getCurrentMana(player) * getConvertRatio());
    }

    @Override
    public int getMaxMedia(ItemStack stack) {
        var maxMedia = stack.getOrCreateTag().getInt(KEY_MAXMEDIA);
        if (maxMedia <= 0) maxMedia = getMedia(stack);
        return maxMedia;
    }

    int getMaxMediaRaw(Player player) {
        if (player == null) return 0;
        return (int) (ManaUtil.getMaxMana(player) * getConvertRatio());
    }

    void refreshCapCache(Player player, ItemStack stack) {
        stack.getOrCreateTag().putInt(KEY_MAXMEDIA, getMaxMediaRaw(player));
    }

    @Override
    public synchronized void setMedia(ItemStack stack, int i) {
        var player = getOwner(stack);
        // forge only
        CapabilityRegistry.getMana(player).ifPresent(mana -> mana.setMana(((double) i) / getConvertRatio()));
        refreshCapCache(player, stack);
    }

    // item methods
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        var res = OwnerBinder.super.use(world, player, hand);
        var stack = player.getItemInHand(hand);
        refreshCapCache(player, stack);
        return res;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.hex_ars_link.linker.ratio", getConvertRatio()).withStyle(ChatFormatting.GRAY));
        appendOwnerTooltip(pStack, pTooltipComponents);
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
