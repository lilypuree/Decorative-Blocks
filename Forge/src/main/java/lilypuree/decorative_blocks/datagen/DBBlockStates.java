package lilypuree.decorative_blocks.datagen;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.core.DBBlocks;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.ModWoodTypes;
import lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DBBlockStates extends BlockStateProvider {

    public DBBlockStates(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
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
        BlockStateGenerationHelper generationHelper = new BlockStateGenerationHelper(Constants.MODID, this);
        for (IWoodType wood : VanillaWoodTypes.values()) {
            generationHelper.beamBlock(DBBlocks.BEAMS.get(wood).get());
            generationHelper.palisadeBlock(DBBlocks.PALISADES.get(wood).get());
            generationHelper.seatBlock(DBBlocks.SEATS.get(wood).get());
            generationHelper.supportBlock(DBBlocks.SUPPORTS.get(wood).get());
        }


        ModelBuilder<?> builder = models().getBuilder("bar_panel_bottom").parent(new ModelFile.UncheckedModelFile(modLoc("custom/bar_panel_bottom")));
        ModelFile barPanelBottomModel = withSideEndTextures(builder, "bar_panel");

        builder = models().getBuilder("bar_panel_top").parent(new ModelFile.UncheckedModelFile(modLoc("custom/bar_panel_top")));
        ModelFile barPanelTopModel = withSideEndTextures(builder, "bar_panel");

        builder = models().getBuilder("bar_panel_open").parent(new ModelFile.UncheckedModelFile(modLoc("custom/bar_panel_open")));
        ModelFile barPanelOpenModel = withSideEndTextures(builder, "bar_panel");

        trapdoorBlock((TrapDoorBlock) DBBlocks.BAR_PANEL.get(), barPanelBottomModel, barPanelTopModel, barPanelOpenModel, true);

//        ModelFile chainModel = models().getBuilder("chain").parent(new ModelFile.UncheckedModelFile(modLoc("custom/chain")))
//                .texture("particle", modLoc("block/chain"))
//                .texture("texture", modLoc("block/chain"));
//        axisBlock(Registration.CHAIN.get(), chainModel);
    }
}
