package noc.mods.Item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulInfusedResurrectionItem extends Item {

    public SoulInfusedResurrectionItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        // Always glint for the soul-infused version
        return true;
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.literal("Soul Infused Resurrection Stone");
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("This stone is infused with the soul of a villager.").formatted(Formatting.GRAY));
    }
}
