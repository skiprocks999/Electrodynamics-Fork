package voltaicapi.compatibility.jei;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import voltaicapi.VoltaicAPI;
import voltaicapi.api.gas.Gas;
import voltaicapi.api.gas.GasStack;
import voltaicapi.compatibility.jei.utils.ingredients.ModularElectricityJeiTypes;
import voltaicapi.compatibility.jei.utils.ingredients.IngredientHelperGasStack;
import voltaicapi.compatibility.jei.utils.ingredients.IngredientRendererGasStack;
import voltaicapi.registers.VoltaicAPIGases;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IExtraIngredientRegistration;
import mezz.jei.api.registration.IModIngredientRegistration;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

@JeiPlugin
public class VoltaicAPIJEIPlugin implements IModPlugin {

    public static final ResourceLocation ID = VoltaicAPI.rl( "jei");

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        registration.register(ModularElectricityJeiTypes.GAS_STACK, new ArrayList<>(), new IngredientHelperGasStack(), IngredientRendererGasStack.LIST_RENDERER, GasStack.CODEC);
    }

    @Override
    public void registerExtraIngredients(IExtraIngredientRegistration registration) {

        List<GasStack> gases = new ArrayList<>();
        for(DeferredHolder<Gas, ? extends Gas> gas : VoltaicAPIGases.GASES.getEntries()) {
            if(gas.get() == VoltaicAPIGases.EMPTY.value()) {
                continue;
            }

            gases.add(new GasStack(gas.get(), 1000, Gas.ROOM_TEMPERATURE, Gas.PRESSURE_AT_SEA_LEVEL));
        }
        registration.addExtraIngredients(ModularElectricityJeiTypes.GAS_STACK, gases);
    }

}
