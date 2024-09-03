package noc.mods.Item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import noc.mods.HardcorePlusPlus;

public class ModItems {
    public static final Item RESURRECTION_STONE = registerItem("resurrection_stone", new Item(new Item.Settings()));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(HardcorePlusPlus.MOD_ID, name), item);
    }

    public static void registerModItems() {
        HardcorePlusPlus.LOGGER.info("Registering items for " + HardcorePlusPlus.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(RESURRECTION_STONE));
    }
}
