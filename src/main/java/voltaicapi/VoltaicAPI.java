package voltaicapi;

import java.util.Random;
import java.util.function.Consumer;

import voltaicapi.common.reloadlistener.RadiationShieldingRegister;
import voltaicapi.common.reloadlistener.RadioactiveFluidRegister;
import voltaicapi.common.reloadlistener.RadioactiveGasRegister;
import voltaicapi.common.reloadlistener.RadioactiveItemRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import voltaicapi.client.VoltaicAPIClientRegister;
import voltaicapi.common.block.states.ModularElectricityBlockStates;
import voltaicapi.common.packet.types.client.PacketResetGuidebookPages;
import voltaicapi.common.settings.ModularElectricityConstants;
import voltaicapi.common.tags.ModularElectricityTags;
import voltaicapi.prefab.configuration.ConfigurationHandler;
import voltaicapi.registers.UnifiedVoltaicAPIRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@Mod(VoltaicAPI.ID)
@EventBusSubscriber(modid = VoltaicAPI.ID, bus = EventBusSubscriber.Bus.MOD)
public class VoltaicAPI {

    public static Logger LOGGER = LogManager.getLogger(VoltaicAPI.ID);

    public static final Random RANDOM = new Random();

    public static final String ID = "voltaicapi";
    public static final String NAME = "Voltaic API";

    public static final String MEKANISM_ID = "mekanism";

    public VoltaicAPI(IEventBus bus) {
        ConfigurationHandler.registerConfig(ModularElectricityConstants.class);
        // MUST GO BEFORE BLOCKS!!!!
        ModularElectricityBlockStates.init();
        UnifiedVoltaicAPIRegister.register(bus);

    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {

        NeoForge.EVENT_BUS.addListener(getGuidebookListener());
        ModularElectricityTags.init();
        RadioactiveItemRegister.INSTANCE = new RadioactiveItemRegister().subscribeAsSyncable();
        RadioactiveFluidRegister.INSTANCE = new RadioactiveFluidRegister().subscribeAsSyncable();
        RadioactiveGasRegister.INSTANCE = new RadioactiveGasRegister().subscribeAsSyncable();
        RadiationShieldingRegister.INSTANCE = new RadiationShieldingRegister().subscribeAsSyncable();
        // CraftingHelper.register(ConfigCondition.Serializer.INSTANCE); // Probably wrong location after update from 1.18.2 to
        // 1.19.2

        // RegisterFluidToGasMapEvent map = new RegisterFluidToGasMapEvent();
        // MinecraftForge.EVENT_BUS.post(map);
        // ElectrodynamicsGases.MAPPED_GASSES.putAll(map.fluidToGasMap);

    }

    // I wonder how long this bug has been there
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            VoltaicAPIClientRegister.setup();
        });
    }

    // Don't really have a better place to put this for now
    private static Consumer<OnDatapackSyncEvent> getGuidebookListener() {

        return event -> {
            ServerPlayer player = event.getPlayer();
            if (player == null) {
                PacketDistributor.sendToAllPlayers(PacketResetGuidebookPages.PACKET);
            } else {
                PacketDistributor.sendToPlayer(player, PacketResetGuidebookPages.PACKET);
            }
        };

    }

    public static final ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }

    public static final ResourceLocation vanillarl(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }

    public static final ResourceLocation forgerl(String path) {
        return ResourceLocation.fromNamespaceAndPath("neoforge", path);
    }

    public static final ResourceLocation commonrl(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }

}
