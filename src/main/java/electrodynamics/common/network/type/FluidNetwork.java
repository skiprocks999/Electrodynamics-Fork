package electrodynamics.common.network.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;

import electrodynamics.api.network.cable.type.IFluidPipe;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.network.NetworkRegistry;
import electrodynamics.common.network.utils.FluidUtilities;
import electrodynamics.common.tile.pipelines.fluids.TileFluidPipePump;
import electrodynamics.prefab.network.AbstractNetwork;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;

public class FluidNetwork extends AbstractNetwork<IFluidPipe, SubtypeFluidPipe, BlockEntity, FluidStack> {

	public HashMap<Integer, HashSet<TileFluidPipePump>> priorityPumpMap = new HashMap<>();

	public FluidNetwork() {
		this(new HashSet<IFluidPipe>());
	}

	public FluidNetwork(Collection<? extends IFluidPipe> varCables) {
		conductorSet.addAll(varCables);
		NetworkRegistry.register(this);
	}

	public FluidNetwork(Set<AbstractNetwork<IFluidPipe, SubtypeFluidPipe, BlockEntity, FluidStack>> networks) {
		for (AbstractNetwork<IFluidPipe, SubtypeFluidPipe, BlockEntity, FluidStack> net : networks) {
			if (net != null) {
				conductorSet.addAll(net.conductorSet);
				net.deregister();
			}
		}
		refresh();
		NetworkRegistry.register(this);
	}

	public FluidNetwork(Set<FluidNetwork> networks, boolean special) {
		for (FluidNetwork net : networks) {
			if (net != null) {
				conductorSet.addAll(net.conductorSet);
				net.deregister();
			}
		}
		refresh();
		NetworkRegistry.register(this);
	}

	@Override
	public FluidStack emit(FluidStack transfer, ArrayList<BlockEntity> ignored, boolean debug) {

		if (transfer.getAmount() <= 0) {
			return FluidStack.EMPTY;
		}

		FluidStack initial = transfer.copy();

		// Don't allow more than how much can be transferred at once throughout the network
		// Fails if there is a pipe in the network that is not in the "path" and it has lower throughput,
		// but it's either this, pathfinding through the network or discarding throughput completely
		initial.setAmount(Math.min(initial.getAmount(), (int) networkMaxTransfer));

		FluidStack taken = new FluidStack(transfer.getFluid(), 0);

		Pair<FluidStack, Set<TileFluidPipePump>> priorityFilled = emitToPumps(initial, ignored);

		initial.shrink(priorityFilled.getFirst().getAmount());
		taken.grow(priorityFilled.getFirst().getAmount());

		if (initial.isEmpty()) {

			return taken;

		}

		HashSet<BlockEntity> availableAcceptors = Sets.newHashSet();

		availableAcceptors.addAll(acceptorSet);

		availableAcceptors.removeAll(ignored);
		availableAcceptors.removeAll(priorityFilled.getSecond());

		if (availableAcceptors.isEmpty()) {
			return FluidStack.EMPTY;
		}

		// This algorithm is not perfect, but it helps deal with tiles that do not accept the full amount allotted to them

		FluidStack perTile, prePerTile, perConnection, prePerConnection;

		int size = availableAcceptors.size();

		int connectionsSize, amtTaken, takenAmt;

		HashSet<Direction> connections;

		for (BlockEntity tile : availableAcceptors) {

			perTile = new FluidStack(initial.getFluid(), initial.getAmount() / size);
			prePerTile = perTile.copy();

			connections = acceptorInputMap.getOrDefault(tile, new HashSet<>());

			connectionsSize = connections.size();

			for (Direction dir : connections) {

				perConnection = new FluidStack(initial.getFluid(), perTile.getAmount() / connectionsSize);
				prePerConnection = perConnection.copy();

				amtTaken = FluidUtilities.receiveFluid(tile, dir, perConnection, false);

				perConnection.shrink(amtTaken);

				perTile.shrink(prePerConnection.getAmount() - perConnection.getAmount());

				connectionsSize--;
			}

			takenAmt = prePerTile.getAmount() - perTile.getAmount();

			initial.shrink(takenAmt);

			taken.grow(takenAmt);

			size--;
		}

		return taken;

	}

	private Pair<FluidStack, Set<TileFluidPipePump>> emitToPumps(FluidStack transfer, ArrayList<BlockEntity> ignored) {

		FluidStack taken = new FluidStack(transfer.getFluid(), 0);

		HashSet<TileFluidPipePump> acceptedPumps = new HashSet<>();

		if (priorityPumpMap.isEmpty()) {
			return Pair.of(taken, acceptedPumps);
		}

		Pair<FluidStack, Set<TileFluidPipePump>> accepted;

		Set<TileFluidPipePump> prioritySet;

		FluidStack copy = transfer.copy();

		for (int i = 9; i >= 0; i--) {

			if (copy.isEmpty()) {
				return Pair.of(taken, acceptedPumps);
			}

			prioritySet = priorityPumpMap.getOrDefault(i, new HashSet<>());

			if (prioritySet.isEmpty()) {
				continue;
			}

			accepted = emitToPumpSet(copy, prioritySet, ignored);

			acceptedPumps.addAll(accepted.getSecond());

			taken.grow(accepted.getFirst().getAmount());

			copy.shrink(accepted.getFirst().getAmount());

		}

		return Pair.of(taken, acceptedPumps);

	}

