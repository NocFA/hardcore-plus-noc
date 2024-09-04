package noc.mods.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import noc.mods.Item.ModItems;

public class ResurrectionHandler {

    public static void registerEvents() {
        ServerTickEvents.END_WORLD_TICK.register(ResurrectionHandler::checkForThrownItem);
    }

    private static void checkForThrownItem(ServerWorld world) {
        world.getEntitiesByType(EntityType.ITEM, itemEntity -> itemEntity.getStack().getItem() == ModItems.SOUL_INFUSED_RESURRECTION_ITEM)
                .forEach(ResurrectionHandler::handleThrownSoulCrystal);
    }

    private static void handleThrownSoulCrystal(ItemEntity itemEntity) {
        ServerPlayerEntity closestSpectator = getClosestSpectatorPlayer(itemEntity);
        if (closestSpectator != null) {
            System.out.println("Found a spectator player within range: " + closestSpectator.getName().getString());
            resurrectPlayer(itemEntity, closestSpectator);
        }
    }

    private static ServerPlayerEntity getClosestSpectatorPlayer(ItemEntity itemEntity) {
        double closestDistance = 9.0;
        ServerPlayerEntity closestPlayer = null;
        Vec3d itemPos = itemEntity.getPos();

        for (PlayerEntity player : itemEntity.getWorld().getPlayers()) {
            if (player instanceof ServerPlayerEntity serverPlayer && serverPlayer.interactionManager.getGameMode() == GameMode.SPECTATOR) {
                double distance = serverPlayer.squaredDistanceTo(itemPos);
                if (distance <= closestDistance) {
                    closestDistance = distance;
                    closestPlayer = serverPlayer;
                }
            }
        }

        if (closestPlayer != null) {
            System.out.println("Closest spectator player found: " + closestPlayer.getName().getString() + " at distance: " + Math.sqrt(closestDistance));
        }

        return closestPlayer;
    }

    private static void resurrectPlayer(ItemEntity itemEntity, ServerPlayerEntity player) {
        System.out.println("Initiating resurrection for player: " + player.getName().getString() + " at item location: " + itemEntity.getPos());

        ServerWorld world = (ServerWorld) itemEntity.getWorld();
        Vec3d pos = itemEntity.getPos();

        world.spawnParticles(ParticleTypes.EXPLOSION, pos.x, pos.y, pos.z, 10, 1, 1, 1, 0.1);
        world.spawnParticles(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 5, 0, 0, 0, 0.5);
        world.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y, pos.z, 20, 0.5, 0.5, 0.5, 0.1);

        world.playSound(null, pos.x, pos.y, pos.z, SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.WEATHER, 3.0F, 1.0F);

        player.changeGameMode(GameMode.SURVIVAL);
        player.setHealth(10.0F);
        player.clearStatusEffects();
        System.out.println("Player " + player.getName().getString() + " has been resurrected to survival mode.");

        itemEntity.remove(RemovalReason.DISCARDED);
    }
}
