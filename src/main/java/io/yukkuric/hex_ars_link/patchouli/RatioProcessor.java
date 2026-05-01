package io.yukkuric.hex_ars_link.patchouli;

import at.petrak.hexcasting.api.misc.MediaConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.*;

import static io.yukkuric.hex_ars_link.items.HexArsLinkItems.ALL_LINKERS;

public class RatioProcessor implements IComponentProcessor {
    @Override
    public void setup(Level level, IVariableProvider iVariableProvider) {
    }

    @Override
    public IVariable process(Level level, String s) {
        var linkId = 1;
        var sb = new StringBuilder();
        for (var item : ALL_LINKERS.getValue()) {
            double valMana = 1, valDust = item.getConvertRatio() / MediaConstants.DUST_UNIT;
            if (valDust > 0 && valDust < 1) {
                valMana = 1 / valDust;
                valDust = 1;
            }

            sb.append(Component.translatable(
                    "hex_ars_link.page.linkers.1.unit",
                    String.format("lv%s", linkId++),
                    item.getDescription().getString(),
                    valMana, valDust
            ).getString());
        }
        return IVariable.wrap(sb.toString());
    }
}
