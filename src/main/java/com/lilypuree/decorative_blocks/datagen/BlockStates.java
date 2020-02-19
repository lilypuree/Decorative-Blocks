package com.lilypuree.decorative_blocks.datagen;

import biomesoplenty.core.BiomesOPlenty;
import com.lilypuree.decorative_blocks.DecorativeBlocks;
import com.lilypuree.decorative_blocks.datagen.types.BOPWoodTypes;
import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import com.lilypuree.decorative_blocks.datagen.types.WoodTypes;
import com.lilypuree.decorative_blocks.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class BlockStates extends BlockStateProvider {

    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, DecorativeBlocks.MODID, exFileHelper);
    }


    private String name(Block block) {
        return block.getRegistryName().getPath();
    }

    public void palisadeBlock(IWoodType wood) {
        palisadeInventory(wood);
        MultiPartBlockStateBuilder builder = getMultipartBuilder(Registration.getPalisadeBlock(wood));
        ModelFile postModel = palisadePostPart(wood);
        ModelFile sideModel = palisadeSidePart(wood);

        builder.part().modelFile(postModel).addModel().end()
                .part().modelFile(sideModel).uvLock(true).addModel().condition(BlockStateProperties.NORTH, Boolean.TRUE).end()
                .part().modelFile(sideModel).uvLock(true).rotationY(180).addModel().condition(BlockStateProperties.SOUTH, Boolean.TRUE).end()
                .part().modelFile(sideModel).uvLock(true).rotationY(90).addModel().condition(BlockStateProperties.EAST, Boolean.TRUE).end()
                .part().modelFile(sideModel).uvLock(true).rotationY(270).addModel().condition(BlockStateProperties.WEST, Boolean.TRUE).end();
    }

    public void beamBlock(IWoodType wood){
        VariantBlockStateBuilder builder = getVariantBuilder(Registration.getBeamBlock(wood));
        ModelFile beamXModel = beamModel(wood, Direction.Axis.X);
        ModelFile beamYModel = beamModel(wood, Direction.Axis.Y);
        ModelFile beamZModel = beamModel(wood, Direction.Axis.Z);

        builder.partialState().with(BlockStateProperties.AXIS, Direction.Axis.X)
                .modelForState().modelFile(beamXModel).addModel();
        builder.partialState().with(BlockStateProperties.AXIS, Direction.Axis.Y)
                .modelForState().modelFile(beamYModel).addModel();
        builder.partialState().with(BlockStateProperties.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(beamZModel).addModel();
    }

    public ModelFile beamModel(IWoodType wood, Direction.Axis axis){
        ModelBuilder<?> builder = getBuilder(wood+"_beam_"+axis).parent(new ModelFile.UncheckedModelFile(modLoc("custom/beam_"+axis)));
        return withSideEndTextures(builder, wood+"_beam");
    }

    public ModelFile palisadePostPart(IWoodType wood) {
        ModelBuilder<?> builder = getBuilder(wood + "_palisade_post").parent(new ModelFile.UncheckedModelFile(modLoc("custom/palisade_post")));
        return withSideEndTextures(builder, wood + "_palisade");
    }

    public ModelFile palisadeSidePart(IWoodType wood) {
        ModelBuilder<?> builder = getBuilder(wood + "_palisade_side").parent(new ModelFile.UncheckedModelFile(modLoc("custom/palisade_side")));
        return withSideEndTextures(builder, wood + "_palisade");
    }

    public ModelFile palisadeInventory(IWoodType wood){
        ModelBuilder<?> builder = getBuilder(wood + "_palisade_inventory").parent(new ModelFile.UncheckedModelFile(modLoc("custom/palisade_inventory")));
        return withSideEndTextures(builder, wood + "_palisade");
    }

    public ModelFile seatBlockModel(IWoodType wood) {
        return getBuilder(wood + "_seat").parent(new ModelFile.UncheckedModelFile(modLoc("custom/seat")))
                .texture("particle", modLoc("block/" + wood+"_seat"))

                .texture("texture",  modLoc("block/" +wood + "_seat"));
    }

    public ModelFile supportBlockModel(IWoodType wood){
        return getBuilder(wood+"_support").parent(new ModelFile.UncheckedModelFile(modLoc("custom/support")))
                .texture("particle", modLoc("block/" + wood+"_support"))

                .texture("texture", modLoc("block/" + wood+"_support"));
    }

    private ModelBuilder<?> withSideEndTextures(ModelBuilder<?> model, String name){
        ResourceLocation side = modLoc( "block/" + name + "_side");
        ResourceLocation end = modLoc( "block/" +name + "_end");
        return model.texture("particle", side).texture("side", side).texture("end",end);
    }


    @Override
    protected void registerStatesAndModels() {
        for (IWoodType wood : Registration.modWoodTypes){
            beamBlock(wood);
            palisadeBlock(wood);
            horizontalBlock(Registration.getSeatBlock(wood), seatBlockModel(wood));
            horizontalBlock(Registration.getSupportBlock(wood), supportBlockModel(wood));
        }


        ModelBuilder<?> builder = getBuilder("bar_panel_bottom").parent( new ModelFile.UncheckedModelFile(modLoc("custom/bar_panel_bottom")));
        ModelFile barPanelBottomModel = withSideEndTextures(builder, "bar_panel");

        builder = getBuilder("bar_panel_top").parent( new ModelFile.UncheckedModelFile(modLoc("custom/bar_panel_top")));
        ModelFile barPanelTopModel = withSideEndTextures(builder, "bar_panel");

        builder = getBuilder("bar_panel_open").parent( new ModelFile.UncheckedModelFile(modLoc("custom/bar_panel_open")));
        ModelFile barPanelOpenModel = withSideEndTextures(builder, "bar_panel");

        trapdoorBlock(Registration.BAR_PANEL.get(),barPanelBottomModel, barPanelTopModel, barPanelOpenModel, true);

        ModelFile chainModel = getBuilder("chain").parent(new ModelFile.UncheckedModelFile(modLoc("custom/chain")))
                .texture("particle", modLoc("block/chain"))
                .texture("texture", modLoc("block/chain"));
        axisBlock(Registration.CHAIN.get(), chainModel);
    }
}
