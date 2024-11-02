package io.yukkuric.hex_ars_link.items;

import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.common.items.magic.ItemMediaHolder;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemLinker extends ItemMediaHolder implements OwnerBinder {
    public ItemLinker(int convertRatio, Properties pProperties) {
        super(pProperties);
        _convertRatio = convertRatio;
    }

    public ItemLinker(Properties pProperties) {
        super(pProperties);
    }

    int _convertRatio = MediaConstants.DUST_UNIT / 10;

    public int getConvertRatio() {
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
        var player = getOwner(stack);
        if (player == null) return 0;
        return (ManaUtil.getMaxMana(player) * getConvertRatio());
    }

    @Override
    public void setMedia(ItemStack stack, int i) {
        var player = getOwner(stack);
        // forge only
        CapabilityRegistry.getMana(player).ifPresent(mana -> mana.setMana(((double) i) / getConvertRatio()));
    }

    // item methods
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        return OwnerBinder.super.use(world, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        appendOwnerTooltip(pStack, pTooltipComponents);
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
