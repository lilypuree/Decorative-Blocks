package com.lilypuree.decorative_blocks.items;

import com.lilypuree.decorative_blocks.Config;
import com.lilypuree.decorative_blocks.setup.Registration;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GoldSparkles extends Item {
    public GoldSparkles(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (!world.isRemote()) {

            if (context.getPlayer().isSneaking()) {
                BlockPos usePos = context.getPos();
                int range = Config.GOLD_GATHER_RADIUS.get();
                BlockPos.getAllInBox(usePos.add(-range, -range, -range), usePos.add(range, range,range)).filter(pos->{
                   return world.getBlockState(pos).getBlock() == Registration.GOLD_SPRINKLES.get();
                }).forEach(pos ->{
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                });
            } else if (context.getFace() == Direction.UP) {
                BlockPos setPos = context.getPos().offset(context.getFace());
                world.setBlockState(setPos, Registration.GOLD_SPRINKLES.get().getDefaultState());
            }

        }
        return ActionResultType.SUCCESS;
    }
}
