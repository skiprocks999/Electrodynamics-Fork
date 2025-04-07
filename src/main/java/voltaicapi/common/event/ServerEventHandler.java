package voltaicapi.common.event;

import voltaicapi.VoltaicAPI;
import voltaicapi.api.multiblock.assemblybased.CommandScanMultiblock;
import voltaicapi.common.command.CommandWipeRadiationSources;
import voltaicapi.common.reloadlistener.RadiationShieldingRegister;
import voltaicapi.common.reloadlistener.RadioactiveFluidRegister;
import voltaicapi.common.reloadlistener.RadioactiveGasRegister;
import voltaicapi.common.reloadlistener.RadioactiveItemRegister;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@EventBusSubscriber(modid = VoltaicAPI.ID, bus = EventBusSubscriber.Bus.GAME)
public class ServerEventHandler {

	@SubscribeEvent
	public static void addReloadListeners(AddReloadListenerEvent event) {
		event.addListener(RadioactiveItemRegister.INSTANCE);
		event.addListener(RadioactiveFluidRegister.INSTANCE);
		event.addListener(RadioactiveGasRegister.INSTANCE);
		event.addListener(RadiationShieldingRegister.INSTANCE);
	}

	@SubscribeEvent
	public static void serverStartedHandler(ServerStartedEvent event) {
		RadioactiveItemRegister.INSTANCE.generateTagValues();
		RadioactiveFluidRegister.INSTANCE.generateTagValues();
		RadioactiveGasRegister.INSTANCE.generateTagValues();
		RadiationShieldingRegister.INSTANCE.generateTagValues();
	}

	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		CommandScanMultiblock.register(event.getDispatcher());
		CommandWipeRadiationSources.register(event.getDispatcher());
	}

}
