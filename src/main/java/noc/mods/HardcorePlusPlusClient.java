package noc.mods;

import net.fabricmc.api.ClientModInitializer;
import noc.mods.events.GhostParticleEffectHandlerClient;

public class HardcorePlusPlusClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GhostParticleEffectHandlerClient.register();
    }
}
