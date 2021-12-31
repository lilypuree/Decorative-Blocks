package lilypuree.decorative_blocks.events;

import com.mojang.blaze3d.systems.RenderSystem;
import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import net.minecraft.client.Camera;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FogHandler {
    private static Tag.Named<Fluid> fluidTag = FluidTags.bind(new ResourceLocation(Constants.MODID, "thatch").toString());

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onFogDensity(EntityViewRenderEvent.FogDensity event) {
        Camera camera = event.getCamera();
        Level world = camera.getEntity().getCommandSenderWorld();
        BlockPos pos = camera.getBlockPosition();
        FluidState state = world.getFluidState(pos);

        if (isEntityInHay(state)) {
//            RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
            event.setDensity(2.0F);
            event.setCanceled(true);
        }
//        else if (actualState.is(FluidTags.LAVA)) {
//            event.setCanceled(false);
//            return;
//        } else if (actualState.isEmpty()) {
//            event.setDensity(0.00001F);
//        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onFogColor(EntityViewRenderEvent.FogColors event) {

        Level world = event.getCamera().getEntity().getCommandSenderWorld();
        BlockPos pos = event.getCamera().getBlockPosition();
        FluidState state = world.getFluidState(pos);

        if (isEntityInHay(state)) {
            int color = ((ThatchFluid) state.getType()).getReferenceHolder().getColor();
            event.setRed((float) (color >> 16 & 0xFF) / 0xFF);
            event.setGreen((float) ((color >> 8) & 0xFF) / 0xFF);
            event.setBlue((float) (color & 0xFF) / 0xFF);
        }
    }

    private static boolean isEntityInHay(FluidState fluidState) {
        return fluidTag.contains(fluidState.getType());
    }


}
