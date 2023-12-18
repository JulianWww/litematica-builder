package com.denanu.litematicaBuilder.builder.phases;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import com.denanu.litematicaBuilder.LitematicaBuilderModClient;
import com.denanu.litematicaBuilder.utils.blocks.BlockTypes;
import com.denanu.litematicaBuilder.utils.math.BetterBlockBox;

import baritone.api.pathing.goals.GoalBlock;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import fi.dy.masa.litematica.schematic.placement.SchematicPlacement;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.World;

public class RemoveTreeBuildPhase extends BuildPhase {
	private List<BlockPos> poses;
	private BlockPos target;
	
	public RemoveTreeBuildPhase() {
		poses = new LinkedList<BlockPos>();
	}
	
	@Override
	@Nullable
	public PathingCommand onTick(boolean calcFailed, boolean isSafeToCancel, SchematicPlacement placement) {
		GoalBlock goal = new GoalBlock(this.target);
		return new PathingCommand(goal, PathingCommandType.SET_GOAL_AND_PATH);
	}

	@Override
	public void gainControl(SchematicPlacement placement, BetterBlockBox boundingBox) {
		World world = MinecraftClient.getInstance().player.getWorld();
		boundingBox.getColumns().forEach(c -> {
			Mutable tree = new Mutable(0, -128, 0);
			c.find(
				pos -> tree.set(pos), 
				pos -> {
					return BlockTypes.isLog(world.getBlockState(pos));
				}, 
				pos -> {
					return tree.getY() != -128 && tree != pos;
				});
			
			if (tree.getY() != 128) {
				world.addParticle(ParticleTypes.HAPPY_VILLAGER, tree.getX(), tree.getY(), tree.getZ(), 0, 0, 0);
				poses.add(tree);
				this.target = tree;
				//MinecraftClient.getInstance().player.getServer().getOverworld().setBlockState(tree, Blocks.DIAMOND_BLOCK.getDefaultState());
			}
		});	
		
	}

	@Override
	public void looseControl() {
		this.poses.clear();
		
	}

	@Override
	public boolean active() {
		// TODO Auto-generated method stub
		return false;
	}
}
