package noc.mods.Item.custom;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class Resurrection_Item extends Item {

    public Resurrection_Item(Settings settings) {
        super(settings);

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(this::onEntityDeath);
    }

    private void onEntityDeath(ServerWorld world, Entity entity, LivingEntity killedEntity) {
        if (killedEntity.getType() == EntityType.VILLAGER && !world.isClient) {
            for (ServerPlayerEntity player : world.getPlayers()) {
                if (isPlayerHoldingItem(player) && isWithinRange(killedEntity, player)) {
                    handleVillagerDeathNearby(player);
                }
            }
        }
    }

    private boolean isPlayerHoldingItem(PlayerEntity player) {
        ItemStack offHandStack = player.getStackInHand(Hand.OFF_HAND);
        return offHandStack.getItem() instanceof Resurrection_Item;
    }

    private boolean isWithinRange(Entity entity, PlayerEntity player) {
        Vec3d entityPos = entity.getPos();
        Vec3d playerPos = player.getPos();
        return entityPos.isInRange(playerPos, 15);
    }

    private void handleVillagerDeathNearby(PlayerEntity player) {
        player.sendMessage(Text.literal("A villager was killed nearby!"), false);
    }
}
