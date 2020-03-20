package com.lilypuree.decorative_blocks.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ClientProxy implements IProxy {

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    public static void spawnGoldParticles(World world, BlockPos pos, Random rand){
        PlayerEntity player = Minecraft.getInstance().player;
        if(player.getHeldItemMainhand().getItem() == Registration.GOLD_SPARKLES.get()){
            for(int i = 0; i< 1 + rand.nextInt(4); i++){
                float posX = pos.getX() + 0.5F + (float)rand.nextGaussian() * 0.3F;
                float posZ = pos.getZ() + 0.5F + (float)rand.nextGaussian() * 0.3F;
                world.addParticle(Registration.GOLD_PARTICLE.get(), (double)posX, (double)((float)pos.getY() + 0.1F), (double)posZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}