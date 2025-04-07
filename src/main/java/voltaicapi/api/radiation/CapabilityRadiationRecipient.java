package voltaicapi.api.radiation;

import voltaicapi.VoltaicAPI;
import voltaicapi.api.radiation.util.IHazmatSuit;
import voltaicapi.api.radiation.util.IRadiationRecipient;
import voltaicapi.api.radiation.util.RadioactiveObject;
import voltaicapi.common.settings.ModularElectricityConstants;
import voltaicapi.registers.VoltaicAPIAttachmentTypes;
import voltaicapi.registers.VoltaicAPIEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CapabilityRadiationRecipient implements IRadiationRecipient {

    private static final EquipmentSlot[] ARMOR_SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    @Override
    public void recieveRadiation(LivingEntity entity, double rads, double strength) {

        if (rads <= 0) {
            return;
        }

        if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) {
            player.setData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONAMOUNT, player.getData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONAMOUNT) + rads);
            player.setData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONSTRENGTH, player.getData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONSTRENGTH) + strength);
            return;
        }

        if(entity.hasEffect(VoltaicAPIEffects.RADIATION_RESISTANCE)) {
            if(rads <= ModularElectricityConstants.IODINE_RESISTANCE_THRESHHOLD) {
                entity.setData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONAMOUNT, entity.getData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONAMOUNT) + rads);
                entity.setData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONSTRENGTH, entity.getData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONSTRENGTH) + strength);
                return;
            } else {
                rads = rads * ModularElectricityConstants.IODINE_RAD_REDUCTION;
            }
        }

        int count = 0;

        for (EquipmentSlot slot : ARMOR_SLOTS) {

            ItemStack stack = entity.getItemBySlot(slot);

            if (stack.getItem() instanceof IHazmatSuit) {

                //TODO implement damage reduction based on radiation amount and strength

                count++;

                float damage = (float) (rads * 2.15f) / 2169.9975f;

                if (VoltaicAPI.RANDOM.nextFloat() >= damage) {
                    continue;
                }

                stack.hurtAndBreak((int) Math.ceil(damage), entity, slot);

            }

        }

        // Not Full Set
        if (count < 4) {

            int amplitude = getAmplitudeFromRadiation(rads, strength);
            int time = getDurationFromRadiation(rads);

            if (entity.hasEffect(VoltaicAPIEffects.RADIATION)) {

                MobEffectInstance instance = entity.getEffect(VoltaicAPIEffects.RADIATION);

                if (instance.getAmplifier() > amplitude) {
                    entity.addEffect(new MobEffectInstance(VoltaicAPIEffects.RADIATION, time + instance.getDuration(), instance.getAmplifier(), false, true));
                } else {
                    entity.addEffect(new MobEffectInstance(VoltaicAPIEffects.RADIATION, time + instance.getDuration(), amplitude, false, true));
                }

            } else {
                entity.addEffect(new MobEffectInstance(VoltaicAPIEffects.RADIATION, time, amplitude, false, true));
            }
        }

        entity.setData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONAMOUNT, entity.getData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONAMOUNT) + rads);
        entity.setData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONSTRENGTH, entity.getData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONSTRENGTH) + strength);

    }

    @Override
    public RadioactiveObject getRecievedRadiation(LivingEntity entity) {
        return new RadioactiveObject(entity.getData(VoltaicAPIAttachmentTypes.OLD_RECIEVED_RADIATIONSTRENGTH), entity.getData(VoltaicAPIAttachmentTypes.OLD_RECIEVED_RADIATIONAMOUNT));
    }

    @Override
    public void tick(LivingEntity entity) {

        entity.setData(VoltaicAPIAttachmentTypes.OLD_RECIEVED_RADIATIONAMOUNT, entity.getData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONAMOUNT));
        entity.setData(VoltaicAPIAttachmentTypes.OLD_RECIEVED_RADIATIONSTRENGTH, entity.getData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONSTRENGTH));

        entity.setData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONAMOUNT, 0.0);
        entity.setData(VoltaicAPIAttachmentTypes.RECIEVED_RADIATIONSTRENGTH, 0.0);

    }

    public static int getDurationFromRadiation(double radiation) {
        return (int) Math.max(20.0, radiation / 100.0 * 20.0);
    }

    public static int getAmplitudeFromRadiation(double radiation, double strength) {
        return (int) Math.min(40.0, radiation / 100.0 * strength);
    }

}
