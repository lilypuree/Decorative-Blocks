package com.lilypuree.decorative_blocks.setup;

import com.lilypuree.decorative_blocks.DecorativeBlocks;
import com.lilypuree.decorative_blocks.fluid.ThatchFluid;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
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
    private static ITag.INamedTag<Fluid> fluidTag = FluidTags.makeWrapperTag(new ResourceLocation(DecorativeBlocks.MODID, "thatch").toString());

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onFogDensity(EntityViewRenderEvent.FogDensity event) {
        World world = event.getInfo().getRenderViewEntity().getEntityWorld();
        BlockPos pos = event.getInfo().getBlockPos();
        FluidState state = world.getFluidState(pos);

        FluidState actualState = event.getInfo().getFluidState();

        if (isEntityInHay(state)) {
            RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
            event.setDensity(2.0F);
            event.setCanceled(true);
        } else if (actualState.isTagged(FluidTags.LAVA)) {
            event.setCanceled(false);
            return;
        } else if (actualState.isEmpty()) {
            event.setDensity(0.00001F);
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onFogColor(EntityViewRenderEvent.FogColors event) {

        World world = event.getInfo().getRenderViewEntity().getEntityWorld();
        BlockPos pos = event.getInfo().getBlockPos();
        FluidState state = world.getFluidState(pos);

        FluidState actualState = event.getInfo().getFluidState();

        if (isEntityInHay(state)) {

            int color = ((ThatchFluid) state.getFluid()).getReferenceHolder().getColor();
            event.setRed((float) (color >> 16 & 0xFF) / 0xFF);
            event.setGreen((float) ((color >> 8) & 0xFF) / 0xFF);
            event.setBlue((float) (color & 0xFF) / 0xFF);
        }
    }

    private static boolean isEntityInHay(FluidState fluidState) {
        return fluidTag.contains(fluidState.getFluid());
    }


}
