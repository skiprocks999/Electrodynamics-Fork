package voltaicapi.datagen.client;

import voltaicapi.VoltaicAPI;
import voltaicapi.datagen.utils.client.BaseSoundProvider;
import voltaicapi.registers.VoltaicAPISounds;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class VoltaicAPISoundProvider extends BaseSoundProvider {
    public VoltaicAPISoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, helper, VoltaicAPI.ID);
    }

    @Override
    public void registerSounds() {
        add(VoltaicAPISounds.SOUND_BATTERY_SWAP);
        add(VoltaicAPISounds.SOUND_PRESSURERELEASE);
    }
}
