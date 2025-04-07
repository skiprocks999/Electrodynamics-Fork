package voltaicapi.datagen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import voltaicapi.VoltaicAPI;
import voltaicapi.datagen.client.VoltaicAPIBlockStateProvider;
import voltaicapi.datagen.client.VoltaicAPIItemModelsProvider;
import voltaicapi.datagen.client.VoltaicAPILangKeyProvider;
import voltaicapi.datagen.client.VoltaicAPISoundProvider;
import voltaicapi.datagen.server.VoltaicAPIAdvancementProvider;
import voltaicapi.datagen.server.recipe.VoltaicAPIRecipeProvider;
import voltaicapi.datagen.utils.client.BaseLangKeyProvider.Locale;
import voltaicapi.datagen.server.tags.VoltaicAPITagsProvider;
import voltaicapi.registers.VoltaicAPIDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = VoltaicAPI.ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();

        PackOutput output = generator.getPackOutput();

        ExistingFileHelper helper = event.getExistingFileHelper();

        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();


        if (event.includeServer()) {

            DatapackBuiltinEntriesProvider datapacks = new DatapackBuiltinEntriesProvider(output, lookupProvider, new RegistrySetBuilder()
                    //
                    .add(Registries.DAMAGE_TYPE, VoltaicAPIDamageTypes::registerTypes)
                    //
                    , Set.of(VoltaicAPI.ID));

            generator.addProvider(true, datapacks);
            VoltaicAPITagsProvider.addTagProviders(generator, output, datapacks.getRegistryProvider(), helper);
            generator.addProvider(true, new VoltaicAPIRecipeProvider(output, lookupProvider));
            generator.addProvider(true, new VoltaicAPIAdvancementProvider(output, datapacks.getRegistryProvider()));
        }
        if (event.includeClient()) {
            generator.addProvider(true, new VoltaicAPIBlockStateProvider(output, helper));
            generator.addProvider(true, new VoltaicAPIItemModelsProvider(output, helper));
            generator.addProvider(true, new VoltaicAPILangKeyProvider(output, Locale.EN_US));
            generator.addProvider(true, new VoltaicAPISoundProvider(output, helper));
        }
    }

}
