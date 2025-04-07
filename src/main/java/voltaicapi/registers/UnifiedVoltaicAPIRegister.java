package voltaicapi.registers;

import net.neoforged.bus.api.IEventBus;

public class UnifiedVoltaicAPIRegister {

	public static void register(IEventBus bus) {
		VoltaicAPIBlocks.BLOCKS.register(bus);
		VoltaicAPITiles.BLOCK_ENTITY_TYPES.register(bus);
		VoltaicAPIItems.ITEMS.register(bus);
		VoltaicAPIMenuTypes.MENU_TYPES.register(bus);
		VoltaicAPIGases.GASES.register(bus);
		VoltaicAPIParticles.PARTICLES.register(bus);
		VoltaicAPICreativeTabs.CREATIVE_TABS.register(bus);
		VoltaicAPIIngredients.INGREDIENT_TYPES.register(bus);
		VoltaicAPISounds.SOUNDS.register(bus);
		VoltaicAPIEffects.EFFECTS.register(bus);
		VoltaicAPIDataComponentTypes.DATA_COMPONENT_TYPES.register(bus);
		VoltaicAPIAttachmentTypes.ATTACHMENT_TYPES.register(bus);
		VoltaicAPIConditions.CONDITIONS.register(bus);
	}

}
