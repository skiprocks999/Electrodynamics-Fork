package voltaicapi.prefab.properties;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.mojang.serialization.Codec;
import voltaicapi.api.gas.GasStack;
import voltaicapi.prefab.utilities.CodecUtils;
import voltaicapi.prefab.utilities.object.Location;
import voltaicapi.prefab.utilities.object.TransferPack;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidStack;

public class PropertyTypes {

    public static final PropertyType<Byte, ByteBuf> BYTE = new PropertyType<>(
            //
            Objects::equals,
            //
            ByteBufCodecs.BYTE,
            //
            Codec.BYTE
            //
    );

    public static final PropertyType<Boolean, ByteBuf> BOOLEAN = new PropertyType<>(
            //
            Objects::equals,
            //
            ByteBufCodecs.BOOL,
            //
            Codec.BOOL
            //
    );

    public static final PropertyType<Integer, ByteBuf> INTEGER = new PropertyType<>(
            //
            Objects::equals,
            //
            ByteBufCodecs.INT,
            //
            Codec.INT
            //
    );

    public static final PropertyType<Long, ByteBuf> LONG = new PropertyType<>(
            //
            Objects::equals,
            //
            ByteBufCodecs.VAR_LONG,
            //
            Codec.LONG
            //
    );

    public static final PropertyType<Float, ByteBuf> FLOAT = new PropertyType<>(
            //
            Objects::equals,
            //
            ByteBufCodecs.FLOAT,
            //
            Codec.FLOAT
            //
    );

    public static final PropertyType<Double, ByteBuf> DOUBLE = new PropertyType<>(
            //
            Objects::equals,
            //
            ByteBufCodecs.DOUBLE,
            //
            Codec.DOUBLE
            //
    );

    public static final PropertyType<UUID, ByteBuf> UUID = new PropertyType<>(
            //
            Objects::equals,
            //
            UUIDUtil.STREAM_CODEC,
            //
            UUIDUtil.CODEC
            //
    );

    public static final PropertyType<CompoundTag, ByteBuf> COMPOUND_TAG = new PropertyType<>(
            //
            Objects::equals,
            //
            ByteBufCodecs.COMPOUND_TAG,
            //
            CompoundTag.CODEC
            //
    );

    public static final PropertyType<BlockPos, ByteBuf> BLOCK_POS = new PropertyType<>(
            //
            Objects::equals,
            //
            BlockPos.STREAM_CODEC,
            //
            BlockPos.CODEC
            //
    );

    public static final PropertyType<NonNullList<ItemStack>, RegistryFriendlyByteBuf> INVENTORY_ITEMS = new PropertyType<>(
            //
            (thisList, otherList) -> {
                if (thisList.size() != otherList.size()) {
                    return false;
                }
                ItemStack a, b;
                for (int i = 0; i < thisList.size(); i++) {
                    a = thisList.get(i);
                    b = otherList.get(i);
                    if (!ItemStack.isSameItemSameComponents(a, b)) {
                        return false;
                    }
                }
                return true;
            },
            //
            CodecUtils.INVENTORYITEMS_STREAM_CODEC,
            //
            NonNullList.codecOf(ItemStack.OPTIONAL_CODEC)
            //
    );

    public static final PropertyType<FluidStack, RegistryFriendlyByteBuf> FLUID_STACK = new PropertyType<>(
            //
            (thisStack, otherStack) -> {
                if (thisStack.getAmount() != otherStack.getAmount()) {
                    return false;
                }
                return thisStack.getFluid().isSame(otherStack.getFluid());
            },
            //
            FluidStack.OPTIONAL_STREAM_CODEC,
            //
            FluidStack.OPTIONAL_CODEC
            //
    );

    public static final PropertyType<List<BlockPos>, ByteBuf> BLOCK_POS_LIST = new PropertyType<>(
            //
            (thisList, otherList) -> {
                if (thisList.size() != otherList.size()) {
                    return false;
                }
                BlockPos a, b;
                for (int i = 0; i < thisList.size(); i++) {
                    a = thisList.get(i);
                    b = otherList.get(i);
                    if (!a.equals(b)) {
                        return false;
                    }
                }
                return true;
            },
            //
            CodecUtils.BLOCKPOS_LIST_STREAMCODEC,
            //
            BlockPos.CODEC.listOf()
            //
    );

    public static final PropertyType<Location, FriendlyByteBuf> LOCATION = new PropertyType<>(
            //
            Objects::equals,
            //
            Location.STREAM_CODEC,
            //
            Location.CODEC
            //
    );

    public static final PropertyType<GasStack, RegistryFriendlyByteBuf> GAS_STACK = new PropertyType<>(
            //
            Objects::equals,
            //
            GasStack.STREAM_CODEC,
            //
            GasStack.CODEC
            //
    );

    public static final PropertyType<ItemStack, RegistryFriendlyByteBuf> ITEM_STACK = new PropertyType<>(
            //
            ItemStack::matches,
            //
            ItemStack.OPTIONAL_STREAM_CODEC,
            //
            ItemStack.OPTIONAL_CODEC
            //
    );

    public static final PropertyType<Block, RegistryFriendlyByteBuf> BLOCK = new PropertyType<>(
            //
            Objects::equals,
            //
            ByteBufCodecs.registry(Registries.BLOCK),
            //
            BuiltInRegistries.BLOCK.byNameCodec()
            //
    );

    public static final PropertyType<BlockState, ByteBuf> BLOCK_STATE = new PropertyType<>(
            //
            Objects::equals,
            //
            ByteBufCodecs.fromCodec(BlockState.CODEC),
            //
            BlockState.CODEC
            //
    );

    public static final PropertyType<TransferPack, FriendlyByteBuf> TRANSFER_PACK = new PropertyType<>(
            //
            Objects::equals,
            //
            TransferPack.STREAM_CODEC,
            //
            TransferPack.CODEC
            //
    );

    public static final PropertyType<ResourceLocation, ByteBuf> RESOURCE_LOCATION = new PropertyType<>(
            //
            Objects::equals,
            //
            ResourceLocation.STREAM_CODEC,
            //
            ResourceLocation.CODEC
            //
    );

    public static final PropertyType<Vec3, ByteBuf> VEC3 = new PropertyType<>(
            //
            Objects::equals,
            //
            CodecUtils.VEC3_STREAM_CODEC,
            //
            Vec3.CODEC
            //
    );


}
