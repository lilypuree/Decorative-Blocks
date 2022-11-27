package lilypuree.decorative_blocks;


import lilypuree.decorative_blocks.core.*;
import lilypuree.decorative_blocks.core.setup.ModSetup;
import lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;

public class DecorativeBlocks implements ModInitializer {

    @Override
    public void onInitialize() {
        DBTags.init();
        Registration.init();
        DBBlocks.init();
        DBItems.init();
        FuelRegistration.init();
        ModSetup.init();
        Callbacks.initCallbacks();
    }
}
