package com.lilypuree.decorative_blocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class RockyDirtBlock extends Block {
    public RockyDirtBlock(){
        super(Block.Properties.create(Material.EARTH, MaterialColor.STONE).hardnessAndResistance(1.0F, 6.0F).sound(SoundType.GROUND));
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return plantable.getPlantType(world, pos) != PlantType.CROP;
    }
}
