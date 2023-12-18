package com.denanu.litematicaBuilder.utils.blocks;




import com.denanu.litematicaBuilder.LitematicaBuilderModClient;

import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;

public class BlockTypes {
	public static boolean isLog(BlockState state) {
		LitematicaBuilderModClient.logger.info(state);
		return state.streamTags().anyMatch(tag -> { return tag.equals(BlockTags.LOGS); });
	}
}
