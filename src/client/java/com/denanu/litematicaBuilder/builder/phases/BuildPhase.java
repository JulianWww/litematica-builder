package com.denanu.litematicaBuilder.builder.phases;

import javax.annotation.Nullable;

import com.denanu.litematicaBuilder.utils.math.BetterBlockBox;

import baritone.api.process.PathingCommand;
import fi.dy.masa.litematica.schematic.placement.SchematicPlacement;

public abstract class BuildPhase {
	public abstract void gainControl(SchematicPlacement placement, BetterBlockBox boundingBox);
	public abstract void looseControl();
	public abstract boolean active();
	@Nullable
	public abstract PathingCommand onTick(boolean calcFailed, boolean isSafeToCancel, SchematicPlacement placement);
}
