package io.yukkuric.hex_ars_link.items

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.yukkuric.hex_ars_link.HexArsLink.SERVER
import io.yukkuric.hex_ars_link.items.HexArsLinkItems.DataComps.LINKER_OWNER
import net.minecraft.core.UUIDUtil
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import java.util.*

class OwnerData(var uuid: UUID?, var name: String?) {
    constructor() : this(null, null)
    constructor(u: Optional<UUID>, n: Optional<String>) : this(u.orElse(null), n.orElse(null))

    fun uuidOptional() = Optional.ofNullable(uuid)
    fun nameOptional() = Optional.ofNullable(name)

    companion object {
        @JvmStatic
        val CODEC: Codec<OwnerData> = RecordCodecBuilder.create {
            it.group(
                UUIDUtil.CODEC.optionalFieldOf("uuid").forGetter(OwnerData::uuidOptional),
                Codec.STRING.optionalFieldOf("name").forGetter(OwnerData::nameOptional)
            ).apply(it, ::OwnerData)
        }
        @JvmStatic
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, OwnerData> = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC.apply(ByteBufCodecs::optional), OwnerData::uuidOptional,
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs::optional), OwnerData::nameOptional,
            ::OwnerData
        )
        @JvmStatic
        fun from(stack: ItemStack) = stack.get(LINKER_OWNER) ?: OwnerData()
    }

    var owner: Player?
        get() {
            if (SERVER == null) return null
            return SERVER.getPlayerList().getPlayer(uuid)
        }
        set(plr) {
            if (plr == null) {
                uuid = null
                name = null
                return
            }
            uuid = plr.uuid
            name = plr.name.string
        }

    fun save(stack: ItemStack) = stack.set(LINKER_OWNER, this)
}