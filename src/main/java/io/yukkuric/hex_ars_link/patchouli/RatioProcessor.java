package io.yukkuric.hex_ars_link.patchouli;

import at.petrak.hexcasting.api.misc.MediaConstants;
import io.yukkuric.hex_ars_link.items.HexArsLinkItems;
import io.yukkuric.hex_ars_link.items.ItemLinker;
import net.minecraft.network.chat.Component;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.ArrayList;

public class RatioProcessor implements IComponentProcessor {
    static ItemLinker[] TARGETS = {HexArsLinkItems.LINKER_BASE, HexArsLinkItems.LINKER_ADVANCED, HexArsLinkItems.LINKER_GREAT};

    @Override
    public void setup(IVariableProvider iVariableProvider) {

    }

    @Override
    public IVariable process(String s) {
        var linkId = 1;
        var sb = new StringBuilder();
        for (var item : TARGETS) {
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
