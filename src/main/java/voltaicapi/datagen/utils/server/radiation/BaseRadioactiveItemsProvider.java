package voltaicapi.datagen.utils.server.radiation;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import voltaicapi.VoltaicAPI;
import voltaicapi.api.radiation.util.RadioactiveObject;
import voltaicapi.common.reloadlistener.RadioactiveItemRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class BaseRadioactiveItemsProvider implements DataProvider {

	public static final String LOC = "data/" + VoltaicAPI.ID + "/" + RadioactiveItemRegister.FOLDER + "/" + RadioactiveItemRegister.FILE_NAME;

	private final PackOutput output;

	public BaseRadioactiveItemsProvider(PackOutput output) {
		this.output = output;
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		JsonObject json = new JsonObject();
		getRadioactiveItems(json);

		Path parent = output.getOutputFolder().resolve(LOC + ".json");

		return CompletableFuture.allOf(DataProvider.saveStable(cache, json, parent));
	}

	public void getRadioactiveItems(JsonObject json) {

	}

	@SuppressWarnings("unused")
	private void addItem(Item item, double radiationAmount, double radiationStrength, JsonObject json) {
		JsonObject data = new JsonObject();
		json.add(BuiltInRegistries.ITEM.getKey(item).toString(), RadioactiveObject.CODEC.encode(new RadioactiveObject(radiationStrength, radiationAmount), JsonOps.INSTANCE, data).getOrThrow());
	}

	private void addTag(TagKey<Item> tag, double radiationAmount, double radiationStrength, JsonObject json) {
		JsonObject data = new JsonObject();
		json.add("#" + tag.location().toString(), RadioactiveObject.CODEC.encode(new RadioactiveObject(radiationStrength, radiationAmount), JsonOps.INSTANCE, data).getOrThrow());
	}

	@Override
	public String getName() {
		return "Nuclear Science Radioactive Items Provider";
	}

}
