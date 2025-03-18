package io.yukkuric.hex_ars_link.env;

import at.petrak.hexcasting.api.casting.SpellList;
import at.petrak.hexcasting.api.casting.iota.Iota;
import net.minecraft.server.level.ServerPlayer;

import java.util.*;

public class CallbackStorage {
    private static final WeakHashMap<ServerPlayer, List<Iota>> POOL = new WeakHashMap<>();

    public static void put(ServerPlayer player, SpellList callback) {
        POOL.computeIfAbsent(player, (p) -> new ArrayList<>());
        var dump = POOL.get(player);
        callback.forEach(dump::add);
    }

    public static List<Iota> get(ServerPlayer player) {
        return POOL.get(player);
    }
}
