package com.denanu.litematicaBuilder.builder;

import com.denanu.litematicaBuilder.LitematicaBuilderModClient;

import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.litematica.schematic.placement.SchematicPlacement;
import baritone.api.BaritoneAPI;

public class BuildManager {
	private static final BuildManager INSTANCE = new BuildManager();
	
	
	private LitematicaBuildProcess builder = new LitematicaBuildProcess();
	
	public static BuildManager getInstance() {
		return INSTANCE;
	}
	
	private void reset() {
	}
	
	public void stop() {
	}
	
	public void start() {
		LitematicaBuilderModClient.logger.info("started builder");
		SchematicPlacement placement = DataManager.getSchematicPlacementManager().getSelectedSchematicPlacement();
		if(placement == null) {
			this.stop();
			return;
		}
		this.reset();
		LitematicaBuilderModClient.logger.info(placement.toString());
		this.builder.setPlacement(placement);
		
	}
}
