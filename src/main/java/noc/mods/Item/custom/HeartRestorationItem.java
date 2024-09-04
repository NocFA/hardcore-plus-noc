package noc.mods.Item.custom;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HeartRestorationItem extends Item {

    private static final double MAX_HEALTH_CAP = 20.0; // 10 hearts

    public HeartRestorationItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        if (player != null && !player.getWorld().isClient) {
            restoreMaxHealth(player, context.getStack());
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            ItemStack stack = player.getStackInHand(hand);
            restoreMaxHealth(player, stack);
            return TypedActionResult.success(stack);
        }
        return TypedActionResult.pass(player.getStackInHand(hand));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    private void restoreMaxHealth(PlayerEntity player, ItemStack stack) {
        EntityAttributeInstance maxHealthAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (maxHealthAttribute != null) {
            double currentMaxHealth = maxHealthAttribute.getBaseValue();
            if (currentMaxHealth < MAX_HEALTH_CAP) {
                double newMaxHealth = Math.min(currentMaxHealth + 2.0, MAX_HEALTH_CAP);  // Restores 1 heart (2 points)
                maxHealthAttribute.setBaseValue(newMaxHealth);
                player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
                stack.decrement(1);  // Consume the item
                player.sendMessage(Text.of("Your maximum health has been restored by 1 heart!"), true);
                System.out.println("Restored max health of " + player.getName().getString() + " to " + newMaxHealth + " health points.");
            } else {
                player.sendMessage(Text.of("Your health is already at maximum!"), true);
            }
        }
    }
}
