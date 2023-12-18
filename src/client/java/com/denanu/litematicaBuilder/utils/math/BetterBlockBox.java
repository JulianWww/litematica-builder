package com.denanu.litematicaBuilder.utils.math;


import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.google.common.collect.AbstractIterator;

import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class BetterBlockBox extends BlockBox {
	public BetterBlockBox(int x1, int y1, int z1, int x2, int y2, int z2) {
		super(x1, y1, z1, x2, y2, z2);
    }

    /**
     * Creates a box that only contains the given block position.
     */
    public BetterBlockBox(BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }

    /**
     * Creates a box of the given positions as corners.
     */
    public BetterBlockBox(BlockPos pos1, BlockPos pos2) {
        this(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
    }
    
    public BetterBlockBox getLayer(int layerid) {
    	return new BetterBlockBox(this.getMaxX(), this.getMinY() + layerid, this.getMaxZ(), this.getMinX(), this.getMinY() + layerid, this.getMinZ());
    }
    
    public int getLayers() {
    	return this.getMaxY() - this.getMinY() + 1;
    }
    
    public Iterable<BetterBlockBox> getColumns() {
    	final var sx = this.getMaxX() - this.getMinX() + 1;
		final var sz = this.getMaxZ() - this.getMinZ() + 1;
		final var count = sx * sz;
    	return () -> new AbstractIterator<>() {
    		int idx=0;
    		
			@Override
			protected BetterBlockBox computeNext() {
				if (idx == count ) {
					return this.endOfData();
				}
				final int dx = idx % sx;
				final int dz = idx / sx;
				idx ++;
				return new BetterBlockBox(
						BetterBlockBox.this.getMinX() + dx, BetterBlockBox.this.getMinY(), BetterBlockBox.this.getMinZ() + dz, 
						BetterBlockBox.this.getMinX() + dx, BetterBlockBox.this.getMaxY(), BetterBlockBox.this.getMinZ() + dz
					);
			}
		};
    }
    
    public Iterable<BlockPos> iterate(final boolean inverseX, final boolean inverseY, final boolean inverseZ) {
		final var i = this.getMaxX() - this.getMinX() + 1;
		final var j = this.getMaxY() - this.getMinY() + 1;
		final var k = this.getMaxZ() - this.getMinZ() + 1;
		final var l = i * j * k;

		final var xBase = inverseX ? this.getMaxX() : this.getMinX();
		final var yBase = inverseY ? this.getMaxY() : this.getMinY();
		final var zBase = inverseZ ? this.getMaxZ() : this.getMinZ();

		return () -> new AbstractIterator<>() {
			private int index;

			@Override
			protected BlockPos computeNext() {
				if (this.index == l) {
					return this.endOfData();
				}
				final var dy = this.index % j;
				final var j2 = this.index / j;
				final var dx = j2 % i;
				final var dz = j2 / i;
				++this.index;
				return new BlockPos(inverseX ? xBase - dx : xBase + dx, inverseY ? yBase - dy : yBase + dy,
						inverseZ ? zBase - dz : zBase + dz);
			}
		};
	}

    public void find(Consumer<BlockPos> consume, Predicate<BlockPos> pred, Predicate<BlockPos> kill) {
    	for (BlockPos pos : this.iterate(false, false, false)) {
    		if (pred.test(pos)) {
    			consume.accept(pos);
    		}
    		if (kill.test(pos)) {
    			break;
    		}
    	}
    };
}
