package voltaicapi.datagen.client;

import voltaicapi.VoltaicAPI;
import voltaicapi.common.item.subtype.SubtypeItemUpgrade;
import voltaicapi.datagen.utils.client.BaseItemModelsProvider;
import voltaicapi.registers.VoltaicAPIItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class VoltaicAPIItemModelsProvider extends BaseItemModelsProvider {

    public VoltaicAPIItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper, VoltaicAPI.ID);
    }

    @Override
    protected void registerModels() {

        layeredItem(VoltaicAPIItems.GUIDEBOOK, Parent.GENERATED, itemLoc("guidebook"));
        layeredItem(VoltaicAPIItems.ITEM_WRENCH, Parent.GENERATED, itemLoc("wrench"));
        //layeredItem(VoltaicAPIItems.ITEM_ANTIDOTE, Parent.GENERATED, itemLoc("antidote"));
        //layeredItem(VoltaicAPIItems.ITEM_IODINETABLET, Parent.GENERATED, itemLoc("iodinetablet"));

        for (SubtypeItemUpgrade upgrade : SubtypeItemUpgrade.values()) {
            //layeredBuilder(name(VoltaicAPIItems.ITEMS_UPGRADE.getValue(upgrade)), Parent.GENERATED, itemLoc("upgrade/" + upgrade.tag())).transforms().transform(ItemDisplayContext.GUI).scale(0.8F).end();
        }

    }


}
