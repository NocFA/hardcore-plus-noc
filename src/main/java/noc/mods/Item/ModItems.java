package noc.mods.Item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import noc.mods.HardcorePlusPlus;
import noc.mods.Item.custom.HeartRestorationItem;
import noc.mods.Item.custom.Resurrection_Item;
import noc.mods.Item.custom.SoulInfusedResurrectionItem;

public class ModItems {
    public static final Item RESURRECTION_STONE = registerItem("resurrection_stone",
            new Resurrection_Item(new Item.Settings()));

    public static final Item SOUL_INFUSED_RESURRECTION_ITEM = registerItem("soul_infused_resurrection_stone",
            new SoulInfusedResurrectionItem(new Item.Settings()));

    public static final Item HEART_RESTORATION_ITEM = registerItem("heart_restoration_item",
            new HeartRestorationItem(new Item.Settings().maxCount(1)));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(HardcorePlusPlus.MOD_ID, name), item);
    }

    public static void registerModItems() {
        HardcorePlusPlus.LOGGER.info("Registering items for " + HardcorePlusPlus.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(RESURRECTION_STONE);
            entries.add(SOUL_INFUSED_RESURRECTION_ITEM);
            entries.add(HEART_RESTORATION_ITEM);
        });
    }
}
