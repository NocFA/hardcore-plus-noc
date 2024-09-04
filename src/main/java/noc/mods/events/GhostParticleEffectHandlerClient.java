package noc.mods.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import noc.mods.HardcorePlusPlus;
import org.joml.Vector3f;
import net.minecraft.sound.SoundEvents;

@Environment(EnvType.CLIENT)
public class GhostParticleEffectHandlerClient {

    public static void register() {
        HardcorePlusPlus.LOGGER.info("Registering client-side ghost particle event for " + HardcorePlusPlus.MOD_ID);
        ClientTickEvents.END_CLIENT_TICK.register(GhostParticleEffectHandlerClient::onClientTick);
    }

    private static long lastSoundTime = 0;

    private static void onClientTick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player != null) {
            if (player.isSpectator()) {
                spawnGhostParticles(player);
            }

            if (player.isSpectator() && player.getVelocity().lengthSquared() > 0.01) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastSoundTime >= 1000) {
                    player.playSound(
                            SoundEvents.ENTITY_WARDEN_AMBIENT,
                            0.2F,
                            0.6F + player.getWorld().random.nextFloat() * 0.2F
                    );
                    lastSoundTime = currentTime;
                }
            }
        }
    }

    private static void spawnGhostParticles(ClientPlayerEntity player) {
        Vec3d pos = player.getPos();
        double radius = 1.0;
        int particleCount = 15;

        for (int i = 0; i < particleCount / 3; i++) {
            double angle = Math.toRadians((360.0 / ((double) particleCount / 3)) * i);
            double offsetX = Math.cos(angle) * (radius - 0.10) + (player.getWorld().random.nextDouble() - 0.5) * 0.7;
            double offsetZ = Math.sin(angle) * (radius - 0.10) + (player.getWorld().random.nextDouble() - 0.5) * 0.7;
            double offsetY = (player.getWorld().random.nextDouble()) * 0.5;
            player.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ, 0, 0.005, 0);
        }

        for (int i = 0; i < 4; i++) {
            float r = 0.4f + (player.getWorld().random.nextFloat() * 0.1f);
            float g = 0.5f + (player.getWorld().random.nextFloat() * 0.1f);
            float b = 0.8f;
            DustParticleEffect fadingDust = new DustParticleEffect(new Vector3f(r, g, b), 0.5F);
            double offsetX = Math.cos(i) * (radius / 2) + (player.getWorld().random.nextDouble() - 0.5) * 0.7;
            double offsetY = player.getWorld().random.nextDouble() * 1.2;
            double offsetZ = Math.sin(i) * (radius / 2) + (player.getWorld().random.nextDouble() - 0.5) * 0.7;
            player.getWorld().addParticle(fadingDust, pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ, 0, 0.005, 0);
        }
    }
}
