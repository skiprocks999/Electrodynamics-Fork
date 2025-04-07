package voltaicapi.prefab.tile;

import voltaicapi.prefab.properties.PropertyManager;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IPropertyHolderTile {
	PropertyManager getPropertyManager();

	default BlockEntity getTile() {
		return (BlockEntity) this;
	}
}
