package com.lilypuree.decorative_blocks.datagen;

import com.lilypuree.decorative_blocks.DecorativeBlocks;
import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import com.lilypuree.decorative_blocks.datagen.types.ModWoodTypes;
import com.lilypuree.decorative_blocks.datagen.types.WoodDecorativeBlockTypes;
import com.lilypuree.decorative_blocks.core.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {

    public BlockStates(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    private static String name(Block block) {
        return block.getRegistryName().getPath();
    }


    private ModelBuilder<?> createModel(IWoodType wood, WoodDecorativeBlockTypes type) {
        return createModel(wood, type, null);
    }

    private ModelBuilder<?> createModel(IWoodType wood, WoodDecorativeBlockTypes type, String suffix) {
        String name = type + ((suffix == null) ? "" : "_" + suffix);
        return models().getBuilder(wood + "_" + name).parent(new ModelFile.UncheckedModelFile(modLoc("custom/" + name)));
    }

    private ModelBuilder<?> withParticleTexture(ModelBuilder<?> model, String name) {
        ResourceLocation texture = modLoc("block/" + name);
        return model.texture("particle", texture).texture("texture", texture);
    }

    private ModelBuilder<?> withSideEndTextures(ModelBuilder<?> model, String name) {
        ResourceLocation side = modLoc("block/" + name + "_side");
        ResourceLocation end = modLoc("block/" + name + "_end");
        return model.texture("particle", side).texture("side", side).texture("end", end);
    }


    @Override
    protected void registerStatesAndModels() {
        BlockStateGenerationHelper generationHelper = new BlockStateGenerationHelper(DecorativeBlocks.MODID, this);
        for (IWoodType wood : ModWoodTypes.allWoodTypes()) {
            generationHelper.beamBlock(Registration.getBeamBlock(wood));
            generationHelper.palisadeBlock(Registration.getPalisadeBlock(wood));
            generationHelper.seatBlock(Registration.getSeatBlock(wood));
            generationHelper.supportBlock(Registration.getSupportBlock(wood));
        }


        ModelBuilder<?> builder = models().getBuilder("bar_panel_bottom").parent(new ModelFile.UncheckedModelFile(modLoc("custom/bar_panel_bottom")));
        ModelFile barPanelBottomModel = withSideEndTextures(builder, "bar_panel");

        builder = models().getBuilder("bar_panel_top").parent(new ModelFile.UncheckedModelFile(modLoc("custom/bar_panel_top")));
        ModelFile barPanelTopModel = withSideEndTextures(builder, "bar_panel");

        builder = models().getBuilder("bar_panel_open").parent(new ModelFile.UncheckedModelFile(modLoc("custom/bar_panel_open")));
        ModelFile barPanelOpenModel = withSideEndTextures(builder, "bar_panel");

        trapdoorBlock(Registration.BAR_PANEL.get(), barPanelBottomModel, barPanelTopModel, barPanelOpenModel, true);

//        ModelFile chainModel = models().getBuilder("chain").parent(new ModelFile.UncheckedModelFile(modLoc("custom/chain")))
//                .texture("particle", modLoc("block/chain"))
//                .texture("texture", modLoc("block/chain"));
//        axisBlock(Registration.CHAIN.get(), chainModel);
    }
}
