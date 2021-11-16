package electrodynamics.common.block;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.multiblock.IMultiblockNode;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.tile.TileTransformer;
import electrodynamics.prefab.block.GenericMachineBlock;
import electrodynamics.prefab.utilities.UtilitiesElectricity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockMachine extends GenericMachineBlock implements IMultiblockNode {

    public static final HashSet<Subnode> advancedsolarpanelsubnodes = new HashSet<>();
    public static final HashSet<Subnode> windmillsubnodes = new HashSet<>();
    static {
	int radius = 1;
	for (int i = -radius; i <= radius; i++) {
	    for (int j = -radius; j <= radius; j++) {
		if (i == 0 && j == 0) {
		    advancedsolarpanelsubnodes.add(new Subnode(new BlockPos(i, 1, j), Shapes.block()));
		} else {
		    advancedsolarpanelsubnodes.add(new Subnode(new BlockPos(i, 1, j), Shapes.box(0, 13.0 / 16.0, 0, 1, 1, 1)));
		}
	    }
	}
	windmillsubnodes.add(new Subnode(new BlockPos(0, 1, 0), Shapes.block()));
    }

    public final SubtypeMachine machine;

    public BlockMachine(SubtypeMachine machine) {
	super(machine::createTileEntity);
	this.machine = machine;
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
	if (machine == SubtypeMachine.downgradetransformer || machine == SubtypeMachine.upgradetransformer) {
	    TileTransformer tile = (TileTransformer) worldIn.getBlockEntity(pos);
	    if (tile != null && tile.lastTransfer.getJoules() > 0) {
		UtilitiesElectricity.electrecuteEntity(entityIn, tile.lastTransfer);
	    }
	}
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
	return machine.getCustomShape() != null ? machine.getCustomShape() : super.getShape(state, worldIn, pos, context);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
	return isValidMultiblockPlacement(state, worldIn, pos, machine == SubtypeMachine.advancedsolarpanel ? advancedsolarpanelsubnodes
		: machine == SubtypeMachine.windmill ? windmillsubnodes : new HashSet<Subnode>());
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
	if (machine.showInItemGroup) {
	    items.add(new ItemStack(this));
	}
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
	return machine.getRenderType();
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	ItemStack addstack;
	switch (machine) {
	case coalgeneratorrunning:
	    addstack = getMachine(SubtypeMachine.coalgenerator);
	    break;
	case electricfurnacerunning:
	    addstack = getMachine(SubtypeMachine.electricfurnace);
	    break;
	case oxidationfurnacerunning:
	    addstack = getMachine(SubtypeMachine.oxidationfurnace);
	    break;
	case energizedalloyerrunning:
	    addstack = getMachine(SubtypeMachine.energizedalloyer);
	    break;
	default:
	    addstack = getMachine(machine);
	}

	BlockEntity tile = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
	tile.getCapability(CapabilityElectrodynamic.ELECTRODYNAMIC).ifPresent(el -> {
	    double joules = el.getJoulesStored();
	    if (joules > 0) {
		addstack.getOrCreateTag().putDouble("joules", joules);
	    }
	});
	return Arrays.asList(addstack);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {

	switch (machine) {
	case coalgeneratorrunning:
	    return 12;
	case electricfurnacerunning:
	    return 8;
	case oxidationfurnacerunning:
	    return 6;
	case energizedalloyerrunning:
	    return 10;
	default:
	    return super.getLightEmission(state, world, pos);
	}

    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
	super.setPlacedBy(worldIn, pos, state, placer, stack);
	BlockEntity tile = worldIn.getBlockEntity(pos);
	if (hasMultiBlock() && tile instanceof IMultiblockTileNode multi) {
	    multi.onNodePlaced(worldIn, pos, state, placer, stack);
	}
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	boolean update = SubtypeMachine.shouldBreakOnReplaced(state, newState);
	if (hasMultiBlock()) {
	    BlockEntity tile = worldIn.getBlockEntity(pos);
	    if (tile instanceof IMultiblockTileNode multi) {
		multi.onNodeReplaced(worldIn, pos, !update);
	    }
	}
	if (update) {
	    super.onRemove(state, worldIn, pos, newState, isMoving);
	} else {
	    worldIn.setBlocksDirty(pos, state, newState);
	}
    }

    @Override
    public boolean hasMultiBlock() {
	return machine == SubtypeMachine.advancedsolarpanel || machine == SubtypeMachine.windmill;
    }

    private static ItemStack getMachine(SubtypeMachine inputMachine) {
	return new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(inputMachine));
    }
}
