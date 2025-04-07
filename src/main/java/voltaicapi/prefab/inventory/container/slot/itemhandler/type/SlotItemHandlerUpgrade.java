package voltaicapi.prefab.inventory.container.slot.itemhandler.type;

import java.util.ArrayList;
import java.util.List;

import voltaicapi.common.item.subtype.SubtypeItemUpgrade;
import voltaicapi.prefab.inventory.container.slot.itemhandler.SlotItemHandlerGeneric;
import voltaicapi.prefab.inventory.container.slot.utils.IUpgradeSlot;
import voltaicapi.prefab.screen.component.types.ScreenComponentSlot.IconType;
import voltaicapi.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import voltaicapi.registers.VoltaicAPIItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

public class SlotItemHandlerUpgrade extends SlotItemHandlerGeneric implements IUpgradeSlot {

	private final List<Item> items = new ArrayList<>();

	public SlotItemHandlerUpgrade(IItemHandler itemHandler, int index, int xPosition, int yPosition, Item... upgrades) {
		super(SlotType.NORMAL, IconType.UPGRADE_DARK, itemHandler, index, xPosition, yPosition);

		items.clear();
		for (Item upg : upgrades) {
			items.add(upg);
		}

	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return items.contains(stack.getItem());
	}

	@Override
	public List<Item> getUpgrades() {
		return items;
	}

}
