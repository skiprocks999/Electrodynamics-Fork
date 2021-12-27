package electrodynamics.common.inventory.container.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.tile.TileFermentationPlant;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.GenericSlot;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import electrodynamics.prefab.inventory.container.slot.UpgradeSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class ContainerFermentationPlant extends GenericContainerBlockEntity<TileFermentationPlant> {

	public ContainerFermentationPlant(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(6), new SimpleContainerData(3));
	}

	public ContainerFermentationPlant(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_FERMENTATIONPLANT.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerFermentationPlant(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new GenericSlot(inv, nextIndex(), 74, 31));
		addSlot(new SlotRestricted(inv, nextIndex(), 74, 51, 0, CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY));
		addSlot(new SlotRestricted(inv, nextIndex(), 108, 51, 0, CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY));
		addSlot(new UpgradeSlot(inv, nextIndex(), 150, 14, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.iteminput));
		addSlot(new UpgradeSlot(inv, nextIndex(), 150, 34, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.iteminput));
		addSlot(new UpgradeSlot(inv, nextIndex(), 150, 54, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.iteminput));
	}
}