	private Pair<FluidStack, Set<TileFluidPipePump>> emitToPumpSet(FluidStack transfer, Set<TileFluidPipePump> recievingTiles, ArrayList<BlockEntity> ignored) {

		FluidStack initial = transfer.copy();
		FluidStack taken = new FluidStack(transfer.getFluid(), 0);

		FluidStack perTile, prePerTile, perConnection, prePerConnection;

		HashSet<TileFluidPipePump> filledPumps = new HashSet<>();

		int size = recievingTiles.size();

		int connectionsSize, amtTaken, takenAmt;

		HashSet<Direction> connections;

		for (TileFluidPipePump tile : recievingTiles) {
			if (!tile.isPowered() || ignored.contains(tile)) {
				size--;
				continue;
			}

			perTile = new FluidStack(initial.getFluid(), initial.getAmount() / size);
			prePerTile = perTile.copy();

			connections = acceptorInputMap.getOrDefault(tile, new HashSet<>());

			connectionsSize = connections.size();

			for (Direction dir : connections) {

				perConnection = new FluidStack(initial.getFluid(), perTile.getAmount() / connectionsSize);
				prePerConnection = perConnection.copy();

				amtTaken = FluidUtilities.receiveFluid(tile, dir, perConnection, false);

				perConnection.shrink(amtTaken);

				perTile.shrink(prePerConnection.getAmount() - perConnection.getAmount());

				connectionsSize--;
			}

			takenAmt = prePerTile.getAmount() - perTile.getAmount();

			initial.shrink(takenAmt);

			taken.grow(takenAmt);

			filledPumps.add(tile);

			size--;
		}

		return Pair.of(taken, filledPumps);

	}

	@Override
	public void updateRecieverStatistics(BlockEntity reciever, Direction dir) {

		if (reciever instanceof TileFluidPipePump pump) {
			int priority = pump.priority.get();
			HashSet<TileFluidPipePump> set = priorityPumpMap.getOrDefault(priority, new HashSet<>());
			set.add(pump);
			priorityPumpMap.put(priority, set);

		}

	}

	public void updateFluidPipePumpStats(TileFluidPipePump changedPump, int newPriority, int prevPriority) {
		HashSet<TileFluidPipePump> oldSet = priorityPumpMap.getOrDefault(prevPriority, new HashSet<>());
		oldSet.remove(changedPump);
		priorityPumpMap.put(prevPriority, oldSet);

		HashSet<TileFluidPipePump> newSet = priorityPumpMap.getOrDefault(newPriority, new HashSet<>());
		newSet.add(changedPump);
		priorityPumpMap.put(newPriority, newSet);
	}

	/*
	 * 
	 * There is no need for this as we have no concept of fluid pressure nor temperature
	 * 
	 * private boolean checkForOverload(int attemptSend) { if (attemptSend >= networkMaxTransfer) { HashSet<SubtypeFluidPipe> checkList = new HashSet<>(); for (SubtypeFluidPipe type : SubtypeFluidPipe.values()) { if (type.maxTransfer <= attemptSend) { checkList.add(type); } } for (SubtypeFluidPipe index : checkList) { for (IFluidPipe conductor : conductorTypeMap.get(index)) { conductor.destroyViolently(); } } return true; } return false; }
	 */

	@Override
	public boolean isConductor(BlockEntity tile, IFluidPipe requsterCable) {
		return tile instanceof IFluidPipe;
	}

	@Override
	public boolean isConductorClass(BlockEntity tile) {
		return tile instanceof IFluidPipe;
	}

	@Override
	public boolean isAcceptor(BlockEntity acceptor, Direction orientation) {
		return FluidUtilities.isFluidReceiver(acceptor);
	}

	@Override
	public AbstractNetwork<IFluidPipe, SubtypeFluidPipe, BlockEntity, FluidStack> createInstance() {
		return new FluidNetwork();
	}

	@Override
	public AbstractNetwork<IFluidPipe, SubtypeFluidPipe, BlockEntity, FluidStack> createInstanceConductor(Set<IFluidPipe> conductors) {
		return new FluidNetwork(conductors);
	}

	@Override
	public AbstractNetwork<IFluidPipe, SubtypeFluidPipe, BlockEntity, FluidStack> createInstance(Set<AbstractNetwork<IFluidPipe, SubtypeFluidPipe, BlockEntity, FluidStack>> networks) {
		return new FluidNetwork(networks);

	}

	@Override
	public SubtypeFluidPipe[] getConductorTypes() {
		return SubtypeFluidPipe.values();
	}

	@Override
	public boolean canConnect(BlockEntity acceptor, Direction orientation) {
		return FluidUtilities.isFluidReceiver(acceptor, orientation.getOpposite());
	}
}
