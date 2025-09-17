package io.yukkuric.hex_ars_link.mixin_interface;

import at.petrak.hexcasting.api.addldata.ADHexHolder;
import at.petrak.hexcasting.api.spell.iota.Iota;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public interface HarnessCallbackMarker {
    void setForceDrainInventory();

    enum NoobHolder implements ADHexHolder {
        INSTANCE;

        // force enabled
        public boolean canDrawMediaFromInventory() {
            return true;
        }

        // whatever
        public boolean hasHex() {
            return false;
        }
        public List<Iota> getHex(ServerLevel serverLevel) {
            return List.of();
        }
        public void writeHex(List<Iota> list, int i) {
        }
        public void clearHex() {
        }
    }
}
