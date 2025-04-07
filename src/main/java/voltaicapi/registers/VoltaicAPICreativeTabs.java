package voltaicapi.registers;

import voltaicapi.VoltaicAPI;
import voltaicapi.prefab.utilities.ModularElectricityTextUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VoltaicAPICreativeTabs {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VoltaicAPI.ID);

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder().title(ModularElectricityTextUtils.creativeTab("main")).icon(() -> new ItemStack(VoltaicAPIItems.ITEM_WRENCH)).build());

}
