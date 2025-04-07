package voltaicapi.registers;

import voltaicapi.VoltaicAPI;
import voltaicapi.common.block.BlockMultiSubnode;
import voltaicapi.common.block.BlockMultiblockSlave;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VoltaicAPIBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, VoltaicAPI.ID);

    public static final DeferredHolder<Block, BlockMultiSubnode> BLOCK_MULTISUBNODE = BLOCKS.register("multisubnode", BlockMultiSubnode::new);
    public static final DeferredHolder<Block, BlockMultiblockSlave> BLOCK_MULTIBLOCK_SLAVE = BLOCKS.register("multiblockslave", BlockMultiblockSlave::new);

}
