package com.denanu.litematicaBuilder.builder;

import javax.annotation.Nullable;

import com.denanu.litematicaBuilder.LitematicaBuilderModClient;
import com.denanu.litematicaBuilder.Utils;
import com.denanu.litematicaBuilder.builder.phases.BuildPhase;
import com.denanu.litematicaBuilder.builder.phases.RemoveTreeBuildPhase;
import com.denanu.litematicaBuilder.utils.math.BetterBlockBox;

import baritone.api.process.IBaritoneProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import fi.dy.masa.litematica.schematic.placement.SchematicPlacement;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public class LitematicaBuildProcess implements IBaritoneProcess {
	@Nullable
	SchematicPlacement placement;
	@Nullable
	BetterBlockBox boundingBox;
	@Nullable
	BuildPhase phase;

	@Override
	public String displayName0() {
		return "Litematica Build Process";
	}

	@Override
	public boolean isActive() {
		return this.placement != null;
	}

	@Override
	public boolean isTemporary() {
		return false;
	}

	@Override
	public void onLostControl() {	
		this.placement = null;
	}

	@Override
	public PathingCommand onTick(boolean calcFailed, boolean isSafeToCancel) {
		return new PathingCommand(null, PathingCommandType.SET_GOAL_AND_PATH);
	}
	
	private void switchPhases(BuildPhase nextPhase) {
		if (this.phase != null) {
			this.phase.looseControl();
		}
		if (nextPhase != null) {
			nextPhase.gainControl(this.placement, this.boundingBox);
		}
		this.phase = nextPhase;
	}

	public void setPlacement(SchematicPlacement placement) {
		this.placement = placement;
		this.boundingBox = Utils.getPlacementBoundingBox(placement);
		LitematicaBuilderModClient.logger.info("set placement");
		LitematicaBuilderModClient.logger.info(this.boundingBox);
		this.switchPhases(new RemoveTreeBuildPhase());
	}

}
