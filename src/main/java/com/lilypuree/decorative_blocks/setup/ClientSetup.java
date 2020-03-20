package com.lilypuree.decorative_blocks.setup;

import com.lilypuree.decorative_blocks.DecorativeBlocks;
import com.lilypuree.decorative_blocks.client.particles.GoldParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DecorativeBlocks.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public static void onParticleFactoryRegister(final ParticleFactoryRegisterEvent event){
        Minecraft.getInstance().particles.registerFactory(Registration.GOLD_PARTICLE.get(), GoldParticle.GoldFactory::new);
    }

}
