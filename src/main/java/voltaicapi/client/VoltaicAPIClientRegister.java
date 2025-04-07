package voltaicapi.client;

import java.util.HashMap;
import java.util.List;

import voltaicapi.VoltaicAPI;
import voltaicapi.client.guidebook.ScreenGuidebook;
import voltaicapi.client.model.block.bakerytypes.CableModelLoader;
import voltaicapi.client.model.block.bakerytypes.MultiblockModelLoader;
import voltaicapi.client.model.block.bakerytypes.SlaveNodeModelLoader;
import voltaicapi.client.particle.fluiddrop.ParticleFluidDrop;
import voltaicapi.client.particle.lavawithphysics.ParticleLavaWithPhysics;
import voltaicapi.client.particle.plasmaball.ParticlePlasmaBall;
import voltaicapi.client.guidebook.ReloadListenerResetGuidebook;

import voltaicapi.client.texture.atlas.AtlasHolderElectrodynamicsCustom;
import voltaicapi.registers.VoltaicAPIMenuTypes;
import voltaicapi.registers.VoltaicAPIParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterAdditional;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.TextureAtlasStitchedEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = VoltaicAPI.ID, bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class VoltaicAPIClientRegister {


    public static final ResourceLocation ON = VoltaicAPI.vanillarl("on");

    // Custom Textures
    public static final ResourceLocation TEXTURE_WHITE = VoltaicAPI.forgerl("white");
    public static final ResourceLocation TEXTURE_MERCURY = VoltaicAPI.rl("block/custom/mercury");
    public static final ResourceLocation TEXTURE_GAS = VoltaicAPI.rl("block/custom/gastexture");
    public static final ResourceLocation TEXTURE_MULTISUBNODE = VoltaicAPI.rl("block/multisubnode");

    private static final String MULTIBLOCK_API_MODEL_FOLDER = "multiblockmodels";

    private static final HashMap<ResourceLocation, TextureAtlasSprite> CACHED_TEXTUREATLASSPRITES = new HashMap<>();
    // for registration purposes only!
    private static final List<ResourceLocation> CUSTOM_TEXTURES = List.of(VoltaicAPIClientRegister.TEXTURE_WHITE, VoltaicAPIClientRegister.TEXTURE_MERCURY, VoltaicAPIClientRegister.TEXTURE_GAS, VoltaicAPIClientRegister.TEXTURE_MULTISUBNODE);

    public static void setup() {

    }

    @SubscribeEvent
    public static void onModelEvent(RegisterAdditional event) {

        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        FileToIdConverter converter = FileToIdConverter.json("models/" + MULTIBLOCK_API_MODEL_FOLDER);
        converter.listMatchingResources(manager).forEach((location, resource) -> event.register(ModelResourceLocation.standalone(converter.fileToId(location).withPrefix(MULTIBLOCK_API_MODEL_FOLDER + "/"))));
    }

    @SubscribeEvent
    public static void registerMenus(RegisterMenuScreensEvent event) {
        event.register(VoltaicAPIMenuTypes.CONTAINER_GUIDEBOOK.get(), ScreenGuidebook::new);
    }

    @SubscribeEvent
    public static void cacheCustomTextureAtlases(TextureAtlasStitchedEvent event) {
        if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
            CACHED_TEXTUREATLASSPRITES.clear();
            for (ResourceLocation loc : CUSTOM_TEXTURES) {
                VoltaicAPIClientRegister.CACHED_TEXTUREATLASSPRITES.put(loc, event.getAtlas().getSprite(loc));
            }
        }
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(VoltaicAPIParticles.PARTICLE_PLASMA_BALL.get(), ParticlePlasmaBall.Factory::new);
        event.registerSpriteSet(VoltaicAPIParticles.PARTICLE_LAVAWITHPHYSICS.get(), ParticleLavaWithPhysics.Factory::new);
        event.registerSpriteSet(VoltaicAPIParticles.PARTICLE_FLUIDDROP.get(), ParticleFluidDrop.Factory::new);
    }

    @SubscribeEvent
    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(AtlasHolderElectrodynamicsCustom.INSTANCE = new AtlasHolderElectrodynamicsCustom(Minecraft.getInstance().getTextureManager()));
        event.registerReloadListener(new ReloadListenerResetGuidebook());
    }

    @SubscribeEvent
    public static void registerGeometryLoaders(final ModelEvent.RegisterGeometryLoaders event) {
        event.register(CableModelLoader.ID, CableModelLoader.INSTANCE);
        event.register(SlaveNodeModelLoader.ID, SlaveNodeModelLoader.INSTANCE);
        event.register(MultiblockModelLoader.ID, MultiblockModelLoader.INSTANCE);
    }

    public static TextureAtlasSprite getSprite(ResourceLocation sprite) {
        return CACHED_TEXTUREATLASSPRITES.getOrDefault(sprite, CACHED_TEXTUREATLASSPRITES.get(TEXTURE_WHITE));
    }

}
