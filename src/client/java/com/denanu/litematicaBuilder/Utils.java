package com.denanu.litematicaBuilder;

import com.denanu.litematicaBuilder.utils.math.BetterBlockBox;
import com.ibm.icu.impl.Pair;

import fi.dy.masa.litematica.schematic.placement.SchematicPlacement;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;

public class Utils {
	public static Pair<Integer, Integer> getRotationMirrorOffset(BlockRotation rotation, BlockMirror mirror, int dx, int dy) {		
		switch (mirror) {
		case FRONT_BACK:
			dx = -dx;
			break;
		case LEFT_RIGHT:
			dy = -dy;
			break;
		case NONE:
			break;		
		}
		
		int tmp;
		switch (rotation) {
		case CLOCKWISE_180:
			dy = -dy;
			dx = -dx;
			break;
		case CLOCKWISE_90:
			tmp = dx;
			dx = -dy;
			dy = tmp;
			break;
		case COUNTERCLOCKWISE_90:
			tmp = -dx;
			dx = dy;
			dy = tmp;
			break;
		case NONE:
			break;
		}		
		LitematicaBuilderModClient.logger.info(dx + "; " + dy);
		return Pair.of(dx, dy);
	}
	
	public static BetterBlockBox getPlacementBoundingBox(SchematicPlacement placement) {
		Vec3i size = placement.getSchematic().getTotalSize();
		Pair<Integer, Integer> directions = getRotationMirrorOffset(placement.getRotation(), placement.getMirror(), size.getX() - 1, size.getZ() - 1);
		Vec3i other_offset = new Vec3i(directions.first, size.getY() - 1, directions.second);
		return new BetterBlockBox(placement.getOrigin(), placement.getOrigin().add(other_offset));
	}
}
