package io.yukkuric.hex_ars_link.env.hex;

import at.petrak.hexcasting.api.casting.SpellList;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CallbackStorage extends SavedData {
    static final String SAVENAME = "hex_ars_link.callbacks";
    static final String NBT_K = "keys", NBT_V = "values", NBT_N = "n";
    private final Map<UUID, Tag> pool = new HashMap<>();
    private static final SavedData.Factory<CallbackStorage> FACTORY = new SavedData.Factory<>(CallbackStorage::new, CallbackStorage::new);

    public static CallbackStorage getInstance(ServerLevel level) {
        level = level.getServer().overworld();
        var ds = level.getDataStorage();
        return ds.computeIfAbsent(FACTORY, SAVENAME);
    }
    public static void Put(ServerPlayer player, SpellList callback) {
        getInstance(player.serverLevel()).put(player, callback);
    }
    public static @Nullable SpellList Get(ServerPlayer player) {
        return getInstance(player.serverLevel()).get(player);
    }

    public void put(ServerPlayer player, SpellList callback) {
        var uuid = player.getUUID();
        var result = SpellList.getCODEC().encodeStart(NbtOps.INSTANCE, callback);
        result.ifSuccess(tag -> {
            pool.put(uuid, tag);
            setDirty();
        });
    }
    public @Nullable SpellList get(ServerPlayer player) {
        var raw = pool.get(player.getUUID());
        if (raw == null) return null;
        var result = SpellList.getCODEC().decode(NbtOps.INSTANCE, raw);
        if (result.isSuccess()) {
            return result.getOrThrow().getFirst();
        }
        return null;
    }

    public CallbackStorage() {
    }
    public CallbackStorage(CompoundTag toLoad, HolderLookup.Provider provider) {
        this();
        try {
            var n = toLoad.getInt(NBT_N);
            if (n <= 0) return;
            var keys = toLoad.getList(NBT_K, Tag.TAG_INT_ARRAY);
            var values = toLoad.getList(NBT_V, Tag.TAG_LIST);
            for (var i = 0; i < n; i++) {
                var uuid = NbtUtils.loadUUID(keys.get(i));
                var list = values.get(i);
                pool.put(uuid, list);
            }
        } catch (Throwable e) {
        }
    }

    @Override
    public CompoundTag save(CompoundTag toDump, HolderLookup.Provider provider) {
        toDump.putInt(NBT_N, pool.size());
        ListTag keys = new ListTag(),
                values = new ListTag();
        for (var pair : pool.entrySet()) {
            keys.add(NbtUtils.createUUID(pair.getKey()));
            values.add(pair.getValue());
        }
        toDump.put(NBT_K, keys);
        toDump.put(NBT_V, values);
        return toDump;
    }
}
