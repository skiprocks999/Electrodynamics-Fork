package voltaicapi.datagen.client;

import voltaicapi.VoltaicAPI;
import voltaicapi.datagen.utils.client.BaseBlockstateProvider;
import voltaicapi.registers.VoltaicAPIBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class VoltaicAPIBlockStateProvider extends BaseBlockstateProvider {

    public VoltaicAPIBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, exFileHelper, VoltaicAPI.ID);
    }

    @Override
    protected void registerStatesAndModels() {
        airBlock(VoltaicAPIBlocks.BLOCK_MULTISUBNODE, "block/multisubnode", false);
        slaveNode(VoltaicAPIBlocks.BLOCK_MULTIBLOCK_SLAVE.get(), "block/multisubnode");
    }
}
