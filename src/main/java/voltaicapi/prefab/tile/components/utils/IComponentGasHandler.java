package voltaicapi.prefab.tile.components.utils;

import org.jetbrains.annotations.Nullable;

import voltaicapi.api.gas.IGasHandler;
import voltaicapi.api.gas.PropertyGasTank;
import voltaicapi.prefab.tile.components.CapabilityInputType;
import voltaicapi.prefab.tile.components.IComponent;
import net.minecraft.core.Direction;

public interface IComponentGasHandler extends IComponent {

	public static final int TANK_MULTIPLIER = 1000;

	PropertyGasTank[] getInputTanks();

	PropertyGasTank[] getOutputTanks();
	
	@Nullable
	public IGasHandler getCapability(@Nullable Direction direction, CapabilityInputType mode);

}
