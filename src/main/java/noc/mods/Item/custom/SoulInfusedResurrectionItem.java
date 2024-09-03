package noc.mods.Item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class SoulInfusedResurrectionItem extends Item {

    public SoulInfusedResurrectionItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.literal("Soul Infused Resurrection Stone");
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("This stone is infused with the soul of a villager.").formatted(Formatting.GRAY));
    }

}
