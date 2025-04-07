package voltaicapi.api.network.cable;

import voltaicapi.prefab.network.AbstractNetwork;
import voltaicapi.prefab.tile.types.GenericRefreshingConnectTile;
import net.minecraft.core.Direction;

public interface IRefreshableCable<CONDUCTORTYPE, T extends AbstractNetwork<? extends GenericRefreshingConnectTile<?, ?, ?>, ?, ?, ?>> extends IAbstractCable<CONDUCTORTYPE, T> {

	void updateNetwork(Direction...dirs);

	void destroyViolently();

}
