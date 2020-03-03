package com.lilypuree.decorative_blocks.setup;

import com.lilypuree.decorative_blocks.DecorativeBlocks;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DecorativeBlocks.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FogHandler {

    @SubscribeEvent(priority =  EventPriority.NORMAL)
    public static void onFogDensity(EntityViewRenderEvent.FogDensity event)
    {
        World world =  event.getInfo().getRenderViewEntity().getEntityWorld();
        BlockPos pos = event.getInfo().getBlockPos();
        IFluidState state = world.getFluidState(pos);

        IFluidState actualState = event.getInfo().getFluidState();

        if(isEntityInHay(state)){
            RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
            event.setDensity(2.0F);
            event.setCanceled(true);
        }else if(actualState.isTagged(FluidTags.LAVA)){
            event.setCanceled(false);
            return;
        }
        else if(actualState.isEmpty()) {
            event.setDensity(0.00001F);
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onFogColor(EntityViewRenderEvent.FogColors event){

        World world =  event.getInfo().getRenderViewEntity().getEntityWorld();
        BlockPos pos = event.getInfo().getBlockPos();
        IFluidState state = world.getFluidState(pos);

        IFluidState actualState = event.getInfo().getFluidState();

        if(isEntityInHay(state)){

            event.setRed((float)0xAC/0xFF);
            event.setGreen((float)0x8D/0xFF);
            event.setBlue((float)0x08/0xFF);
        }
    }

    private static boolean isEntityInHay(IFluidState fluidState){
        Tag<Fluid> fluidTag = FluidTags.getCollection().get(new ResourceLocation(DecorativeBlocks.MODID, "thatch"));
        if(fluidTag == null){
            System.out.println("wrong");
        }
        else if(fluidState.isTagged(fluidTag)){
            return true;
        }
        return false;
    }


}
