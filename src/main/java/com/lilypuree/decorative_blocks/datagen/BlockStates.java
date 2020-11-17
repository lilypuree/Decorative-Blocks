package com.lilypuree.decorative_blocks.datagen;

import com.lilypuree.decorative_blocks.datagen.types.IWoodType;
import com.lilypuree.decorative_blocks.datagen.types.ModWoodTypes;
import com.lilypuree.decorative_blocks.datagen.types.WoodDecorativeBlockTypes;
import com.lilypuree.decorative_blocks.setup.Registration;
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

    public ModelFile palisadePostPart(IWoodType wood) {
        ModelBuilder<?> builder = createModel(wood, WoodDecorativeBlockTypes.PALISADE, "post");
        return withSideEndTextures(builder, wood + "_palisade");
    }

    public ModelFile palisadeSidePart(IWoodType wood) {
        ModelBuilder<?> builder = createModel(wood, WoodDecorativeBlockTypes.PALISADE, "side");
        return withSideEndTextures(builder, wood + "_palisade");
    }

    public ModelFile palisadeInventory(IWoodType wood) {
        ModelBuilder<?> builder = createModel(wood, WoodDecorativeBlockTypes.PALISADE, "inventory");
        return withSideEndTextures(builder, wood + "_palisade");
    }


    public void beamBlock(IWoodType wood) {
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

    public ModelFile beamModel(IWoodType wood, Direction.Axis axis) {
        ModelBuilder<?> builder = createModel(wood, WoodDecorativeBlockTypes.BEAM, axis.getName2());
        return withSideEndTextures(builder, wood + "_beam");
    }

    public void seatBlock(IWoodType wood) {
        seatInventory(wood);
        MultiPartBlockStateBuilder builder = getMultipartBuilder(Registration.getSeatBlock(wood));
        ModelFile seatTopModel = seatTopModel(wood);
        ModelFile seatPostModel = seatPostModel(wood);

        builder.part().modelFile(seatPostModel).addModel().condition(BlockStateProperties.ATTACHED, Boolean.TRUE).end()
                .part().modelFile(seatTopModel).addModel().condition(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).end()
                .part().modelFile(seatTopModel).rotationY(180).addModel().condition(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH).end()
                .part().modelFile(seatTopModel).rotationY(90).addModel().condition(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST).end()
                .part().modelFile(seatTopModel).rotationY(270).addModel().condition(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST).end();
    }

    public ModelFile seatTopModel(IWoodType wood) {
        ModelBuilder<?> builder = createModel(wood, WoodDecorativeBlockTypes.SEAT, "top");
        return withParticleTexture(builder, wood + "_seat");
    }

    public ModelFile seatPostModel(IWoodType wood) {
        ModelBuilder<?> builder = createModel(wood, WoodDecorativeBlockTypes.SEAT, "post");
        return withParticleTexture(builder, wood + "_seat");
    }

    public ModelFile seatInventory(IWoodType wood) {
        ModelBuilder<?> builder = createModel(wood, WoodDecorativeBlockTypes.SEAT, "inventory");
        return withParticleTexture(builder, wood + "_seat");
    }

    public void supportBlock(IWoodType wood) {
        ModelFile supportUpModel = supportBlockModel(wood);
        ModelFile supportDownModel = supporBlockDownModel(wood);

        horizontalBlock(Registration.getSupportBlock(wood), state ->
                state.get(BlockStateProperties.UP) ? supportUpModel : supportDownModel
        );
    }

    public ModelFile supportBlockModel(IWoodType wood) {
        ModelBuilder<?> builder = createModel(wood, WoodDecorativeBlockTypes.SUPPORT);
        return withParticleTexture(builder, wood + "_support");
    }

    public ModelFile supporBlockDownModel(IWoodType wood) {
        ModelBuilder<?> builder = createModel(wood, WoodDecorativeBlockTypes.SUPPORT, "down");
        return withParticleTexture(builder, wood + "_support");
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
        for (IWoodType wood : ModWoodTypes.allWoodTypes()) {
            beamBlock(wood);
            palisadeBlock(wood);
            seatBlock(wood);
            supportBlock(wood);
        }


        ModelBuilder<?> builder = models().getBuilder("bar_panel_bottom").parent(new ModelFile.UncheckedModelFile(modLoc("custom/bar_panel_bottom")));
        ModelFile barPanelBottomModel = withSideEndTextures(builder, "bar_panel");

        builder = models().getBuilder("bar_panel_top").parent(new ModelFile.UncheckedModelFile(modLoc("custom/bar_panel_top")));
        ModelFile barPanelTopModel = withSideEndTextures(builder, "bar_panel");

        builder = models().getBuilder("bar_panel_open").parent(new ModelFile.UncheckedModelFile(modLoc("custom/bar_panel_open")));
        ModelFile barPanelOpenModel = withSideEndTextures(builder, "bar_panel");

        trapdoorBlock(Registration.BAR_PANEL.get(), barPanelBottomModel, barPanelTopModel, barPanelOpenModel, true);

        ModelFile chainModel = models().getBuilder("chain").parent(new ModelFile.UncheckedModelFile(modLoc("custom/chain")))
                .texture("particle", modLoc("block/chain"))
                .texture("texture", modLoc("block/chain"));
//        axisBlock(Registration.CHAIN.get(), chainModel);
    }
}
