package voltaicapi.datagen.server.recipe;

import voltaicapi.datagen.utils.server.recipe.BaseRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class VoltaicAPIRecipeProvider extends BaseRecipeProvider {

    public VoltaicAPIRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    public void addRecipes() {
        generators.add(new VoltaicAPICraftingTableRecipes());
    }
}
