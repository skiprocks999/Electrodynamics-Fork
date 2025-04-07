package voltaicapi.registers;

import java.util.ArrayList;
import java.util.List;

import voltaicapi.VoltaicAPI;
import voltaicapi.api.creativetab.CreativeTabSupplier;
import voltaicapi.api.registration.BulkDeferredHolder;
import voltaicapi.common.item.ItemAntidote;
import voltaicapi.common.item.ItemIodineTablet;
import voltaicapi.common.item.ItemUpgrade;
import voltaicapi.common.item.gear.ItemGuidebook;
import voltaicapi.common.item.gear.ItemWrench;
import voltaicapi.common.item.subtype.SubtypeItemUpgrade;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VoltaicAPIItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, VoltaicAPI.ID);

	public static final DeferredHolder<Item, ItemWrench> ITEM_WRENCH = ITEMS.register("wrench", () -> new ItemWrench(new Item.Properties().stacksTo(1), VoltaicAPICreativeTabs.MAIN));
	public static final DeferredHolder<Item, ItemGuidebook> GUIDEBOOK = ITEMS.register("guidebook", () -> new ItemGuidebook(new Item.Properties(), VoltaicAPICreativeTabs.MAIN));
	//public static final DeferredHolder<Item, Item> ITEM_ANTIDOTE = ITEMS.register("antidote", () -> new ItemAntidote(new Item.Properties(), VoltaicAPICreativeTabs.MAIN));
	//public static final DeferredHolder<Item, Item> ITEM_IODINETABLET = ITEMS.register("iodinetablet", () -> new ItemIodineTablet(new Item.Properties(), VoltaicAPICreativeTabs.MAIN));

	//public static final BulkDeferredHolder<Item, ItemUpgrade, SubtypeItemUpgrade> ITEMS_UPGRADE = new BulkDeferredHolder<>(SubtypeItemUpgrade.values(), subtype -> ITEMS.register(subtype.tag(), () -> new ItemUpgrade(new Item.Properties(), subtype)));

	@EventBusSubscriber(value = Dist.CLIENT, modid = VoltaicAPI.ID, bus = EventBusSubscriber.Bus.MOD)
	private static class ElectroCreativeRegistry {

		@SubscribeEvent
		public static void registerItems(BuildCreativeModeTabContentsEvent event) {

			ITEMS.getEntries().forEach(reg -> {

				CreativeTabSupplier supplier = (CreativeTabSupplier) reg.get();

				if (supplier.hasCreativeTab() && supplier.isAllowedInCreativeTab(event.getTab())) {
					List<ItemStack> toAdd = new ArrayList<>();
					supplier.addCreativeModeItems(event.getTab(), toAdd);
					event.acceptAll(toAdd);
				}

			});

		}

	}

}
