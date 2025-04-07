package voltaicapi.registers;

import com.google.common.collect.Sets;

import voltaicapi.VoltaicAPI;
import voltaicapi.api.multiblock.assemblybased.TileMultiblockSlave;
import voltaicapi.api.multiblock.subnodebased.TileMultiSubnode;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VoltaicAPITiles {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, VoltaicAPI.ID);

	public static final DeferredHolder<BlockEntityType<?>,BlockEntityType<TileMultiSubnode>> TILE_MULTI = BLOCK_ENTITY_TYPES.register("multisubnode", () -> new BlockEntityType<>(TileMultiSubnode::new, Sets.newHashSet(VoltaicAPIBlocks.BLOCK_MULTISUBNODE.get()), null));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileMultiblockSlave>> TILE_MULTIBLOCK_SLAVE = BLOCK_ENTITY_TYPES.register("multiblockslave", () -> new BlockEntityType<>(TileMultiblockSlave::new, Sets.newHashSet(VoltaicAPIBlocks.BLOCK_MULTIBLOCK_SLAVE.get()), null));

}
