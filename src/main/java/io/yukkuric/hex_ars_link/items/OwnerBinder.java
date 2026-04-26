package io.yukkuric.hex_ars_link.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface OwnerBinder {
    @Nullable
    default Player getOwner(ItemStack stack) {
        return OwnerData.from(stack).getOwner();
    }

    default void setOwner(ItemStack stack, Player player) {
        var data = OwnerData.from(stack);
        data.setOwner(player);
        data.save(stack);
    }

    default InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (world.isClientSide) return InteractionResultHolder.pass(stack);
        if (player.isShiftKeyDown()) setOwner(stack, null);
        else setOwner(stack, player);

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    default void appendOwnerTooltip(ItemStack stack, List<Component> tooltips) {
        var playerName = OwnerData.from(stack).getName();
        if (playerName == null || playerName.isEmpty()) {
            tooltips.add(Component.translatable("hex_ars_link.tooltip.owner.null"));
        } else {
            var nameComp = Component.literal(playerName).withStyle(ChatFormatting.GOLD);
            tooltips.add(Component.translatable("hex_ars_link.tooltip.owner", nameComp));
        }
    }
}
