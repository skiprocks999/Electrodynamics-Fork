package voltaicapi.datagen.utils.server.radiation;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import voltaicapi.VoltaicAPI;
import voltaicapi.api.gas.Gas;
import voltaicapi.api.radiation.util.RadioactiveObject;
import voltaicapi.common.reloadlistener.RadioactiveGasRegister;
import voltaicapi.registers.VoltaicAPIGases;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class BaseRadioactiveGasesProvider implements DataProvider {

    public static final String LOC = "data/" + VoltaicAPI.ID + "/" + RadioactiveGasRegister.FOLDER + "/" + RadioactiveGasRegister.FILE_NAME;

    private final PackOutput output;

    public BaseRadioactiveGasesProvider(PackOutput output) {
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
    private void addItem(Gas gas, double radiationAmount, double radiationStrength, JsonObject json) {
        JsonObject data = new JsonObject();
        json.add(VoltaicAPIGases.GAS_REGISTRY.getKey(gas).toString(), RadioactiveObject.CODEC.encode(new RadioactiveObject(radiationStrength, radiationAmount), JsonOps.INSTANCE, data).getOrThrow());
    }

    private void addTag(TagKey<Gas> tag, double radiationAmount, double radiationStrength, JsonObject json) {
        JsonObject data = new JsonObject();
        json.add("#" + tag.location().toString(), RadioactiveObject.CODEC.encode(new RadioactiveObject(radiationStrength, radiationAmount), JsonOps.INSTANCE, data).getOrThrow());
    }

    @Override
    public String getName() {
        return "Nuclear Science Radioactive Gases Provider";
    }

}
