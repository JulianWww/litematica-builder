package com.denanu.litematicaBuilder;

import org.apache.logging.log4j.Logger;

import com.denanu.litematicaBuilder.builder.LitematicaBuildProcess;

import net.fabricmc.api.ClientModInitializer;
import baritone.api.BaritoneAPI;

public class LitematicaBuilderModClient implements ClientModInitializer {
	public static final String MOD_ID = "litematica-builder";
	public static final Logger logger = LogManager.getLogger(MOD_ID);
	
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BaritoneAPI.getProvider().getPrimaryBaritone().getPathingControlManager().registerProcess(new LitematicaBuildProcess());
	}
}