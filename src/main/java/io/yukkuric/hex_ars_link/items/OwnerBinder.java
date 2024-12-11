package io.yukkuric.hex_ars_link.items;

import at.petrak.hexcasting.api.utils.NBTHelper;
import com.hollingsworth.arsnouveau.api.nbt.ItemstackData;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

import static io.yukkuric.hex_ars_link.HexArsLink.SERVER;

public interface OwnerBinder {
    @Nullable
    default Player getOwner(ItemStack stack) {
        return new OwnerData(stack).getOwner();
    }

    default void setOwner(ItemStack stack, Player player) {
        new OwnerData(stack).setOwner(player);
    }

    default InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (world.isClientSide) return InteractionResultHolder.pass(stack);
        if (player.isShiftKeyDown()) setOwner(stack, null);
        else setOwner(stack, player);

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    default void appendOwnerTooltip(ItemStack stack, List<Component> tooltips) {
        var playerName = NBTHelper.getString(stack, "name");
        if (playerName == null || playerName.isEmpty()) {
            tooltips.add(Component.translatable("hex_ars_link.tooltip.owner.null"));
        } else {
            var nameComp = Component.literal(playerName).withStyle(ChatFormatting.GOLD);
            tooltips.add(Component.translatable("hex_ars_link.tooltip.owner", nameComp));
        }
    }

    class OwnerData extends ItemstackData {
        private UUID ownerUUID;

        public OwnerData(ItemStack stack) {
            super(stack);
            var tag = getItemTag(stack);
            try {
                ownerUUID = tag.getUUID("owner");
            } catch (Exception e) {
                ownerUUID = new UUID(114, 514);
                writeItem();
            }
        }

        @Override
        public String getTagString() {
            return "hex_staff";
        }

        @Override
        public void writeToNBT(CompoundTag tag) {
            tag.putUUID("owner", ownerUUID);
        }

        public ServerPlayer getOwner() {
            if (SERVER == null) return null;
            return SERVER.getPlayerList().getPlayer(ownerUUID);
        }

        public void setOwner(@Nullable Player player) {
            if (player == null) this.ownerUUID = new UUID(1919, 810);
            else this.ownerUUID = player.getUUID();
            writeItem();
        }
    }
}
