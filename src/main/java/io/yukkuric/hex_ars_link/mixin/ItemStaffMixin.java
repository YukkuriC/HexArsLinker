package io.yukkuric.hex_ars_link.mixin;

import at.petrak.hexcasting.api.item.MediaHolderItem;
import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.common.items.ItemStaff;
import io.yukkuric.hex_ars_link.items.HexArsLinkItems;
import io.yukkuric.hex_ars_link.items.ItemLinker;
import io.yukkuric.hex_ars_link.items.OwnerBinder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(ItemStaff.class)
public class ItemStaffMixin extends Item implements OwnerBinder, MediaHolderItem {
    public ItemStaffMixin(Properties properties) {
        super(properties);
    }

    // link weakest item
    private static ItemLinker LINKER_WEAK = new ItemLinker(MediaConstants.DUST_UNIT / 100, HexArsLinkItems.tool());

    @Override
    public int getMedia(ItemStack stack) {
        return LINKER_WEAK.getMedia(stack);
    }

    @Override
    public int getMaxMedia(ItemStack stack) {
        return LINKER_WEAK.getMaxMedia(stack);
    }

    @Override
    public void setMedia(ItemStack stack, int i) {
        LINKER_WEAK.setMedia(stack, i);
    }

    @Override
    public boolean canProvideMedia(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canRecharge(ItemStack stack) {
        return false;
    }

    // TODO use mixin inject
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
