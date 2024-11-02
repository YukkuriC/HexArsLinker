package io.yukkuric.hex_ars_link.mixin;

import at.petrak.hexcasting.common.items.ItemStaff;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStaff.class)
public class ItemStaffMixin extends Item {
    public ItemStaffMixin(Properties properties) {
        super(properties);
    }

    // TODO link weakest item
}
