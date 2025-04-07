package voltaicapi.registers;

import voltaicapi.VoltaicAPI;
import voltaicapi.common.inventory.container.ContainerGuidebook;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MenuType.MenuSupplier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VoltaicAPIMenuTypes {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, VoltaicAPI.ID);

	public static final DeferredHolder<MenuType<?>,MenuType<ContainerGuidebook>> CONTAINER_GUIDEBOOK = register("guidebook", ContainerGuidebook::new);

	private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>,MenuType<T>> register(String id, MenuSupplier<T> supplier) {
		return MENU_TYPES.register(id, () -> new MenuType<>(supplier, FeatureFlags.DEFAULT_FLAGS));
	}
}
