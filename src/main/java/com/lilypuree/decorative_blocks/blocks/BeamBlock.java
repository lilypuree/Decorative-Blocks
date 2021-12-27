package com.lilypuree.decorative_blocks.blocks;

import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;

public class BeamBlock extends RotatedPillarBlock implements IWoodenBlock {

    private IWoodType woodType;

    public BeamBlock(Block.Properties properties, IWoodType woodType) {
        super(properties);
        this.woodType = woodType;
    }

    @Override
    public IWoodType getWoodType() {
        return woodType;
    }
}
