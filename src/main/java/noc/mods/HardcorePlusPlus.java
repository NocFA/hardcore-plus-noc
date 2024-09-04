package noc.mods;

import net.fabricmc.api.ModInitializer;
import noc.mods.Item.ModItems;
import noc.mods.events.GhostParticleEffectHandlerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HardcorePlusPlus implements ModInitializer {
	public static final String MOD_ID = "hardcoreplusplus";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		GhostParticleEffectHandlerClient.register();
	}
}
