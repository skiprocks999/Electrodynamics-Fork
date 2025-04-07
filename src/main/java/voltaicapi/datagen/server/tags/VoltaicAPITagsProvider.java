package voltaicapi.datagen.server.tags;

import java.util.concurrent.CompletableFuture;

import voltaicapi.datagen.server.tags.types.VoltaicAPIDamageTagsProvider;
import voltaicapi.datagen.server.tags.types.VoltaicAPIEnchantmentTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class VoltaicAPITagsProvider {

	public static void addTagProviders(DataGenerator generator, PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
		generator.addProvider(true, new VoltaicAPIDamageTagsProvider(output, lookupProvider, helper));
		generator.addProvider(true, new VoltaicAPIEnchantmentTagsProvider(output, lookupProvider, helper));
	}

}
