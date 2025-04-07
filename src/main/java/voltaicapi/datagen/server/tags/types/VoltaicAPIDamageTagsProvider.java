package voltaicapi.datagen.server.tags.types;

import java.util.concurrent.CompletableFuture;

import voltaicapi.VoltaicAPI;
import voltaicapi.registers.VoltaicAPIDamageTypes;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class VoltaicAPIDamageTagsProvider extends DamageTypeTagsProvider {

	public VoltaicAPIDamageTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, VoltaicAPI.ID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider provider) {
		tag(DamageTypeTags.BYPASSES_ARMOR).add(VoltaicAPIDamageTypes.ELECTRICITY, VoltaicAPIDamageTypes.RADIATION);
		// .isMagic()
		tag(DamageTypeTags.WITCH_RESISTANT_TO).add(VoltaicAPIDamageTypes.ELECTRICITY, VoltaicAPIDamageTypes.RADIATION);
		tag(DamageTypeTags.AVOIDS_GUARDIAN_THORNS).add(VoltaicAPIDamageTypes.ELECTRICITY, VoltaicAPIDamageTypes.RADIATION);
		tag(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH).add(VoltaicAPIDamageTypes.ELECTRICITY, VoltaicAPIDamageTypes.RADIATION);
	}

}
