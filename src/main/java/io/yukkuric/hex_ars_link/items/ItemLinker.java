package io.yukkuric.hex_ars_link.items;

import at.petrak.hexcasting.api.utils.NBTHelper;
import at.petrak.hexcasting.common.items.magic.ItemMediaHolder;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ItemLinker extends ItemMediaHolder implements OwnerBinder {
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

    long getMediaRaw(Player player) {
        if (player == null) return 0;
        return (long) (ManaUtil.getCurrentMana(player) * getConvertRatio());
    }

    long getMaxMediaRaw(Player player) {
        if (player == null) return 0;
        return (long) (ManaUtil.getMaxMana(player) * getConvertRatio());
    }

    @Override
    public void setMedia(ItemStack stack, long i) {
        var player = getOwner(stack);
        // forge only
        CapabilityRegistry.getMana(player).ifPresent(mana -> mana.setMana(((double) i) / getConvertRatio()));
        super.setMedia(stack, i);
    }

    // item methods
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        return OwnerBinder.super.use(world, player, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level.isClientSide) return;
        var owner = getOwner(stack);
        var rawMedia = getMediaRaw(owner);
        var rawCap = getMaxMediaRaw(owner);
        if (rawMedia != super.getMedia(stack)) super.setMedia(stack, rawMedia);
        if (rawCap != super.getMaxMedia(stack)) NBTHelper.putLong(stack, TAG_MAX_MEDIA, rawCap);
        if (owner != null) NBTHelper.putString(stack, "name", owner.getName().getString());
        else NBTHelper.remove(stack, "name");
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.hex_ars_link.linker.ratio", getConvertRatio()).withStyle(ChatFormatting.GRAY));
        appendOwnerTooltip(pStack, pTooltipComponents);
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public int getConsumptionPriority(ItemStack stack) {
        return super.getConsumptionPriority(stack) + 1;
    }
}
